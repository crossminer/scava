package org.eclipse.scava.crossflow.tests.exceptions;

import org.eclipse.scava.crossflow.runtime.Mode;

public class ExceptionsWorkflowApp {

	public static void main(String[] args) throws Exception {
		
		ExceptionsWorkflow master = new ExceptionsWorkflow(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example ExceptionsWorkflow Instance");
		master.setName("ExceptionsWorkflow");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
