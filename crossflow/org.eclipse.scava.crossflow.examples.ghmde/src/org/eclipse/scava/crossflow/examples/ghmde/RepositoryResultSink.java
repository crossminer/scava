package org.eclipse.scava.crossflow.examples.ghmde;

import org.eclipse.scava.crossflow.runtime.utils.CrossflowLogger.SEVERITY;
import org.eclipse.scava.crossflow.runtime.utils.CsvWriter;

public class RepositoryResultSink extends RepositoryResultSinkBase {
	// CSV file writer(s)
	protected CsvWriter writer1;
	
	@Override
	public void consumeRepositoryResults(RepositoryResult repositoryResult) {
		try {
			if ( writer1 == null ) {
				writer1 = new CsvWriter("output.csv", "fileCount", "authorCount", "totalCount",  "cached");
			}
		
			writer1.writeRecord( repositoryResult.getFileCount(), repositoryResult.getAuthorCount(), repositoryResult.getTotalCount(),  repositoryResult.isCached() );
			writer1.flush();
		} catch (Exception e) {
			workflow.log(SEVERITY.ERROR, e.getMessage());
		}
	}
	

}