package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

import org.eclipse.scava.crossflow.runtime.utils.ControlMessage;

public interface GhReposConsumer {

	public void consumeGhRepos(GhRepo ghRepo);
	
	/**
	 * wraps consumeGhRepos() to provide task status information
	 */
	public void consumeGhReposActual(GhRepo ghRepo);

	public void processTerminationMessage(ControlMessage cm);

}