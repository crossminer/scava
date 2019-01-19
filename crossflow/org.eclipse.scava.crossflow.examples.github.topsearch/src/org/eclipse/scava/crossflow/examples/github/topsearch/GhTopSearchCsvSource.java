package org.eclipse.scava.crossflow.examples.github.topsearch;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.scava.crossflow.runtime.utils.CsvParser;

import java.io.File;
import java.io.IOException;

public class GhTopSearchCsvSource extends GhTopSearchCsvSourceBase {
	
	protected Iterable<CSVRecord> records;
	
	@Override
	public void produce() {
		try {
			CsvParser parser = new CsvParser(new File(workflow.getInputDirectory(), "input.csv").getAbsolutePath());

			records = parser.getRecordsIterable();
		
			for (CSVRecord record : records) {
				OwnerRepoTuple ownerRepoTuple = new OwnerRepoTuple();
				ownerRepoTuple.setField0(record.get(0));
				ownerRepoTuple.setField1(record.get(1));
				sendToGhTopSearchRepos( ownerRepoTuple);
	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Iterable<CSVRecord> getRecords() {
		return records;
	}
}