/*******************************************************************************
 * Copyright (c) 2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributor(s):
 *      Patrick Neubauer - initial API and implementation
 ******************************************************************************/
package org.eclipse.scava.crossflow.examples.ghmde;

import java.io.File;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.scava.crossflow.runtime.utils.CrossflowLogger.SEVERITY;
import org.eclipse.scava.crossflow.runtime.utils.CsvWriter;

public class RepositoryResultSink extends RepositoryResultSinkBase {
	
	protected HashMap<String, RepositoryResult> results = new HashMap<String, RepositoryResult>();
	
	private boolean started = false;
	private Timer t = new Timer();

	@Override
	public synchronized void consumeRepositoryResults(RepositoryResult repositoryResult) {
		System.out.println("sink consuming: "+repositoryResult);

		if (!started)
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					flushToDisk();
				}
			}, 2000, 2000);
		started = true;
		
		if ( !results.containsKey(repositoryResult.getName()) && repositoryResult.getName()!=null ) {
			// add new item
			results.put(repositoryResult.getName(), repositoryResult);
			
		} else if ( results.containsKey(repositoryResult.getName()) ) {
			// supplement new item with existing information (if available)
			RepositoryResult existingResult = results.get(repositoryResult.getName());

			if ( repositoryResult.getAuthorCount()==-1 && existingResult.getAuthorCount()!=-1 ) {
				repositoryResult.setAuthorCount( existingResult.getAuthorCount() );
			}
			if ( repositoryResult.getFileCount()==-1 && existingResult.getFileCount()!=-1 ) {
				repositoryResult.setFileCount( existingResult.getFileCount() );
			}
			results.replace(existingResult.getName(), repositoryResult);
		}

	}

	@Override
	public void close() {
		t.cancel();
		flushToDisk();
	}

	private synchronized void flushToDisk() {
		try {

			File output = new File(workflow.getOutputDirectory(), "output.csv");
			File outputTemp = new File(workflow.getOutputDirectory(), "output-temp.csv");

			CsvWriter writer = new CsvWriter(outputTemp.getAbsolutePath(), "repository name", "MDE technology file count", "unique author count", "cached");
			
			for ( RepositoryResult result : results.values() ) {
				writer.writeRecord(result.getName(), result.getFileCount(), result.getAuthorCount(), result.isCached());
			}
			
			writer.flush();
			writer.close();

			if (output.exists())
				output.delete();
			outputTemp.renameTo(new File(workflow.getOutputDirectory(), "output.csv"));

		} catch (Exception ex) {
			log(SEVERITY.ERROR, "Exception occurred while flushing workflow output to disk. Message: " + ex.getMessage());
		}
	}
	// ----
	

}
