package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

import org.eclipse.scava.crossflow.runtime.utils.ControlMessage;

public interface ResultsPublisherConsumer {

	public void consumeResultsPublisher(Result result);
	
	/**
	 * wraps consumeResultsPublisher() to provide task status information
	 */
	public void consumeResultsPublisherActual(Result result);

	public void processTerminationMessage(ControlMessage cm);

}