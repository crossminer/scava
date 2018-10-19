package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

public interface MdeTechnologyRepoOwnerPopularityCountEntriesConsumer {

	public void consumeMdeTechnologyRepoOwnerPopularityCountEntries(StringStringIntegerStringIntegerTuple stringStringIntegerStringIntegerTuple);
	
	/**
	 * wraps consumeMdeTechnologyRepoOwnerPopularityCountEntries() to provide task status information
	 */
	public void consumeMdeTechnologyRepoOwnerPopularityCountEntriesActual(StringStringIntegerStringIntegerTuple stringStringIntegerStringIntegerTuple);

}