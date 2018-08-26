package heignamerican.gfgazou.base;

import java.io.File;
import java.nio.file.Paths;

public class Files {
	final private String name;
	final private File dir;
	final private String extension;

	public static final String EXTENSION_JPG = "jpg";
	public static final String EXTENSION_PNG = "png";

	public Files(final UNext unext, final String fileName) {
		this(unext, fileName, EXTENSION_JPG);
	}

	public Files(final UNext unext, final String fileName, final String extension) {
		final File sourceFile = new File(Paths.get(unext.getWorkFolder(), fileName).toString());
		this.name = sourceFile.getName();
		this.dir = sourceFile.getParentFile();

		this.extension = extension;
	}

	public File getSourceFile() {
		return new File(this.dir, this.name);
	}

	public File getDestFile() {
		return new File(this.dir, this.name + "-henkan." + this.extension);
	}

	public String getExtension() {
		return extension;
	}
}
