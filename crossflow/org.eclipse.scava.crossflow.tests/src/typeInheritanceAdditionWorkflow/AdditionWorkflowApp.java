package typeInheritanceAdditionWorkflow;

import org.eclipse.scava.crossflow.runtime.Mode;

public class AdditionWorkflowApp {

	public static void main(String[] args) throws Exception {
		
		AdditionWorkflow master = new AdditionWorkflow(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example AdditionWorkflow Instance");
		master.setName("AdditionWorkflow");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
