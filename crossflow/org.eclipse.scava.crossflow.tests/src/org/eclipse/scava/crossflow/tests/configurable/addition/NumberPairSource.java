package org.eclipse.scava.crossflow.tests.configurable.addition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumberPairSource extends NumberPairSourceBase {

	protected List<Integer> numbers = null;
	protected long interval = 100;

	@Override
	public void produce() throws Exception {

		if (numbers == null) {
			numbers = new ArrayList<>();
			for (int i = 1; i <= workflow.getParallelization(); i++)
				numbers.addAll(Arrays.asList(1*i, 2*i, 3*i, 4*i, 5*i));
		}

		for (int i : numbers) {
			sendToAdditions(new NumberPair(i, i));
			Thread.sleep(interval);
		}

	}

	public void setNumbers(List<Integer> numbers) {
		this.numbers = numbers;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

}