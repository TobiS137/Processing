package eecc;

import processing.app.ui.Editor;

public class ReplaceMacros extends Macros {

    String imp = "";

    public ReplaceMacros(String key, String code, int carBack) {
        super(key, code, carBack);
    }
    
    public ReplaceMacros(String key, String code, int carBack, String commentCode, int commentCarBack) {
        super(key, code, carBack, commentCode, commentCarBack);
    } 
   
    public ReplaceMacros(String key, String code, int carBack, String imp) {
        super(key, code, carBack);
        this.imp = imp;
    }
    
    

    @Override
    public void insert(Editor editor, int indent) {
    	boolean addComments = (CodeCompletionMacros.addComments && commentCode != null);
    	
    	int nr = getNumbersOfLineBreaks((addComments) ? commentCode.substring(commentCode.length() - commentCarBack) : code.substring(code.length() - carBack));
        String indentStr = new String(new char[indent]).replace('\0', ' ');
        
        String str = (addComments) ? commentCode.replaceAll("\n", "\n" + indentStr) : code.replaceAll("\n", "\n" + indentStr);

        String etxt = editor.getText();
        int cur = editor.getCaretOffset();
        etxt = etxt.substring(0, cur - usedKey.length()) + str + etxt.substring(cur);
        int scrollPos = editor.getTextArea().getVerticalScrollPosition();
        editor.setText(etxt);
        int implen=0;
        if (imp.length() > 0) {
            if (editor.getText().indexOf(imp) < 0) {
                editor.setText(imp + etxt);
                implen=imp.length();
            }
        }
        int carPos = cur + str.length() + implen - usedKey.length() - nr * indent - ((addComments) ? commentCarBack : carBack);
        editor.getTextArea().setCaretPosition(carPos);
        editor.getTextArea().setVerticalScrollPosition(scrollPos);
    }
}
