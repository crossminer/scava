package org.eclipse.scava.crossflow.tests.addition;

import java.util.ArrayList;
import java.util.List;

public class AdditionResultsSink extends AdditionResultsSinkBase {
	
	protected List<Integer> numbers = new ArrayList<>();
	
	@Override
	public void consumeAdditionResults(Number number) {
		numbers.add(number.getN());
		// System.err.println(workflow.getName()+" consumed "+number);
	}
	
	public List<Integer> getNumbers() {
		return numbers;
	}

}