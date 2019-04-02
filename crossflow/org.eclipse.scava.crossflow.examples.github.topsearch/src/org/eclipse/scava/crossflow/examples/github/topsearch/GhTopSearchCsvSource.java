package org.eclipse.scava.crossflow.examples.github.topsearch;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.scava.crossflow.runtime.utils.CsvParser;

public class GhTopSearchCsvSource extends GhTopSearchCsvSourceBase {
	
	protected Iterable<OwnerRepoTuple> ownerRepoTupleRecords;
	
	@Override
	public void produce() {
		try {
			final CsvParser parser = new CsvParser("data/repos.csv");
		
			for (CSVRecord record : parser.getRecordsIterable()) {
				OwnerRepoTuple ownerRepoTuple = new OwnerRepoTuple();
				ownerRepoTuple.setRepoOwner(record.get(0));
				ownerRepoTuple.setRepoRemote(record.get(1));
				sendToGhTopSearchRepos( ownerRepoTuple);
	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Iterable<OwnerRepoTuple> getOwnerRepoTupleRecords() {
		return ownerRepoTupleRecords;
	}
	
}