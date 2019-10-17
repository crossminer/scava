package org.eclipse.scava.crossflow.tests.opinionated;

import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class OccurencesWorkflowTests extends WorkflowTests {

	@Test
	public void testWorkflow() throws Exception {

		HashMap<String, Integer> wordMap = new HashMap<>();
		wordMap.put("Apple", 3);
		wordMap.put("Banana", 2);

		OpinionatedWorkflow master = new OpinionatedWorkflow();
		master.createBroker(createBroker);
		master.getOccurencesMonitor().setFavouriteWord("Apple");
		master.getWordSource().setWordMap(wordMap);

		OpinionatedWorkflow worker = master.createWorker();
		worker.getOccurencesMonitor().setFavouriteWord("Banana");

		master.run();
		worker.run(500);

		waitFor(master);

		assertEquals(3, master.getOccurencesMonitor().getOccurences());
		assertEquals(2, worker.getOccurencesMonitor().getOccurences());

	}

}
