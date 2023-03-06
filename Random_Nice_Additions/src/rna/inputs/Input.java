package rna.inputs;

import processing.core.*;

import java.util.ArrayList;

public class Input {
	
	protected static ArrayList<Input> inputs = new ArrayList<>();

	int x, y;

	protected Input(int x, int y) {
		this.x = x;
		this.y = y;
	}

	protected void primary() {
	}

	public static void core() {
		for(Input i : inputs) {
			i.primary();
		}
	}

	public static Slider createSlider(PApplet pa, String name, int x, int y, int len, int hght, float min, float max, float incr, float start) {
		Slider slider = new Slider(pa, x, y, len, hght, min, max, incr, start);
		inputs.add(slider);
		return slider;
	}

	public static Toggle createToggle(PApplet pa, String name, int x, int y, int scale, boolean startState, int Color) {
		Toggle toggle = new Toggle(pa, x, y, scale, startState, Color);
		inputs.add(toggle);
		return toggle;
	}
}



