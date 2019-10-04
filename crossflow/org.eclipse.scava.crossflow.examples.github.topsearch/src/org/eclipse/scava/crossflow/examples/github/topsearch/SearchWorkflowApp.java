package org.eclipse.scava.crossflow.examples.github.topsearch;

import org.eclipse.scava.crossflow.runtime.Mode;

public class SearchWorkflowApp {

	public static void main(String[] args) throws Exception {
		
		SearchWorkflow master = new SearchWorkflow(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example SearchWorkflow Instance");
		master.setName("SearchWorkflow");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
