package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

public interface MdeTechnologyClonedRepoEntriesConsumer {

	public void consumeMdeTechnologyClonedRepoEntries(StringStringIntegerStringTuple stringStringIntegerStringTuple);
	
	/**
	 * wraps consumeMdeTechnologyClonedRepoEntries() to provide task status information
	 */
	public void consumeMdeTechnologyClonedRepoEntriesActual(StringStringIntegerStringTuple stringStringIntegerStringTuple);

}