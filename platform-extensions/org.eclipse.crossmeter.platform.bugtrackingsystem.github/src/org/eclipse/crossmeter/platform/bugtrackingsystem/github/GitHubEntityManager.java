/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.platform.bugtrackingsystem.github;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.crossmeter.platform.bugtrackingsystem.github.api.ExtendedComment;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.PullRequestMarker;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;

class GitHubEntityManager {
	private Map<Integer, GitHubMilestone> milestones = new HashMap<Integer, GitHubMilestone>();
	private Map<String, GitHubUser> users = new HashMap<String, GitHubUser>();
	private Map<Long, GitHubRepository> repositories = new HashMap<Long, GitHubRepository>();

	public GitHubEntityManager() {
		// TODO Auto-generated constructor stub
	}

	public GitHubMilestone getMilestone(int id) {
		return milestones.get(id);
	}

	public GitHubUser getUser(String id) {
		return users.get(id);
	}

	public GitHubRepository getRepository(long id) {
		return repositories.get(id);
	}

	GitHubComment process(ExtendedComment comment) {
		GitHubComment ghComment = new GitHubComment();

		ghComment.setCommentId(Long.toString(comment.getId()));
		ghComment.setBugId(Long.toString(comment.getIssueId()));
		ghComment.setCreationTime(comment.getCreatedAt());
		ghComment.setCreator(processUser(comment.getUser()));
		ghComment.setUpdatedAt(comment.getUpdatedAt());
		ghComment.setUrl(comment.getUrl());
		ghComment.setText(comment.getBody());

		return ghComment;
	}

	GitHubIssue process(Issue issue) {
		GitHubIssue ghIssue = new GitHubIssue();

		ghIssue.setAssignee(processUser(issue.getAssignee()));
		ghIssue.setBugId(Long.toString(issue.getId()));
		ghIssue.setClosedTime(issue.getClosedAt());
		ghIssue.setCreationTime(issue.getCreatedAt());
		ghIssue.setCreator(processUser(issue.getUser()));
		ghIssue.setNumComments(issue.getComments());
		ghIssue.setStatus(issue.getState());
		ghIssue.setTitle(issue.getTitle());
		ghIssue.setSummary(issue.getTitle());
		ghIssue.setUpdatedTime(issue.getUpdatedAt());
		ghIssue.setUrl(issue.getUrl());
		ghIssue.setHtmlUrl(issue.getHtmlUrl());
		ghIssue.setMilestone(processMilestone(issue.getMilestone()));

		for (Label label : issue.getLabels()) {
			GitHubLabel ghLabel = new GitHubLabel();
			ghLabel.setColour(label.getColor());
			ghLabel.setName(label.getName());
			ghLabel.setUrl(label.getUrl());
			ghIssue.addLabel(ghLabel);
		}

		return ghIssue;
	}

	GitHubPullRequest process(PullRequest pr) {
		GitHubPullRequest pullRequest = new GitHubPullRequest();

		pullRequest.setAdditions(pr.getAdditions());
		pullRequest.setBody(pr.getBody());
		pullRequest.setBodyHtml(pr.getBodyHtml());
		pullRequest.setBodyText(pr.getBodyText());
		pullRequest.setChangedFiles(pr.getChangedFiles());
		pullRequest.setClosedAt(pr.getClosedAt());
		pullRequest.setComments(pr.getComments());
		pullRequest.setCommits(pr.getCommits());
		pullRequest.setCreatedAt(pr.getCreatedAt());
		pullRequest.setDeletions(pr.getDeletions());
		pullRequest.setDiffUrl(pr.getDiffUrl());
		pullRequest.setHtmlUrl(pr.getHtmlUrl());
		pullRequest.setId(pr.getId());
		pullRequest.setIssueUrl(pr.getIssueUrl());
		pullRequest.setMergeable(pr.isMergeable());
		pullRequest.setMerged(pr.isMerged());
		pullRequest.setMergedAt(pr.getMergedAt());
		pullRequest.setNumber(pr.getNumber());
		pullRequest.setPatchUrl(pr.getPatchUrl());
		pullRequest.setState(pr.getState());
		pullRequest.setTitle(pr.getTitle());
		pullRequest.setUpdatedAt(pr.getUpdatedAt());
		pullRequest.setUrl(pr.getUrl());

		pullRequest.setAssignee(processUser(pr.getAssignee()));
		pullRequest.setMergedBy(processUser(pr.getMergedBy()));
		pullRequest.setUser(processUser(pr.getUser()));

		pullRequest.setMilestone(processMilestone(pr.getMilestone()));
		pullRequest.setBase(processPullRequestMarker(pr.getBase()));
		pullRequest.setHead(processPullRequestMarker(pr.getHead()));

		return pullRequest;
	}

	private GitHubPullRequestMarker processPullRequestMarker(
			PullRequestMarker prm) {
		if (prm == null) {
			return null;
		}

		GitHubPullRequestMarker marker = new GitHubPullRequestMarker();
		marker.setLabel(prm.getLabel());
		marker.setRef(prm.getRef());
		marker.setSha(prm.getSha());
		marker.setUser(processUser(prm.getUser()));

		marker.setRepository(processRepository(prm.getRepo()));

		return marker;
	}

	private Integer processMilestone(Milestone milestone) {
		if (null == milestone) {
			return null;
		}

		int number = milestone.getNumber();

		GitHubMilestone ghMilestone = new GitHubMilestone(milestone.getNumber());
		ghMilestone.setClosedIssues(milestone.getClosedIssues());
		ghMilestone.setCreatedAt(milestone.getCreatedAt());
		ghMilestone.setCreator(processUser(milestone.getCreator()));
		ghMilestone.setDescription(milestone.getDescription());
		ghMilestone.setDueOn(milestone.getDueOn());
		ghMilestone.setOpenIssues(milestone.getOpenIssues());
		ghMilestone.setStatus(milestone.getState());
		ghMilestone.setTitle(milestone.getTitle());
		ghMilestone.setUrl(milestone.getUrl());

		milestones.put(number, ghMilestone);

		return number;
	}

	private String processUser(User user) {
		if (null == user) {
			return null;
		}

		String id = Integer.toString(user.getId());
		GitHubUser ghUser = new GitHubUser();
		ghUser.setId(id);
		ghUser.setLogin(user.getLogin());
		ghUser.setName(user.getName());
		ghUser.setType(user.getType());
		ghUser.setUrl(user.getUrl());

		users.put(id, ghUser);

		return id;
	}

	private Long processRepository(Repository repo) {
		if (null == repo) {
			return null;
		}

		long id = repo.getId();
		GitHubRepository repository = new GitHubRepository();
		repository.setId(id);
		repository.setName(repo.getName());
		repository.setOwner(processUser(repo.getOwner()));

		repositories.put(id, repository);
		return id;
	}
}
