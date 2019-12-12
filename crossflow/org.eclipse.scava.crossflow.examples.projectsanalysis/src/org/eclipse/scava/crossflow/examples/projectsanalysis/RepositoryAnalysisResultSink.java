package org.eclipse.scava.crossflow.examples.projectsanalysis;

import java.io.File;

import org.eclipse.scava.crossflow.runtime.utils.LogLevel;
import org.eclipse.scava.crossflow.runtime.utils.CsvWriter;

public class RepositoryAnalysisResultSink extends RepositoryAnalysisResultSinkBase {
	// CSV file writer(s)
	protected CsvWriter writer1;
	
	@Override
	public void consumeRepositoryAnalysisResults(RepositoryAnalysisResult repositoryAnalysisResult) {
		try {
			if ( writer1 == null ) {
				writer1 = new CsvWriter(new File(workflow.getOutputDirectory(), "output.csv").getAbsolutePath(), "sizeAtCommit", "linesAdded", "linesDeleted", "repo", "numberOfCommits", "numberOfFiles",  "cached");
			}
		
			writer1.writeRecord( repositoryAnalysisResult.getSizeAtCommit(), repositoryAnalysisResult.getLinesAdded(), repositoryAnalysisResult.getLinesDeleted(), repositoryAnalysisResult.getRepo().url, repositoryAnalysisResult.getNumberOfCommits(), repositoryAnalysisResult.getNumberOfFiles(),  repositoryAnalysisResult.isCached() );
			writer1.flush();
		} catch (Exception e) {
			workflow.log(LogLevel.ERROR, e.getMessage());
		}
	}
	

}