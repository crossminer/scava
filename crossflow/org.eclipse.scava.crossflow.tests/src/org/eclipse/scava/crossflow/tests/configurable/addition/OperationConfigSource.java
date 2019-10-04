package org.eclipse.scava.crossflow.tests.configurable.addition;

public class OperationConfigSource extends OperationConfigSourceBase {
	
	@Override
	public void produce() throws Exception {
		Operation ret = new Operation();
		ret.operation = "+";
		//System.out.println("sending: "+ret);
		sendToOperationConfigTopic(ret);
		//System.out.println("sent: "+ret);
	}


}
