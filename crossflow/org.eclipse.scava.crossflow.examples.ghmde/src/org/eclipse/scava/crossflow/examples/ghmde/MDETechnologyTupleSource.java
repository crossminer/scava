package org.eclipse.scava.crossflow.examples.ghmde;

import org.eclipse.scava.crossflow.runtime.utils.CrossflowLogger.SEVERITY;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.scava.crossflow.runtime.utils.CsvParser;

public class MDETechnologyTupleSource extends MDETechnologyTupleSourceBase {
	
	protected Iterable<CSVRecord> records;
	
	@Override
	public void produce() {
		try {
			final CsvParser parser = new CsvParser("input.csv");
			records = parser.getRecordsIterable();
		
			for (CSVRecord record : records) {
				Tuple tuple = new Tuple();
				// tuple.setFileExt(record.get(0));
				// tuple.setTechKey(record.get(1));
				sendToMDETechnologyTuples( tuple);
	
			}
		} catch (Exception e) {
			workflow.log(SEVERITY.ERROR, e.getMessage());
		}
	}
	
	public Iterable<CSVRecord> getRecords() {
		return records;
	}
}