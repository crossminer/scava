package org.eclipse.scava.crossflow.examples.wordcount;

import org.eclipse.scava.crossflow.runtime.FailedJob;
import org.eclipse.scava.crossflow.runtime.InternalException;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.utils.CloneUtils;
import org.eclipse.scava.crossflow.examples.wordcount.WordCountWorkflow;

public class WordCountApp {

	public static void main(String[] args) throws Exception {
		
		WordCountWorkflow master = new WordCountWorkflow(Mode.MASTER);
		master.setName("Master");
		
		WordCountWorkflow worker1 = new WordCountWorkflow(Mode.WORKER);
		worker1.setName("Worker1");
		
		WordCountWorkflow worker2 = new WordCountWorkflow(Mode.WORKER);
		worker2.setName("Worker2");
		
		master.run();
		worker1.run();
//		worker2.run();
		
		// master
		while (!master.hasTerminated()) {
			Thread.sleep(100);
		}
		
		for (InternalException ex : master.getInternalExceptions()) {
			ex.getException().printStackTrace();
		}
		
		for (FailedJob failed : master.getFailedJobs()) {
			failed.getException().printStackTrace();
		}
		
		// worker1
		while (!worker1.hasTerminated()) {
			Thread.sleep(100);
		}
		
		for (InternalException ex : worker1.getInternalExceptions()) {
			ex.getException().printStackTrace();
		}
		
		for (FailedJob failed : worker1.getFailedJobs()) {
			failed.getException().printStackTrace();
		}
		
		// worker2
		while (!worker2.hasTerminated()) {
			Thread.sleep(100);
		}
		
		for (InternalException ex : worker2.getInternalExceptions()) {
			ex.getException().printStackTrace();
		}
		
		for (FailedJob failed : worker2.getFailedJobs()) {
			failed.getException().printStackTrace();
		}
		
		System.out.println("Done");
		
	}
	
}
