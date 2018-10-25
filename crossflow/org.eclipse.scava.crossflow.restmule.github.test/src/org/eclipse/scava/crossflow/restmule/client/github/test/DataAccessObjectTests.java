package org.eclipse.scava.crossflow.restmule.client.github.test;
import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.client.github.api.IGitHubApi;
import org.eclipse.scava.crossflow.restmule.client.github.model.Commits;
import org.eclipse.scava.crossflow.restmule.client.github.model.Repo;
import org.eclipse.scava.crossflow.restmule.client.github.model.SearchRepositories;
import org.eclipse.scava.crossflow.restmule.client.github.model.User;
import org.eclipse.scava.crossflow.restmule.client.github.util.GitHubUtils;
import org.eclipse.scava.crossflow.restmule.core.data.IData;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;
import org.eclipse.scava.crossflow.restmule.core.data.Status;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class DataAccessObjectTests extends GitHubUtils {

	private static final String ERROR = "something went wrong";

	private static final Logger LOG = LogManager.getLogger(DataAccessObjectTests.class);

	private static IGitHubApi api;

	@BeforeClass
	public static void setup(){
		api = GitHubUtils.getOAuthClient();
	}

	@Test
	public void testSingleRepoObjectReturnType() {
		IData<Repo> repo = api.getReposRepoByRepo("epsilonlabs", "emc-json");
		repo.observe()
			.doOnNext(r -> {
				assertNotNull(r);
			})
			.doOnError(e -> fail(ERROR))
			.doOnComplete(() -> assertEquals(Status.COMPLETED, repo.status()))
			.blockingSubscribe();
	}

	@Test
	public void testSingleUserObjectReturnType() {
		IData<User> user = api.getUsersUserByUsername("fabpot");
		user.observe()
			.doOnNext(r -> {
				assertNotNull(r);
			})
			.doOnError(e -> fail(ERROR))
			.doOnComplete(() -> assertEquals(Status.COMPLETED, user.status()))
			.blockingSubscribe();
	}
	
	@Test
	public void testDataSetFromArray() {
		IDataSet<Commits> reposCommits = api.getReposCommits("epsilonlabs", "emc-json", null, null, null, null, null);
		assertEquals(Status.CREATED, reposCommits.status());
		
		reposCommits
			.observe()
			.doOnNext(commits -> {
				assertNotNull(commits);
				assertTrue(reposCommits.status() == Status.ADDING || reposCommits.status() == Status.AWAITING);	
			})
			.doOnError(e -> fail(ERROR))
			.doOnComplete(() -> assertEquals(Status.COMPLETED, reposCommits.status()))
			.blockingSubscribe();
		
		Long count = reposCommits.observe().count().blockingGet();
		assertTrue(count > 0);
	}
	
	@Test
	public void testDataSetFromWapper() {
		IDataSet<SearchRepositories> searchRepositories = api.getSearchRepositories("asc", "epsilon", "stars");
		assertEquals(Status.CREATED, searchRepositories.status());
		
		searchRepositories.observe()
			.doOnNext(repo -> {
				assertNotNull(repo);
				assertTrue(searchRepositories.status() == Status.ADDING || searchRepositories.status() == Status.AWAITING);	
			})
			.doOnError(e -> fail(ERROR))
			.doOnComplete(() -> assertEquals(Status.COMPLETED, searchRepositories.status()))
			.blockingSubscribe();
	
		Long count = searchRepositories.observe().count().blockingGet();
		assertTrue(count > 0);
	}

}
