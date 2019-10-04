package org.eclipse.scava.crossflow.tests.multisource;

import org.eclipse.scava.crossflow.runtime.Mode;

public class MSWorkflowApp {

	public static void main(String[] args) throws Exception {
		
		MSWorkflow master = new MSWorkflow(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example MSWorkflow Instance");
		master.setName("MSWorkflow");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
