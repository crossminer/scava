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

public class TechAnalysisMasterOnlyApp {

	public static void main(String[] args) throws Exception {
		
		//CloneUtils.removeRepoClones(TechAnalysisProperties.CLONE_PARENT_DESTINATION); 
		GitHubTechnologyAnalysis master = new GitHubTechnologyAnalysis(Mode.MASTER);
		master.createBroker(false);
		master.setMaster("localhost");
		master.setInputDirectory(new File("experiment/in"));
		master.setOutputDirectory(new File("experiment/out"));
		master.setInstanceId("GitHub Technology Popularity Analysis");
		master.setName("Master");
		
		master.run();
		
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
		
		System.out.println("Done");
		
	}
	
}
