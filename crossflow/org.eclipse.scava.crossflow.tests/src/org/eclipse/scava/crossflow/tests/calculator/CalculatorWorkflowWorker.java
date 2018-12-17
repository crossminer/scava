package org.eclipse.scava.crossflow.tests.calculator;

import org.eclipse.scava.crossflow.runtime.Mode;

public class CalculatorWorkflowWorker {
	
	public static void main(String[] args) throws Exception {
		
		CalculatorWorkflow worker1 = new CalculatorWorkflow(Mode.WORKER);
		worker1.setName("Eclipse");
		worker1.getCalculator().setDelay(1000);
		worker1.setInstanceId("calculator");
		worker1.run();
		
		/*
		CalculatorWorkflow worker2 = new CalculatorWorkflow(Mode.WORKER);
		worker2.setName("Worker 2");
		worker2.getCalculator().setDelay(1000);
		worker2.setInstanceId("calculator");
		worker2.run();
		*/
		
		while (!worker1.hasTerminated() /*|| !worker2.hasTerminated()*/) {
			Thread.sleep(100);
		}
		
	}
	
}
