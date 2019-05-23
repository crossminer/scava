package org.eclipse.scava.crossflow.examples.wordcount;

import java.io.File;

import org.eclipse.scava.crossflow.examples.wordcount.WordCountWorkflow;
import org.eclipse.scava.crossflow.runtime.FailedJob;
import org.eclipse.scava.crossflow.runtime.InternalException;
import org.eclipse.scava.crossflow.runtime.Mode;

public class WordCountStandaloneExecution {
	
	public WordCountStandaloneExecution() throws Exception {
		WordCountWorkflow master = new WordCountWorkflow(Mode.MASTER);
		master.createBroker(true);
		master.setInputDirectory(new File("experiment/in"));
		master.setOutputDirectory(new File("experiment/out"));
		master.setName("Master");
		master.setInstanceId("wc");
		
		WordCountWorkflow worker1 = new WordCountWorkflow(Mode.WORKER);
		worker1.setName("Worker1");
		worker1.setInstanceId("wc");
		
		WordCountWorkflow worker2 = new WordCountWorkflow(Mode.WORKER);
		worker2.setName("Worker2");
		worker2.setInstanceId("wc");
		
		master.run();
		worker1.run();
		worker2.run();
		
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
	
		System.out.println("Done");
	}

	public static void main(String[] args) throws Exception {
		// setup and launch experiment
		WordCountStandaloneExecution app = new WordCountStandaloneExecution();
	}
	
}
