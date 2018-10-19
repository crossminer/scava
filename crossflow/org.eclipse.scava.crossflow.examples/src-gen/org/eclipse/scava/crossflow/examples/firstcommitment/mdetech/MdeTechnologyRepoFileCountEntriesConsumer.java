package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

public interface MdeTechnologyRepoFileCountEntriesConsumer {

	public void consumeMdeTechnologyRepoFileCountEntries(StringStringIntegerStringIntegerTuple stringStringIntegerStringIntegerTuple);
	
	/**
	 * wraps consumeMdeTechnologyRepoFileCountEntries() to provide task status information
	 */
	public void consumeMdeTechnologyRepoFileCountEntriesActual(StringStringIntegerStringIntegerTuple stringStringIntegerStringIntegerTuple);

}