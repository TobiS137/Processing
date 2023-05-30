/*

COMMENTED FOR COMPILATION

package ccm;



class ConstructorMacro extends Macros {

	ConstructorMacro() {

	}

	@Override
	public boolean stringIsThisMacro(Editor editor, String sstr) {
		String etxt = editor.getText();
		if (!etxt.trim().replaceAll("\n", "").startsWith("class")) {
			return false;
		}

		if (etxt.charAt(editor.getCaretOffset()) == ')') {

		}
	}

	private String getConstructorClass(Editor editor) {
		String etxt = editor.getText();
		int startPos = editor.getCaretOffset();

		if (startPos <= 0 || etxt.length() <= 0) {
			return null;
		}

		if (startPos == etxt.length()) {
			startPos--;
		}

		while (etxt.charAt(startPos) != '\n' && startPos > 0) {
			if (etxt.charAt(startPos) == '(') {
				break;
			}
			startPos--;
			if (startPos == 0) {
				break;
			}
		}
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

}*/