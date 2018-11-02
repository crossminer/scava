package org.eclipse.scava.crossflow.examples.addition;

public interface AdditionsConsumer {

	public void consumeAdditions(NumberPair numberPair);
	
	/**
	 * wraps consumeAdditions() to provide task status information
	 */
	public void consumeAdditionsActual(NumberPair numberPair);

}