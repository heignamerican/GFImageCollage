package heignamerican.gfgazou.base;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class RecurseGazou {
	public static void henkan(final UNext unext, final String... fileNames) throws Exception {
		unext.gazou(fileNames);
	}

	public static void henkan(final UNext unext, final int count, final String... fileNames) throws Exception {
		if (count == 0) {
			henkan(unext, fileNames);
			return;
		}

		final Path dir = Paths.get(unext.getWorkFolder());
		final String nameTemplate = fileNames[0] + "--henkan-r%d." + Files.EXTENSION_JPG;

		String currentName = fileNames[0];

		for (int i = 0; i <= count; i++) {
			Files files = new Files(unext, currentName, Files.EXTENSION_JPG);
			unext.gazou(files);

			final Path result = dir.resolve(files.getDestFile().getName());
			currentName = String.format(nameTemplate, i);

			java.nio.file.Files.move(result, dir.resolve(currentName), StandardCopyOption.REPLACE_EXISTING);
		}
	}
}
