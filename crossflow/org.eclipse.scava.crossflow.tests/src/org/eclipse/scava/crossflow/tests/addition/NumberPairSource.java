package org.eclipse.scava.crossflow.tests.addition;

import java.util.List;

public class NumberPairSource extends NumberPairSourceBase {
	
	protected List<Integer> numbers = null;
	
	@Override
	public void produce() {
		
		for (int i : numbers) {
			sendToAdditions(new NumberPair(i, i));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		
	}
	
	public void setNumbers(List<Integer> numbers) {
		this.numbers = numbers;
	}
	
}