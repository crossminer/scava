package org.eclipse.scava.crossflow.examples.github.topsearch;

import org.eclipse.scava.crossflow.runtime.FailedJob;
import org.eclipse.scava.crossflow.runtime.InternalException;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.utils.CloneUtils;

public class SearchApp {

	public static void main(String[] args) throws Exception {
		
		CloneUtils.removeRepoClones(SearchRepoProperties.CLONE_PARENT_DESTINATION); 
		
		SearchWorkflow master = new SearchWorkflow(Mode.MASTER);
		master.setName("Master");
		
		SearchWorkflow worker1 = new SearchWorkflow(Mode.WORKER);
		worker1.setName("Worker1");
		
		SearchWorkflow worker2 = new SearchWorkflow(Mode.WORKER);
		worker2.setName("Worker2");
		
		master.run();
		worker1.run();
		worker2.run();
		
		// master
		while (!master.hasTerminated()) {
			Thread.sleep(100);
		}
		
		for (InternalException ex : master.getInternalExceptions()) {
			System.err.println(ex.getStacktrace());
		}
		
		for (FailedJob failed : master.getFailedJobs()) {
			System.err.println(failed.getStacktrace());
		}
		
		// worker1
		while (!worker1.hasTerminated()) {
			Thread.sleep(100);
		}
		
		for (InternalException ex : worker1.getInternalExceptions()) {
			System.err.println(ex.getStacktrace());
		}
		
		for (FailedJob failed : worker1.getFailedJobs()) {
			System.err.println(failed.getStacktrace());
		}
		
		// worker2
		while (!worker2.hasTerminated()) {
			Thread.sleep(100);
		}
		
		for (InternalException ex : worker2.getInternalExceptions()) {
			System.err.println(ex.getStacktrace());
		}
		
		for (FailedJob failed : worker2.getFailedJobs()) {
			System.err.println(failed.getStacktrace());
		}
		
		System.out.println("Done");
		
	}
	
}
