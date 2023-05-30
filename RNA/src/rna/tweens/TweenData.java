package rna.tweens;

import java.util.HashMap;
import processing.core.PVector;

public class TweenData {

	private HashMap<String, Float> floatData = new HashMap<>();
	private HashMap<String, PVector> vectorData = new HashMap<>();
	
	public void add(String key, float num) {
		floatData.put(key, num);
	}
	
	public void add(String key, PVector vector) {
		vectorData.put(key, vector);
	}
	
	public HashMap<String, Float> getFloatData() {
		return floatData;
	}
	
	public HashMap<String, PVector> getVectorData() {
		return vectorData;
	}
}