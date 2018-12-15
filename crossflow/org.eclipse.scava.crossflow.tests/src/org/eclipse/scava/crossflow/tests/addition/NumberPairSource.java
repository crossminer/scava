package org.eclipse.scava.crossflow.tests.addition;

import java.util.Arrays;
import java.util.List;

public class NumberPairSource extends NumberPairSourceBase {
	
	protected List<Integer> numbers = null;
	protected long interval = 500; 
	
	@Override
	public void produce() {
		
		if (numbers == null) {
			numbers = Arrays.asList(1, 2, 3, 4, 5);
		}
		
		for (int i : numbers) {
			sendToAdditions(new NumberPair(i, i));
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		
	}
	
	public void setNumbers(List<Integer> numbers) {
		this.numbers = numbers;
	}
	
	public void setInterval(long interval) {
		this.interval = interval;
	}
	
}