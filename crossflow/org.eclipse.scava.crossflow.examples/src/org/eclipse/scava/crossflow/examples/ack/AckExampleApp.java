package org.eclipse.scava.crossflow.examples.ack;

import org.eclipse.scava.crossflow.runtime.Mode;

public class AckExampleApp {

	public static void main(String[] args) throws Exception {
		
		AckExample master = new AckExample(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example AckExample Instance");
		master.setName("AckExample");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
