package org.eclipse.scava.crossflow.examples.github.topsearch;

import java.io.File;

import org.eclipse.scava.crossflow.runtime.utils.CsvWriter;

public class SearchRepoAuthorCountCsvSink extends SearchRepoAuthorCountCsvSinkBase {
	// CSV file writer(s)
	protected CsvWriter writer1;
	
	@Override
	public synchronized void consumeOwnerRepoAuthorCountEntries(OwnerRepoAuthorCountTuple ownerRepoAuthorCountTuple) throws Exception {

			if ( writer1 == null ) {
				File output = new File(workflow.getOutputDirectory(), "data/repos-authorCount.csv");
				writer1 = new CsvWriter(output.getAbsolutePath(), "owner", "repo", "authorCount",  "cached");
			}
		
			writer1.writeRecord( ownerRepoAuthorCountTuple.getRepoOwner(), ownerRepoAuthorCountTuple.getRepoRemote(), ownerRepoAuthorCountTuple.getAuthorCount(),  ownerRepoAuthorCountTuple.isCached() );
			writer1.flush();

	}

}