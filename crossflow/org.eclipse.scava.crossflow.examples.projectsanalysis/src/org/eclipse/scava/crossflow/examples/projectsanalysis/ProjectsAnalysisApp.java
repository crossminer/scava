package org.eclipse.scava.crossflow.examples.projectsanalysis;

import java.io.File;

import org.eclipse.scava.crossflow.runtime.Mode;

public class ProjectsAnalysisApp {

	public static void main(String[] args) throws Exception {
		
		ProjectsAnalysis master = new ProjectsAnalysis(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		ProjectsAnalysis worker1 = new ProjectsAnalysis(Mode.WORKER);
		worker1.setMaster("localhost");
		
		ProjectsAnalysis worker2 = new ProjectsAnalysis(Mode.WORKER);
		worker2.setMaster("localhost");

		
		//master.setParallelization(4);
		
		master.setInputDirectory(new File("experiment/in"));
		master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example ProjectsAnalysis Instance");
		master.setName("ProjectsAnalysis");
		
		master.run();
		worker1.run();
		worker2.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
