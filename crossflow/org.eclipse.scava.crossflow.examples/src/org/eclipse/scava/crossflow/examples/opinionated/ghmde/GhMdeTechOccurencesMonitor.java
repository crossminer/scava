package org.eclipse.scava.crossflow.examples.opinionated.ghmde;

import org.eclipse.scava.crossflow.examples.opinionated.ghmde.GhMdeTech;
import org.eclipse.scava.crossflow.examples.opinionated.ghmde.OccurencesMonitorBase;

public class GhMdeTechOccurencesMonitor extends OccurencesMonitorBase {
	
	protected int occurences = 0;
	protected int skips = 0;
	
	@Override
	public void consumeWords(GhMdeTech ghMdeTech) {
		
		if (ghMdeTech.getFileExtension().equals(workflow.getFavouriteGhMdeTech())) {
			occurences++;
			System.out.println("[" + workflow.getName() + "] " + occurences + " instances of " + ghMdeTech.getFileExtension());
		}
		else {
			skips++;
			System.out.println("[" + workflow.getName() + "] Skipping " + ghMdeTech.getFileExtension() + " (" + skips + " skips)");
			// Send it back to the queue for someone else to process
			workflow.getGhMdeTechs().send(ghMdeTech);
		}
 	}
	
}
