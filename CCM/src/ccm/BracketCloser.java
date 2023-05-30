package ccm;

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
				// System.out.println(Character.toString(key) +
				// Character.toString(openingChars[i]));
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

	public void smartClose(char key) {
		switch (key) {
		case '\'':
			if (getCharCountBefore('\'', editor.getCaretOffset(), getLine(editor.getCaretOffset())) % 2 == 1
					&& getCharCountAfter('\'', editor.getCaretOffset(), getLine(editor.getCaretOffset())) % 2 == 1) {
				removeClosingChar(3);
			} else if (getCharCountBefore('\'', editor.getCaretOffset(), getLine(editor.getCaretOffset())) % 2 == 0
					&& getCharCountAfter('\'', editor.getCaretOffset(), getLine(editor.getCaretOffset())) % 2 == 0) {
				closeBracket(3);
			}
			break;

		case '"':
			//System.out.println("Before: " + getCharCountBefore('"', editor.getCaretOffset(), getLine(editor.getCaretOffset())));
			//System.out.println("After: " + getCharCountAfter('"', editor.getCaretOffset(), getLine(editor.getCaretOffset())));
			if (getCharCountBefore('"', editor.getCaretOffset(), getLine(editor.getCaretOffset())) % 2 == 1
					&& getCharCountAfter('"', editor.getCaretOffset(), getLine(editor.getCaretOffset())) % 2 == 1) {
				removeClosingChar(4);
			} else if (getCharCountBefore('"', editor.getCaretOffset(), getLine(editor.getCaretOffset())) % 2 == 0
					&& getCharCountAfter('"', editor.getCaretOffset(), getLine(editor.getCaretOffset())) % 2 == 0) {
				closeBracket(4);
			}
			break;
		default:
			for (int i = 0; i < openingChars.length; i++) {
				if (key == openingChars[i]) {
					openCharPressed(i);
				} else if (key == closingChars[i]) {
					closeCharPressed(i);
				}
			}
		}
	}

	private void openCharPressed(int charLocation) {
		if (getOpenCloseDiff(charLocation, getLine(editor.getCaretOffset())) >= 0) {
			closeBracket(charLocation);
		}
	}

	private void closeCharPressed(int charLocation) {
		if (getOpenCloseDiff(charLocation, getLine(editor.getCaretOffset())) <= 0) {
			removeClosingChar(charLocation);
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
		editor.getTextArea().setCaretPosition(editor.getCaretOffset() - 1);
		lastChar = openingChars[charLocation];
	}

	public void removeClosingChar(int charLocation) {

		// System.out.println(editor.getCaretOffset() + " : " +
		// editor.getText().length());

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

		int scrollPos = editor.getTextArea().getVerticalScrollPosition();
		editor.setText(first + last);
		// editor.setSelection(caretPos, caretPos);
		editor.getTextArea().setCaretPosition(caretPos);
		editor.getTextArea().setVerticalScrollPosition(scrollPos);

		lastChar = (closingChars[charLocation] == '"' || closingChars[charLocation] == '\'') ? '*'
				: closingChars[charLocation];
	}

	public void openBracket() {
		String etxt = editor.getText();
		int caretPos = editor.getCaretOffset();

		String first = etxt.substring(0, caretPos);
		String last = etxt.substring(caretPos + 1, etxt.length());

		etxt = first + last;

		editor.setText(etxt);
		int scrollPos = editor.getTextArea().getVerticalScrollPosition();
		// editor.setSelection(caretPos, caretPos);
		editor.getTextArea().setCaretPosition(caretPos);
		editor.getTextArea().setVerticalScrollPosition(scrollPos);
	}

	private int getOpenCloseDiff(int charLocation, String line) {
		int startingChars = 0;
		int endingChars = 0;

		for (int j = 0; j < line.length(); j++) {
			if (line.charAt(j) == openingChars[charLocation]) {
				startingChars++;
			}
			if (line.charAt(j) == closingChars[charLocation]) {
				endingChars++;
			}
		}

		return startingChars - endingChars;
	}

	private int getCharCountBefore(char key, int caretPos, String line) {
		if (line.length() <= 0 || caretPos == 0) {
			return 0;
		}

		int count = 0;
		//System.out.println(caretPos - getStartOfLine(editor.getCaretOffset()));
		for (int i = 1; i <= caretPos - getStartOfLine(editor.getCaretOffset()); i++) {
			if (line.charAt(caretPos - getStartOfLine(editor.getCaretOffset()) - i) == key) {
				if (caretPos - getStartOfLine(editor.getCaretOffset()) - i > 0) {
					if (caretPos - getStartOfLine(editor.getCaretOffset()) - i != '\\') {
						count++;
					}
				} else {
					count++;
				}
			}
		}
		return count;
	}

	private int getCharCountAfter(char key, int caretPos, String line) {
		if (line.length() <= 0 || caretPos == line.length()) {
			return 0;
		}
		int count = 0;
		for (int i = 0; i < getEndOfLine(editor.getCaretOffset()) - caretPos; i++) {
			//System.out.println("Before: " + i + " : " + (caretPos - getStartOfLine(editor.getCaretOffset()) + i) + " :: " + (i < getEndOfLine(editor.getCaretOffset()) - caretPos));
			if (line.charAt(caretPos - getStartOfLine(editor.getCaretOffset()) + i) == key) {
				//System.out.println((caretPos - getStartOfLine(editor.getCaretOffset()) + i) + " : " + (caretPos - getStartOfLine(editor.getCaretOffset()) + i > 0));
				if (caretPos - getStartOfLine(editor.getCaretOffset()) + i > 0) {
					if (line.charAt(caretPos - getStartOfLine(editor.getCaretOffset()) + i - 1) != '\\') {
						count++;
					}
				} else {
					count++;
				}
			}
			//System.out.println("After: " + i + " : " + (getEndOfLine(editor.getCaretOffset()) - caretPos) + " :: " + (i < getEndOfLine(editor.getCaretOffset()) - caretPos));
		}
		return count;
	}

	private String getLine(int caretPos) {
		return editor.getText().substring(getStartOfLine(caretPos), getEndOfLine(caretPos));
	}

	private int getStartOfLine(int caretPos) {
		String etxt = editor.getText();

		int startPos = caretPos;

		if (caretPos == etxt.length()) {
			startPos--;
			if (etxt.length() <= 0) {
				return 0;
			}
		}

		while (etxt.charAt(startPos) != '\n') {
			if (startPos <= 0) {
				break;
			}
			startPos--;
		}

		return startPos;
	}

	private int getEndOfLine(int caretPos) {
		String etxt = editor.getText();

		if (caretPos >= etxt.length() || etxt.length() < 1) {
			return caretPos;
		}

		int endPos = caretPos;
		while (etxt.charAt(endPos) != '\n' && endPos < etxt.length()) {
			endPos++;
			if (endPos >= etxt.length()) {
				break;
			}
		}
		return endPos;
	}

}