package org.eclipse.scava.crossflow.tests.multiflow;


public class MultiTask extends MultiTaskBase {
	
	protected int executions = 0;
	protected boolean cacheIn1 = false;
	protected boolean cacheIn2 = false;
	
	@Override
	public void consumeIn1(Number number) {
		consume(number, cacheIn1);
	}
	
	@Override
	public void consumeIn2(Number number) {
		consume(number, cacheIn2);
	}
	
	public void consume(Number number, boolean cache) {
		executions ++;
		
		Number out1 = new Number(number.getN() + number.getN());
		if (cache) out1.setCorrelationId(number.getId());
		sendToOut1(out1);
		
		Number out2 = new Number(number.getN() * number.getN());
		if (cache) out2.setCorrelationId(number.getId());
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