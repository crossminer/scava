package org.eclipse.scava.crossflow.tests.basecase;

import java.util.UUID;

import org.eclipse.scava.crossflow.runtime.Mode;

public class BaseCaseMasterWorker {
	
	public static void main(String[] args) throws Exception {
		BaseCase master = new BaseCase();
		master.setName("Master-"+UUID.randomUUID().toString());
		BaseCase worker = new BaseCase();
		worker.setName("Worker");
		worker.setMode(Mode.WORKER);
		master.run();
		worker.run();
	}
	
}
