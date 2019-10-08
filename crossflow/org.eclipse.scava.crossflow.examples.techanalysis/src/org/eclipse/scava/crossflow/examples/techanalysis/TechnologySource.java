package org.eclipse.scava.crossflow.examples.techanalysis;

import java.io.File;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.scava.crossflow.runtime.utils.CsvParser;
import org.eclipse.scava.crossflow.runtime.utils.LogLevel;

public class TechnologySource extends TechnologySourceBase {

	protected Iterable<CSVRecord> records;

	@Override
	public void produce() {
		try {
			final CsvParser parser = new CsvParser(
					new File(workflow.getInputDirectory(), "input.csv").getAbsolutePath());
			records = parser.getRecordsIterable();

			for (CSVRecord record : records) {
				Technology technologyTuple = new Technology();
				technologyTuple.setFileExt(record.get(0));
				technologyTuple.setTechKey(record.get(1));
				sendToTechnologies(technologyTuple);

			}
		} catch (Exception e) {
			workflow.log(LogLevel.ERROR, e.getMessage());
		}
	}

	public Iterable<CSVRecord> getRecords() {
		return records;
	}

}