package rna.tools;

import java.util.HashMap;
import java.util.Map;

import processing.core.PConstants;

public class KeyHandler {
	HashMap<Character, Boolean> keys;
	HashMap<Integer, Boolean> skeys;
	
	boolean ignoreCase = false;

	public KeyHandler() {
		keys = new HashMap<>();
		skeys = new HashMap<>();
	}
	
	public KeyHandler(boolean ignoreCase) {
		keys = new HashMap<>();
		skeys = new HashMap<>();
		this.ignoreCase = ignoreCase;
	}

	public void set(char c, int pc, boolean pressed) {
		char ac = (c == PConstants.CODED) ? c : (ignoreCase) ? Character.toLowerCase(c) : c;
		
		if (c == PConstants.CODED) {
			skeys.put(pc, pressed);
		} else {
			keys.put(ac, pressed);
		}
	}

	public boolean isDown(char c) {
		char ac = (ignoreCase) ? Character.toLowerCase(c) : c;
		
		if (keys.containsKey(ac)) {
			return keys.get(ac);
		} else {
			return false;
		}
	}

	public boolean isDown(int c) {
		if (skeys.containsKey(c)) {
			return skeys.get(c);
		} else {
			return false;
		}
	}

	public String toString() {
		String back = "";

		for (Map.Entry<Character, Boolean> entry : keys.entrySet()) {
			back += "['" + entry.getKey() + "']: " + entry.getValue() + "\n";
		}
		
		for (Map.Entry<Integer, Boolean> entry : skeys.entrySet()) {
			back += "['" + entry.getKey() + "']: " + entry.getValue() + "\n";
		}
		
		return back;
	}
}
