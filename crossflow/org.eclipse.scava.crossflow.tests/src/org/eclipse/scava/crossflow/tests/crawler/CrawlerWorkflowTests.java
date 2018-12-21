package org.eclipse.scava.crossflow.tests.crawler;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Collectors;

import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.eclipse.scava.crossflow.tests.util.BuiltinStreamRecorder;
import org.junit.Test;

public class CrawlerWorkflowTests extends WorkflowTests {
	
	@Test
	public void testOutput() throws Exception {
		
		CrawlerWorkflow master = new CrawlerWorkflow(Mode.MASTER_BARE);
		CrawlerWorkflow worker = master.createWorker();
		
		master.run();
		worker.run();
		
		waitFor(master);
		
		assertNull(worker.getUrlCollector());
		assertArrayEquals(new String[] {"index.html", "a.html", "b.html"}, 
				master.getUrlCollector().getLocations().toArray());

	}
	
	@Test
	public void testTaskStatusNotifications() throws Exception {
		
		CrawlerWorkflow master = new CrawlerWorkflow(Mode.MASTER_BARE);
		CrawlerWorkflow worker = master.createWorker();
		
		BuiltinStreamRecorder<TaskStatus> recorder = new BuiltinStreamRecorder<>();
		master.getTaskStatusTopic().addConsumer(recorder);
		
		master.run();
		worker.run();
		
		waitFor(master);
		
		// (3 pages + 1 duplicate URL) x 2 statuses 
		assertEquals(8, recorder.getRecorded().
			stream().filter(r -> r.getCaller().startsWith("UrlCollector")).
				collect(Collectors.toList()).size());
		
		// 3 pages to analyse x 2 statuses
		assertEquals(6, recorder.getRecorded().
				stream().filter(r -> r.getCaller().startsWith("UrlAnalyser")).
					collect(Collectors.toList()).size());
	}
	
}
