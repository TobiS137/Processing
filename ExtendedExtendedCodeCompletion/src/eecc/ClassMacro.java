package eecc;

import processing.app.ui.Editor;

public class ClassMacro extends Macros {
	
	private final String PREFIX = "class";
	private final String CODE = " {\n\n  \n\n  #() {\n    \n  }\n\n}";
	private final int CAR_BACK = 20;

	public ClassMacro() {
		super("", "", 0);
	}
	
	@Override
	public void insert(Editor editor, int indent) {
		String text = getTextAfterIndex(editor, PREFIX.length() + 1);
		String codeToInsert = CODE.replace("#", text);
		editor.setText(editor.getText() + codeToInsert);
		
		editor.setSelection(editor.getText().length() - CAR_BACK - text.length(), editor.getText().length() - CAR_BACK - text.length());
	}
	
	@Override
	public boolean stringIsThisMacro(Editor editor, String sstr) {
		if (editor.getText().indexOf(PREFIX) == 0) {
			if (editor.getText().charAt(PREFIX.length()) == ' ') {
				String text = getTextAfterIndex(editor, PREFIX.length() + 1);
				System.out.println(text + " : " + text.length());
				if (PREFIX.length() + 1 + text.length() == editor.getText().length() && PREFIX.length() + 1 + text.length() == editor.getCaretOffset()) {
					return true;
				}
			}
		}
		return false;
	}
	
	private String getTextAfterIndex(Editor editor, int start) {
		String textAfterIndex = "";
		int i = start;
		
		while (editor.getText().charAt(i) != ' ' && editor.getText().charAt(i) != '\n') {
			textAfterIndex += Character.toString(editor.getText().charAt(i));
			i++;
			
			if (i >= editor.getText().length()) {
				break;
			}
		}
		
		return textAfterIndex;
	}
}