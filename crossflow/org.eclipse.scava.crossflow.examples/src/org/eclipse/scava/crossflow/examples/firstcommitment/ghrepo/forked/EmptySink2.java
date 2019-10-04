package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.forked;

public class EmptySink2 extends EmptySink2Base {

	@Override
	public void consumeResultsPublisher2(Result result) {
		// does nothing!
		System.err.println("es2+++");
	}

}
