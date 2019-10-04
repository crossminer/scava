package org.eclipse.scava.crossflow.examples.techanalysis;

import org.eclipse.scava.crossflow.runtime.Mode;

public class GitHubTechnologyAnalysisApp {

	public static void main(String[] args) throws Exception {
		
		GitHubTechnologyAnalysis master = new GitHubTechnologyAnalysis(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example GitHubTechnologyAnalysis Instance");
		master.setName("GitHubTechnologyAnalysis");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
