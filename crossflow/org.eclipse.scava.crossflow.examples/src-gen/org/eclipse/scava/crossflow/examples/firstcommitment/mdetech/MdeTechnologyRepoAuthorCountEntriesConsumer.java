package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

public interface MdeTechnologyRepoAuthorCountEntriesConsumer {

	public void consumeMdeTechnologyRepoAuthorCountEntries(StringStringIntegerStringIntegerTuple stringStringIntegerStringIntegerTuple);
	
	/**
	 * wraps consumeMdeTechnologyRepoAuthorCountEntries() to provide task status information
	 */
	public void consumeMdeTechnologyRepoAuthorCountEntriesActual(StringStringIntegerStringIntegerTuple stringStringIntegerStringIntegerTuple);

}