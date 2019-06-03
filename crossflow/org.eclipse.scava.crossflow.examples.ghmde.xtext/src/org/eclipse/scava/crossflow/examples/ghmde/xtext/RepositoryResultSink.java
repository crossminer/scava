package org.eclipse.scava.crossflow.examples.ghmde.xtext;

import org.eclipse.scava.crossflow.runtime.utils.CrossflowLogger.SEVERITY;
import org.eclipse.scava.crossflow.runtime.utils.CsvWriter;

public class RepositoryResultSink extends RepositoryResultSinkBase {
	// CSV file writer(s)
	protected CsvWriter writer1;
	
	@Override
	public void consumeRepositoryResults(Repository repository) {
		try {
			if ( writer1 == null ) {
				writer1 = new CsvWriter("", "url",  "cached");
			}
		
			writer1.writeRecord( repository.getUrl(),  repository.isCached() );
			writer1.flush();
		} catch (Exception e) {
			workflow.log(SEVERITY.ERROR, e.getMessage());
		}
	}
	

}