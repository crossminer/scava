package org.eclipse.scava.crossflow.examples.opinionated.ghrepo;

import org.eclipse.scava.crossflow.runtime.Mode;

public class GhRepoExampleMasterWorkers {
	
	public static void main(String[] args) throws Exception {
		
		GhRepoExample master = new GhRepoExample();
		master.setName("UserXRepoXCounter");
		master.setFavouriteGhRepoUrl("https://github.com/userX/repoX/");
		
		GhRepoExample worker1 = new GhRepoExample();
		worker1.setName("UserYRepoYCounter");
		worker1.setMode(Mode.WORKER);
		worker1.setFavouriteGhRepoUrl("https://github.com/userY/repoY/");
		
		GhRepoExample worker2 = new GhRepoExample();
		worker2.setName("UserZRepoZCounter");
		worker2.setMode(Mode.WORKER);
		worker2.setFavouriteGhRepoUrl("https://github.com/userZ/repoZ/");
		
		master.run();
		worker1.run();
		worker2.run();
		
	}
	
}
