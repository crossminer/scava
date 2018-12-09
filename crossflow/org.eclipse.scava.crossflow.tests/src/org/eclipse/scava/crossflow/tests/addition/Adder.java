package org.eclipse.scava.crossflow.tests.addition;


public class Adder extends AdderBase {
	
	protected int executions = 0;
	protected boolean caching = false;
	
	@Override
	public void consumeAdditions(NumberPair numberPair) {
		executions++;
		Number result = new Number();
		result.setN(numberPair.getA() + numberPair.getB());
		if (caching) result.setCorrelationId(numberPair.getId());
		sendToAdditionResults(result);
	}
	
	public int getExecutions() {
		return executions;
	}
	
	public void setCaching(boolean caching) {
		this.caching = caching;
	}
	
}