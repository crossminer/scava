package rWorkflow;

import org.eclipse.scava.crossflow.runtime.Mode;

public class RWorkflowApp {

	public static void main(String[] args) throws Exception {
		
		RWorkflow master = new RWorkflow(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example RWorkflow Instance");
		master.setName("RWorkflow");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
