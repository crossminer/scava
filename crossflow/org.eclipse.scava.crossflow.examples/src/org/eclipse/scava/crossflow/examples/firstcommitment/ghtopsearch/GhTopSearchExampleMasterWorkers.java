package org.eclipse.scava.crossflow.examples.firstcommitment.ghtopsearch;

import java.io.File;

import org.eclipse.scava.crossflow.runtime.utils.CloneUtils;
import org.eclipse.scava.crossflow.runtime.FailedJob;
import org.eclipse.scava.crossflow.runtime.InternalException;
import org.eclipse.scava.crossflow.runtime.Mode;

public class GhTopSearchExampleMasterWorkers {

	public static void main(String[] args) throws Exception {
		
		CloneUtils.removeRepoClones(GhTopSearchRepoProperties.CLONE_PARENT_DESTINATION); 
		
		GhTopSearchExample master = new GhTopSearchExample(Mode.MASTER);
		master.setName("Master");
		
		GhTopSearchExample worker1 = new GhTopSearchExample(Mode.WORKER);
		worker1.setName("Worker1");
		
		GhTopSearchExample worker2 = new GhTopSearchExample(Mode.WORKER);
		worker2.setName("Worker2");
		
		master.run();
		worker1.run();
		worker2.run();
		
		// master
		while (!master.hasTerminated()) {
			Thread.sleep(100);
		}
		
		for (InternalException ex : master.getInternalExceptions()) {
			ex.getException().printStackTrace();
		}
		
		for (FailedJob failed : master.getFailedJobs()) {
			failed.getException().printStackTrace();
		}
		
		// worker1
		while (!worker1.hasTerminated()) {
			Thread.sleep(100);
		}
		
		for (InternalException ex : worker1.getInternalExceptions()) {
			ex.getException().printStackTrace();
		}
		
		for (FailedJob failed : worker1.getFailedJobs()) {
			failed.getException().printStackTrace();
		}
		
		// worker2
		while (!worker2.hasTerminated()) {
			Thread.sleep(100);
		}
		
		for (InternalException ex : worker2.getInternalExceptions()) {
			ex.getException().printStackTrace();
		}
		
		for (FailedJob failed : worker2.getFailedJobs()) {
			failed.getException().printStackTrace();
		}
		
		System.out.println("Done");
		
	}
	
}
