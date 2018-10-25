package org.eclipse.scava.crossflow.examples.opinionated;

public class OccurencesMonitor extends OccurencesMonitorBase {
	
	protected int occurences = 0;
	protected int skips = 0;
	
	@Override
	public void consumeWords(Word word) {
		
		if (word.getW().equals(workflow.getFavouriteWord())) {
			occurences++;
			System.out.println("[" + workflow.getName() + "] " + occurences + " instances of " + word.getW());
		}
		else {
			skips++;
			System.out.println("[" + workflow.getName() + "] Skipping " + word.getW() + " (" + skips + " skips)");
			// Send it back to the queue for someone else to process
			workflow.getWords().send(word);
		}
 	}
	
}
