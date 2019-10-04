package org.eclipse.scava.crossflow.tests.opinionated;

import org.eclipse.scava.crossflow.runtime.Mode;

public class OpinionatedWorkflowApp {

	public static void main(String[] args) throws Exception {
		
		OpinionatedWorkflow master = new OpinionatedWorkflow(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example OpinionatedWorkflow Instance");
		master.setName("OpinionatedWorkflow");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
