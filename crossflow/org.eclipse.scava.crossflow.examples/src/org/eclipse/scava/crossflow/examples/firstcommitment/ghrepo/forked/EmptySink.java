package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.forked;

public class EmptySink extends EmptySinkBase {

	@Override
	public void consumeResultsPublisher(Result result) {
		// does nothing!
		System.err.println("es+++");
	}

}
