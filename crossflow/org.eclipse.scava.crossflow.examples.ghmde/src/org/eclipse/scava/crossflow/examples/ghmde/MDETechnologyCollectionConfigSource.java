package org.eclipse.scava.crossflow.examples.ghmde;

import java.io.File;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.scava.crossflow.runtime.utils.CrossflowLogger.SEVERITY;
import org.eclipse.scava.crossflow.runtime.utils.CsvParser;

public class MDETechnologyCollectionConfigSource extends MDETechnologyCollectionConfigSourceBase {

	@Override
	public void produce() throws Exception {
		try {
			final CsvParser parser = new CsvParser(
					new File(workflow.getInputDirectory(), "input.csv").getAbsolutePath());

			MDETechnologyCollection collection = new MDETechnologyCollection();

			for (CSVRecord record : parser.getRecordsIterable()) {
				MDETechnology mDETechnologyTuple = new MDETechnology();
				mDETechnologyTuple.setFileExt(record.get(0));
				mDETechnologyTuple.setTechKey(record.get(1));
				collection.technologies.add(mDETechnologyTuple);
			}
			sendToMDETechnologyCollectionTopic(collection);

		} catch (Exception e) {
			workflow.log(SEVERITY.ERROR, e.getMessage());
		}

	}

}
