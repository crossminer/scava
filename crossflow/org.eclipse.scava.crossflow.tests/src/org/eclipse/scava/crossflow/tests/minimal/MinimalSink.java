package org.eclipse.scava.crossflow.tests.minimal;

import java.util.ArrayList;
import java.util.List;

public class MinimalSink extends MinimalSinkBase {
	
	protected List<Integer> numbers = new ArrayList<>();
	
	@Override
	public void consumeOutput(Number number) {
		numbers.add(number.getN());
	}
	
	public List<Integer> getNumbers() {
		return numbers;
	}

}