package org.eclipse.scava.crossflow.examples.techanalysis.flexmi;

import java.io.File;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.scava.crossflow.runtime.utils.CrossflowLogger.SEVERITY;
import org.eclipse.scava.crossflow.runtime.utils.CsvParser;

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
			sendToTechnologyCollectionTopic(collection);

		} catch (Exception e) {
			workflow.log(SEVERITY.ERROR, e.getMessage());
		}

	}

}
