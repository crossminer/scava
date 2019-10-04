package org.eclipse.scava.crossflow.tests.wordcount;

import org.eclipse.scava.crossflow.runtime.Mode;

public class WordCountWorkflowWorker {
	
	public static void main(String[] args) throws Exception {
		
		WordCountWorkflow worker1 = new WordCountWorkflow(Mode.WORKER);
		worker1.setName("Eclipse");
		worker1.setInstanceId("wordcount");
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
