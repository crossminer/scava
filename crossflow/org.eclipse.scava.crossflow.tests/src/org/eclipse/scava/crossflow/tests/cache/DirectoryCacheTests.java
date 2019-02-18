package org.eclipse.scava.crossflow.tests.cache;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
	
}
