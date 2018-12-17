package org.eclipse.scava.crossflow.tests.wordcount;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.scava.crossflow.runtime.utils.CsvWriter;

public class WordCountSink extends WordCountSinkBase {
	
	protected Map<String, Integer> frequencies = new HashMap<>();
	protected long lastSaveTime = System.currentTimeMillis();
	
	@Override
	public synchronized void consumeWordFrequencies(WordFrequency wordFrequency) {
		
		
		String word = wordFrequency.getWord();
		int frequency = wordFrequency.getFrequency();
		
		if (!frequencies.containsKey(wordFrequency.getWord())) {
			frequencies.put(word, frequency);
		}
		else {
			frequencies.put(word, frequencies.get(word) + frequency);
		}
		
		try {
			
			if (System.currentTimeMillis() - lastSaveTime > 2000) {
				
				File output = new File(workflow.getOutputDirectory(), "output.csv");
				File outputTemp = new File(workflow.getOutputDirectory(), "output-temp.csv");
				
				CsvWriter writer = new CsvWriter(outputTemp.getAbsolutePath(), "word", "frequency");
				
				
				final Map<String, Integer> sorted = frequencies.entrySet().stream()
		                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
		                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
				
				for (String w : new ArrayList<String>(sorted.keySet()).subList(0, Math.min(sorted.keySet().size(), 100))) {
					writer.writeRecord(w, sorted.get(w));
				}
				writer.flush();
				writer.close();
				
				if (output.exists()) output.delete();
				outputTemp.renameTo(new File(workflow.getOutputDirectory(), "output.csv"));
				
				lastSaveTime = System.currentTimeMillis();
			}
		}
		catch (Exception ex) {
			
		}
		
	}

}