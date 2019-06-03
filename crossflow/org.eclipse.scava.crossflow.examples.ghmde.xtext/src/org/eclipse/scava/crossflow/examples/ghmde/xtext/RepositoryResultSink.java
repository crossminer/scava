/*******************************************************************************
 * Copyright (c) 2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributor(s):
 *      Patrick Neubauer - initial API and implementation
 ******************************************************************************/
package org.eclipse.scava.crossflow.examples.ghmde.xtext;

import org.eclipse.scava.crossflow.runtime.utils.CrossflowLogger.SEVERITY;
import org.eclipse.scava.crossflow.runtime.utils.CsvWriter;

public class RepositoryResultSink extends RepositoryResultSinkBase {
	// CSV file writer(s)
	protected CsvWriter writer1;
	
	@Override
	public void consumeRepositoryResults(RepositoryResult repositoryResult) {
		try {
			if ( writer1 == null ) {
				writer1 = new CsvWriter("output.csv", "fileCount", "authorCount",  "cached");
			}
		
			writer1.writeRecord( repositoryResult.getFileCount(), repositoryResult.getAuthorCount(),  repositoryResult.isCached() );
			writer1.flush();
		} catch (Exception e) {
			workflow.log(SEVERITY.ERROR, e.getMessage());
		}
	}
	

}
