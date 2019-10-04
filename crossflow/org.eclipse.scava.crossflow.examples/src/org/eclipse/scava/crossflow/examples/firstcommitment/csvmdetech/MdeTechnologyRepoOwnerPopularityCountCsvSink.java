package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

import org.eclipse.scava.crossflow.runtime.utils.CsvWriter;

public class MdeTechnologyRepoOwnerPopularityCountCsvSink extends MdeTechnologyRepoOwnerPopularityCountCsvSinkBase {
	
	protected CsvWriter writer;
	
	@Override
	public void consumeMdeTechnologyRepoOwnerPopularityCountEntries(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) {
		
		try {
			if (writer == null) {
				writer = new CsvWriter("csvs/MDE-ownerPopularity.csv", "field0", "field1", "field2", "field3", "field4",  "cached");
			}
			
			writer.writeRecord( extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField0(), extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField1(), extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField2(), extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField3(), extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField4(),  extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.isCached() );
			writer.flush();
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	

}