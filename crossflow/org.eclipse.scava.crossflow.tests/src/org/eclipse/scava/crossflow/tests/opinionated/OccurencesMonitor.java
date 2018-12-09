package org.eclipse.scava.crossflow.tests.opinionated;

public class OccurencesMonitor extends OccurencesMonitorBase {
	
	protected int occurences = 0;
	
	@Override
	public void consumeWords(Word word) {
		
		if (word.getW().equals(workflow.getFavouriteWord())) {
			occurences++;
		}
		else {
			workflow.getWords().send(word,this.getClass().getName());
		}
 	}
	
	public int getOccurences() {
		return occurences;
	}

}