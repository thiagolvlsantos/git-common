package io.github.thiagolvlsantos.git.commons.file;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Test;

public class FileUtilsTest {

	private File root = new File("target", "git-commons");

	@Test
	public void testPrepareNew() throws IOException {
		File file = new File(root, "test.txt");
		assertTrue(FileUtils.prepare(file));
	}

	@Test
	public void testPrepareExisting() throws IOException {
		File file = File.createTempFile("git-commons", "_test.txt");
		assertTrue(FileUtils.prepare(file));
	}

	@Test
	public void testWriteOk() throws IOException {
		File file = new File(root, "test.txt");
		assertTrue(FileUtils.write(file, "OK")); // not exists
		assertTrue(FileUtils.write(file, "OK")); // already exists
	}

	@Test
	public void testDelete() throws IOException {
		File file = new File(root, "test.txt");
		assertTrue(FileUtils.write(file, "OK"));
	}

	@Test
	public void testDeleteChildren() throws IOException {
		File file = new File(root, "test.txt");
		assertTrue(FileUtils.write(file, "OK"));

		File sub1 = new File(root, "sub1");
		File child1 = new File(sub1, "child.txt");
		assertTrue(FileUtils.write(child1, "Child"));

		File empty = new File(root, "empty");
		assertTrue(empty.mkdirs());
	}

	@After
	public void clean() throws IOException {
		if (root.exists()) {
			if (!FileUtils.delete(root)) {
				throw new IOException("Could not delete.");
			}
		}
	}
}
