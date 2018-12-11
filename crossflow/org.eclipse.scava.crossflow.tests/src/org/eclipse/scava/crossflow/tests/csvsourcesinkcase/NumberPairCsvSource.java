package org.eclipse.scava.crossflow.tests.csvsourcesinkcase;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.scava.crossflow.runtime.utils.CsvParser;
import java.io.IOException;

public class NumberPairCsvSource extends NumberPairCsvSourceBase {
	
	protected static final CsvParser parser = new CsvParser("csvs/NumberPairCsvSource.csv");
	protected static final Iterable<CSVRecord> records = parser.getRecordsIterable();
	
	@Override
	public void produce() {
		for (CSVRecord record : records) {
			sendToAdditions(new NumberPair(Integer.parseInt(record.get(0)), Integer.parseInt (record.get(1))));
		}
	}
	
}