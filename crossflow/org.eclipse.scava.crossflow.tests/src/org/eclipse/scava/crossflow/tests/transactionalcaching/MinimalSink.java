package org.eclipse.scava.crossflow.tests.transactionalcaching;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MinimalSink extends MinimalSinkBase {

	protected List<String> elements = new ArrayList<>();

	protected long delay = 0;

	@Override
	public void consumeOutput(Element number) {

		if (delay > 0)
			try {
				int sleep = ThreadLocalRandom.current().nextInt((int) delay / 2, (int) delay * 3 / 2);
				// System.out.println("sleeping for: " + sleep + "ms");
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		elements.add(number.getS());

	}

	public List<String> getElements() {
		return elements;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

}