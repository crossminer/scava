package org.eclipse.scava.crossflow.examples.opinionated;

import java.util.ArrayList;
import java.util.List;

public class WordSource extends WordSourceBase {
	
	protected List<String> words;
	
	public WordSource() {
		words = new ArrayList<String>();
		for (int i=0;i<10;i++) {
			words.add("apple");
			words.add("orange");
			words.add("banana");
		}
	}
	
	@Override
	public void produce() {
		for (String w : words) {
			Word word = new Word();
			word.setW(w);
			getWords().send(word);
		}
	}
}
