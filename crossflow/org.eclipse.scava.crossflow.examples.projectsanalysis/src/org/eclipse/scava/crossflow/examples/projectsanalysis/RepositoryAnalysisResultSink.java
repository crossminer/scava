package org.eclipse.scava.crossflow.examples.projectsanalysis;

import java.io.File;

import org.eclipse.scava.crossflow.runtime.utils.LogLevel;
import org.eclipse.scava.crossflow.runtime.utils.CsvWriter;

public class RepositoryAnalysisResultSink extends RepositoryAnalysisResultSinkBase {
	// CSV file writer(s)
	protected CsvWriter writer1;
	
	@Override
	public void consumeRepositoryAnalysisResults(PythonRepositoryAnalysisResult pythonRepositoryAnalysisResult) {
		try {
			if ( writer1 == null ) {
				writer1 = new CsvWriter(new File(workflow.getOutputDirectory(), "output.csv").getAbsolutePath(), "url", "sizeAtCommit", "numberOfFiles", "linesAdded", "linesDeleted", "numberOfCommits", "numOfDevs", "projectDuration", "projectLOC",  "cached");
			}
		
			writer1.writeRecord( pythonRepositoryAnalysisResult.getUrl(), pythonRepositoryAnalysisResult.getSizeAtCommit(), pythonRepositoryAnalysisResult.getNumberOfFiles(), pythonRepositoryAnalysisResult.getLinesAdded(), pythonRepositoryAnalysisResult.getLinesDeleted(), pythonRepositoryAnalysisResult.getNumberOfCommits(), pythonRepositoryAnalysisResult.getNumOfDevs(), pythonRepositoryAnalysisResult.getProjectDuration(), pythonRepositoryAnalysisResult.getProjectLOC(),  pythonRepositoryAnalysisResult.isCached() );
			writer1.flush();
		} catch (Exception e) {
			workflow.log(LogLevel.ERROR, e.getMessage());
		}
	}
	

}