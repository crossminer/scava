package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

import org.eclipse.scava.crossflow.runtime.Mode;

public class GhRepoExampleApp {

	public static void main(String[] args) throws Exception {
		
		GhRepoExample master = new GhRepoExample(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example GhRepoExample Instance");
		master.setName("GhRepoExample");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
