package org.eclipse.scava.crossflow.examples.github.topsearch;

import java.io.File;

import org.eclipse.scava.crossflow.runtime.FailedJob;
import org.eclipse.scava.crossflow.runtime.InternalException;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.utils.CloneUtils;

public class GhTopSearchApp {

	public static void main(String[] args) throws Exception {
		
		CloneUtils.removeRepoClones(GhTopSearchRepoProperties.CLONE_PARENT_DESTINATION); 
		
		GhTopSearchWorkflow master = new GhTopSearchWorkflow(Mode.MASTER);
		master.setName("Master");
		
		GhTopSearchWorkflow worker1 = new GhTopSearchWorkflow(Mode.WORKER);
		worker1.setName("Worker1");
		
		GhTopSearchWorkflow worker2 = new GhTopSearchWorkflow(Mode.WORKER);
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
