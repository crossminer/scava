package org.eclipse.scava.crossflow.tests.minimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.crossflow.runtime.DirectoryCache;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class MinimalWorkflowTests extends WorkflowTests {
	
	@Test
	public void testCache() throws Exception {
		
		List<Integer> numbers = Arrays.asList(1,1);
		
		MinimalWorkflow workflow = new MinimalWorkflow();
		workflow.getMinimalSource().setNumbers(numbers);
		DirectoryCache cache = new DirectoryCache();
		workflow.setCache(cache);
		workflow.run();
		
		waitFor(workflow);
		
		assertEquals(2, workflow.getMinimalSink().getNumbers().size());
		assertEquals(2, workflow.getCopierTask().getExecutions());
		
		workflow = new MinimalWorkflow();
		workflow.getMinimalSource().setNumbers(numbers);
		workflow.setCache(new DirectoryCache(cache.getDirectory()));
		workflow.run();
		
		waitFor(workflow);
		
		assertEquals(2, workflow.getMinimalSink().getNumbers().size());
		assertEquals(0, workflow.getCopierTask().getExecutions());
	}
	
}
