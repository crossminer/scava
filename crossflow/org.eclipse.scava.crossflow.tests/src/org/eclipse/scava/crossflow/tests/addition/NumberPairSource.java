package org.eclipse.scava.crossflow.tests.addition;

public class NumberPairSource extends NumberPairSourceBase {
	
	@Override
	public void produce() {
		
		for (int i=1;i<=2; i++) {
			NumberPair pair = new NumberPair();
			pair.setA(i);
			pair.setB(i);
			sendToAdditions(pair);
		}
		
	}

}