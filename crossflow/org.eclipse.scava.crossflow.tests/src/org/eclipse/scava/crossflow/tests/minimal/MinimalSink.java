package org.eclipse.scava.crossflow.tests.minimal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MinimalSink extends MinimalSinkBase {

	protected List<Integer> numbers = new ArrayList<>();

	protected long delay = 0;

	@Override
	public void consumeOutput(Number number) {

		if (delay > 0)
			try {
				int sleep = ThreadLocalRandom.current().nextInt((int) delay / 2, (int) delay * 3 / 2);
				// System.out.println("sleeping for: " + sleep + "ms");
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		numbers.add(number.getN());

	}

	public List<Integer> getNumbers() {
		return numbers;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

}