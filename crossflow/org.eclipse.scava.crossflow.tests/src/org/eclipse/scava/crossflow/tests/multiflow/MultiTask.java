package org.eclipse.scava.crossflow.tests.multiflow;

public class MultiTask extends MultiTaskBase {

	protected int executions = 0;
	protected boolean cacheIn1 = false;
	protected boolean cacheIn2 = false;

	@Override
	public void consumeIn1(Number2 number) {
		consume(number, cacheIn1);
	}

	@Override
	public void consumeIn2(Number1 number) {
		consume(number, cacheIn2);
	}

	public void consume(Number1 number, boolean cache) {
		executions++;

		// System.err.println("consuming: "+number +", caching: "+cache);

		Number1 out1 = new Number1(number.getN1() + number.getN1());
		if (cache)
			out1.setCorrelationId(number.getId());
		sendToOut1(out1);

		Number2 out2 = new Number2(number.getN1() * number.getN1());
		if (cache)
			out2.setCorrelationId(number.getId());
		sendToOut2(out2);
	}

	public void consume(Number2 number, boolean cache) {
		executions++;

		// System.err.println("consuming: "+number +", caching: "+cache);

		Number1 out1 = new Number1(number.getN2() + number.getN2());
		if (cache)
			out1.setCorrelationId(number.getId());
		sendToOut1(out1);

		Number2 out2 = new Number2(number.getN2() * number.getN2());
		if (cache)
			out2.setCorrelationId(number.getId());
		sendToOut2(out2);
	}

	public int getExecutions() {
		return executions;
	}

	public void configureCache(boolean cacheIn1, boolean cacheIn2) {
		this.cacheIn1 = cacheIn1;
		this.cacheIn2 = cacheIn2;
	}

}