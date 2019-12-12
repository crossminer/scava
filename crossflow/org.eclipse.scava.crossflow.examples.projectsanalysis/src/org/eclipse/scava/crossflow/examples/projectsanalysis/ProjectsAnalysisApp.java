package org.eclipse.scava.crossflow.examples.projectsanalysis;

import org.eclipse.scava.crossflow.runtime.Mode;

public class ProjectsAnalysisApp {

	public static void main(String[] args) throws Exception {
		
		ProjectsAnalysis master = new ProjectsAnalysis(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example ProjectsAnalysis Instance");
		master.setName("ProjectsAnalysis");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
