package org.eclipse.scava.crossflow.examples.wordcount;

import java.io.File;

import org.eclipse.scava.crossflow.runtime.Mode;

public class WordCountWorkflowApp {

	public static void main(String[] args) throws Exception {
		
		WordCountWorkflow master = new WordCountWorkflow(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		master.setInputDirectory(new File("artifacts/in"));
		master.setOutputDirectory(new File("artifacts/out"));
		
		master.setInstanceId("Example WordCountWorkflow Instance");
		master.setName("WordCountWorkflow");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
