package org.eclipse.scava.crossflow.examples.addition;

public interface AdditionResultsConsumer {

	public void consumeAdditionResults(Number number);
	
	/**
	 * wraps consumeAdditionResults() to provide task status information
	 */
	public void consumeAdditionResultsActual(Number number);

}