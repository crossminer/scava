package org.eclipse.scava.crossflow.examples.github.techrank;

import java.io.File;

import org.eclipse.scava.crossflow.runtime.DirectoryCache;
import org.eclipse.scava.crossflow.runtime.FailedJob;
import org.eclipse.scava.crossflow.runtime.InternalException;

public class TechrankApp {
	
	public static void main(String[] args) throws Exception {
		
		TechrankWorkflow workflow = new TechrankWorkflow();
		workflow.setCache(new DirectoryCache(new File("cache")));
		workflow.getRepositorySearchDispatcher().setCacheable(false);
		workflow.getRepositorySearcher().setCacheable(false);
		
		workflow.run();
		
		while (!workflow.hasTerminated()) {
			Thread.sleep(100);
		}
		
		for (InternalException ex : workflow.getInternalExceptions()) {
			ex.getException().printStackTrace();
		}
		
		for (FailedJob failed : workflow.getFailedJobs()) {
			failed.getException().printStackTrace();
		}
		
		System.out.println("Done");
		
		//System.exit(0);
	}
	
}
