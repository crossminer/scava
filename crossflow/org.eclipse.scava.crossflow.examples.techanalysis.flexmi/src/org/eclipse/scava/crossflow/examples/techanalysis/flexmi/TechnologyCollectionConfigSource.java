package org.eclipse.scava.crossflow.examples.techanalysis.flexmi;

import java.io.File;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.scava.crossflow.runtime.utils.CsvParser;
import org.eclipse.scava.crossflow.runtime.utils.LogLevel;

public class TechnologyCollectionConfigSource extends TechnologyCollectionConfigSourceBase {

	@Override
	public void produce() throws Exception {
		try {
			final CsvParser parser = new CsvParser(
					new File(workflow.getInputDirectory(), "input.csv").getAbsolutePath());

			TechnologyCollection collection = new TechnologyCollection();

			for (CSVRecord record : parser.getRecordsIterable()) {
				Technology technologyTuple = new Technology();
				technologyTuple.setFileExt(record.get(0));
				technologyTuple.setTechKey(record.get(1));
				collection.technologies.add(technologyTuple);
			}
			sendToTechnologyCollectionConfigTopic(collection);

		} catch (Exception e) {
			workflow.log(LogLevel.ERROR, e.getMessage());
		}

	}

}
