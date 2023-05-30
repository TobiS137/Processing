package rna.tweens;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map.Entry;
import processing.core.PVector;

public class Tween {
	private Tweenable obj;
	private EasingStyles style;
	private EasingModes dir;
	private HashMap<String, Float> floatData;
	private HashMap<String, PVector> vectorData;
	private HashMap<String, Float> startingFloatData;
	private HashMap<String, PVector> startingVectorData;
	

	private int endFrame = -1;
	private float time;
	private int frameTime = 0;
	private int frameRate = 60;

	private boolean animating = false;

	protected Tween(Tweenable obj, EasingStyles style, EasingModes dir, HashMap<String, Float> floatData,
			HashMap<String, PVector> vectorData, float time) {
		this.obj = obj;
		this.style = style;
		this.dir = dir;
		this.floatData = floatData;
		this.vectorData = vectorData;
		this.time = time;
	}

	protected boolean core() {
		if (animating) {
			if (frameTime >= endFrame) {
				animEnded();
				TweenService.animationEnded(this);
				return true;
			} else {
				frameTime++;
				for (Entry<String, Float> entry : floatData.entrySet()) {
					Field field = TweenService.getField(obj.getClass(), entry.getKey());
					field.setAccessible(true);
					try {
						if (field.getType().getTypeName() == "int") {
							field.setInt(obj, (int)(startingFloatData.get(entry.getKey()) + ((entry.getValue() - startingFloatData.get(entry.getKey())) * style.ease(dir, map(frameTime, 0, endFrame, 0, 1)))));
						} else {
							field.setFloat(obj, startingFloatData.get(entry.getKey()) + ((entry.getValue() - startingFloatData.get(entry.getKey())) * style.ease(dir, map(frameTime, 0, endFrame, 0, 1))));
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}

				for (Entry<String, PVector> entry : vectorData.entrySet()) {
					Field field = TweenService.getField(obj.getClass(), entry.getKey());
					field.setAccessible(true);
					PVector vector = null;
					try {
						vector = (PVector) field.get(obj);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					PVector startingVector = startingVectorData.get(entry.getKey());
					PVector endVector = vectorData.get(entry.getKey());
					
					vector.x = startingVector.x + ((endVector.x - startingVector.x) * style.ease(dir, map(frameTime, 0, endFrame, 0, 1)));
					vector.y = startingVector.y + ((endVector.y - startingVector.y) * style.ease(dir, map(frameTime, 0, endFrame, 0, 1)));
				}

			} // if
		} // if
		return false;
	}

	private void animEnded() {
		animating = false;
		for (Entry<String, Float> entry : floatData.entrySet()) {
			Field field = TweenService.getField(obj.getClass(), entry.getKey());
			field.setAccessible(true);
			try {
				if (field.getType().getTypeName() == "int") {
					field.setInt(obj, entry.getValue().intValue());
				} else {
					field.setFloat(obj, entry.getValue());
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		for (Entry<String, PVector> entry : vectorData.entrySet()) {
			Field field = TweenService.getField(obj.getClass(), entry.getKey());
			field.setAccessible(true);
			PVector vector = null;
			try {
				vector = (PVector) field.get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			vector.x = entry.getValue().x;
			vector.y = entry.getValue().y;
		}
	}

	public boolean isRunning() {
		return animating;
	}

	public void start() {
		if (endFrame == -1) {
			startingFloatData = new HashMap<>();
			startingVectorData = new HashMap<>();
			endFrame = (int) (time * frameRate);
			for (Entry<String, Float> entry : floatData.entrySet()) {
				Field field = TweenService.getField(obj.getClass(), entry.getKey());
				field.setAccessible(true);
				try {
					if (field.getType().getTypeName().equals("int")) {
						startingFloatData.put(entry.getKey(), (float)field.getInt(obj));
					} else {
						startingFloatData.put(entry.getKey(), field.getFloat(obj));
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			for (Entry<String, PVector> entry : vectorData.entrySet()) {
				Field field = TweenService.getField(obj.getClass(), entry.getKey());
				field.setAccessible(true);
				PVector vector;
				try {
					vector = (PVector)field.get(obj);
					startingVectorData.put(entry.getKey(), new PVector(vector.x, vector.y));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		animating = true;
	}

	public void stop() {
		animating = false;
	}

	public Tweenable getObject() {
		return obj;
	}

	public void setFrameRate(int frameRate) {
		this.frameRate = frameRate;
	}
	
	private float map(float input, float from1, float from2, float to1, float to2) {
		return to1 + ((to2 - to1) / (from2 - from1)) * (input - from1);
	}
}
