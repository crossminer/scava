package org.eclipse.scava.crossflow.tests.opinionated;

public class OccurencesMonitor extends OpinionatedOccurencesMonitorBase {

	protected int occurences = 0;

	@Override
	public void consumeWords(Word word) throws Exception {

		occurences++;

	}

	@Override
	public boolean acceptInput(Word input) {
		// TODO: logic for when to accept tasks for this instance of OccurencesMonitor goes here.
		return input.getW().equals(favouriteWord);
	}

	public int getOccurences() {
		return occurences;
	}

}
