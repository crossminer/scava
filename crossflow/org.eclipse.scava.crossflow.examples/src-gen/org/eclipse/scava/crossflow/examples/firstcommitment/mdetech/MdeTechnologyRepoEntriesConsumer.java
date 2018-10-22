package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

public interface MdeTechnologyRepoEntriesConsumer {

	public void consumeMdeTechnologyRepoEntries(ExtensionKeywordStargazersTuple extensionKeywordStargazersTuple);
	
	/**
	 * wraps consumeMdeTechnologyRepoEntries() to provide task status information
	 */
	public void consumeMdeTechnologyRepoEntriesActual(ExtensionKeywordStargazersTuple extensionKeywordStargazersTuple);

}