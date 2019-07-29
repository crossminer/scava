package org.eclipse.scava.crossflow.examples.techanalysis;

import java.io.File;

import org.apache.commons.csv.CSVRecord;

import org.eclipse.scava.crossflow.runtime.utils.CrossflowLogger.SEVERITY;
import org.eclipse.scava.crossflow.runtime.utils.CsvParser;

public class TechsConfigSource extends TechsConfigSourceBase {

	@Override
	public void produce() throws Exception {
		try {
			final CsvParser parser = new CsvParser(
					new File(workflow.getInputDirectory(), "input.csv").getAbsolutePath());

			Techs collection = new Techs();

			for (CSVRecord record : parser.getRecordsIterable()) {
				Technology technologyTuple = new Technology();
				technologyTuple.setFileExt(record.get(0));
				technologyTuple.setTechKey(record.get(1));
				collection.techs.add(technologyTuple);
			}
			sendToTechsConfigTopic(collection);

		} catch (Exception e) {
			workflow.log(SEVERITY.ERROR, e.getMessage());
		}

	}

}