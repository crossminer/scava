package org.eclipse.scava.crossflow.tests.transactionalcaching;

import org.eclipse.scava.crossflow.runtime.Mode;

public class MinimalWorkflowApp {

	public static void main(String[] args) throws Exception {
		
		MinimalWorkflow master = new MinimalWorkflow(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example MinimalWorkflow Instance");
		master.setName("MinimalWorkflow");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
