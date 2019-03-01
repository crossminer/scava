package org.eclipse.scava.crossflow.tests;

import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;

public class WorkflowTests {

	public static boolean createBroker = true;

	protected BrokerService brokerService;

	public void waitFor(Workflow workflow) throws Exception {
		while (!workflow.hasTerminated()) {
			Thread.sleep(100);
		}
	}

	public void startBroker() throws Exception {

		if (brokerService != null) {
			stopBroker();
		}

		brokerService = new BrokerService();
		brokerService.setUseJmx(true);
		// adds a more lenient delay for heavily loaded servers (60 instead of 10 sec)
		brokerService.addConnector("tcp://localhost:61616" + "?wireFormat.maxInactivityDurationInitalDelay=60000");
		brokerService.start();
	}

	public void stopBroker() throws Exception {
		if (brokerService != null) {
			brokerService.deleteAllMessages();
			brokerService.stopGracefully("", "", 1000, 1000);
			brokerService = null;
		}
	}

}
