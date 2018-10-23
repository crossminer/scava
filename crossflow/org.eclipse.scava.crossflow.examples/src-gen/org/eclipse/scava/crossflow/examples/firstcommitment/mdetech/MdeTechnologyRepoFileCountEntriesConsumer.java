package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

public interface MdeTechnologyRepoFileCountEntriesConsumer {

	public void consumeMdeTechnologyRepoFileCountEntries(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);
	
	/**
	 * wraps consumeMdeTechnologyRepoFileCountEntries() to provide task status information
	 */
	public void consumeMdeTechnologyRepoFileCountEntriesActual(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);

}