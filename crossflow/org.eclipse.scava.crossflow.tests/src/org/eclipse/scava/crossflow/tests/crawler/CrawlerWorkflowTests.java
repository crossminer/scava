package org.eclipse.scava.crossflow.tests.crawler;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;

import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class CrawlerWorkflowTests extends WorkflowTests {
	
	@Test
	public void testOutput() throws Exception {
		
		CrawlerWorkflow master = new CrawlerWorkflow(Mode.MASTER_BARE);
		CrawlerWorkflow worker = new CrawlerWorkflow(Mode.WORKER);
		
		master.run();
		worker.run();
		
		waitFor(master);
		
		assertNull(worker.getUrlCollector());
		assertArrayEquals(new String[] {"index.html", "a.html", "b.html"}, 
				master.getUrlCollector().getLocations().toArray());

	}
	
}
