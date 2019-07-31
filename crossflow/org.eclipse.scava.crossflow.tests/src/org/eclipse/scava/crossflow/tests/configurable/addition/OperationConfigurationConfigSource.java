package org.eclipse.scava.crossflow.tests.configurable.addition;

public class OperationConfigurationConfigSource extends OperationConfigurationConfigSourceBase {
	
	@Override
	public void produce() throws Exception {
		OperationConfiguration ret = new OperationConfiguration();
		ret.operation = "+";
		System.out.println("sending: "+ret);
		sendToOperationConfigurationTopic(ret);
		System.out.println("sent: "+ret);
	}


}
