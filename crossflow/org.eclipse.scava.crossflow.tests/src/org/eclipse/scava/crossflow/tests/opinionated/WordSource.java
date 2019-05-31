package org.eclipse.scava.crossflow.tests.opinionated;

import java.util.HashMap;

public class WordSource extends WordSourceBase {
	
	protected HashMap<String, Integer> wordMap = new HashMap<>();
	
	@Override
	public void produce() {
		for (String word : wordMap.keySet()) {
			for (int i=0;i<wordMap.get(word);i++) {
				sendToWords(new Word(word));
			}
		}
	}
	
	public void setWordMap(HashMap<String, Integer> wordMap) {
		this.wordMap = wordMap;
	}
	
}