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
				writer1 = new CsvWriter(new File(workflow.getOutputDirectory(), "output.csv").getAbsolutePath(), "size_at_commit", "lines_added", "lines_deleted", "repoProject", "numberOfCommits", "numberOfFiles",  "cached");
			}
		
			writer1.writeRecord( repositoryAnalysisResult.getSize_at_commit(), repositoryAnalysisResult.getLines_added(), repositoryAnalysisResult.getLines_deleted(), repositoryAnalysisResult.getRepoProject(), repositoryAnalysisResult.getNumberOfCommits(), repositoryAnalysisResult.getNumberOfFiles(),  repositoryAnalysisResult.isCached() );
			writer1.flush();
		} catch (Exception e) {
			workflow.log(LogLevel.ERROR, e.getMessage());
		}
	}
	

}