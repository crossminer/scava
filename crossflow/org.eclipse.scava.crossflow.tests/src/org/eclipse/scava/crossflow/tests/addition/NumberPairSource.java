package org.eclipse.scava.crossflow.tests.addition;

import java.util.Arrays;
import java.util.List;

public class NumberPairSource extends NumberPairSourceBase {
	
	@Override
	public void produce() {
		
		List<Integer> numbers = Arrays.asList(1, 2);
		
		for (int i : numbers) {
			sendToAdditions(new NumberPair(i, i));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		
	}
	
}