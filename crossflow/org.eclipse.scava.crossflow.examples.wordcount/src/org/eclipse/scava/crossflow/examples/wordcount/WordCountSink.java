/*******************************************************************************
 * Copyright (c) 2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributor(s):
 *      Patrick Neubauer - initial API and implementation
 ******************************************************************************/
package org.eclipse.scava.crossflow.examples.wordcount;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import org.eclipse.scava.crossflow.examples.wordcount.WordCountSinkBase;
import org.eclipse.scava.crossflow.examples.wordcount.WordFrequency;
import org.eclipse.scava.crossflow.runtime.utils.CsvWriter;

/**
 * Consumes instances of {@link WordFrequency}, i.e. words and their ocurrence frequency, by writing them to disk every two seconds.
 * 
 * @author Patrick Neubauer
 *
 */
public class WordCountSink extends WordCountSinkBase {

	protected Map<String, Integer> frequencies = new HashMap<>();

	private boolean started = false;
	private Timer t = new Timer();

	@Override
	public synchronized void consumeFiltered(WordFrequency wordFrequency) {
		 System.out.println("Sink consuming: "+wordFrequency);

		 // schedule reocurring task that flushes current state to disk
		if (!started)
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					flushToDisk();
				}
			}, 2000, 2000);
		started = true;

		String word = wordFrequency.getWord();
		int frequency = wordFrequency.getFrequency();

		if (!frequencies.containsKey(wordFrequency.getWord())) {
			frequencies.put(word, frequency);
		} else {
			frequencies.put(word, frequencies.get(word) + frequency);
		}

	}// consumeFiltered

	private synchronized void flushToDisk() {
		try {

			File output = new File(workflow.getOutputDirectory(), "output.csv");
			File outputTemp = new File(workflow.getOutputDirectory(), "output-temp.csv");

			CsvWriter writer = new CsvWriter(outputTemp.getAbsolutePath(), "word", "frequency");

			final Map<String, Integer> sorted = frequencies.entrySet().stream()
					.sorted((Map.Entry.<String, Integer>comparingByValue().reversed())).collect(Collectors
							.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

			for (String w : new ArrayList<String>(sorted.keySet()).subList(0, Math.min(sorted.keySet().size(), WordCountProperties.MIN_WORD_FREQUENCY))) {
				writer.writeRecord(w, sorted.get(w));
			}
			writer.flush();
			writer.close();

			if (output.exists())
				output.delete();
			outputTemp.renameTo(new File(workflow.getOutputDirectory(), "output.csv"));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}// flushToDisk
	
	@Override
	public void close() {
		t.cancel();
		flushToDisk();
	}// close

}// WordCountSink