/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Daniel Campbell - Implementation.
 * 	  Luis Adri�n Cabrera Diego - Implementation.
 *******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.github;

//Java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.scava.crossflow.restmule.client.github.api.IGitHubApi;
import org.eclipse.scava.crossflow.restmule.client.github.model.Issues;
import org.eclipse.scava.crossflow.restmule.client.github.model.IssuesComments;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;
//OSSMETER
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.bugtrackingsystem.github.utils.GitHubReaderUtils;
import org.eclipse.scava.platform.bugtrackingsystem.github.utils.GitHubSessionUtil;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.IBugTrackingSystemManager;
//DB Model packages
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.github.GitHubBugTracker;

//Mongo DB package
import com.mongodb.DB;

public class GitHubManager implements IBugTrackingSystemManager<GitHubBugTracker> {

	private Map<String, GitHubIssue> issuesMap = new HashMap<>();// Change to Map

	private Map<String, IssueDateRange> issueRangeMap = new HashMap<>();; // this tracks the range of dates for the
																			// every issue.
	private Map<String, IssueDateRange> pullRequestRangeList = new HashMap<>(); // this tracks the range of dates for
																				// the every Pull Request.

	private List<GitHubComment> issueCommentsList = new ArrayList<>();
	private List<GitHubComment> pullRequestCommentList = new ArrayList<>();

	private List<GitHubPullRequest> pullRequestsList = new ArrayList<>();// Change to Map
	private List<Integer> issueIds = new ArrayList<>();// TODO Remove this and use date Range lists ( if a map is used
														// this is not needed)

	private List<Integer> pullRequestIdList = new ArrayList<>(); // TODO Remove this and use date Range lists
																	// this list is necessary for addressing a
																	// limitation
																	// with the get all pull requests function
																	// ( if a map is used this is not needed)

	private static GitHubSessionUtil session = null;

	@Override
	public boolean appliesTo(BugTrackingSystem bugTrackingSystem) {
		return bugTrackingSystem instanceof GitHubBugTracker;
	}

	/*
	 * DELTAS
	 */
	@Override
	public BugTrackingSystemDelta getDelta(DB db, GitHubBugTracker ghbt, Date analysisDate) throws Exception {

		GitHubBugTrackingSystemDelta delta = new GitHubBugTrackingSystemDelta();
		delta.setBugTrackingSystem(ghbt);

		getAllIssues(ghbt, analysisDate); // This gets all the issues, this is mandatory for dramatically reducing the
											// number of calls required.

		for (Entry<String, IssueDateRange> issueDateRange : issueRangeMap.entrySet()) {

			String issueID = issueDateRange.getKey();
			IssueDateRange idr = issueDateRange.getValue();

			if (idr.getCreated().compareTo(analysisDate) == 0) {

				delta.getNewBugs().add(issuesMap.get(issueID));

			}
			// Check if modified date is the same as the analysis 
			if (idr.getModified().compareTo(analysisDate) == 0) {
				
				// This checks to see if the modified date is in the future compared to the creation date.
				if ((idr.getModified().compareTo(idr.getCreated()) > 0)) {

					delta.getUpdatedBugs().add(issuesMap.get(issueID));
				}

			}

			//THis only requests comments for issues that are within the scope of the date range 
			if ((idr.getCreated().compareTo(analysisDate) <= 0)
					&& (idr.getModified().compareTo(analysisDate) >= 0)) {

				// this should attempt to get comments for that particular issue and then add to a list
				getIssueComments(ghbt, issueID);

				for (GitHubComment comment : issueCommentsList) {

					Date commentDate = new Date(comment.getCreationTime());
					
					if (commentDate.compareTo(analysisDate) == 0) {
						//Issues that have a new comment will be considered as updated
						if(!(delta.getNewBugs().contains(issuesMap.get(comment.getBugId()))) && !(delta.getUpdatedBugs().contains(issuesMap.get(comment.getBugId()))))
							delta.getUpdatedBugs().add(issuesMap.get(comment.getBugId()));
						delta.getComments().add(comment);

					}

				}

				this.issueCommentsList.clear();// This prepares for the next series of comments

			}

		}
		
		this.issueCommentsList.clear();// This prepares for the next series of comments
		this.issueRangeMap.clear();
		this.issueIds.clear();
		this.issuesMap.clear();
		
		this.pullRequestCommentList.clear();
		this.pullRequestIdList.clear();
		this.pullRequestRangeList.clear();
		this.pullRequestsList.clear();

		return delta;
	}

	@Override
	public Date getFirstDate(DB db, GitHubBugTracker ghbt) throws Exception {

		 Date earliestDate = getEarliestIssueDate(ghbt);
		//
		// getAllData(ghbt, earliestDate); //TODO POTENTIALLY REMOVE

		return earliestDate;
	}

