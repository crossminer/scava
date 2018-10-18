package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import java.io.File;

import org.eclipse.scava.crossflow.runtime.Mode;

public class MdeTechnologyExampleMasterWorkers {
	
	private static final int COMPLETION_WAITING_TIME = 10000;
	
	final static File CLONE_PARENT_DESTINATION = new File(
			// level: same as this repo (scava)
			".." + File.separator + ".." + File.separator + ".." + File.separator + File.separator + "CLONED-REPOS");

	public static void main(String[] args) throws Exception {
		
		CloneUtils.cleanLocalParentCloneDirectory(CLONE_PARENT_DESTINATION); 
		
		MdeTechnologyExample master = new MdeTechnologyExample();
		master.setName("Master");
		
		MdeTechnologyExample worker1 = new MdeTechnologyExample();
		worker1.setMode(Mode.WORKER);
		worker1.setName("Worker1");
		
		MdeTechnologyExample worker2 = new MdeTechnologyExample();
		worker2.setMode(Mode.WORKER);
		worker2.setName("Worker2");
		
		master.run();
		worker1.run();
		worker2.run();
				
		Thread.sleep(COMPLETION_WAITING_TIME); // to allow printout after run is completed

		System.out.println("\nPRINTING EXECUTION STATISTICS ...\n");
		
		printStatistics(master);
		printStatistics(worker1);
		printStatistics(worker2);
		
		System.out.println("... COMPLETED !");
		
		//System.exit(0);
		
	}
	
	private static void printStatistics(MdeTechnologyExample mdeTechnologyExample) {
		// TODO
	}
	
}
