package org.eclipse.scava.crossflow.examples.projectsanalysis;

import org.eclipse.scava.crossflow.runtime.utils.LogLevel;

import java.io.File;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.scava.crossflow.runtime.utils.CsvParser;

public class ProjectSource extends ProjectSourceBase {
	
	protected Iterable<CSVRecord> records;
	
	@Override
	public void produce() {
		try {
			final CsvParser parser = new CsvParser(new File(workflow.getInputDirectory(), "input.csv").getAbsolutePath());
			records = parser.getRecordsIterable();
		
			for (CSVRecord record : records) {
				Project project = new Project();
				// project.setOwner(record.get(0));
				// project.setRepo(record.get(1));
				// project.setCommit(record.get(2));
				sendToProjects( project);
	
			}
		} catch (Exception e) {
			workflow.log(LogLevel.ERROR, e.getMessage());
		}
	}
	
	public Iterable<CSVRecord> getRecords() {
		return records;
	}
}