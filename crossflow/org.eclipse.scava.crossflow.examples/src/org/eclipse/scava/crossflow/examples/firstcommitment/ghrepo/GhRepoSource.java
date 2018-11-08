package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.crossflow.restmule.client.github.api.IGitHubApi;
import org.eclipse.scava.crossflow.restmule.client.github.model.SearchRepositories;
import org.eclipse.scava.crossflow.restmule.client.github.util.GitHubUtils;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;

public class GhRepoSource extends GhRepoSourceBase {
	
	protected List<String> ghRepoUrls;
	
	public GhRepoSource() {
		// do nothing
	}
	
	@Override
	public void produce() {
		
		ghRepoUrls = new ArrayList<String>();
		
		// Instantiate API client
		IGitHubApi client = GitHubUtils.getOAuthClient();
			
		// Construct query parameters
		String q = "gmf"; // "271 available repository results" supplied by GitHub web search
		String order = "asc";
		String sort = "stars";
		
		// Submit API query
		IDataSet<SearchRepositories> searchRepositories = client.getSearchRepositories(order, q, sort);
		
		// Observe search repositories data set
		searchRepositories.observe()
		
			.doOnNext(repo -> {
				GhRepo ghRepo = new GhRepo();
				ghRepo.setRepoUrl(repo.getUrl());
				ghRepoUrls.add(ghRepo.getRepoUrl());
				//System.out.println("ADDED: " + ghRepo.getRepoUrl());
				getGhRepos().send(ghRepo);
				//System.out.println("SENT: " + ghRepo.getRepoUrl());
				//System.out.println(searchRepositories.status() + " --- " + searchRepositories.percentage() + "% completed.\n");
			})
			
			.doOnError(e -> {
				System.err.println(e);
			})
			
			.doOnComplete(() -> {
				System.out.println("COMPLETED: " + searchRepositories.id());
			})
			
			.blockingSubscribe();
	
		Long count = searchRepositories.observe().count().blockingGet();
		System.out.println("FINAL COUNT: " + count);

	}
	
}
