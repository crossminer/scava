/*******************************************************************************
 * Copyright (c) 2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributor(s):
 *      Patrick Neubauer - initial API and implementation
 ******************************************************************************/
package org.eclipse.scava.crossflow.examples.techanalysis;

import org.eclipse.scava.crossflow.runtime.FailedJob;
import org.eclipse.scava.crossflow.runtime.InternalException;
import org.eclipse.scava.crossflow.runtime.Mode;

public class TechAnalysisWorkerOnlyApp {

	public static void main(String[] args) throws Exception {
		
		//CloneUtils.removeRepoClones(TechAnalysisProperties.CLONE_PARENT_DESTINATION); 
		GitHubTechnologyAnalysis worker = new GitHubTechnologyAnalysis(Mode.WORKER);
		worker.setMaster("localhost");
		worker.setName("Worker-"+String.valueOf(Math.random()).substring(2));
		worker.setInstanceId("GitHub Technology Popularity Analysis");
		
		worker.run();
		
		// worker
		while (!worker.hasTerminated()) {
			Thread.sleep(100);
		}
		
		for (InternalException ex : worker.getInternalExceptions()) {
			System.err.println(ex.getStacktrace());
		}
		
		for (FailedJob failed : worker.getFailedJobs()) {
			System.err.println(failed.getStacktrace());
		}
		
		System.out.println("Done");
		
	}
	
}
