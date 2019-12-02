package orphanqueuesworkflow;

import org.eclipse.scava.crossflow.runtime.Mode;

public class orphanqueuesworkflowApp {

	public static void main(String[] args) throws Exception {
		
		orphanqueuesworkflow master = new orphanqueuesworkflow(Mode.MASTER);
		master.createBroker(true);
		master.setMaster("localhost");
		
		//master.setParallelization(4);
		
		//master.setInputDirectory(new File("experiment/in"));
		//master.setOutputDirectory(new File("experiment/out"));
		
		master.setInstanceId("Example orphanqueuesworkflow Instance");
		master.setName("orphanqueuesworkflow");
		
		master.run();
		
		master.awaitTermination();
		
		System.out.println("Done");
		
	}
	
}
