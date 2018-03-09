/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.bitbucket.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

public class BitbucketRestClient {
	private static final String BITBUCKET_API_V1_URL = "https://bitbucket.org/api/1.0/";
	private static final String BITBUCKET_API_V2_URL = "https://bitbucket.org/api/2.0/";

	private static final ObjectMapper objectMapper;
	private static final ObjectReader issueReader;
	private static final ObjectReader commentReader;
	private static final ObjectReader commentsReader;
	private static final ObjectReader searchReader;
	private static final ObjectReader pullRequestsReader;

	static {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		SimpleModule module = new SimpleModule();
		module.addDeserializer(BitbucketIssue.class,
				new BitbucketIssueDeserialiser());
		module.addDeserializer(BitbucketIssueComment.class,
				new BitbucketIssueCommentDeserialiser());
		module.addDeserializer(BitbucketLinks.class,
				new BitbucketLinksDeserialiser());
		module.addDeserializer(BitbucketPullRequest.class,
				new BitbucketPullRequestDeserialiser());
		objectMapper.registerModule(module);

		issueReader = objectMapper.reader(BitbucketIssue.class);
		commentReader = objectMapper.reader(BitbucketIssueComment.class);
		commentsReader = objectMapper
				.reader(new TypeReference<List<BitbucketIssueComment>>() {
				});
		searchReader = objectMapper.reader(BitbucketSearchResult.class);
		pullRequestsReader = objectMapper
				.reader(BitbucketPullRequestPage.class);
	}

	private String login;
	private String password;

