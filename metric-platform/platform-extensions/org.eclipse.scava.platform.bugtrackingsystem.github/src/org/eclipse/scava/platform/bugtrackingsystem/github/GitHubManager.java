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
import org.eclipse.scava.workflow.restmule.core.data.IDataSet;
import org.eclipse.scava.workflow.restmule.generated.client.github.api.IGitHubApi;
import org.eclipse.scava.workflow.restmule.generated.client.github.model.Issues;
import org.eclipse.scava.workflow.restmule.generated.client.github.model.IssuesComments;

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

		// This checks if a GitHub client is available
		IGitHubApi gitHubApi;

		if (session == null) {

			gitHubApi = getSession(ghbt);

		} else {

			gitHubApi = this.session.getSession();

		}

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
				if ((idr.getModified().compareTo(idr.getCreated()) >= 0)) {

					delta.getUpdatedBugs().add(issuesMap.get(issueID));
				}

			}

			//THis only requests comments for issues that are within the scope of the date range 
			if ((idr.getCreated().compareTo(analysisDate) >= 0)
					&& (idr.getModified().compareTo(analysisDate) <= 0)) {

				// this should attempt to get comments for that particular issue and then add to a list
				getIssueComments(ghbt, issueID);

				for (GitHubComment comment : issueCommentsList) {

					Date commentDate = new Date(comment.getCreationTime());
					
					if (commentDate.compareTo(analysisDate) == 0) {

						delta.getComments().add(comment);

					}

				}

				this.issueCommentsList.clear();// This prepares for the next series of comments

			}

		}

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

	/*----------------------------------------------------------------------------------
	 * HERE BE DRAGONS!
	 * ----------------------------------------------------------------------------------
	 *  These methods are used for testing the Reader
	 * ----------------------------------------------------------------------------------*/

	public static void main(String[] args) throws Exception {

		String login = "Danny2097";
		String user = "Dan Campbell";
		String owner = "Danny2097";
		// String owner = "crossminer";
		String repo = "testing";
		// String repo = "crossminer";
	

		Date today = new Date();
		GitHubBugTracker ghbt = new GitHubBugTracker();
		ghbt.setProject(user, login, "dccw2097", repo, owner);
		// ghbt.setProject(user, login, "x", "paho.mqtt.java", "eclipse");

	
		GitHubManager ghm = new GitHubManager();

		// clears cache as currently there are probelms
		GitHubSessionUtil.clearGitHubCache(); // clears local github cache

		// Gets first Date
		Date date = ghm.getFirstDate(null, ghbt);
		System.out.println(date);
		Thread.sleep(10);// prevents console output bug

		int count = 0;
		
		do {

			BugTrackingSystemDelta delta = new BugTrackingSystemDelta();
			delta = ghm.getDelta(null, ghbt, date);
			System.err
					.println("\n\tDate :\t" + date + "\tNew Bugs :\t" + delta.getNewBugs().size() + "\tUpdated Bugs :\t"
							+ delta.getUpdatedBugs().size() + "\tComments :\t" + delta.getComments().size());
			count = count + delta.getNewBugs().size();
			date = new Date(date.addDays(1).toString());

		} while (date.compareTo(today) < 1);
		
		System.out.println(
				"\n---------------------------------------------------------------------------------------------------------------------"
			  + "\n"
			  + "\n---------------------------------------------------------------------------------------------------------------------");
		
		System.exit(0);
		
	}
		





	/*
	 * ================================== OLD CODE
	 * ==================================
	 * 
	 */

	// private void getCommentsOnDay(GitHubBugTracker ghbt, Date date,
	// GitHubBugTrackingSystemDelta delta)
	// throws Exception {
	//
	//
	// java.util.Date today = date.toJavaDate();
	//
	// for (GitHubComment comment : issueCommentsList) {
	//
	// Date commentDate = new Date(comment.getCreationTime());
	//
	// if (DateUtils.isSameDay(commentDate.toJavaDate(), today)) {
	//
	//
	// delta.getComments().add(comment);
	//
	// }
	//
	// }
	//
	// for (GitHubComment comment : pullRequestCommentList) {
	//
	// Date commentDate = new Date(comment.getCreationTime());
	//
	// if (DateUtils.isSameDay(commentDate.toJavaDate(), today)) {
	//
	// delta.getPullRequestsComments().add(comment);
	//
	// }
	// }
	//
	// }

	// private void getIssuesOnDay(GitHubBugTracker ghbt, Date date,
	// GitHubBugTrackingSystemDelta delta) throws Exception {
	//
	//
	// java.util.Date today = date.toJavaDate();
	//
	// for (Issues issue : issuesMap) {
	//
	// // Simple dates that only provide YYYYMMDD for easy comparison
	// Date createdSimple = new Date(issue.getCreatedAt().replace("-",
	// "").substring(0, 8).toString());
	// Date updatedSimple = new Date(issue.getUpdatedAt().replace("-",
	// "").substring(0, 8).toString());
	//
	// try {
	//
	// if (issue.getPullRequest().equals(null)) {
	//
	// }
	//
	// } catch (Exception e) {
	// GitHubIssue gitHubIssue = new GitHubIssue();
	//
	// // if the issue was created on the day in question add
	// if (DateUtils.isSameDay(createdSimple.toJavaDate(), today)) {
	//
	// gitHubIssue = GitHubReaderUtils.convertToGitHubIssue(issue, ghbt);
	//
	// for (GitHubComment comment : issueCommentsList) {
	//
	// if (gitHubIssue.getBugId().equals(comment.getBugId())) {
	//
	// gitHubIssue.addGithubComment(comment);
	//
	// }
	//
	// }
	//
	// delta.getNewBugs().add(gitHubIssue);
	//
	// }
	//
	// // if issue was updated on this day and was modified after the
	// // creation time (on the same day) add to updated bugs list
	// if (DateUtils.isSameDay(updatedSimple.toJavaDate(), today)) {
	//
	// // Dates that also include time-stamp for exact comparison
	// DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
	// DateTime isoCreated = parser.parseDateTime(issue.getCreatedAt());
	// DateTime isoUpdated = parser.parseDateTime(issue.getUpdatedAt());
	//
	// // this also catches issues that were updated on the same
	// // day but after the time they were created
	// if (isoUpdated.isAfter(isoCreated) == true) {
	//
	// //gitHubIssue = GitHubReaderUtils.convertToGitHubIssue(issue, ghbt, today);
	//
	// delta.getUpdatedBugs().add(gitHubIssue);
	//
	// }
	// }
	// }
	// }
	// }

	// private void getPullRequestsOnDay(GitHubBugTracker ghbt, Date date,
	// GitHubBugTrackingSystemDelta delta)
	// throws Exception {
	//
	//
	// java.util.Date today = date.toJavaDate();
	//
	// for (PullRequest pullRequest : pullRequestsList) {
	//
	// Date pullRequestDate = new Date(pullRequest.getCreatedAt().replace("-",
	// "").substring(0, 8).toString());
	//
	// if (DateUtils.isSameDay(pullRequestDate.toJavaDate(), today)) {
	//
	// GitHubPullRequest gitHubPullRequest = new GitHubPullRequest();
	//
	// gitHubPullRequest = GitHubReaderUtils.convertToGitHubPullRequest(pullRequest,
	// ghbt, today);
	//
	//
	// for (GitHubComment comment : issueCommentsList) {
	//
	// if (gitHubPullRequest.getId().equals(comment.getBugId())) {
	//
	// gitHubPullRequest.addPullRequestComment(comment);
	//
	// }
	// }
	//
	// delta.getPullRequests().add(gitHubPullRequest);
	// }
	// }
	// }
	// /**
	// * Gets all pullrequests from the repository
	// *
	// * @param ghbt
	// * GitHubBugTracker
	// * @throws Exception
	// */
	// private void getAllPullRequests(GitHubBugTracker ghbt) throws Exception {
	//
	// for (int id : pullRequestIdList) {
	//
	// IGitHubApi gitHubApi = getSession(ghbt);
	// IData<PullRequest> pullRequests =
	// gitHubApi.getReposPullsPullRequestByNumber(ghbt.getOwner(),
	// ghbt.getRepository(), id);
	// pullRequests.observe().doOnNext(pullRequest -> {
	// this.addPullRequestToList(pullRequest);
	//
	// }).doOnError(e -> System.out.println(e)).blockingSubscribe();
	// }
	//
	// }
	//
	//
	// //FIXME - Figure out what this is doing
	// private void getAllPullRequestComments(GitHubBugTracker ghbt) throws
	// Exception {
	//
	// for (GitHubPullRequest pr : this.pullRequestsList) {
	//
	// IGitHubApi gitHubApi = getSession(ghbt);
	// IDataSet<IssuesComments> pullRequestComments =
	// gitHubApi.getReposIssuesComments(ghbt.getOwner(),
	// ghbt.getRepository(), pr.getNumber());
	// pullRequestComments.observe().doOnNext(comment -> {
	// this.addPullRequestCommentToList(
	// GitHubReaderUtils.convertToGitHubComment(comment, ghbt, pr));
	// }).doOnError(e -> System.out.println(e)).blockingSubscribe();
	// }
	//
	// }

	//
	// private void addIssueToList(GitHubIssue issue) {
	//
	// this.issuesMap.add(issue);
	//
	// }
	//
	
	//
	// this.pullRequestCommentList.add(gitHubComment);
	//
	// }
	//
	// private void addPullRequestToList(GitHubPullRequest pullRequest) {
	//
	// this.pullRequestsList.add(pullRequest);
	//
	// }

}