	public Date getEarliestIssueDate(GitHubBugTracker ghbt) throws Exception {

		IGitHubApi gitHubApi = getSession(ghbt);
		IDataSet<Issues> repoIssues = gitHubApi.getReposIssues(ghbt.getOwner(), ghbt.getRepository(), "all", "all", "",
				"created", "asc", null);
		Issues firstIssue = repoIssues.observe().blockingFirst();
		Date earliestDate = new Date(firstIssue.getCreatedAt().replace("-", "").substring(0, 8));
		return earliestDate;

	}

	/**
	 * Gets all issues from the repository from the earliest date
	 * 
	 * @param ghbt
	 * @param earliestDate
	 * @throws Exception
	 */
	private void getAllIssues(GitHubBugTracker ghbt, Date earliestDate) throws Exception {

		IGitHubApi gitHubApi = getSession(ghbt);
		IDataSet<Issues> repoIssues = gitHubApi.getReposIssues(ghbt.getOwner(), ghbt.getRepository(), "all", "all", "",
				"created", "asc", earliestDate.toString());
		repoIssues.observe().doOnNext(issue -> {

			this.checkForPullRequest(issue, ghbt);// this checks if an issue is a pull request. if it is, then adds it
													// to the
			// pull request list, else the issue is added to the issue and issue id
			// list.
		}).doOnError(e -> System.out.println(e)).blockingSubscribe();
	}

	/**
	 * Gets all comments from issues that have them
	 * 
	 * @param ghbt
	 * @param issueID
	 * @throws Exception
	 */
	private void getIssueComments(GitHubBugTracker ghbt, String issueID) throws Exception {

		IGitHubApi gitHubApi = getSession(ghbt);
		IDataSet<IssuesComments> repoComments = gitHubApi.getReposIssuesComments(ghbt.getOwner(), ghbt.getRepository(),
				Integer.parseInt(issueID));
		repoComments.observe().doOnNext(comment -> {
			this.addIssueCommentToList(GitHubReaderUtils.convertToGitHubComment(comment, ghbt, issueID));
		}).doOnError(e -> System.out.println(e)).blockingSubscribe();

	}


	private void addIssueCommentToList(GitHubComment gitHubComment) {

		this.issueCommentsList.add(gitHubComment);

	}


	/**
	 * This checks if an issue is a pull request and also populates a list with date
	 * ranges for each issue and pull request respectively
	 * 
	 * 
	 * 
	 * @param issue
	 */
	private void checkForPullRequest(Issues issue, GitHubBugTracker ghbt) {

		try {
			if (!issue.getPullRequest().equals(null)) {

				if (containsPullRequest(this.pullRequestsList, issue.getNumber().toString()) == false) {

					this.pullRequestIdList.add(issue.getNumber());// TODO FIX THIS

				}

			}

		} catch (NullPointerException np) {// If a null pointer is caught the issue must not be a pull request!

			issuesMap.put(issue.getNumber().toString(), GitHubReaderUtils.convertToGitHubIssue(issue, ghbt));

			// This checks if the issue is all ready present in the list, if it isnt it adds
			// it performs the tasks below.
			String id = issue.getNumber().toString();

			Date creation = new Date(GitHubReaderUtils.convertStringToDate(issue.getCreatedAt()));
			Date modified = new Date(GitHubReaderUtils.convertStringToDate(issue.getUpdatedAt()));

			IssueDateRange idr = new IssueDateRange(id, creation, modified);
			this.issueRangeMap.put(id, idr);

			GitHubIssue ghissue = GitHubReaderUtils.convertToGitHubIssue(issue, ghbt);
			this.issuesMap.put(id, ghissue);
		}
	}

	/**
	 * This checks if a specific issue (via its ID) is present in a list.
	 * 
	 * @param list
	 * @param issueID
	 * @return boolean
	 */
	public boolean containsPullRequest(List<GitHubPullRequest> list, final String issueID) {

		return list.stream().filter(pr -> pr.getId().equals(issueID)).findFirst().isPresent();

		// return list.stream().filter(issue ->
		// issue.getBugId().equals(issueID)).findFirst().isPresent();
	}

	public static IGitHubApi getSession(GitHubBugTracker GitHubBugTracker) throws Exception {
		if (session == null) {
			session = new GitHubSessionUtil(GitHubBugTracker.getAuthenticationData());
		}
		return session.getSession();
	}
	
	@Override
	public boolean isRestmule() {
		return true;
	}

	/*----------------------------------------------------------------------------------
	 *  TRASH CODE
	 *  ----------------------------------------------------------------------------------
	 *  The logic contained within these constructors maybe useless code 
	 *  Keep logic in comments until its fate has been decided 
	 *  ----------------------------------------------------------------------------------*/

	@Override
	public String getContents(DB db, GitHubBugTracker ghbt, BugTrackingSystemBug bug) throws Exception {

		return null;
	}

	@Override
	public String getContents(DB db, GitHubBugTracker ghbt, BugTrackingSystemComment comment) throws Exception {

		return null;
	}

}