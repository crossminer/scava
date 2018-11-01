package org.eclipse.scava.crossflow.tests.addition;


public class Adder extends AdderBase {
	
	protected int executions = 0;
	
	@Override
	public void consumeAdditions(NumberPair numberPair) {
		executions++;
		Number result = new Number();
		result.setN(numberPair.getA() + numberPair.getB());
		getAdditionResults().send(result, this.getClass().getName());
	}
	
	public int getExecutions() {
		return executions;
	}
	
}