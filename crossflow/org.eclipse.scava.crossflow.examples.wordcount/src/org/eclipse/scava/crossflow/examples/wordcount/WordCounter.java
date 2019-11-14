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

import java.util.HashMap;

import org.eclipse.scava.crossflow.examples.wordcount.WordCounterBase;
import org.eclipse.scava.crossflow.examples.wordcount.WordFrequency;

/**
 * Consumes lines of text and counts frequency of words.
 * 
 * @author Patrick Neubauer
 *
 */
public class WordCounter extends WordCounterBase {
	
	@Override
	public void consumeLines(Line line) {
		System.out.println(workflow.getName()+" consuming: "+line);
		
		HashMap<String, Integer> frequencies = countWordFrequency(line);
		
		for (String word : frequencies.keySet()) {
			WordFrequency frequency = new WordFrequency(word, frequencies.get(word));
			frequency.setCorrelationId(line.getJobId());
			sendToWordFrequencies(frequency);
		}

	}

	/**
	 * Count the frequency of words in a line of text by considering characters A-Z and a-z (only) transformed to lower-case.
	 * 
	 * @param line a line of text
	 * @return map of words and their frequency
	 */
	private HashMap<String, Integer> countWordFrequency(Line line) {
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
		
		return frequencies;
	}// countWordFrequency

}// WordCounter