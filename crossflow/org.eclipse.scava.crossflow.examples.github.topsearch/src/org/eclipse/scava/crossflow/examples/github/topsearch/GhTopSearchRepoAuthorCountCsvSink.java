package org.eclipse.scava.crossflow.examples.github.topsearch;

import org.eclipse.scava.crossflow.runtime.utils.CsvWriter;

public class GhTopSearchRepoAuthorCountCsvSink extends GhTopSearchRepoAuthorCountCsvSinkBase {
	// CSV file writer(s)
	protected CsvWriter writer1;
	
	@Override
	public void consumeOwnerRepoAuthorCountEntries(OwnerRepoAuthorCountTuple ownerRepoAuthorCountTuple) {
		try {
			if ( writer1 == null ) {
				writer1 = new CsvWriter("build/out/GhTopJava-authors.csv", "owner", "repo", "authorCount",  "cached");
			}
		
			writer1.writeRecord( ownerRepoAuthorCountTuple.getField0(), ownerRepoAuthorCountTuple.getField1(), ownerRepoAuthorCountTuple.getField2(),  ownerRepoAuthorCountTuple.isCached() );
			writer1.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}