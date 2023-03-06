package speed.tool;

import processing.app.*;
import processing.app.ui.*;
import processing.app.tools.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SpeedToolRevived implements Tool, KeyListener {

	Base base;
	Editor editor;

	BracketCloser bc;
	BracketWrapper bw;
	NewLineHandler nlh;

	char[] openingChars = { '(', '[', '{', '\'', '"' };
	char[] closingChars = { ')', ']', '}', '\'', '"' };

	public String getMenuTitle() {
		return "Speed Tool Revived";
	}

	public void init(Base base) {
		this.base = base;
	}

	public void run() {
		if (editor != base.getActiveEditor()) {
			this.editor = base.getActiveEditor();
			editor.getTextArea().addKeyListener(this);
			bc = new BracketCloser(editor, openingChars, closingChars);
			bw = new BracketWrapper(editor, openingChars, closingChars);
			nlh = new NewLineHandler(editor);
			System.out.println("Speed tool is now running.");
		} else {
			System.out.println("Speed tool is already running.");
		}
	}
	
	@Override
	public void keyPressed(KeyEvent ke) {
		char key = ke.getKeyChar();
		int caretPos = editor.getCaretOffset();
		
		//System.out.println(key);
		
		if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
			//System.out.println("enter");
			nlh.newLine();
		} else if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
			//System.out.println("space");
		} else if (ke.getKeyCode() == KeyEvent.VK_CONTROL) {
			//System.out.println(Integer.toString(editor.getCaretOffset()) + "/" + Integer.toString(editor.getText().length()));
		} else if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			bc.open();
		} else {
			if (!editor.isSelectionActive()) {
				bc.close(key);
			} else {
				//ke.consume();
				bw.close(key);
				//System.out.println("selection is active : " + ke.isConsumed());
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}