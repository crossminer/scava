package org.eclipse.scava.crossflow.tests.wordcount;

import java.io.File;

import org.eclipse.scava.crossflow.runtime.Mode;

public class WordCountWorkflowApp {

	public static void main(String[] args) throws Exception {
		
		WordCountWorkflow master = new WordCountWorkflow(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		master.setInputDirectory(new File("inputs/wordcount"));
		master.setOutputDirectory(new File("outputs/wordcount"));
		
		master.setInstanceId("Example WordCountWorkflow Instance");
		master.setName("WordCountWorkflow");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
