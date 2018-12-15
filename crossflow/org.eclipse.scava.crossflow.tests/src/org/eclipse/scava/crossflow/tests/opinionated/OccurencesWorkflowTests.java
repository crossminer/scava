package org.eclipse.scava.crossflow.tests.opinionated;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class OccurencesWorkflowTests extends WorkflowTests {
	
	@Test
	public void testWorkflow() throws Exception {
		
		HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
		wordMap.put("Apple", 3);
		wordMap.put("Banana", 2);
		
		OpinionatedWorkflow master = new OpinionatedWorkflow();
		master.setFavouriteWord("Apple");
		master.getWordSource().setWordMap(wordMap);
		
		OpinionatedWorkflow worker = master.createWorker();
		worker.setFavouriteWord("Banana");
		
		master.run();
		worker.run();
		
		waitFor(master);
		
		assertEquals(3, master.getOccurencesMonitor().getOccurences());
		assertEquals(2, worker.getOccurencesMonitor().getOccurences());
		
	}
	
}
