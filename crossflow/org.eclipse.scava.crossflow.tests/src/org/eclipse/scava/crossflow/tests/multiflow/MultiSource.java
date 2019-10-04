package org.eclipse.scava.crossflow.tests.multiflow;

public class MultiSource extends MultiSourceBase {
	
	protected int numbers;
	
	@Override
	public void produce() {
		
		for (int i=0;i<numbers;i++) {
			sendToIn1(new Number2(i+1));
			sendToIn2(new Number1(numbers + i + 1));
		}
		
	}
	
	public void setNumbers(int numbers) {
		this.numbers = numbers;
	}
	
}