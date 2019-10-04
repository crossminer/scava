package org.eclipse.scava.crossflow.tests.commitment;

import org.eclipse.scava.crossflow.runtime.Mode;

public class CommitmentWorkflowApp {

	public static void main(String[] args) throws Exception {
		
		CommitmentWorkflow master = new CommitmentWorkflow(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example CommitmentWorkflow Instance");
		master.setName("CommitmentWorkflow");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
