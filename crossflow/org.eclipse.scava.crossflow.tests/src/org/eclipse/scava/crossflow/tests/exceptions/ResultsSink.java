package org.eclipse.scava.crossflow.tests.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ResultsSink extends ResultsSinkBase {
	
	protected List<Integer> numbers = new ArrayList<>();
	
	@Override
	public void consumeResults(Number number) throws Exception {
		numbers.add(number.getN());
	}

	public List<Integer> getNumbers() {
		return numbers;
	}
}