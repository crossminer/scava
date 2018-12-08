package org.eclipse.scava.crossflow.tests.basecase;

public class NumberPairSource extends NumberPairSourceBase {

	@Override
	public void produce() {

		for (int i=1;i<=10; i++) {
			sendToAdditions(new NumberPair(i, i));
		}
		
	}

}
