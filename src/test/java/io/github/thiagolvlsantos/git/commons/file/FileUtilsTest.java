package io.github.thiagolvlsantos.git.commons.file;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class FileUtilsTest {

	@Test
	public void testCreate() throws IOException {
		File file = new File("target", "git-commons" + File.separator + "test.txt");
		assertTrue(FileUtils.write(file, "OK")); // not exists
		assertTrue(FileUtils.write(file, "OK")); // already exists
	}

	@Test
	public void testDelete() throws IOException {
		File file = new File("target", "git-commons" + File.separator + "test.txt");
		assertTrue(FileUtils.write(file, "OK"));
		assertTrue(FileUtils.delete(file.getParentFile()));
	}

	@Test
	public void testDeleteChildren() throws IOException {
		File root = new File("target", "git-commons");

		File file = new File(root, "test.txt");
		assertTrue(FileUtils.write(file, "OK"));

		File sub1 = new File(root, "sub1");
		File child1 = new File(sub1, "child.txt");
		assertTrue(FileUtils.write(child1, "Child"));

		File empty = new File(root, "empty");
		assertTrue(empty.mkdirs());

		assertTrue(FileUtils.delete(root));
	}
}
