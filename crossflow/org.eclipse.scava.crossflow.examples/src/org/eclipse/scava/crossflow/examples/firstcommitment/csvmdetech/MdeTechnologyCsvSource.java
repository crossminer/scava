package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.scava.crossflow.runtime.utils.CsvParser;

public class MdeTechnologyCsvSource extends MdeTechnologyCsvSourceBase {
	
	protected Iterable<CSVRecord> records;
	
	@Override
	public void produce() {
		
		try {
			CsvParser parser = new CsvParser("csvs/MDE.csv");
			records = parser.getRecordsIterable();
			
			for (CSVRecord record : records) {
				ExtensionKeywordTuple extensionKeywordTuple = new ExtensionKeywordTuple();
				extensionKeywordTuple.setField0(record.get(0));
				extensionKeywordTuple.setField1(record.get(1));
				sendToMdeTechnologies( extensionKeywordTuple);
		
			}
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public Iterable<CSVRecord> getRecords() {
		return records;
	}
	
}