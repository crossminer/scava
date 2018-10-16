package org.eclipse.scava.crossflow.examples.opinionated.ghrepo;

import java.util.ArrayList;
import java.util.List;

public class GhRepoSource extends GhRepoSourceBase {
	
	protected List<String> ghRepoUrls;
	
	public GhRepoSource() {
		// TODO: replace with actual unique repository URLs obtained from GitHub search results
		
		ghRepoUrls = new ArrayList<String>();
		for (int i=0;i<10;i++) {
			ghRepoUrls.add("https://github.com/userX/repoX/");
			ghRepoUrls.add("https://github.com/userY/repoY/");
			ghRepoUrls.add("https://github.com/userZ/repoZ/");
		}
	}
	
	@Override
	public void produce() {
		for (String repoUrl : ghRepoUrls) {
			GhRepo ghRepo = new GhRepo();
			ghRepo.setRepoUrl(repoUrl);
			getGhRepos().send(ghRepo);
		}
	}
}
