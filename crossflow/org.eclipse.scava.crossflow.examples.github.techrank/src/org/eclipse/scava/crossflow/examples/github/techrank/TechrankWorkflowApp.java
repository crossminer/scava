package org.eclipse.scava.crossflow.examples.github.techrank;

import org.eclipse.scava.crossflow.runtime.Mode;

public class TechrankWorkflowApp {

	public static void main(String[] args) throws Exception {
		
		TechrankWorkflow master = new TechrankWorkflow(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example TechrankWorkflow Instance");
		master.setName("TechrankWorkflow");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
