package org.eclipse.scava.crossflow.tests.multiflow;

import org.eclipse.scava.crossflow.runtime.Mode;

public class MultiflowApp {

	public static void main(String[] args) throws Exception {
		
		Multiflow master = new Multiflow(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example Multiflow Instance");
		master.setName("Multiflow");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
