package org.eclipse.scava.crossflow.tests.minimal;

import java.util.concurrent.ThreadLocalRandom;

import org.eclipse.scava.crossflow.runtime.utils.LogLevel;

public class CopierTask extends CopierTaskBase {

	protected int executions;
	private long delay = 0;
	private boolean verbose = false;

	@Override
	public Number consumeInput(Number number) {

		if (verbose)
			log(LogLevel.INFO, "CopierTask:" + workflow.getCopierTasks().lastIndexOf(this) + " consuming: " + number);

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
		output.setCorrelationId(number.getJobId());
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

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

}