	public void setCredentials(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public static void shutdown() throws IOException {
		Unirest.shutdown();
	}

	public BitbucketIssue getIssue(String user, String repository,
			String issueId, boolean retrieveComments) throws UnirestException,
			JsonProcessingException, IOException {
		String url = BITBUCKET_API_V1_URL + "repositories/" + user + '/'
				+ repository + "/issues/" + issueId;
		GetRequest getRequest = Unirest.get(url);
		HttpResponse<InputStream> response = executeRestCall(getRequest);

		BitbucketIssue issue = issueReader.readValue(response.getRawBody());
		if (null != issue && retrieveComments) {
			List<BitbucketIssueComment> comments = getIssueComments(user,
					repository, issue.getBugId());
			for (BitbucketIssueComment comment : comments) {
				issue.getComments().add(comment);
			}
		}
		return issue;
	}

	public BitbucketIssueComment getIssueComment(String user,
			String repository, String issueId, String commentId)
			throws UnirestException, JsonProcessingException, IOException {
		String url = BITBUCKET_API_V1_URL + "repositories/" + user + '/'
				+ repository + "/issues/" + issueId + "/comments/" + commentId;
		GetRequest getRequest = Unirest.get(url);
		HttpResponse<InputStream> response = executeRestCall(getRequest);

		BitbucketIssueComment comment = commentReader.readValue(response
				.getRawBody());
		if (null != comment) {
			comment.setBugId(issueId);
		}
		return comment;
	}

	public List<BitbucketIssueComment> getIssueComments(String user,
			String repository, String issueId) throws UnirestException,
			JsonProcessingException, IOException {
		String url = BITBUCKET_API_V1_URL + "repositories/" + user + '/'
				+ repository + "/issues/" + issueId + "/comments/";
		GetRequest getRequest = Unirest.get(url);
		HttpResponse<InputStream> response = executeRestCall(getRequest);

		List<BitbucketIssueComment> comments = commentsReader
				.readValue(response.getRawBody());
		for (BitbucketIssueComment comment : comments) {
			comment.setBugId(issueId);
		}
		return comments;
	}

	public Iterator<BitbucketIssue> getIssues(BitbucketIssueQuery query,
			boolean retrieveComments) {
		return new BitbucketIssueIterator(this, query, retrieveComments);
	}

	public BitbucketSearchResult search(BitbucketIssueQuery query,
			boolean retrieveComments, int startAt)
			throws JsonProcessingException, UnirestException, IOException {
		return search(query, retrieveComments, startAt,
				BitbucketConstants.DEFAULT_PAGE_SIZE);
	}

	public BitbucketSearchResult search(BitbucketIssueQuery query,
			boolean retrieveComments, int startAt, int pageSize)
			throws UnirestException, JsonProcessingException, IOException {

		String user = query.getUser();
		String repository = query.getRepository();

		String url = BITBUCKET_API_V1_URL + "repositories/" + user + '/'
				+ repository + "/issues";

		GetRequest get = Unirest.get(url);

		get = get.field("limit", pageSize).field("start", startAt);

		if (null != query.getSearch()) {
			get = get.field("search", query.getSearch());
		}

		for (String status : query.getStatuses()) {
			get = get.field("status", status);
		}

		for (String priority : query.getPriorities()) {
			get = get.field("priority", priority);
		}

		if (null != query.getSort()) {
			get = get.field("sort", query.getSort());
		}

		HttpResponse<InputStream> response = executeRestCall(get);
		BitbucketSearchResult result = searchReader.readValue(response
				.getRawBody());
		if (retrieveComments) {
			for (BitbucketIssue issue : result.getIssues()) {
				List<BitbucketIssueComment> comments = getIssueComments(user,
						repository, issue.getBugId());
				for (BitbucketIssueComment comment : comments) {
					issue.getComments().add(comment);
				}
			}
		}
		return result;
	}

	public Iterator<BitbucketPullRequest> getPullRequests(String user,
			String repository) {
		return new BitbucketPullRequestIterator(this, user, repository);
	}

	BitbucketPullRequestPage getPullRequests(String user, String repository,
			int page) throws JsonProcessingException, IOException,
			UnirestException {
		String url = BITBUCKET_API_V2_URL + "repositories/" + user + '/'
				+ repository + "/pullrequests";

		GetRequest get = Unirest.get(url);
		get = get.field("page", page).field("state", "OPEN,MERGED,DECLINED");

		HttpResponse<InputStream> response = executeRestCall(get);
		return pullRequestsReader.readValue(response.getRawBody());

	}

	protected HttpResponse<InputStream> executeRestCall(GetRequest getRequest)
			throws UnirestException {
		if (null != login && null != password) {
			getRequest = getRequest.basicAuth(login, password);
		}

		HttpResponse<InputStream> response = getRequest.asBinary();
		if (response.getCode() != 200) {
			throw new RuntimeException("Error executing call to webservice. ["
					+ response.getCode() + "," + getRequest.getUrl() + "]");
		}
		return response;
	}

	public static void main(String[] args) throws JsonProcessingException,
			UnirestException, IOException {
		BitbucketRestClient bitbucket = new BitbucketRestClient();

		Iterator<BitbucketPullRequest> prs = bitbucket.getPullRequests(
				"jmurty", "jets3t");
		int i = 0;
		while (prs.hasNext()) {
			BitbucketPullRequest pr = prs.next();
			System.out.println(i++ + " " + pr.getTitle());
		}

		BitbucketIssueQuery query2 = new BitbucketIssueQuery("jmurty", "jets3t");
		query2.setSort("-utc_last_updated");

		Iterator<BitbucketIssue> issues = bitbucket.getIssues(query2, false);
		while (issues.hasNext()) {
			BitbucketIssue iss = issues.next();
			System.out.println(iss.getBugId() + " " + iss.getTitle());
		}

		BitbucketIssueQuery query = new BitbucketIssueQuery("jmurty", "jets3t");
		query.setSort("-utc_last_updated");

		BitbucketSearchResult result = bitbucket.search(query, false, 0, 2);
		System.out.println(result.getIssues().get(0).getBugId());
		System.out.println(result.getIssues().get(1).getBugId());
		result = bitbucket.search(query, false, 1, 1);
		System.out.println(result.getIssues().get(0).getBugId());

		BitbucketIssue issue = bitbucket.getIssue("jmurty", "jets3t", "123",
				true);
		System.out.println(issue);
		BitbucketIssueComment comment = bitbucket.getIssueComment("jmurty",
				"jets3t", "123", "828334");
		System.out.println(comment);

		BitbucketRestClient.shutdown();
	}
}
