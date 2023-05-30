package eecc;

import processing.app.ui.Editor;

class SelectionMacro extends Macros {

	char div;
	char[] choiceKeys;
	String[] choiceCodes;
	int carBackEmpty;

	public SelectionMacro(String key, String code, int carBack, int carBackEmpty, char div, char[] choiceKeys, String[] choiceCodes) {
		super(key, code, carBack);
		this.div = div;
		this.choiceKeys = choiceKeys;
		this.choiceCodes = choiceCodes;
		this.carBackEmpty = carBackEmpty;
	}

	@Override
	public void insert(Editor editor, int indent) {
		String extracted = this.usedKey.substring(this.usedKey.indexOf(this.key), this.usedKey.length());
		String strToInsert = getChoiceFromKey(this.key, extracted);

		String codeToInsert = this.code.replaceAll("#", strToInsert);

		int caret = editor.getCaretOffset();

		String etxt = editor.getText();
		String first = etxt.substring(0, caret - extracted.length());
		String last = etxt.substring(caret);
		int scrollPos = editor.getTextArea().getVerticalScrollPosition();
		editor.setText(first + last);
		editor.setSelection(caret - extracted.length(), caret - extracted.length());
		editor.insertText(codeToInsert);
		editor.getTextArea().setVerticalScrollPosition(scrollPos);
		int caretPos = editor.getCaretOffset() - ((strToInsert == "") ? carBackEmpty : carBack) - getCharsBeforeCaret(editor, '#', carBack) * strToInsert.length();
		//System.out.println(getCharsBeforeCaret(editor, '#', carBack));
		editor.setSelection(caretPos, caretPos);
	}

	private String getChoiceFromKey(String key, String usedKey) {
		int divIndex = key.length();

		if (usedKey.length() <= divIndex + 1) {
			return "";
		}

		if (usedKey.charAt(divIndex) != div) {
			return "";
		}

		for (int i = 0; i < choiceKeys.length; i++) {
			if (choiceKeys[i] == usedKey.charAt(divIndex + 1)) {
				return choiceCodes[i];
			}
		}
		return "";
	}

	@Override
	public boolean stringIsThisMacro(Editor editor, String sstr) {
		if (sstr.indexOf(key) == 0) {
			if (sstr.length() == key.length()) {
				return true;
			} else if(sstr.length() > key.length()) {
				if (sstr.charAt(key.length()) == div) {
					return true;
				}
			}
		}
		return false;
	}
	
	private int getCharsBeforeCaret(Editor editor, char c, int carBack) {
		int count = 0;
		for (int i = code.length() - 1; i >= code.length() - carBack; i--) {
			if (code.charAt(i) == c) {
				count++;
			}
		}
		
		return count;
	}
}