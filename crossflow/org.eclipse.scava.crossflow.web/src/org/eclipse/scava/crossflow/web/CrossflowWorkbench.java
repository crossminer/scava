package org.eclipse.scava.crossflow.web;

import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;

public class CrossflowWorkbench {
	
	public static void main(String[] args) throws Exception {
		TTransport transport = new THttpClient("http://localhost:8080/org.eclipse.scava.crossflow.web/crossflow");
		transport.open();
		TProtocol protocol = new TJSONProtocol(transport); // JSON transport format
		
		Crossflow.Client client = new Crossflow.Client(protocol);
		
		client.startBroker();
		
		Thread.sleep(1000);
		
		String instanceId = client.startWorkflow("org.eclipse.scava.crossflow.tests.jar", 
			"org.eclipse.scava.crossflow.tests.addition.AdditionWorkflow");
		
		Thread.sleep(1000);
		
		System.out.println(client.isWorkflowRunning(instanceId));
		
		while (client.isWorkflowRunning(instanceId)) {
			Thread.sleep(1000);
		}
		
		System.out.println("Finished");
		
		transport.close();
		
		
		
	}
	
	
}
