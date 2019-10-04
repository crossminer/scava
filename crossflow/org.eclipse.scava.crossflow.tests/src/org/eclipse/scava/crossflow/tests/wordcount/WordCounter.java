package org.eclipse.scava.crossflow.tests.wordcount;

import java.util.HashMap;

public class WordCounter extends WordCounterBase {
	
	@Override
	public void consumeLines(Line line) {
		
		String text = line.getText().replaceAll("[^A-Za-z]", " ");
		HashMap<String, Integer> frequencies = new HashMap<>();
		for (String part : text.split(" ")) {
			part = part.trim().toLowerCase();
			if (part.length() == 0) continue;
			if (!frequencies.containsKey(part)) {
				frequencies.put(part, 1);
			}
			else {
				frequencies.put(part, frequencies.get(part) + 1);
			}
		}
		
		for (String word : frequencies.keySet()) {
			WordFrequency frequency = new WordFrequency(word, frequencies.get(word));
			frequency.setCorrelationId(line.getId());
			sendToWordFrequencies(frequency);
		}
		
	}

}