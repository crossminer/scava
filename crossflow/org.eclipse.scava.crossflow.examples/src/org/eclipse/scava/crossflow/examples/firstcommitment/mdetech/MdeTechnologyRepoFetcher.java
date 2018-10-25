/**
 * 
 */
package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.scava.crossflow.restmule.client.github.api.IGitHubApi;
import org.eclipse.scava.crossflow.restmule.client.github.model.Repo;
import org.eclipse.scava.crossflow.restmule.client.github.model.SearchCode;
import org.eclipse.scava.crossflow.restmule.client.github.util.GitHubUtils;
import org.eclipse.scava.crossflow.restmule.core.data.IData;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;

public class MdeTechnologyRepoFetcher extends MdeTechnologyRepoFetcherBase {

	
	protected final int MAX_NUMBER_OF_COMMITMENTS = 128;
	
	protected Set<String> alreadySeenJobs = new HashSet<String>();
	
	// < repository-url, number-of-repository-occurrence >
	protected Map<String, Integer> committedRepoMap = new HashMap<String, Integer>(); 
	
	/**
	 * 
	 */
	public MdeTechnologyRepoFetcher() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see org.eclipse.scava.crossflow.examples.firstcommitment.mdetech.MdeTechnologiesConsumer#consumeMdeTechnologies(org.eclipse.scava.crossflow.examples.firstcommitment.mdetech.StringStringTuple)
	 */
	@Override
	public void consumeMdeTechnologies(ExtensionKeywordTuple extensionKeywordTuple) {
		// Use MDE extension-keyword tuple to issue API calls using generated RestMule client for GitHub
		
		// Instantiate API client
		IGitHubApi client = GitHubUtils.getOAuthClient();
		
		// Construct query parameters
		String q = "class+extension:kmt"; //MDE.query(stringStringTuple.getField0(), stringStringTuple.getField1()); //"figure+extension:gmfgraph";
		String order = "asc";
		String sort = null;//"stars"; // sorting by "stars" is not possible for code search (works for repository search)
		
		// Submit API query
		IDataSet<SearchCode> searchCode = client.getSearchCode(order, q, sort);
		
		// Observe search repositories data set
		searchCode.observe()
		
			.doOnNext(result -> {
				ExtensionKeywordStargazersTuple extensionKeywordStargazersTuple = new ExtensionKeywordStargazersTuple();
				extensionKeywordStargazersTuple.setField0(extensionKeywordTuple.field0);
				extensionKeywordStargazersTuple.setField1(result.getRepository().getHtmlUrl());
				extensionKeywordStargazersTuple.setField2(getRepoStargazerCount(extensionKeywordStargazersTuple.getField1()));				
				getMdeTechnologyRepoEntries().send(extensionKeywordStargazersTuple);
				
				System.out.println("\n" + "[" + workflow.getName() + "] " + "Consuming " + extensionKeywordStargazersTuple.getField1() + " (search " + searchCode.percentage() + "% completed)");
			})
			
			.doOnError(e -> {
				System.err.println("\n" + "[" + workflow.getName() + "] " + "Failure occurred during repository search: " + e.getMessage() );
			})
			
			.doOnComplete(() -> {
				System.out.println("\n" + "[" + workflow.getName() + "] " + "Completed task: " + searchCode.id());
			})
			
			.blockingSubscribe();
	
		Long count = searchCode.observe().count().blockingGet();
		System.out.println("\n" + "[" + workflow.getName() + "] " + "Final observe count of task " + searchCode.id() + ": " + count);

	}
	
	private int getRepoStargazerCount(String repoUrl) {
		AtomicInteger repoStargazerCount = new AtomicInteger();
		
		IGitHubApi client = GitHubUtils.getOAuthClient();
		IData<Repo> repo = client.getReposRepoByRepo(CloneUtils.extractGhRepoOwner(repoUrl), CloneUtils.extractGhRepoName(repoUrl));
		
		repo.observe()
        .doOnNext(r -> {
    		repoStargazerCount.set( r.getStargazersCount() );   
        })
        .blockingSubscribe();
		
		return repoStargazerCount.intValue();
	}

}
