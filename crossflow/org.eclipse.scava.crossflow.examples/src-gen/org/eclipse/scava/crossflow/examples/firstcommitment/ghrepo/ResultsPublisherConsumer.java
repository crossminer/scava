package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

public interface ResultsPublisherConsumer {

	public void consumeResultsPublisher(Result result);
	
	/**
	 * wraps consumeResultsPublisher() to provide task status information
	 */
	public void consumeResultsPublisherActual(Result result);

}