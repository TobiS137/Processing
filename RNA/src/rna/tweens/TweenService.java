package rna.tweens;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import processing.core.PVector;

public final class TweenService {

	private static HashMap<Tweenable, Tween> tweens = new HashMap<Tweenable, Tween>();

	public static void core() {
		ArrayList<Entry<Tweenable, Tween>> entriesToDelete = new ArrayList<>();
		for (Map.Entry<Tweenable, Tween> entry : tweens.entrySet()) {
			if (entry.getValue().core()) {
				entriesToDelete.add(entry);
			}
		} // for each
		
		for (int i = 0; i < entriesToDelete.size(); i++) {
			Entry<Tweenable, Tween> entry = entriesToDelete.get(i);
			tweens.remove(entry.getKey(), entry.getValue());
		}
	}
 
	public static Tween create(Tweenable obj, EasingStyles style, EasingModes dir, TweenData data, float time) {
		if (time < 0) {
			throw new IllegalArgumentException("Time cannot be less than 0.");
		}
		
		for (Entry<String, Float> entry : data.getFloatData().entrySet()) {
			if (!hasField(obj.getClass(), entry.getKey())) {
				throw new IllegalArgumentException("No field found by the name of \"" + entry.getKey() + "\".");
			}

			if (!fieldIsOfType(obj.getClass(), entry.getKey(), "float") && !fieldIsOfType(obj.getClass(), entry.getKey(), "int")) {
				System.out.println(fieldIsOfType(obj.getClass(), entry.getKey(), "float"));
				System.out.println(fieldIsOfType(obj.getClass(), entry.getKey(), "int"));
				
				throw new IllegalArgumentException("Type of field by the name of \"" + entry.getKey()
						+ "\", does not match given input's type: (Input type = \"float\", Field type = \""
						+ getField(obj.getClass(), entry.getKey()).getType().getTypeName() + "\")");
			}

		}

		for (Entry<String, PVector> entry : data.getVectorData().entrySet()) {
			if (!hasField(obj.getClass(), entry.getKey())) {
				throw new IllegalArgumentException("No field found by the name of \"" + entry.getKey() + "\".");
			}

			if (!fieldIsOfType(obj.getClass(), entry.getKey(), "processing.core.PVector")) {
				throw new IllegalArgumentException("Type of field by the name of \"" + entry.getKey()
						+ "\", does not match given input's type: (Input type = \"processing.core.PVector\", Field type = \""
						+ getField(obj.getClass(), entry.getKey()).getType().getTypeName() + "\")");
			}

		}

		Tween tween = new Tween(obj, style, dir, data.getFloatData(), data.getVectorData(), time);
		tweens.put(obj, tween);
		return tween;
	}
	
	public static Tween create(Tweenable obj, TweenData data, float time) {
		return create(obj, EasingStyles.LINEAR, EasingModes.OUT, data, time);
	}

	protected static void animationEnded(Tween tween) {
		tween.getObject().tweenEnded();
		tween.getObject().tweenEnded(tween);
	}
	
	private static boolean fieldIsOfType(Class<?> c, String fieldName, String type) {
		List<Field> fields = getAllFields(new ArrayList<Field>(), c);
		for (Field field : fields) {
			if (!field.getName().equals(fieldName)) {
				continue;
			}
			if (field.getType().getTypeName().equals(type)) {
				return true;
			}

		}
 
		return false;
	}
	
	protected static Field getField(Class<?> c, String fieldName) {		
		List<Field> fields = getAllFields(new ArrayList<Field>(), c);
		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		
		return null;
	}
	
	protected static boolean hasField(Class<?> c, String fieldName) {
		List<Field> fields = getAllFields(new ArrayList<Field>(), c);
		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				return true;
			}
		}
		return false;
	}
	
	private static List<Field> getAllFields(List<Field> fields, Class<?> type) {
	    fields.addAll(Arrays.asList(type.getDeclaredFields()));

	    if (type.getSuperclass() != null) {
	        getAllFields(fields, type.getSuperclass());
	    }

	    return fields;
	}
}
