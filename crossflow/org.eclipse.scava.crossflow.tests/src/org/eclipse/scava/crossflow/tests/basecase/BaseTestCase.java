package org.eclipse.scava.crossflow.tests.basecase;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.UUID;

import org.eclipse.scava.crossflow.runtime.Mode;
import org.junit.jupiter.api.Test;

class BaseTestCase {
	
	private final static long SLEEP_DURATION = 1000;
	
	@Test
	void testCase() {
		
		// setup test case configuration
		BaseCase master = new BaseCase();
		master.setName("Master-"+UUID.randomUUID().toString());
		master.setEnableCache(false);
		
		BaseCase worker = new BaseCase();
		worker.setName("Worker");
		worker.setMode(Mode.WORKER);
		
		master.addActiveWorkerId(worker.getName());
		master.addShutdownHook(new Runnable() {
			
			@Override
			public void run() {
				// assert statements		
				assertEquals( Mode.MASTER, master.getMode() );
				assertEquals( Mode.WORKER, worker.getMode() );
				assertNotNull( master.getAdditions() );
				assertNotNull( master.getAdditionResults() );
				
			}
		});
		
		try {
			// run test case configuration
			master.run();
			worker.run();
			
			// sleep / wait before test result assertion
			Thread.sleep(master.getTerminationTimeout() + SLEEP_DURATION);
			
			assertTrue(master.hasTerminated());
		
			
		} catch (Exception e1) {
			fail("Unexpected exception occurred during test case execution.");
			e1.printStackTrace();
			
		}
		
	}

}
