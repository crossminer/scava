package org.eclipse.scava.crossflow.examples.github.topsearch;

import java.io.File;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.scava.crossflow.runtime.utils.CsvParser;

public class SearchCsvSource extends SearchCsvSourceBase {
	
	protected Iterable<OwnerRepoTuple> ownerRepoTupleRecords;
	
	@Override
	public void produce() {
		try {
			CsvParser parser = new CsvParser(new File(workflow.getInputDirectory(), "repos.csv").getAbsolutePath());
		
			for (CSVRecord record : parser.getRecordsIterable()) {
				OwnerRepoTuple ownerRepoTuple = new OwnerRepoTuple();
				ownerRepoTuple.setRepoOwner(record.get(0));
				ownerRepoTuple.setRepoRemote(record.get(1));
				sendToSearchRepos( ownerRepoTuple);
	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Iterable<OwnerRepoTuple> getOwnerRepoTupleRecords() {
		return ownerRepoTupleRecords;
	}
	
}