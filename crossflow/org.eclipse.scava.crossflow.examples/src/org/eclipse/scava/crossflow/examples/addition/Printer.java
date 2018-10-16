package org.eclipse.scava.crossflow.examples.addition;

public class Printer extends PrinterBase {

	@Override
	public void consumeAdditionResults(Number number) {
		System.out.println("[" + workflow.getName() + "] Result is " + number.getN() + " (" + number.isCached() + ")");
	}
}
