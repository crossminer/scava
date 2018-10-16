package org.eclipse.scava.crossflow.examples.addition;

public class Adder extends AdderBase {
	
	protected int invocations = 0;
	
	@Override
	public void consumeAdditions(NumberPair numberPair) {
		//System.out.println(workflow.getName() + ": " + invocations++);
		System.out.println("[" + workflow.getName() + "] Adding " + numberPair.getA() + " + " + numberPair.getB() + " (" + invocations++ + ")");
		Number number = new Number();
		number.setCorrelationId(numberPair.getId());
		number.setN(numberPair.getA() + numberPair.getB());
		getAdditionResults().send(number);
	}
	
}
