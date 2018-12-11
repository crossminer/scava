package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

import org.eclipse.scava.crossflow.runtime.utils.CsvWriter;

public class MdeTechnologyRepoFileCountCsvSink extends MdeTechnologyRepoFileCountCsvSinkBase {
	// CSV file writer(s)
	protected CsvWriter writer1 = new CsvWriter("csvs/MDE-files.csv", "field0", "field1", "field2", "field3", "field4",  "cached");
	
	public void flushAll() {
		writer1.flush();
	}
	
	@Override
	public void consumeMdeTechnologyRepoFileCountEntries(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) {
		writer1.writeRecord( extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField0(), extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField1(), extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField2(), extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField3(), extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.getField4(),  extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.isCached() );
		writer1.flush();
	}
	

}