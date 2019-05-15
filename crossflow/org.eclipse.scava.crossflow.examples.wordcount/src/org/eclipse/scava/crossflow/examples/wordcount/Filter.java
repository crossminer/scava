package org.eclipse.scava.crossflow.examples.wordcount;


public class Filter extends FilterBase {
	
	@Override
	public WordFrequency consumeWordFrequencies(WordFrequency wordFrequency) throws Exception {

		return wordFrequency;
	
	}


}