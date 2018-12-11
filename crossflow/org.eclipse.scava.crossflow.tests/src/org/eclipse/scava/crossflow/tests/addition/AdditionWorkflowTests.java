package org.eclipse.scava.crossflow.tests.addition;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;

import org.eclipse.scava.crossflow.runtime.BuiltinTopicConsumer;
import org.eclipse.scava.crossflow.runtime.DirectoryCache;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class AdditionWorkflowTests extends WorkflowTests {
	
	@Test
	public void testOutput() throws Exception {
		AdditionWorkflow workflow = new AdditionWorkflow();
		workflow.getNumberPairSource().setNumbers(Arrays.asList(1, 2));
		workflow.setTerminationTimeout(0);
		workflow.run();
		waitFor(workflow);
		assertArrayEquals(new Integer[] {2, 4}, workflow.getAdditionResultsSink().getNumbers().toArray());
	}
	
	@Test
	public void testCachingWithCache() throws Exception {
		
		AdditionWorkflow workflow = new AdditionWorkflow();
		workflow.getAdder().setCaching(true);
		workflow.setCache(new DirectoryCache());
		workflow.getNumberPairSource().setNumbers(Arrays.asList(1, 2));
		workflow.run();
		waitFor(workflow);
		
		AdditionWorkflow fresh = new AdditionWorkflow();
		fresh.getAdder().setCaching(true);
		fresh.setCache(new DirectoryCache(((DirectoryCache)workflow.getCache()).getDirectory()));
		fresh.getNumberPairSource().setNumbers(Arrays.asList(1, 2, 3));
		fresh.run();
		waitFor(fresh);
		
		assertEquals(1, fresh.getAdder().getExecutions());
		
	}
	
	@Test
	public void testCachingWithoutCache() throws Exception {
		
		AdditionWorkflow workflow = new AdditionWorkflow();
		workflow.getAdder().setCaching(true);
		workflow.getNumberPairSource().setNumbers(Arrays.asList(1, 2));
		workflow.run();
		waitFor(workflow);
		
		AdditionWorkflow fresh = new AdditionWorkflow();
		fresh.getAdder().setCaching(true);
		fresh.getNumberPairSource().setNumbers(Arrays.asList(1, 2, 3));
		fresh.run();
		waitFor(fresh);
		
		assertEquals(3, fresh.getAdder().getExecutions());
	}
	
	@Test
	public void testMasterWorker() throws Exception {
		AdditionWorkflow master = new AdditionWorkflow(Mode.MASTER);
		master.setTerminationTimeout(5000);
		master.getNumberPairSource().setNumbers(Arrays.asList(1, 2, 3, 4, 5));
		
		AdditionWorkflow worker = new AdditionWorkflow(Mode.WORKER);
		
		master.run();
		worker.run();
		
		waitFor(master);
		
		assertEquals(true, master.getAdder().getExecutions() < 5);
		assertEquals(5, worker.getAdder().getExecutions() + master.getAdder().getExecutions());
		
		assertArrayEquals(new Integer[] {2, 4, 6, 8, 10}, 
				master.getAdditionResultsSink().getNumbers().toArray());
	}
	
	@Test
	public void testBareMasterWorker() throws Exception {
		AdditionWorkflow master = new AdditionWorkflow(Mode.MASTER_BARE);
		master.setTerminationTimeout(5000);
		master.getNumberPairSource().setNumbers(Arrays.asList(1, 2, 3, 4, 5));
		
		AdditionWorkflow worker = new AdditionWorkflow(Mode.WORKER);
		
		master.run();
		worker.run();
		
		waitFor(master);
		
		assertNull(master.getAdder());
		assertEquals(5, worker.getAdder().getExecutions());
		
		assertArrayEquals(new Integer[] {2, 4, 6, 8, 10}, 
				master.getAdditionResultsSink().getNumbers().toArray());
	}
	
	@Test
	public void testNotifications() throws Exception {
		
		AdditionWorkflow master = new AdditionWorkflow();
		master.setName("master");
		master.getNumberPairSource().setNumbers(Arrays.asList(1, 2, 3, 4, 5));
		master.getTaskStatusPublisher().addConsumer(new BuiltinTopicConsumer<TaskStatus>() {
			
			@Override
			public void consume(TaskStatus t) {
				System.out.println(t.getCaller() + "/" + t.getStatus());
			}
		});
		master.run();
		waitFor(master);
		
	}
	
}
