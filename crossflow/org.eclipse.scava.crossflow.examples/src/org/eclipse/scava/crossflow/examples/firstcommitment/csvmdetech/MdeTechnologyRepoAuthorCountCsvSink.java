package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

import org.eclipse.scava.crossflow.runtime.utils.CsvWriter;

public class MdeTechnologyRepoAuthorCountCsvSink extends MdeTechnologyRepoAuthorCountCsvSinkBase {
	// CSV file writer(s)
	protected CsvWriter writer1;
	
	@Override
	public void consumeMdeTechnologyRepoAuthorCountEntries(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) {
		try {
			if ( writer1 == null ) {
				writer1 = new CsvWriter("csvs/MDE-authors.csv", "field0", "field1", "field2", "field3", "field4",  "cached");
			}
		
			writer1.writeRecord( extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField0(), extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField1(), extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField2(), extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField3(), extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField4(),  extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.isCached() );
			writer1.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}