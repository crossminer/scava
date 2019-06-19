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

import java.io.File;

import org.eclipse.scava.crossflow.runtime.FailedJob;
import org.eclipse.scava.crossflow.runtime.InternalException;
import org.eclipse.scava.crossflow.runtime.Mode;

public class TechAnalysisApp {

	public static void main(String[] args) throws Exception {
		
		//CloneUtils.removeRepoClones(TechAnalysisProperties.CLONE_PARENT_DESTINATION); 
		
		TechnologyAnalysis master = new TechnologyAnalysis(Mode.MASTER);
		master.setInputDirectory(new File("experiment/in"));
		master.setOutputDirectory(new File("experiment/out"));
		master.setName("Master");
		
		TechnologyAnalysis worker1 = new TechnologyAnalysis(Mode.WORKER);
		worker1.setName("Worker1");
		
		TechnologyAnalysis worker2 = new TechnologyAnalysis(Mode.WORKER);
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
