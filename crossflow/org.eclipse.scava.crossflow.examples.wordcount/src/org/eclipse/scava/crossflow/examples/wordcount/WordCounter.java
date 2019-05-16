package org.eclipse.scava.crossflow.examples.wordcount;

import java.util.HashMap;

import org.eclipse.scava.crossflow.examples.wordcount.Line;
import org.eclipse.scava.crossflow.examples.wordcount.WordCounterBase;
import org.eclipse.scava.crossflow.examples.wordcount.WordFrequency;

public class WordCounter extends WordCounterBase {
	
	@Override
	public void consumeLines(Line line) {
		//System.out.println(workflow.getName()+" consuming: "+line);
		
		String text = line.getText().replaceAll("[^A-Za-z]", " ");
		HashMap<String, Integer> frequencies = new HashMap<String, Integer>();
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