package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.forked;

public interface ResultsPublisher2Consumer {

	public void consumeResultsPublisher2(Result result);
	
	/**
	 * wraps consumeResultsPublisher2() to provide task status information
	 */
	public void consumeResultsPublisher2Actual(Result result);

}