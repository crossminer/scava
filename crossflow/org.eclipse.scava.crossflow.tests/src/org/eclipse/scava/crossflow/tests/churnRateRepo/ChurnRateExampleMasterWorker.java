package org.eclipse.scava.crossflow.tests.churnRateRepo;

import java.util.UUID;

public class ChurnRateExampleMasterWorker {

	public static void main(String[] args) throws Exception {
		ChurnRateWorkflow master = new ChurnRateWorkflow();
		master.setName("Master-" + UUID.randomUUID().toString());
		master.run();

	}

}
