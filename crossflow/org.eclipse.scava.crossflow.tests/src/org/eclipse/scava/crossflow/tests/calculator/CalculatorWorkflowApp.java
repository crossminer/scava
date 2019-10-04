package org.eclipse.scava.crossflow.tests.calculator;

import org.eclipse.scava.crossflow.runtime.Mode;

public class CalculatorWorkflowApp {

	public static void main(String[] args) throws Exception {
		
		CalculatorWorkflow master = new CalculatorWorkflow(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example CalculatorWorkflow Instance");
		master.setName("CalculatorWorkflow");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
