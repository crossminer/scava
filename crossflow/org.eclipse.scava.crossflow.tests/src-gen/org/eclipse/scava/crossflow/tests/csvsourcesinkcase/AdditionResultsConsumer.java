package org.eclipse.scava.crossflow.tests.csvsourcesinkcase;

public interface AdditionResultsConsumer {

	public void consumeAdditionResults(Number number);
	
	/**
	 * wraps consumeAdditionResults() to provide task status information
	 */
	public void consumeAdditionResultsActual(Number number);

}