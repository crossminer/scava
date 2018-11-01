package org.eclipse.scava.crossflow.tests.addition;

import java.util.ArrayList;
import java.util.List;

public class AdditionResultsSink extends AdditionResultsSinkBase {
	
	protected List<Integer> numbers = new ArrayList<Integer>();
	
	@Override
	public void consumeAdditionResults(Number number) {
		numbers.add(number.getN());
	}
	
	public List<Integer> getNumbers() {
		return numbers;
	}

}