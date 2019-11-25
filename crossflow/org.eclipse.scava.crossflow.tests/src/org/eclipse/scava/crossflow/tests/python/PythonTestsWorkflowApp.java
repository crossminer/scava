package org.eclipse.scava.crossflow.tests.python;

import org.eclipse.scava.crossflow.runtime.Mode;

public class PythonTestsWorkflowApp {

	public static void main(String[] args) throws Exception {
		
		PythonTestsWorkflow master = new PythonTestsWorkflow(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example PythonTestsWorkflow Instance");
		master.setName("PythonTestsWorkflow");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
