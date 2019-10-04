package org.eclipse.scava.crossflow.tests.crawler;

import org.eclipse.scava.crossflow.runtime.Mode;

public class CrawlerWorkflowApp {

	public static void main(String[] args) throws Exception {
		
		CrawlerWorkflow master = new CrawlerWorkflow(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example CrawlerWorkflow Instance");
		master.setName("CrawlerWorkflow");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
