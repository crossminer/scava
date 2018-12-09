package org.eclipse.scava.crossflow.tests.opinionated;

public interface WordsConsumer {

	public void consumeWords(Word word);
	
	/**
	 * wraps consumeWords() to provide task status information
	 */
	public void consumeWordsActual(Word word);

}