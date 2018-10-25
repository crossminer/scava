package org.eclipse.scava.crossflow.restmule.client.github.test;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.client.github.api.IGitHubApi;
import org.eclipse.scava.crossflow.restmule.client.github.model.Commits;
import org.eclipse.scava.crossflow.restmule.client.github.model.SearchCode;
import org.eclipse.scava.crossflow.restmule.client.github.model.SearchCode.Repository;
import org.eclipse.scava.crossflow.restmule.client.github.test.mde.MDE;
import org.eclipse.scava.crossflow.restmule.client.github.util.GitHubUtils;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;

public class SearchCodeResult {

	private static final Logger LOG = LogManager.getLogger(SearchCodeResult.class);

	MDE type;
	IDataSet<SearchCode> dataset;

	Integer totalItems;
	Integer totalResults;
	long totalRepos;
	long totalDevelopers;
	
	private List<Commits> commits;

	public SearchCodeResult(MDE type){
		this.type = type;
	}

	public SearchCodeResult getFor(IDataSet<SearchCode> dataset){
		this.dataset = dataset;
		getTotalItems();
		getTotalRepos();
		getTotalResults();
		//getTotalDevelopers(client);
		return this;
	}

	public void getTotalItems(){
		this.totalItems = dataset.count();
	}

	public void getTotalResults(){
		this.totalResults = dataset.total();
	}

	public void getTotalRepos(){
		this.totalRepos = dataset.observe().distinct( item -> item.getRepository().getId() ).count().blockingGet(); 
	}
	public void getCommits(String owner, String repo, String path){
		this.commits = GitHubUtils.getPublicClient()
				.getReposCommits(owner, repo, null, null, path, null, null)
				.observe().toList().blockingGet();
	}

	public void getTotalDevelopers(IGitHubApi client){
		this.totalDevelopers = dataset.observe()
				.distinct( item -> item.getPath())
				.map( e -> {
					Repository r = e.getRepository();
					return client
							.getReposCommits(r.getOwner().getLogin(), r.getFullName(), null, null, e.getPath(), null, null)
							.observe()
							//.doOnError(error -> LOG.info(error.getMessage()))
							//.doOnNext(n -> LOG.info("NEXT " + n.getAuthor().getId()))
							.map(commit -> commit.getCommitter().getLogin());
				})
				.distinct().count()
				.doOnError(onError -> {
					onError.printStackTrace();
				}).blockingGet();
	}
	
	@Override
	public String toString() {
		return "Results for " + type + ":\n"
				+ "-> total items: " + totalItems + ",\n"
				+ "-> total results: " + totalResults+ ",\n"
				+ "-> total repositories: " + totalRepos+ ",\n"
				+ "-> total developers=" + totalDevelopers ;
	}

}