package eecc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import processing.app.ui.Editor;
import processing.app.Base;

public class CommandHandler {

	private static ArrayList<String> macroLines = new ArrayList<String>();
	private static UnfinishedMacro unfinishedMacro = new UnfinishedMacro();

	private static String seperator = "__";
	private static ExtendedExtendedCodeCompletion eecc;
	

	public static void loadMacros(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			String line;
			while ((line = reader.readLine()) != null) {
				macroLines.add(line);
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Failed to load macros");
			e.printStackTrace();
		}

		for (int i = 0; i < macroLines.size(); i++) {
			additionHandler(null, macroLines.get(i));
		}
	}

	public static void additionHandler(Editor editor, String selection) {
		if (!selection.equals("")) {
			String[] args = selection.split(seperator);
			switch (args[0]) {
			case "newMacro":
				if (args.length == 3) {
					unfinishedMacro.reset();
					String key = args[1];
					String code = args[2];
					int caret = editor.getCaretOffset();

					if (key.contains(" ") || key.contains("\n")) {
						System.out.println("Invalid key: key cannot contain spaces or new lines.\n");
						break;
					}

					int end = editor.getSelectionStop() - (12 + key.length());
					int start = end - code.length();

					boolean duplicate = Macros.find(editor, key) != null;
					if (!duplicate) {
						String etxt = editor.getText();
						String first = etxt.substring(0, start);
						String last = etxt.substring(editor.getSelectionStop() - code.length());
						editor.setText(first + last);
						editor.setSelection(start, end);
						unfinishedMacro.setInfo(editor.getText(), "replace", key, code, end - code.length(), end);
						System.out.println("Select caret position.\n");
					} else {
						System.out.println("Invalid key: duplicate key.\n");
					}
				}
				break;
			case "newSelectMacro":
				if (args.length == 4) {
					unfinishedMacro.reset();
					String key = args[1];
					String code = args[3];
					int caret = editor.getCaretOffset();

					char div;
					if (args[2].length() == 1) {
						div = args[2].charAt(0);
					} else {
						System.out.println("Invalid divider: divider must be a single character.\n");
						break;
					}

					if (key.contains(" ") || key.contains("\n")) {
						System.out.println("Invalid key: key cannot contain spaces or new lines.\n");
						break;
					}

					int end = editor.getSelectionStop() - "newSelectMacro".length() - 3 * seperator.length() - 1
							- key.length();
					int start = end - code.length();

					System.out.println(start + " : " + end);

					boolean duplicate = Macros.find(editor, key) != null;
					if (!duplicate) {
						String etxt = editor.getText();
						String first = etxt.substring(0, start);
						String last = etxt.substring(editor.getSelectionStop() - code.length());

						editor.setText(first + last);
						editor.setSelection(start, end);
						unfinishedMacro.setInfo(editor.getText(), "select", key, div, code, end - code.length(), end);
						System.out.println("Select caret position.\n");
					} else {
						System.out.println("Invalid key: duplicate key.\n");
					}
				}
				break;
			case "loadMacro":
				if (args.length == 4) {
					unfinishedMacro.reset();
					String key = args[1];
					String code = args[2].replaceAll("\\\\n", "\n");
					int carBack = Integer.parseInt(args[3]);

					Macros.addMacro(editor, key, code, carBack);
				} else if(args.length == 6) {
					String key = args[1];
					char div = args[2].charAt(0);
					String code = args[3].replaceAll("\\\\n", "\n");
					int carBack = Integer.parseInt(args[4]);
					int emptyCarBack = Integer.parseInt(args[5]);
					
					Macros.addMacro(editor, key, div, code, carBack, emptyCarBack);
				}
				break;
			case "listMacros":
				if (args.length == 1) {
					unfinishedMacro.reset();
					System.out.println((macroLines.size() == 1) ? "You have created 1 macro:\n"
							: "You have created " + macroLines.size() + " macros:\n");
					for (int i = 0; i < macroLines.size(); i++) {
						String[] split = macroLines.get(i).split(seperator);
						System.out.println(Integer.toString(i + 1) + ". Key = \"" + split[1] + "\"");
					}
					System.out.print("\n");
				}
				break;
			case "help":
				if (args.length == 2) {
					switch (args[1]) {
					case "newMacro":
						System.out.println("newMacro syntax: newMacro__KEY__CODE\n");
						break;
					case "newSelectMacro":
						System.out.println(
								"newSelectMacro syntax: newSelectMacro__KEY__DIVIDER[char]__CODE[\"#\" replaced by selection]\n");
						break;
					}
				}
				break;
			case "removeMacro":
				if (args.length == 2) {
					unfinishedMacro.reset();
					String key = args[1];
					String[] keys = new String[macroLines.size()];
					for (int i = 0; i < macroLines.size(); i++) {
						String[] split = macroLines.get(i).split(seperator);
						keys[i] = split[1];
					}

					if (Arrays.stream(keys).anyMatch(key::equals)) {
						int keyIndex = Arrays.asList(keys).indexOf(key);
						macroLines.remove(keyIndex);
						saveFile();
						Macros m = Macros.find(editor, key);
						Macros.getMacros().remove(Macros.getMacros().indexOf(m));

						ExtendedExtendedCodeCompletion.insertAndSelectText(editor, "Successfully removed macro",
								editor.getSelectionStart(), editor.getSelectionStop());

						System.out.println("Removed macro: Key = \"" + key + "\"");
					}

				}
				break;
			}
		} else if (!unfinishedMacro.isEmpty()) {
			if (unfinishedMacro.type == "replace") {
				String key = unfinishedMacro.key;
				String code = unfinishedMacro.code;
				int start = unfinishedMacro.start;
				int end = unfinishedMacro.end;

				if (!editor.getText().equals(unfinishedMacro.prevText)) {
					unfinishedMacro.reset();
					return;
				}

				int caret = editor.getCaretOffset();

				if (caret < start || caret > end) {
					System.out.println("Invalid caret position.");
					return;
				}

				int carBack = end - caret;

				Macros.addMacro(editor, key, code, carBack);
				addLineToTextFile(key, code, carBack);
				System.out.println("Macro added: Key = \"" + key + "\", Code = \n\n" + code + "\n");

				ExtendedExtendedCodeCompletion.insertAndSelectText(editor, "Successfully added macro",
						end - code.length(), end);
			} else if (unfinishedMacro.type == "select") {
				String key = unfinishedMacro.key;
				String code = unfinishedMacro.code;
				char div = unfinishedMacro.div;
				int start = unfinishedMacro.start;
				int end = unfinishedMacro.end;

				if (!editor.getText().equals(unfinishedMacro.prevText)) {
					unfinishedMacro.reset();
					return;
				}

				int caret = editor.getCaretOffset();
				if (caret < start || caret > end) {
					System.out.println("Invalid caret position.");
					return;
				}

				int carBack = end - caret;
				if (unfinishedMacro.carBack == null) {
					unfinishedMacro.setCarBack(carBack);
					String replacedText = code.replaceAll("#", "");
					ExtendedExtendedCodeCompletion.insertAndSelectText(editor, replacedText,
							start, end);
					unfinishedMacro.setInfo(editor.getText(), "select", key, div, code, start, end - getCharsInCode(code, '#'));
					System.out.println("Select empty selection's caret position.\n");
				} else {
					Macros.addMacro(editor, key, div, code, unfinishedMacro.carBack.intValue(), carBack);
					addLineToTextFile(key, div, code, unfinishedMacro.carBack.intValue(), carBack);
					ExtendedExtendedCodeCompletion.insertAndSelectText(editor, "Successfully added macro", start, end);
					System.out.println("Select macro added: Key = \"" + key + "\", Divider = \'" + Character.toString(div) + "\', Code = " + code + "\n");
				}
			}
		}
	}

