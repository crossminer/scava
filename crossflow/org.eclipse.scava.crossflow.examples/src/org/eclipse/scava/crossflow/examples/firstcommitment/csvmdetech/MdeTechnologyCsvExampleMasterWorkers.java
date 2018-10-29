package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

import java.io.File;

import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.utils.CloneUtils;

public class MdeTechnologyCsvExampleMasterWorkers {
		
	final static File CLONE_PARENT_DESTINATION = new File(
			// level: same as this repo (scava)
			".." + File.separator + ".." + File.separator + "CLONED-REPOS");

	public static void main(String[] args) throws Exception {
		
		CloneUtils.removeRepoClones(CLONE_PARENT_DESTINATION); 
		
		MdeTechnologyCsvExample master = new MdeTechnologyCsvExample();
		master.setName("Master");
		
		MdeTechnologyCsvExample worker1 = new MdeTechnologyCsvExample();
		worker1.setMode(Mode.WORKER);
		worker1.setName("Worker1");
		
		MdeTechnologyCsvExample worker2 = new MdeTechnologyCsvExample();
		worker2.setMode(Mode.WORKER);
		worker2.setName("Worker2");
		
		master.run();
		worker1.run();
		worker2.run();
		
	}
	
}
