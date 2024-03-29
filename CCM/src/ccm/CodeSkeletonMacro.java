/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccm;

import processing.app.ui.Editor;

/**
 *
 * @author dahjon
 */
public class CodeSkeletonMacro extends Macros {

    public static final String CODE_SKELETON
            = "\n"
            + "void setup() {\n"
            + "   size(400, 400);\n"
            + "   \n"
            + "}\n"
            + "\n"
            + "void draw() {\n"
            + "   \n"
            + "}\n";
    
    public static final String COMMENT_CODE_SKELETON
    = "\n"
    + "void setup() {\n"
    + "   size(400, 400);\n"
    + "   \n"
    + "}// setup\n"
    + "\n"
    + "void draw() {\n"
    + "   \n"
    + "}// draw\n";
    
    public static final int CARETPOS = 38;

    public CodeSkeletonMacro() {
        super("", "", 0);
    }

    @Override
    public boolean stringIsThisMacro(Editor editor, String sstr) {
        return editor.getText().trim().length() == 0;
    }

    @Override
    public void insert(Editor editor, int indent) {
    	if(CodeCompletionMacros.addComments) {
    		editor.setText(COMMENT_CODE_SKELETON);
            //System.out.println("editor.getCaretOffset() = " + editor.getCaretOffset());
    	} else {
    		editor.setText(CODE_SKELETON);
    	}
        editor.getTextArea().setCaretPosition(CARETPOS);
        //System.out.println("editor.getCaretOffset() = " + editor.getCaretOffset());
    }
}
