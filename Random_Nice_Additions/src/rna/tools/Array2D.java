package rna.tools;

import java.util.ArrayList;

import rna.util.ForEachCMD;

public class Array2D <T> {
	ArrayList<ArrayList<T>> main;
	
	public Array2D(int w) {
		main = new ArrayList<ArrayList<T>>();
		
		for(int i = 0; i < w; i++) {
			main.add(new ArrayList<T>());
		}
	}
	
	public Array2D(int w, int h) {
		main = new ArrayList<ArrayList<T>>();
		
		for(int i = 0; i < w; i++) {
			main.add(new ArrayList<T>());
			for (int j = 0; j < h; j++) {
				main.get(i).add(null);
			}
		}
	}
	
	public T get(int w, int h) {
		return main.get(w).get(h);
	}
	
	public void add(int w, T t) {
		main.get(w).add(t);
	}
	
	public void set(int w, int h, T t) {
		main.get(w).set(h, t);
	}
	
	public void remove(int w, int h) {
		main.get(w).remove(h);
	}
	
	public int size() {
		return main.size();
	}
	
	public int subSize(int w) {
		return main.get(w).size();
	}
	
	public ArrayList<ArrayList<T>> arraySet() {
		return main;
	}
	
	public void forEach(ForEachCMD<T> command) {
		for (int i = 0; i < size(); i++) {
			for (int j = 0; j < subSize(i); j++) {
				command.run(main.get(i).get(j));
			}
		}
	}
}
