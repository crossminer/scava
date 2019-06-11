package org.eclipse.scava.crossflow.examples.ghmde;

import org.eclipse.scava.crossflow.runtime.utils.CrossflowLogger.SEVERITY;

import java.io.File;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.scava.crossflow.runtime.utils.CsvParser;

public class MDETechnologySource extends MDETechnologySourceBase {

	protected Iterable<CSVRecord> records;

	@Override
	public void produce() {
		try {
			final CsvParser parser = new CsvParser(
					new File(workflow.getInputDirectory(), "input.csv").getAbsolutePath());
			records = parser.getRecordsIterable();

			for (CSVRecord record : records) {
				MDETechnology mDETechnologyTuple = new MDETechnology();
				mDETechnologyTuple.setFileExt(record.get(0));
				mDETechnologyTuple.setTechKey(record.get(1));
				sendToMDETechnologies(mDETechnologyTuple);

			}
		} catch (Exception e) {
			workflow.log(SEVERITY.ERROR, e.getMessage());
		}
	}

	public Iterable<CSVRecord> getRecords() {
		return records;
	}

}