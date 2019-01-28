package org.eclipse.scava.crossflow.tests.minimal;

import java.util.ArrayList;
import java.util.List;

public class MinimalSink extends MinimalSinkBase {

	protected List<Integer> numbers = new ArrayList<>();

	protected long delay = 0;

	@Override
	public void consumeOutput(Number number) {

		numbers.add(number.getN());

		if (delay > 0)
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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