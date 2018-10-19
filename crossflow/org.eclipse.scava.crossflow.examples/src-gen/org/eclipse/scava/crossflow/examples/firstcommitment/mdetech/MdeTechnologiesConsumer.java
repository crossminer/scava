package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

public interface MdeTechnologiesConsumer {

	public void consumeMdeTechnologies(StringStringTuple stringStringTuple);
	
	/**
	 * wraps consumeMdeTechnologies() to provide task status information
	 */
	public void consumeMdeTechnologiesActual(StringStringTuple stringStringTuple);

}