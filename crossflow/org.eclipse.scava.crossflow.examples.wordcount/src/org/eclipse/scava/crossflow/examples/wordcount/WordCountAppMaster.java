/*******************************************************************************
 * Copyright (c) 2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributor(s):
 *      Patrick Neubauer - initial API and implementation
 ******************************************************************************/
package org.eclipse.scava.crossflow.examples.wordcount;

import java.io.File;

import org.eclipse.scava.crossflow.examples.wordcount.WordCountWorkflow;
import org.eclipse.scava.crossflow.runtime.FailedJob;
import org.eclipse.scava.crossflow.runtime.InternalException;
import org.eclipse.scava.crossflow.runtime.Mode;

/**
 * Standalone application for running a master (only) for workers to connect to.
 * 
 *  (!) Requires matching instance identifier (instanceId).
 * 
 * @author Patrick Neubauer
 * @author Konstantinos Barmpis
 *
 */
public class WordCountAppMaster {
	
	public WordCountAppMaster() throws Exception {
		WordCountWorkflow master = new WordCountWorkflow(Mode.MASTER_BARE);
		//master.createBroker(false);
		master.setInputDirectory(new File("artifacts/in"));
		master.setOutputDirectory(new File("artifacts/out"));
		master.setInstanceId(WordCountProperties.INSTANCE_ID);
		master.setName("Master");
		
		master.run();

		// master
		master.awaitTermination();
		
		for (InternalException ex : master.getInternalExceptions()) {
			ex.getException().printStackTrace();
		}
		
		for (FailedJob failed : master.getFailedJobs()) {
			failed.getException().printStackTrace();
		}
	
		System.out.println("Done");
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		// setup and launch experiment
		WordCountAppMaster app = new WordCountAppMaster();
	}// main
	
}// WordCountAppMaster