package org.eclipse.scava.crossflow.tests.cache;

import static org.junit.Assert.*;
import java.io.File;
import org.eclipse.scava.crossflow.runtime.DirectoryCache;
import org.eclipse.scava.crossflow.tests.addition.NumberPair;
import org.eclipse.scava.crossflow.tests.minimal.MinimalWorkflow;
import org.junit.Test;

public class DirectoryCacheTests {

	@Test
	public void testCache() throws Exception {

		NumberPair input = new NumberPair(1, 2);
		input.setDestination("Additions");
		NumberPair output = new NumberPair(2, 4);
		output.setCorrelationId(input.getId());

		DirectoryCache cache = new DirectoryCache();
		cache.setWorkflow(new MinimalWorkflow());
		File directory = cache.getDirectory();
		cache.cache(input);
		cache.cache(output);

		assertTrue(cache.hasCachedOutputs(input));
		assertFalse(cache.hasCachedOutputs(output));
		assertEquals(2, ((NumberPair) cache.getCachedOutputs(input).get(0)).getA());
		assertEquals(4, ((NumberPair) cache.getCachedOutputs(input).get(0)).getB());

		DirectoryCache fresh = new DirectoryCache(directory);
		fresh.setWorkflow(new MinimalWorkflow());

		assertTrue(fresh.hasCachedOutputs(input));
		assertFalse(fresh.hasCachedOutputs(output));
		assertEquals(2, ((NumberPair) fresh.getCachedOutputs(input).get(0)).getA());
		assertEquals(4, ((NumberPair) fresh.getCachedOutputs(input).get(0)).getB());

	}

	@Test
	public void testCacheDeletion() throws Exception {
		testCacheDeletionActual(true, true);
		testCacheDeletionActual(false, true);
		testCacheDeletionActual(true, false);
		testCacheDeletionActual(false, false);
	}

	public void testCacheDeletionActual(boolean cache1, boolean cache2) throws Exception {

		NumberPair input = new NumberPair(1, 2);
		input.setDestination("Additions");
		NumberPair output = new NumberPair(2, 4);
		output.setCorrelationId(input.getId());

		DirectoryCache cache = new DirectoryCache();
		cache.setWorkflow(new MinimalWorkflow());

		if (cache1)
			cache.cache(input);
		if (cache1)
			cache.cache(output);

		if (cache1)
			assertTrue(cache.hasCachedOutputs(input));

		System.out.println(FileAssert.printDirectoryTree(cache.getDirectory()));

		cache.clear();

		System.out.println(FileAssert.printDirectoryTree(cache.getDirectory()));

		assertFalse(cache.hasCachedOutputs(input));

		if (cache2)
			cache.cache(input);
		if (cache2)
			cache.cache(output);

		if (cache2)
			assertTrue(cache.hasCachedOutputs(input));

		System.out.println(FileAssert.printDirectoryTree(cache.getDirectory()));

		cache.clear("Additions");

		System.out.println(FileAssert.printDirectoryTree(cache.getDirectory()));

		assertFalse(cache.hasCachedOutputs(input));

	}

	/**
	 * 
	 * @author Konstantinos Barmpis, adapted from:
	 *         https://stackoverflow.com/questions/10655085/print-directory-tree
	 *
	 */
	public static class FileAssert {

		/**
		 * Pretty print the directory tree and its file names.
		 * 
		 * @param folder must be a directory.
		 * @return
		 */
		public static String printDirectoryTree(File folder) {
			if (!folder.isDirectory()) {
				throw new IllegalArgumentException("'folder' is not a Directory");
			}
			int indent = 0;
			StringBuilder sb = new StringBuilder();
			printDirectoryTree(folder, indent, sb);
			return sb.toString();
		}

		private static void printDirectoryTree(File folder, int indent, StringBuilder sb) {
			if (!folder.isDirectory()) {
				throw new IllegalArgumentException("'folder' is not a Directory");
			}
			sb.append(getIndentString(indent));
			sb.append("+--");
			sb.append(folder.getName());
			sb.append("/");
			sb.append("\n");
			for (File file : folder.listFiles()) {
				if (file.isDirectory()) {
					printDirectoryTree(file, indent + 1, sb);
				} else {
					printFile(file, indent + 1, sb);
				}
			}

		}

		private static void printFile(File file, int indent, StringBuilder sb) {
			sb.append(getIndentString(indent));
			sb.append("+--");
			sb.append(file.getName());
			sb.append("\n");
		}

		private static String getIndentString(int indent) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < indent; i++) {
				sb.append("|  ");
			}
			return sb.toString();
		}
	}

}
