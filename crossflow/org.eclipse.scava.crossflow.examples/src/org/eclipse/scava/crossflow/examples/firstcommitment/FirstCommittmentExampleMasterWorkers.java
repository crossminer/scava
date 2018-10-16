package org.eclipse.scava.crossflow.examples.firstcommitment;

import org.eclipse.scava.crossflow.runtime.Mode;

public class FirstCommittmentExampleMasterWorkers {
	
	public static void main(String[] args) throws Exception {
		
		FirstCommitmentExample master = new FirstCommitmentExample();
		master.setName("Master");
		
		FirstCommitmentExample worker1 = new FirstCommitmentExample();
		worker1.setMode(Mode.WORKER);
		worker1.setName("Worker1");
		
		FirstCommitmentExample worker2 = new FirstCommitmentExample();
		worker2.setMode(Mode.WORKER);
		worker2.setName("Worker2");
		
		master.run();
		worker1.run();
		worker2.run();
		
	}
	
}
