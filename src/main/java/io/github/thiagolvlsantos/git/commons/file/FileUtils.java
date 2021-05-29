package io.github.thiagolvlsantos.git.commons.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FileUtils {

	public static void write(File f, String content) throws IOException {
		if (!f.exists() && !prepare(f)) {
			throw new IOException("Unable to setup file structure.");
		}
		Files.write(f.toPath(), String.valueOf(content).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE,
				StandardOpenOption.TRUNCATE_EXISTING);
	}

	public static boolean prepare(File f) {
		File dir = f.getParentFile();
		return dir.exists() || dir.mkdirs();
	}

	public static boolean delete(File f) {
		boolean ok = true;
		if (f.isDirectory()) {
			File[] children = f.listFiles();
			for (File c : children) {
				ok = ok & delete(c);
			}
		}
		return ok & f.delete();
	}
}