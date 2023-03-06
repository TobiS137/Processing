package speed.tool;

import java.util.Iterator;

import processing.app.*;
import processing.app.ui.*;

public class BracketCloser {

	Editor editor;

	char[] openingChars;
	char[] closingChars;

	char lastChar;

	public BracketCloser(Editor editor, char[] openingChars, char[] closingChars) {
		this.editor = editor;
		this.openingChars = openingChars;
		this.closingChars = closingChars;
	}

	public void close(char key) {
		for (int i = 0; i < openingChars.length; i++) {
			if (key == openingChars[i]) {
				//System.out.println(Character.toString(key) + Character.toString(openingChars[i]));
				if ((key == '"' && lastChar == '"') || (key == '\'' && lastChar == '\'')) {
					removeClosingChar(i);
				} else {
					closeBracket(i);
				}
			} else if (key == closingChars[i] && lastChar == openingChars[i]
					&& editor.getText().charAt(editor.getCaretOffset()) == closingChars[i])
				removeClosingChar(i);
		}
	}

	public void open() {
		for (int i = 0; i < openingChars.length; i++) {
			if (editor.getCaretOffset() > 0 && editor.getCaretOffset() < editor.getText().length()
					&& !editor.isSelectionActive()) {
				if (editor.getText().charAt(editor.getCaretOffset() - 1) == openingChars[i]
						&& editor.getText().charAt(editor.getCaretOffset()) == closingChars[i]) {
					openBracket();
				}
			}
		}
	}

	public void closeBracket(int charLocation) { 
			editor.insertText(Character.toString(closingChars[charLocation]));
			editor.setSelection(editor.getCaretOffset() - 1, editor.getCaretOffset() - 1);
			lastChar = openingChars[charLocation];
	}

	public void removeClosingChar(int charLocation) {	
		
		//System.out.println(editor.getCaretOffset() + " : " + editor.getText().length());
		
		if (editor.getCaretOffset() >= editor.getText().length()) {
			return;
		}
		
		if (editor.getText().charAt(editor.getCaretOffset()) != closingChars[charLocation]) {
			return;
		}
		
		String etxt = editor.getText();
		int caretPos = editor.getCaretOffset();

		String first = etxt.substring(0, caretPos);
		String last = etxt.substring(caretPos + 1, etxt.length());

		editor.setText(first + last);
		editor.setSelection(caretPos, caretPos);

		lastChar = (closingChars[charLocation] == '"' || closingChars[charLocation] == '\'') ? '*' : closingChars[charLocation];
	}

	public void openBracket() {
		String etxt = editor.getText();
		int caretPos = editor.getCaretOffset();

		String first = etxt.substring(0, caretPos);
		String last = etxt.substring(caretPos + 1, etxt.length());

		etxt = first + last;

		editor.setText(etxt);

		editor.setSelection(caretPos, caretPos);
	}

}