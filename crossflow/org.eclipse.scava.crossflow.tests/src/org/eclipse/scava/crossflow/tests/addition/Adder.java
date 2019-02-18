package org.eclipse.scava.crossflow.tests.addition;

public class Adder extends AdderBase {

	protected int executions = 0;
	protected boolean caching = false;
	private long interval = 500;

	@Override
	public Number consumeAdditions(NumberPair numberPair) {
		executions++;
		Number result = new Number();
		result.setN(numberPair.getA() + numberPair.getB());
		if (caching)
			result.setCorrelationId(numberPair.getId());
		try {
			Thread.sleep(interval);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int getExecutions() {
		return executions;
	}

	public void setCaching(boolean caching) {
		this.caching = caching;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

}