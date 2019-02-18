package org.eclipse.scava.crossflow.tests.transactionalcaching;

import java.util.concurrent.ThreadLocalRandom;

public class ClonerTask extends ClonerTaskBase {

	protected int executions;
	private long delay = 0;

	private boolean fail = false;

	@Override

	public void consumeInput(Element element) throws Exception {

		if (delay > 0)
			try {
				int sleep = ThreadLocalRandom.current().nextInt((int) delay / 2, (int) delay * 3 / 2);
				// System.out.println("sleeping for: " + sleep + "ms");
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		executions++;

		Element output = new Element();
		output.setS(element.getS());
		output.setCorrelationId(element.getId());

		sendToOutput(output);

		if (!fail) {
			output = new Element();
			output.setS(element.getS() + "c");
			output.setCorrelationId(element.getId());
			sendToOutput(output);
		} else
			throw new Exception("Cannot clone element: " + element.getS());
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

	public boolean isFail() {
		return fail;
	}

	public void setFail(boolean fail) {
		this.fail = fail;
	}
}