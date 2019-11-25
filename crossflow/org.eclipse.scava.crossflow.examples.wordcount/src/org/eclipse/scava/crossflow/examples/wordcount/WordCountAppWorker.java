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

import org.eclipse.scava.crossflow.examples.wordcount.WordCountWorkflow;
import org.eclipse.scava.crossflow.runtime.Mode;

/**
 * Standalone application for running a set of workers connecting to an existing master.
 * 
 * (!) Requires matching instance identifier (instanceId).
 * 
 * @author Patrick Neubauer
 * @author Konstantinos Barmpis
 *
 */
public class WordCountAppWorker {
	
	public WordCountAppWorker() throws Exception {
		//master.createBroker(false);
		WordCountWorkflow worker1 = new WordCountWorkflow(Mode.WORKER);
		worker1.setInstanceId(WordCountProperties.INSTANCE_ID);
		worker1.setName("Worker1");
		
		WordCountWorkflow worker2 = new WordCountWorkflow(Mode.WORKER);
		worker1.setInstanceId(WordCountProperties.INSTANCE_ID);
		worker2.setName("Worker2");
		
		worker1.run();
		worker2.run();
	
		worker1.awaitTermination();
		worker2.awaitTermination();
		
		System.out.println("Done");
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		// setup and launch experiment
		WordCountAppWorker app = new WordCountAppWorker();
	}// main
	
}// WordCountAppWorker