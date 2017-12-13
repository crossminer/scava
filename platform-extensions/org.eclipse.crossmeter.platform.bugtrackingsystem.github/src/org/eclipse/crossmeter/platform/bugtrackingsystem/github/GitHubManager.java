/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jacob Carter - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.platform.bugtrackingsystem.github;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang3.time.DateUtils;
import org.eclipse.crossmeter.platform.Date;
import org.eclipse.crossmeter.platform.bugtrackingsystem.cache.Cache;
import org.eclipse.crossmeter.platform.bugtrackingsystem.cache.Caches;
import org.eclipse.crossmeter.platform.bugtrackingsystem.github.api.GitHubIssueQuery;
import org.eclipse.crossmeter.platform.bugtrackingsystem.github.api.GitHubSession;
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.IBugTrackingSystemManager;
import org.eclipse.crossmeter.repository.model.BugTrackingSystem;
import org.eclipse.crossmeter.repository.model.github.GitHubBugTracker;
import org.eclipse.egit.github.core.Comment;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.client.PageIterator;
import org.joda.time.DateTime;

import com.mongodb.DB;

public class GitHubManager implements
		IBugTrackingSystemManager<GitHubBugTracker> {

	private final GitHubEntityManager entityManager = new GitHubEntityManager();

	private final Caches<GitHubIssue, String> issueCaches = new Caches<GitHubIssue, String>(
			new IssueCacheProvider(entityManager));

	private final Caches<GitHubComment, String> commentCaches = new Caches<GitHubComment, String>(
			new CommentCacheProvider(entityManager));

	private final Caches<GitHubPullRequest, Integer> pullRequestCaches = new Caches<GitHubPullRequest, Integer>(
			new PullRequestCacheProvider(entityManager));

	@Override
	public boolean appliesTo(BugTrackingSystem bugTrackingSystem) {
		return bugTrackingSystem instanceof GitHubBugTracker;
	}

	@Override
	public BugTrackingSystemDelta getDelta(DB db, GitHubBugTracker bts, Date date)
			throws Exception {
		GitHubBugTrackingSystemDelta delta = new GitHubBugTrackingSystemDelta();
		delta.setBugTrackingSystem(bts);

		GitHubSession github = getSession(bts);

		getIssues(bts, date, github, delta);
		getComments(bts, date, github, delta);
		getPullRequests(bts, date, github, delta);

		return delta;
	}

	@Override
	public Date getFirstDate(DB db, GitHubBugTracker bts) throws Exception {
		return getEarliestIssueDate(bts);
	}

	@Override
	public String getContents(DB db, GitHubBugTracker bts, BugTrackingSystemBug bug) throws Exception {
		GitHubSession github = getSession(bts);
		Issue issue = github.getIssue(bts.getUser(), bts.getRepository(),
				bug.getBugId());
		if (null != issue) {
			return issue.getBodyText();
		}
		return null;
	}

	@Override
	public String getContents(DB db, GitHubBugTracker bts,
			BugTrackingSystemComment comment) throws Exception {
		GitHubSession github = getSession(bts);

		long commentId = Long.parseLong(comment.getCommentId());
		Comment ghComment = github.getComment(bts.getUser(),
				bts.getRepository(), commentId);
		if (null != ghComment) {
			return ghComment.getBodyText();
		}
		return null;
	}
	
	// Not sure if we should just include these in the deltas?
	public GitHubMilestone getMilestone(int id) {
		return entityManager.getMilestone(id);
	}
	
	// Not sure if we should just include these in the deltas?
	public GitHubUser getUser(String id) {
		return entityManager.getUser(id);
	}
	
	// Not sure if we should just include these in the deltas?
	public GitHubRepository getRepository(long id) {
		return entityManager.getRepository(id);
	}

	public static GitHubSession getSession(GitHubBugTracker bugTracker) {
		GitHubSession session = new GitHubSession();
		String login = bugTracker.getLogin();
		if (login != null && login.trim().length() > 0 && !"null".equals(login)) {
			session.setCredentials(login, bugTracker.getPassword());
		}

		return session;
	}

	private static Date getEarliestIssueDate(GitHubBugTracker bts)
			throws IOException {
		GitHubSession github = getSession(bts);
		GitHubIssueQuery query = new GitHubIssueQuery(bts.getUser(),
				bts.getRepository());
		query.sortByCreated();
		query.setAscendingDirection();
		query.setPageSize(1);
		PageIterator<Issue> pages = github.getIssues(query);
		if (pages.hasNext()) {
			Collection<Issue> issues = pages.next();
			Iterator<Issue> it = issues.iterator();
			if (it.hasNext()) {
				return new Date(it.next().getCreatedAt());
			}
		}

		return null;
	}

	/**
	 * 
	 * Gets issues that have either been updated or created on the given date
	 * 
	 * @param bugTracker
	 * @param since
	 * @param github
	 * @param delta
	 * @throws Exception
	 */
	private void getIssues(GitHubBugTracker bugTracker, Date on,
			GitHubSession github, GitHubBugTrackingSystemDelta delta)
			throws Exception {

		java.util.Date day = on.toJavaDate();

		Cache<GitHubIssue, String> issuesCache = issueCaches
				.getCache(bugTracker, true);
		Iterable<GitHubIssue> issues = issuesCache.getItemsOnDate(day);

		for (GitHubIssue issue : issues) {
			if (DateUtils.isSameDay(issue.getCreationTime(), day)) {
				delta.getNewBugs().add(issue);
			} else {
				delta.getUpdatedBugs().add(issue);
			}
		}
	}

	/**
	 * 
	 * Gets comments that have either been updated or created on the given date
	 * 
	 * @param bugTracker
	 * @param since
	 * @param github
	 * @param delta
	 * @throws Exception
	 */

	private void getComments(GitHubBugTracker bugTracker, Date on,
			GitHubSession github, GitHubBugTrackingSystemDelta delta)
			throws Exception {

		java.util.Date day = on.toJavaDate();

		Cache<GitHubComment, String> commentsCache = commentCaches
				.getCache(bugTracker, true);
		Iterable<GitHubComment> comments = commentsCache.getItemsOnDate(day);

		for (GitHubComment comment : comments) {
			delta.getComments().add(comment);
		}
	}

	/**
	 * Gets pull requests that have either been updated or created on the given
	 * date
	 * 
	 * @param bts
	 * @param since
	 * @param github
	 * @param delta
	 * @throws Exception
	 */
	private void getPullRequests(GitHubBugTracker bugTracker, Date on,
			GitHubSession github, GitHubBugTrackingSystemDelta delta)
			throws Exception {

		java.util.Date day = on.toJavaDate();

		Cache<GitHubPullRequest, Integer> pullRequestsCache = pullRequestCaches
				.getCache(bugTracker, true);
		Iterable<GitHubPullRequest> pullRequests = pullRequestsCache
				.getItemsOnDate(day);

		for (GitHubPullRequest pullRequest : pullRequests) {
			delta.getPullRequests().add(pullRequest);
		}
	}

	public static void main(String[] args) throws Exception {

		GitHubBugTracker bugTracker = new GitHubBugTracker();
		bugTracker.setUser("sampsyo");
		bugTracker.setRepository("beets");
		// bugTracker.setLogin("ossmetertest");
		// bugTracker.setPassword("T35tAccount");

		Date date = new Date(new DateTime(2014, 7, 14, 0, 0).toDate());

		GitHubManager github = new GitHubManager();
		GitHubBugTrackingSystemDelta delta = (GitHubBugTrackingSystemDelta) github
				.getDelta(null, bugTracker, date);

		System.out.println(delta.getNewBugs().size());
		System.out.println(delta.getUpdatedBugs().size());

		date = new Date(new DateTime(2014, 6, 20, 0, 0).toDate());

		delta = (GitHubBugTrackingSystemDelta) github
				.getDelta(null, bugTracker, date);
		System.out.println(delta.getNewBugs().size());
		System.out.println(delta.getUpdatedBugs().size());
	}
}
