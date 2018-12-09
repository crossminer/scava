package org.eclipse.scava.crossflow.examples.addition;

import java.util.UUID;

import org.eclipse.scava.crossflow.runtime.Mode;

public class AdditionExampleMasterWorker {

	public static void main(String[] args) throws Exception {
		AdditionExample master = new AdditionExample();
		master.setName("Master-" + UUID.randomUUID().toString());
		
		master.setEnableCache(false);
		
		AdditionExample worker = new AdditionExample(Mode.WORKER);
		worker.setName("Worker");
		
		master.addActiveWorkerId(worker.getName());
		
		master.run();
		worker.run();
	}

}
