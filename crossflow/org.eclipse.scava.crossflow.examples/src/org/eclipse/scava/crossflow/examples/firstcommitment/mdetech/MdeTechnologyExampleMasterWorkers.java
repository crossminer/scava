package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import java.io.File;

import org.eclipse.scava.crossflow.runtime.Mode;

public class MdeTechnologyExampleMasterWorkers {
		
	final static File CLONE_PARENT_DESTINATION = new File(
			// level: same as this repo (scava)
			".." + File.separator + ".." + File.separator + "CLONED-REPOS");

	public static void main(String[] args) throws Exception {
		
		CloneUtils.removeRepoClones(CLONE_PARENT_DESTINATION); 
		
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
		
	}
	
}
