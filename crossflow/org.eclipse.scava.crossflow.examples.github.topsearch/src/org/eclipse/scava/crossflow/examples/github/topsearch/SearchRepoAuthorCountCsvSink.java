package org.eclipse.scava.crossflow.examples.github.topsearch;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.scava.crossflow.runtime.utils.CsvWriter;

public class SearchRepoAuthorCountCsvSink extends SearchRepoAuthorCountCsvSinkBase {
	// CSV file writer(s)
	protected CsvWriter writer1;
	
	protected ArrayList<String> entries = new ArrayList<String>();
	
	@Override
	public synchronized void consumeOwnerRepoAuthorCountEntries(OwnerRepoAuthorCountTuple ownerRepoAuthorCountTuple) throws Exception {

			if ( writer1 == null ) {
				File output = new File(workflow.getOutputDirectory(), "reposAuthorCount.csv");
//				if (output.exists()) { output.delete(); }
				writer1 = new CsvWriter(output.getAbsolutePath(), "owner", "repo", "authorCount",  "cached");
			}
			String entry = ownerRepoAuthorCountTuple.getRepoOwner() + "/" + ownerRepoAuthorCountTuple.getRepoRemote();
			if ( !entries.contains(entry) ) {
				writer1.writeRecord( ownerRepoAuthorCountTuple.getRepoOwner(), ownerRepoAuthorCountTuple.getRepoRemote(), ownerRepoAuthorCountTuple.getAuthorCount(),  ownerRepoAuthorCountTuple.isCached() );
				writer1.flush();
				entries.add(entry);
			}

	}

}