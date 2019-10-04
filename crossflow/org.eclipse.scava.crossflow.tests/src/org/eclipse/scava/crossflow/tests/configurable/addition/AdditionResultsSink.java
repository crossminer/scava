package org.eclipse.scava.crossflow.tests.configurable.addition;

import java.util.LinkedList;
import java.util.List;

public class AdditionResultsSink extends AdditionResultsSinkBase {

	List<Integer> results = new LinkedList<>();

	@Override
	public void consumeAdditionResults(Number number) throws Exception {
		results.add(number.getN());
	}

	public List<Integer> getNumbers() {
		return results;
	}

}