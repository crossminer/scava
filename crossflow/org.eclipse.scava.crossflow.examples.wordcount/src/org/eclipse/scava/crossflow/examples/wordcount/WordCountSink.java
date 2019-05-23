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

public class WordCountSink extends WordCountSinkBase {

	protected Map<String, Integer> frequencies = new HashMap<>();

	private boolean started = false;
	private Timer t = new Timer();

	@Override
	public synchronized void consumeFiltered(WordFrequency wordFrequency) {
		// System.out.println("sink consuming: "+wordFrequency);

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

			CsvWriter writer = new CsvWriter(outputTemp.getAbsolutePath(), "word", "frequency");

			final Map<String, Integer> sorted = frequencies.entrySet().stream()
					.sorted((Map.Entry.<String, Integer>comparingByValue().reversed())).collect(Collectors
							.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

			for (String w : new ArrayList<String>(sorted.keySet()).subList(0, Math.min(sorted.keySet().size(), 100))) {
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
	}

}