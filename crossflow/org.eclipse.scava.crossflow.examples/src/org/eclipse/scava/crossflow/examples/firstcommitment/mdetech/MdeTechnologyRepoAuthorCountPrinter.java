package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

public class MdeTechnologyRepoAuthorCountPrinter extends MdeTechnologyRepoAuthorCountPrinterBase {

	@Override
	public void consumeMdeTechnologyRepoAuthorCountEntries(
			ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) {
		System.out.println("[" + workflow.getName() + "] Repository " + extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField1() + " with " + extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField2() + " stars has " + extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField4() + " unique authors (cached=" + extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.isCached() + ")");
		
	}

}
