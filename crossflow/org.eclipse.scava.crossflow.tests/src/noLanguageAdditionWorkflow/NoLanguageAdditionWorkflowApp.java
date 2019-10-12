package noLanguageAdditionWorkflow;

import org.eclipse.scava.crossflow.runtime.Mode;

public class NoLanguageAdditionWorkflowApp {

	public static void main(String[] args) throws Exception {
		
		NoLanguageAdditionWorkflow master = new NoLanguageAdditionWorkflow(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example NoLanguageAdditionWorkflow Instance");
		master.setName("NoLanguageAdditionWorkflow");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
