package org.eclipse.scava.crossflow.tests.minimal;


public class CopierTask extends CopierTaskBase {
	
	protected int executions;
	
	@Override
	public void consumeInput(Number number) {
		
		executions ++;
		
		Number output = new Number();
		output.setN(number.getN());
		output.setCorrelationId(number.getId());
		sendToOutput(output);
	}
	
	public int getExecutions() {
		return executions;
	}
	
}