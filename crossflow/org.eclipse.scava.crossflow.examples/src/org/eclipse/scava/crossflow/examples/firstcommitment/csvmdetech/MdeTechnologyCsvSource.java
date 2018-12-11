package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.scava.crossflow.runtime.utils.CsvParser;

public class MdeTechnologyCsvSource extends MdeTechnologyCsvSourceBase {
	
	protected final CsvParser parser = new CsvParser("csvs/MDE.csv");
	protected final Iterable<CSVRecord> records = parser.getRecordsIterable();
	
	@Override
	public void produce() {
		for (CSVRecord record : records) {
			ExtensionKeywordTuple extensionKeywordTuple = new ExtensionKeywordTuple();
			extensionKeywordTuple.setField0(record.get(0));
			extensionKeywordTuple.setField1(record.get(1));
			sendToMdeTechnologies( extensionKeywordTuple);
	
		}
	}
	
	public Iterable<CSVRecord> getRecords() {
		return records;
	}
	
}