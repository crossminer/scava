package org.eclipse.scava.crossflow.tests.multiflow;

import java.util.ArrayList;
import java.util.List;

public class MultiSink extends MultiSinkBase {
	
	protected List<Integer> numbers = new ArrayList<>();
	
	@Override
	public void consumeOut1(Number1 number) {
		numbers.add(number.getN1());
	}
	
	@Override
	public void consumeOut2(Number2 number) {
		numbers.add(number.getN2());
	}
	
	public List<Integer> getNumbers() {
		return numbers;
	}
	
}