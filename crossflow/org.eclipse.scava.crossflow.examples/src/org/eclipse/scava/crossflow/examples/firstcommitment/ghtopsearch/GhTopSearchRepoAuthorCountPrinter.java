package org.eclipse.scava.crossflow.examples.firstcommitment.ghtopsearch;


public class GhTopSearchRepoAuthorCountPrinter extends GhTopSearchRepoAuthorCountPrinterBase {
	
	@Override
	public void consumeGhTopSearchRepoAuthorCountEntries(OwnerRepoLocalRepoPathTuple ownerRepoLocalRepoPathTuple)
			throws Exception {
		// TODO: Add implementation that instantiates, sets, and submits result objects (example below)
				System.out.println("[" + workflow.getName() + "] Result is " + ownerRepoLocalRepoPathTuple.toString() + " (cached=" + ownerRepoLocalRepoPathTuple.isCached() + ")");
				
		
	}

}