	private static void addLineToTextFile(String key, String code, int carBack) {
		String editedCode = code.replace("\n", "\\n");
		String newLine = "loadMacro" + seperator + key + seperator + editedCode + seperator + Integer.toString(carBack);
		macroLines.add(newLine);
		saveFile();
	}
	
	private static void addLineToTextFile(String key, char div, String code, int carBack, int emptyCarBack) {
		String editedCode = code.replace("\n", "\\n");
		String newLine = "loadMacro" + seperator + key + seperator + Character.toString(div) + seperator + editedCode + seperator + Integer.toString(carBack) + seperator + Integer.toString(emptyCarBack);
		macroLines.add(newLine);
		saveFile();
	}

	private static void saveFile() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(Base.getSketchbookFolder().getAbsolutePath() + "\\savedMacros.txt", false));
			for (int i = 0; i < macroLines.size(); i++) {
				writer.write(macroLines.get(i) + "\n");
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("Failed to save macro file");
			e.printStackTrace();
		}
	}

	public static void setEECC(ExtendedExtendedCodeCompletion neweecc) {
		eecc = neweecc;
	}
	
	private static int getCharsInCode(String code, char c) {
		int count = 0;
		for (int i = 0; i < code.length(); i++) {
			if (code.charAt(i) == c) {
				count++;
			}
		}
		return count;
	}

}