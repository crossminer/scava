package org.eclipse.scava.crossflow.examples.wordcount;

import java.util.HashMap;

import org.eclipse.scava.crossflow.examples.wordcount.WordCounterBase;
import org.eclipse.scava.crossflow.examples.wordcount.WordFrequency;

public class WordCounter extends WordCounterBase {
	
	@Override
	public void consumeLines(Line line) {
		//System.out.println(workflow.getName()+" consuming: "+line);
		
		String text = line.getText().replaceAll("[^A-Za-z]", " "); // replace all characters that are not letters with a single-space
		HashMap<String, Integer> frequencies = new HashMap<String, Integer>();
		for (String word : text.split(" ")) {
			word = word.trim().toLowerCase();
			if (word.length() == 0) continue; // skip
			if (!frequencies.containsKey(word)) {
				frequencies.put(word, 1); // add new word
			}
			else {
				frequencies.put(word, frequencies.get(word) + 1); // increase frequency of existing word
			}
		}
		
		for (String word : frequencies.keySet()) {
			WordFrequency frequency = new WordFrequency(word, frequencies.get(word));
			frequency.setCorrelationId(line.getId());
			sendToWordFrequencies(frequency);
		}

	}

}