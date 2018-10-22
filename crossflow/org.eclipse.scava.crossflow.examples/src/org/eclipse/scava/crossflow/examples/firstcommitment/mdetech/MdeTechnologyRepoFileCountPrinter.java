package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

public class MdeTechnologyRepoFileCountPrinter extends MdeTechnologyRepoFileCountPrinterBase {

	@Override
	public void consumeMdeTechnologyRepoFileCountEntries(
			ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) {
		System.out.println("[" + workflow.getName() + "] Repository " + extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField1() + " with " + extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField2() + " stars has " + extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField4() + " files (cached=" + extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.isCached() + ")");
		
	}

}
