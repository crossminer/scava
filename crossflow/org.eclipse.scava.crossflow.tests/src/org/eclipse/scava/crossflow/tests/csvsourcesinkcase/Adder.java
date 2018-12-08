package org.eclipse.scava.crossflow.tests.csvsourcesinkcase;


public class Adder extends AdderBase {
	
	@Override
	public void consumeAdditions(NumberPair numberPair) {
		
		// TODO: Add implementation that instantiates, sets, and submits result objects (example below)
		Number number0 = new Number();
		number0.setN( numberPair.getA() + numberPair.getB() );
		sendToAdditionResults(number0);
		
	}

}