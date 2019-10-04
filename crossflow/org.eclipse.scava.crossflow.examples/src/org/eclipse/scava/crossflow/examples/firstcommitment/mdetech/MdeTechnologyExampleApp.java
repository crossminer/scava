package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import org.eclipse.scava.crossflow.runtime.Mode;

public class MdeTechnologyExampleApp {

	public static void main(String[] args) throws Exception {
		
		MdeTechnologyExample master = new MdeTechnologyExample(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example MdeTechnologyExample Instance");
		master.setName("MdeTechnologyExample");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
