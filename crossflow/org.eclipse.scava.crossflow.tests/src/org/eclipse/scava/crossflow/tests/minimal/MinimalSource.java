package org.eclipse.scava.crossflow.tests.minimal;

import java.util.List;

public class MinimalSource extends MinimalSourceBase {
	
	protected List<Integer> numbers;
	
	@Override
	public void produce() {
		for (int n : numbers) sendToInput(new Number(n));
	}
	
	public void setNumbers(List<Integer> numbers) {
		this.numbers = numbers;
	}

}