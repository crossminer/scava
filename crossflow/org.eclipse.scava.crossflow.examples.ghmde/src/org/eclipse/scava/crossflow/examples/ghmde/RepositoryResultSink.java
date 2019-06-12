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

	protected HashMap<String, AnalysisResult> results = new HashMap<String, AnalysisResult>();

	private boolean started = false;
	private Timer t = new Timer();

	@Override
	public void consumeRepositoryResults(AnalysisResult analysisResult) throws Exception {
		System.out.println("sink consuming: " + analysisResult);

		if (!started)
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					flushToDisk();
				}
			}, 2000, 2000);
		started = true;

		if (!results.containsKey(analysisResult.getName()) && analysisResult.getName() != null) {
			// add new item
			results.put(analysisResult.getName(), analysisResult);

		} else if (results.containsKey(analysisResult.getName())) {
			// supplement new item with existing information (if available)
			AnalysisResult existingResult = results.get(analysisResult.getName());

			if (analysisResult.getAuthorCount() == -1 && existingResult.getAuthorCount() != -1) {
				analysisResult.setAuthorCount(existingResult.getAuthorCount());
			}
			if (analysisResult.getFileCount() == -1 && existingResult.getFileCount() != -1) {
				analysisResult.setFileCount(existingResult.getFileCount());
			}
			results.replace(existingResult.getName(), analysisResult);
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

			CsvWriter writer = new CsvWriter(outputTemp.getAbsolutePath(), "repository name", "MDE technology",
					"file count", "unique author count", "cached");

			for (AnalysisResult result : results.values()) {
				writer.writeRecord(result.getName(), result.getTechnology(), result.getFileCount(),
						result.getAuthorCount(), result.isCached());
			}

			writer.flush();
			writer.close();

			if (output.exists())
				output.delete();
			outputTemp.renameTo(new File(workflow.getOutputDirectory(), "output.csv"));

		} catch (Exception ex) {
			log(SEVERITY.ERROR,
					"Exception occurred while flushing workflow output to disk. Message: " + ex.getMessage());
		}
	}
	// ----

}
