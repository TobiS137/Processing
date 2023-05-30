package eecc;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import processing.app.Base;
import processing.app.tools.Tool;
import processing.app.ui.Editor;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class CodeCompletionMacros implements Tool, KeyListener {

	Base base;
	Editor editor;
	
	public static String debugMessage;
	
	public static boolean addComments = false;
	
	public void init(Base base) {
		this.base = base;
		CommandHandler.setCCM(this);
		CommandHandler.loadMacros(Base.getSketchbookFolder().getAbsolutePath() + "\\savedMacros.txt");
	}

	public void run() {
		if (editor != base.getActiveEditor()) {
			// Run this Tool on the currently active Editor window
			System.out.println("Code Completion Macros are now running.\n");
			editor = base.getActiveEditor();
			editor.getTextArea().addKeyListener(this);
		} else {
			System.out.println("Code Completion Macros are already running.\n");
		}

	}

	public String getMenuTitle() {
		return "Code Completion Macros";
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		if (ke.getKeyChar()== 'å') {
			System.out.println("Add comments?: " + addComments);
			System.out.println("Macro lines arrayList: " + CommandHandler.getMacroLines());
			System.out.println("Debug message: " + debugMessage);
		}
		
		if (ke.getKeyCode() == KeyEvent.VK_SPACE
				&& ke.getModifiersEx() == InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK) {
			//System.out.println("space + ctrl + shift");
			String etxt = editor.getText();
			String selection = etxt.substring(editor.getSelectionStart(), editor.getSelectionStop());

			//System.out.println(selection);

			CommandHandler.additionHandler(editor, selection);
		}

		if (ke.getKeyCode() == KeyEvent.VK_SPACE && ke.getModifiersEx() == InputEvent.CTRL_DOWN_MASK) {
			// ke.consume();
			// System.out.println("keyPressed ctrl+space");
			String txt = getTextBeforeCaret();
			// System.out.println(txt);
			// System.out.println("txt = '" + txt + "'");
			int indent = getSpacesBeforeText(txt.length());
			// System.out.println("indent = " + indent);
			Macros m = Macros.find(editor, txt);
			// System.out.println(m);
			//System.out.println(m);
			if (m != null) {
				m.setUsedKey(txt);
				m.insert(editor, indent);
			}
		}
	}
	
	public static void insertAndSelectText(Editor editor, String strToInsert, int start, int end) {
		String etxt = editor.getText();
		
		String first = etxt.substring(0, start);
		String last = etxt.substring(end);
		
		editor.setText(first + strToInsert + last);
		editor.setSelection(start, start + strToInsert.length());
	}

	private int getSpacesBeforeText(int textLen) {
		int start = editor.getCaretOffset() - 1 - textLen;
		int i = start;
		String edtext = editor.getText();
		if (start >= 0) {
			char c = edtext.charAt(start);
			while (c == ' ' && i >= 0) {
				// System.out.println("i = " + i+" c = " + c);
				i--;
				if (i >= 0) {
					c = editor.getText().charAt(i);
				}
			}
		}

		return start - i;
	}

	private String getTextBeforeCaret() {
		int start = editor.getCaretOffset() - 1;
		if (start >= 0) {
			// System.out.println("start = " + start);
			int i = start;
			String edtext = editor.getText();
			// System.out.println("edtext = " + edtext);
			// System.out.println("edtext.length() = " + edtext.length());
			char c = edtext.charAt(start);
			while ((c != ' ' && c != '\n') && i >= 0) {
				i--;
				if (i >= 0) {
					c = editor.getText().charAt(i);
				}
			}
			i++;
			return editor.getText().substring(i, start + 1);
		} else {
			return "";
		}
	}

	@Override
	public void keyTyped(KeyEvent ke) {
//        if (ke.getKeyCode() == KeyEvent.VK_TAB) {
//            System.out.println("keyTyped ...Tab tryckt!!! consume");
//            ke.consume();
//        }

	}

	@Override
	public void keyReleased(KeyEvent ke) {
//        if (ke.getKeyCode() == KeyEvent.VK_TAB) {
//            System.out.println("keyReleased ...Tab tryckt!!! consume");
//            ke.consume();
//        }        
	}
}
