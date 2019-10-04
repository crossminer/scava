package org.eclipse.scava.crossflow.tests.ack;

import org.eclipse.scava.crossflow.runtime.Mode;

public class AckWorkflowApp {

	public static void main(String[] args) throws Exception {
		
		AckWorkflow master = new AckWorkflow(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example AckWorkflow Instance");
		master.setName("AckWorkflow");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
