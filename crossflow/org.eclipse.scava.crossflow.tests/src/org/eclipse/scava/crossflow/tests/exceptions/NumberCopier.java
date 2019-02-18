package org.eclipse.scava.crossflow.tests.exceptions;


public class NumberCopier extends NumberCopierBase {
	
	@Override
	public Number consumeNumbers(Number number) throws Exception {
		
		if (number.getN() == workflow.getFailOnNumber()) {
			throw new Exception("Cannot process number: " + number.getN());
		}
		
		return new Number(number.getN());
		
	}

}