/*******************************************************************************
 * Copyright (c) 2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributor(s):
 *      Patrick Neubauer - initial API and implementation
 ******************************************************************************/
package org.eclipse.scava.crossflow.examples.techanalysis.flexmi;

import org.eclipse.scava.crossflow.runtime.FailedJob;
import org.eclipse.scava.crossflow.runtime.InternalException;
import org.eclipse.scava.crossflow.runtime.Mode;

public class TechAnalysisWorkerOnlyApp {

	public static void main(String[] args) throws Exception {
		
		//CloneUtils.removeRepoClones(TechAnalysisProperties.CLONE_PARENT_DESTINATION); 
		TechnologyAnalysis worker = new TechnologyAnalysis(Mode.WORKER);
		worker.setMaster("localhost");
		worker.setName("Worker-"+String.valueOf(Math.random()).substring(2));
		worker.setInstanceId("GitHub Technology Popularity Analysis");
		
		worker.run();
		
		// worker
		while (!worker.hasTerminated()) {
			Thread.sleep(100);
		}
		
		for (InternalException ex : worker.getInternalExceptions()) {
			ex.getException().printStackTrace();
		}
		
		for (FailedJob failed : worker.getFailedJobs()) {
			failed.getException().printStackTrace();
		}
		
		System.out.println("Done");
		
	}
	
}
