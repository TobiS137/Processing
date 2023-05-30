package ccm;

import processing.app.*;
import processing.app.ui.*;

public class NewLineHandler {

	Editor editor;

	public NewLineHandler(Editor editor) {
		this.editor = editor;
	}

	public void newLine() {
		int caret = editor.getCaretOffset();
		//System.out.println(editor.getText().charAt(caret - 1) + " : " + editor.getText().charAt(caret));
		if (caret > 0 && caret < editor.getText().length()) {
			if (editor.getText().charAt(caret - 1) == '{' && editor.getText().charAt(caret) == '}') {
				if (caret == editor.getText().length() - 1 || editor.getText().charAt(caret + 1) == '\n') {
					insertNewLine();
				}
			}
		}
	}
	
	public void insertNewLine() {
		//System.out.println("Inserting line");
		int caret = editor.getCaretOffset();
		int indentsBeforeCaret = getIndentBeforeCaret(caret);
		String indentStr = "";
		
		for (int i = 0; i < indentsBeforeCaret; i++) {
			indentStr += "  ";
		}
		
		editor.insertText("\n" + indentStr);
		editor.setSelection(caret, caret);
	}
	
	public int getIndentBeforeCaret(int caret) {
		String etxt = editor.getText();
		
		int lineStart = caret;
		
		while(etxt.charAt(lineStart) != '\n' && lineStart > 0) {
			lineStart--;
		}
		
		lineStart++;
		
		int i = 0;
		while(etxt.charAt(lineStart + i) == ' ') {
			i++;
		}
		
		return i/2;
	}
	
	

}