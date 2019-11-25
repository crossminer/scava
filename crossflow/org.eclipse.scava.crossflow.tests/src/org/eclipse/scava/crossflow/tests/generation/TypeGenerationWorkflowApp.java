package org.eclipse.scava.crossflow.tests.generation;

import org.eclipse.scava.crossflow.runtime.Mode;

public class TypeGenerationWorkflowApp {

	public static void main(String[] args) throws Exception {
		
		TypeGenerationWorkflow master = new TypeGenerationWorkflow(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example TypeGenerationWorkflow Instance");
		master.setName("TypeGenerationWorkflow");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
