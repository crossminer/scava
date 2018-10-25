package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

public class MdeTechnologyRepoOwnerPopularityCountPrinter extends MdeTechnologyRepoOwnerPopularityCountPrinterBase {

	@Override
	public void consumeMdeTechnologyRepoOwnerPopularityCountEntries(
			ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) {
		System.out.println("[" + workflow.getName() + "] Repository " + extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField1() + " with " + extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField2() + " stars has " + extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField4() + " owner popularity (cached=" + extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.isCached() + ")");
		
	}

}
