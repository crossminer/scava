package org.eclipse.scava.crossflow.tests.configurable.addition;


public class Adder extends AdderBase {
	
	private String operation;
	private String post;
	
	@Override
	public Number consumeAdditions(NumberPair numberPair) throws Exception {
		
		Number numberInst = new Number();
		//	numberInst.setN( int );
		return numberInst;
	
	}
	@Override
	public void consumeOperationConfigurationTopic(OperationConfiguration operationConfiguration) throws Exception {
		
		System.out.println("config received: "+operationConfiguration);
		
		// TODO: handle configuration
		operation = operationConfiguration.operation;	
	
	}
	@Override
	public void consumePostConfigurationTopic(PostConfiguration postConfiguration) throws Exception {
		
		// TODO: handle configuration		
	
	}


}
