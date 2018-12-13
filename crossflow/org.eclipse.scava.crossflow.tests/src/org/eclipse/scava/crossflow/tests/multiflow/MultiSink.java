package org.eclipse.scava.crossflow.tests.multiflow;

import java.util.ArrayList;
import java.util.List;

public class MultiSink extends MultiSinkBase {
	
	protected List<Integer> numbers = new ArrayList<>();
	
	@Override
	public void consumeOut1(Number number) {
		numbers.add(number.getN());
	}
	
	@Override
	public void consumeOut2(Number number) {
		numbers.add(number.getN());
	}
	
	public List<Integer> getNumbers() {
		return numbers;
	}
	
}