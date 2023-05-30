package rna.util;

import processing.app.Base;

public final class PPath {

	static public String getSketchbookFolder() {
		return Base.getSketchbookFolder().getAbsolutePath();
	}

	static public String getSketchbookLibrariesFolder() {
		return Base.getSketchbookFolder().getAbsolutePath();
	}

	static public String getSketchbookToolsFolder() {
		return Base.getSketchbookToolsFolder().getAbsolutePath();
	}

	static public String getSketchbookModesFolder() {
		return Base.getSketchbookModesFolder().getAbsolutePath();
	}

	static public String getSketchbookExamplesFolder() {
		return Base.getSketchbookExamplesFolder().getAbsolutePath();
	}

	static public String getSketchbookTemplatesFolder() {
		return Base.getSketchbookTemplatesFolder().getAbsolutePath();
	}
}
