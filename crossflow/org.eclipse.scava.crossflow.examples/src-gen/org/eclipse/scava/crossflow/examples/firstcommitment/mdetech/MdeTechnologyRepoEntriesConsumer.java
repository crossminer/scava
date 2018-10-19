package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

public interface MdeTechnologyRepoEntriesConsumer {

	public void consumeMdeTechnologyRepoEntries(StringStringIntegerTuple stringStringIntegerTuple);
	
	/**
	 * wraps consumeMdeTechnologyRepoEntries() to provide task status information
	 */
	public void consumeMdeTechnologyRepoEntriesActual(StringStringIntegerTuple stringStringIntegerTuple);

}