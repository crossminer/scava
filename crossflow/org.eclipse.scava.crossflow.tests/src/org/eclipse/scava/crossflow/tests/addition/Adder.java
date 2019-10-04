package org.eclipse.scava.crossflow.tests.addition;

public class Adder extends AdderBase {

	protected int executions = 0;
	private long interval = 1000;

	@Override
	public Number consumeAdditions(NumberPair numberPair) {
		executions++;
		Number result = new Number();
		result.setN(numberPair.getA() + numberPair.getB());
		//
		try {
			Thread.sleep(interval);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// System.err.println(workflow.getName()+" adding: "+numberPair);
		return result;
	}

	public int getExecutions() {
		return executions;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

}