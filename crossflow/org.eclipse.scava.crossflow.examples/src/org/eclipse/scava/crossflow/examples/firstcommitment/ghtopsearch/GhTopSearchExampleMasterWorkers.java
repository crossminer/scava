package org.eclipse.scava.crossflow.examples.firstcommitment.ghtopsearch;

import java.io.File;

import org.eclipse.scava.crossflow.examples.utils.CloneUtils;
import org.eclipse.scava.crossflow.runtime.Mode;

public class GhTopSearchExampleMasterWorkers {
		
	final static File CLONE_PARENT_DESTINATION = new File(
			// level: same as this repo (scava)
			".." + File.separator + ".." + File.separator + "CLONED-REPOS");

	public static void main(String[] args) throws Exception {
		
		CloneUtils.removeRepoClones(CLONE_PARENT_DESTINATION); 
		
		GhTopSearchExample master = new GhTopSearchExample();
		master.setName("Master");
		
		GhTopSearchExample worker1 = new GhTopSearchExample(Mode.WORKER);
		worker1.setName("Worker1");
		
		GhTopSearchExample worker2 = new GhTopSearchExample(Mode.WORKER);
		worker2.setName("Worker2");
		
		master.run();
		worker1.run();
		worker2.run();
		
	}
	
}
