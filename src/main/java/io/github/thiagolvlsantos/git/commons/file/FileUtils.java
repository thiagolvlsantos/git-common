package io.github.thiagolvlsantos.git.commons.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FileUtils {

	public boolean write(File file, String content) throws IOException {
		boolean ok = prepare(file);
		Files.write(file.toPath(), String.valueOf(content).getBytes(), StandardOpenOption.CREATE,
				StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
		return ok;
	}

	public boolean prepare(File file) {
		File dir = file.getParentFile();
		if (dir.exists()) {
			return true;
		}
		return dir.mkdirs();
	}

	public boolean delete(File file) throws IOException {
		if (file.isDirectory()) {
			File[] children = file.listFiles();
			for (File c : children) {
				delete(c);
			}
		}
		Files.delete(file.toPath());
		return true;
	}
}