package ccm;

import processing.app.*;
import processing.app.ui.*;

public class BracketWrapper {

	Editor editor;

	char[] openingChars;
	char[] closingChars;

	public BracketWrapper(Editor editor, char[] openingChars, char[] closingChars) {
		this.editor = editor;
		this.openingChars = openingChars;
		this.closingChars = closingChars;
	}

	public void close(char key) {
		for (int i = 0; i < openingChars.length; i++) {
			if (key == openingChars[i]) {
				/*if (editor.getSelectionStart() - 1 >= 0 && editor.getSelectionStop() + 1 < editor.getText().length()) {
					if (key == openingChars[i] && key == editor.getText().charAt(editor.getSelectionStart() - 1)
							&& key == editor.getText().charAt(editor.getSelectionStop() + 1)) {
						removeWrap(i, editor.getSelectionStart(), editor.getSelectionStop());
						return;
					}
				}*/
				wrapText(i, editor.getSelectionStart(), editor.getSelectionStop());
			}
		}

	}

	public void wrapText(int charLocation, int start, int end) {
		editor.setSelection(end, end);
		editor.getTextArea().setCaretPosition(end);
		editor.insertText(Character.toString(closingChars[charLocation]));
		editor.getTextArea().setCaretPosition(start);
	}

	public void removeWrap(int charLocation, int start, int end) {
		String etxt = editor.getText();

		String first;
		
		first = (start - 2 >= 0) ? etxt.substring(0, start - 2) : "";
		
		String last = etxt.substring(start, etxt.length());

		etxt = first + last;

		first = etxt.substring(0, end);
		last = etxt.substring(end + 2, etxt.length());

		editor.setText(first + last);
	}
}
