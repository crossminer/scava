package org.eclipse.scava.crossflow.web;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

import org.apache.activemq.broker.BrokerService;
import org.apache.thrift.TException;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.Workflow;

public class CrossflowHandler implements Crossflow.Iface {

	protected HashMap<String, WorkflowInstance> workflows = new HashMap<>();
	protected BrokerService brokerService;
	protected CrossflowServlet servlet;
	
	public CrossflowHandler(CrossflowServlet servlet) {
		this.servlet = servlet;
	}
	
	protected ClassLoader getClassLoader() throws Exception {
		return Thread.currentThread().getContextClassLoader();
	}
	
	@Override
	public String startWorkflow(String jar, String workflowClass) throws TException {
		try {
			Workflow workflow = (Workflow) getClassLoader().loadClass(workflowClass).getConstructor(Mode.class).newInstance(Mode.MASTER);
			workflow.createBroker(false);
			workflow.run();
			workflows.put(workflow.getInstanceId(), new WorkflowInstance(workflow, jar));
			return workflow.getInstanceId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new TException(e);
		}
	}
	
	@Override
	public void stopWorkflow(String instanceId) throws TException {
		WorkflowInstance workflowInstance = workflows.get(instanceId);
		if (workflowInstance != null) {
			workflowInstance.getWorkflow().terminate();
		}
	}
	
	@Override
	public boolean isWorkflowRunning(String instanceId) throws TException {
		WorkflowInstance workflowInstance = workflows.get(instanceId);
		if (workflowInstance != null) {
			return !workflowInstance.getWorkflow().hasTerminated();
		}
		else {
			return false;
		}
	}

	@Override
	public void startBroker() throws TException {
		if (brokerService != null) {
			stopBroker();
		}
		
		try {
			brokerService = new BrokerService();
			brokerService.setUseJmx(true);
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
