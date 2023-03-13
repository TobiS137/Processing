/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eecc;

import processing.app.ui.Editor;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author dahjon
 */
public class Macros {

	protected String key;
	protected String usedKey;
	protected String code;
	protected int carBack;
	protected String commentCode;
	protected int commentCarBack;
	
	protected static char[] simpleTypes = {'i', 'b', 'f', 'S', 'd', 'c', 'p', 's'};
	protected static String[] fullTypes = {"Integer", "Boolean", "Float", "String", "Double", "Character", "PVector", "Short"};

	private static ArrayList<Macros> macrosList = new ArrayList<Macros>(Arrays.asList( 
			new Macros("for", " (int i = 0; i < 10; i++) {\n  \n}", 13, " (int i = 0; i < 10; i++) {\n  \n}// for i", 21),
			new Macros("if", " (){\n  \n}", 7, " (){\n  \n}// if", 12), 
			new Macros("while", " (){\n  \n}", 7, " (){\n  \n}// while", 15),
			new Macros("do", " {\n  \n} while ();\n", 3),
			new Macros("PV", "ector  = new PVector();", 17),
			new RepeatMacro("switch", "switch() {\ncase 1:\n  break;\ndefault:\n}\n", 32,
					"\ncase #:\n  break;\n", 11),
			new ReplaceMacros("forfor",
					"for (int i = 0; i < 10; i++){\n   for (int j = 0; j < 10; j++){\n      \n   }\n}", 54, 
					"for (int i = 0; i < 10; i++){\n   for (int j = 0; j < 10; j++){\n      \n   }// for j\n}// for i", 70),
			new ReplaceMacros("setup", "void setup(){\n  \n}\n", 3, "void setup(){\n  \n}// setup\n", 11),
			new ReplaceMacros("fore", "for ( : ){\n  \n}", 10, "for ( : ){\n  \n}// for each", 21),
			new ReplaceMacros("draw", "void draw(){\n  \n}\n", 3, "void draw(){\n  \n}// draw\n", 10),
			new ReplaceMacros("swing", "import javax.swing.*;\n", 0),
			new ReplaceMacros("input", "JOptionPane.showInputDialog (\"\")", 2, "import javax.swing.JOptionPane;\n"),
			new ReplaceMacros("output", "JOptionPane.showMessageDialog(null, \"\");", 3,
					"import javax.swing.JOptionPane;\n"),
			new ReplaceMacros("inputstr", "String str = JOptionPane.showInputDialog(\"\");", 3,
					"import javax.swing.JOptionPane;\n"),
			new ReplaceMacros("pv", "PVector", 0),
			new CodeSkeletonMacro(), 
			new FunctionMacro(),
			new SelectionMacro("arr", "ArrayList<#>", 0, 1, '<', simpleTypes, fullTypes),
			new SelectionMacro("Arr", "ArrayList<#>  = new ArrayList<#>();", 21, 21, '<', simpleTypes, fullTypes),
			new ClassMacro()));

	public Macros(String key, String code, int carBack) {
		this.key = key;
		this.code = code;
		this.carBack = carBack;
	}
	
	public Macros(String key, String code, int carBack, String commentCode, int commentCarBack) {
		this.key = key;
		this.code = code;
		this.commentCode = commentCode;
		this.carBack = carBack;
		this.commentCarBack = commentCarBack;
	}

	protected int getNumbersOfLineBreaks(String str) {
		int nr = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '\n') {
				nr++;
			}
		}
		return nr;
	}

	public static Macros find(Editor editor, String sstr) {
		for (int i = 0; i < macrosList.size(); i++) {
			Macros m = macrosList.get(i);
			try {
				if (m.stringIsThisMacro(editor, sstr)) {
					return m;
				}
			} catch(NullPointerException npe) {
			}
		}
		return null;
	}
	
	public static void addMacro(Editor editor, String key, String code, int carBack) {
		macrosList.add(new ReplaceMacros(key, code, carBack));
	}
	
	public static void addMacro(Editor editor, String key, char div, String code, int carBack, int emptyCarBack) {
		macrosList.add(new SelectionMacro(key, code, carBack, emptyCarBack, div, simpleTypes, fullTypes));
	}

	public boolean stringIsThisMacro(Editor editor, String sstr) {
		return key.equals(sstr);
	}

	public void insert(Editor editor, int indent) {
		boolean addComments = (ExtendedExtendedCodeCompletion.addComments && commentCode != null);
		
		int nr = getNumbersOfLineBreaks((addComments) ? commentCode.substring(commentCode.length() - commentCarBack) : code.substring(code.length() - carBack));
        String indentStr = new String(new char[indent]).replace('\0', ' ');
		String str = (addComments) ? commentCode.replaceAll("\n", "\n" + indentStr) : code.replaceAll("\n", "\n" + indentStr);;
		//System.out.println(str + " : " + str.length());

		editor.insertText(str);
		// System.out.println("nr = " + nr);
		int carPos = editor.getCaretOffset() - ((addComments) ? commentCarBack : carBack) - nr * indent;
		editor.getTextArea().setCaretPosition(carPos);
	}

	public String insertString(String inputString, String stringToInsert, int index) {
		String first = inputString.substring(0, index);
		String last = inputString.substring(index + 1, inputString.length());

		return first + stringToInsert + last;
	}
	
	public static ArrayList<Macros> getMacros() {
		return macrosList;
	}

	public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

	public void setUsedKey(String key) {
		this.usedKey = key;
	}

}
