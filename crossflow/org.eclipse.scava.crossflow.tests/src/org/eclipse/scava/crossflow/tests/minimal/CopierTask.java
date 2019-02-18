package org.eclipse.scava.crossflow.tests.minimal;

import java.util.concurrent.ThreadLocalRandom;

public class CopierTask extends CopierTaskBase {

	protected int executions;
	private long delay = 0;

	@Override
	public Number consumeInput(Number number) {

		if (delay > 0)
			try {
				int sleep = ThreadLocalRandom.current().nextInt((int) delay / 2, (int) delay * 3 / 2);
				// System.out.println("sleeping for: " + sleep + "ms");
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		executions++;

		Number output = new Number();
		output.setN(number.getN());
		output.setCorrelationId(number.getId());
		return output;

	}

	public int getExecutions() {
		return executions;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

}