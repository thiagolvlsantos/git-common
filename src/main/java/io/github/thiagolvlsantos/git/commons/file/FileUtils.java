package io.github.thiagolvlsantos.git.commons.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FileUtils {

	public static boolean write(File file, String content) throws IOException {
		if (!file.exists()) {
			boolean ok = prepare(file);
			if (!ok) {
				throw new IOException("Unable to setup file structure.");
			}
		}
		Files.write(file.toPath(), String.valueOf(content).getBytes(), StandardOpenOption.CREATE,
				StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
		return true;
	}

	public static boolean prepare(File file) {
		File dir = file.getParentFile();
		if (dir.exists()) {
			return true;
		}
		return dir.mkdirs();
	}

	public static boolean delete(File file) throws IOException {
		boolean ok = true;
		if (file.isDirectory()) {
			File[] children = file.listFiles();
			for (File c : children) {
				ok = delete(c) & ok;
			}
		}
		Files.delete(file.toPath());
		return ok;
	}
}