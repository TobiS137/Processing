package ccm;

import processing.app.ui.Editor;

public class RepeatMacro extends Macros {

	protected String cloningString = "";
	protected int copyCarBack = 0;
	
    public RepeatMacro(String key, String code, int carBack, String cloningString, int copyCarBack) {
        super(key, code, carBack);
        this.cloningString = cloningString;
        this.copyCarBack = copyCarBack;
    }

    @Override
    public void insert(Editor editor, int indent) {
    	int cloningNum = getRepeatCount(usedKey);
    	int nr = getNumbersOfLineBreaks(code.substring(code.length() - carBack));
        String indentStr = new String(new char[indent]).replace('\0', ' ');
        String editableCode = code;

        for (int i = 1; i < cloningNum; i++) {
			//System.out.println(str + " : " + str.length());
        	String replacingText = cloningString.replaceAll("#", Integer.toString(i + 1));
			editableCode = insertString(editableCode, replacingText, editableCode.length() - 1 - copyCarBack);
		}
        
        String str = editableCode.replaceAll("\n", "\n" + indentStr);

        String etxt = editor.getText();
        int cur = editor.getCaretOffset();
        etxt = etxt.substring(0, cur - usedKey.length()) + str + etxt.substring(cur);
        int scrollPos = editor.getTextArea().getVerticalScrollPosition();
        editor.setText(etxt);
        int carPos = cur + str.length() - usedKey.length() - nr * indent - carBack - (cloningString.length() - 1) * (cloningNum - 1);
        editor.getTextArea().setCaretPosition(carPos);
        editor.getTextArea().setVerticalScrollPosition(scrollPos);
    }
    
    private int getRepeatCount(String key) {
		if (Character.isDigit(key.charAt(key.length() - 1))) {
			return Integer.parseInt(Character.toString(key.charAt(key.length() - 1)));
		}
		return 1;
    }
    
    @Override
    public boolean stringIsThisMacro(Editor editor, String sstr) {
		if (sstr.indexOf(key) == 0) {
			return true;
		}
		return false;
	}
}