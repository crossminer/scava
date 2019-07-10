package org.eclipse.scava.crossflow.tests.matrix;

import org.eclipse.scava.crossflow.runtime.Mode;

public class MatrixWorkflowApp {

	public static void main(String[] args) throws Exception {
		
		MatrixWorkflow master = new MatrixWorkflow(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example MatrixWorkflow Instance");
		master.setName("MatrixWorkflow");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
