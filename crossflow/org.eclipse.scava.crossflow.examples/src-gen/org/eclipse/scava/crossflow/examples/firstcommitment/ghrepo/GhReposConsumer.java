package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

public interface GhReposConsumer {

	public void consumeGhRepos(GhRepo ghRepo);
	
	/**
	 * wraps consumeGhRepos() to provide task status information
	 */
	public void consumeGhReposActual(GhRepo ghRepo);

}