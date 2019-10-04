package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

import org.eclipse.scava.crossflow.runtime.Mode;

public class MdeTechnologyCsvExampleApp {

	public static void main(String[] args) throws Exception {
		
		MdeTechnologyCsvExample master = new MdeTechnologyCsvExample(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example MdeTechnologyCsvExample Instance");
		master.setName("MdeTechnologyCsvExample");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
