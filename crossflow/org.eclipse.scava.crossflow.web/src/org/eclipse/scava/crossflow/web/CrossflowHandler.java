package org.eclipse.scava.crossflow.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.activemq.broker.BrokerService;
import org.apache.thrift.TException;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.tests.addition.AdditionWorkflow;

public class CrossflowHandler implements Crossflow.Iface {

	protected HashMap<String, Workflow> workflows = new HashMap<>();
	protected BrokerService brokerService;
	
	public void waitFor(Workflow workflow) throws Exception {
		while (!workflow.hasTerminated()) {
			Thread.sleep(100);
		}
	}
	
	@Override
	public String run(String workflowId) throws TException {
		AdditionWorkflow workflow = new AdditionWorkflow();
		workflow.getNumberPairSource().setNumbers(Arrays.asList(1, 2));
		try {
			workflow.run();
		} catch (Exception e) {
			e.printStackTrace();
			throw new TException(e);
		}
		return workflow.getInstanceId();
	}

	@Override
	public void startBroker() throws TException {
		if (brokerService != null) {
			stopBroker();
		}
		
		brokerService = new BrokerService();
		brokerService.setUseJmx(true);
		try {
			brokerService.addConnector("tcp://localhost:61616");
			brokerService.start();
		}
		catch (Exception ex) {
			throw new TException(ex);
		}
	}

	@Override
	public void stopBroker() throws TException {
		if (brokerService != null) {
			try {
				brokerService.deleteAllMessages();
				brokerService.stopGracefully("", "", 1000, 1000);
			} catch (Exception ex) {
				throw new TException(ex); 
			}
			brokerService = null;
		}
	}

	@Override
	public boolean isBrokerRunning() throws TException {
		return brokerService != null;
	}
	

}
