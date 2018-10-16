package org.eclipse.scava.crossflow.examples.opinionated.ghrepo;

public class GhRepoOccurencesMonitor extends GhRepoOccurencesMonitorBase {
	
	protected int occurences = 0;
	protected int skips = 0;
	
	@Override
	public void consumeGhRepo(GhRepo ghRepo) {
		
		if (ghRepo.getRepoUrl().equals(workflow.getFavouriteGhRepoUrl())) {
			occurences++;
			System.out.println("[" + workflow.getName() + "] " + occurences + " instances of " + ghRepo.getRepoUrl());
		}
		else {
			skips++;
			System.out.println("[" + workflow.getName() + "] Skipping " + ghRepo.getRepoUrl() + " (" + skips + " skips)");
			// Send it back to the queue for someone else to process
			workflow.getGhRepos().send(ghRepo);
		}
 	}
	
}
