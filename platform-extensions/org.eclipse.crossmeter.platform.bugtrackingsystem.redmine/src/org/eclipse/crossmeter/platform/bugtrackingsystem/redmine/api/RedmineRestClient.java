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
package org.eclipse.crossmeter.platform.bugtrackingsystem.redmine.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

public class RedmineRestClient {

	private static final String JSON_SUFFIX = ".json";

	private String apiUrl;
	private String login;
	private String password;

	private final static ObjectMapper objectMapper;
	private final static ObjectReader issueReader;
	private final static ObjectReader basicSearchReader;
	private final static ObjectReader extendedSearchReader;

	static {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		SimpleModule module = new SimpleModule();
		module.addDeserializer(RedmineIssue.class,
				new RedmineIssueDeserialiser());
		module.addDeserializer(RedmineComment.class,
				new RedmineCommentDeserialiser());
		module.addDeserializer(RedmineBasicSearchResult.class,
				new RedmineBasicSearchResultDeserialiser());
		objectMapper.registerModule(module);

		issueReader = objectMapper.reader(RedmineIssueResult.class);
		extendedSearchReader = objectMapper
				.reader(RedmineExtendedSearchResult.class);
		basicSearchReader = objectMapper.reader(RedmineBasicSearchResult.class);
	}

	public RedmineRestClient(String url) {
		if (!url.endsWith("/")) {
			url += '/';
		}
		this.apiUrl = url;
	}

	public static void shutdown() throws IOException {
		Unirest.shutdown();
	}

	public void setCredentials(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public void setCredentials(String apiKey) {
		login = apiKey;
		password = "PASSWORD_NOT_NEEDED";
	}

	public Iterator<RedmineIssue> getIssues(String projectIdOrName, Date after,
			Date before) throws UnirestException {
		try {
			return getIssues(projectIdOrName, after, before, false);
		}
		catch (Exception ex) {
			return getIssues(projectIdOrName, after, before, true);
		}
	}
	
	// TODO think we don't need the created clauses in here, for our caching purposes.
	public Iterator<RedmineIssue> getIssues(String projectIdOrName, Date after,
			Date before, boolean alternativeDateFormat) throws UnirestException {

		Map<String, String> filter = new HashMap<String, String>();

		if (null != projectIdOrName) {
			filter.put("project_id", projectIdOrName);
		}

		filter.put("status_id", "*");

		String dateString = null;
		
		if (null != after && null != before) {
			String afterString = RedmineConstants.getDateFormatter(alternativeDateFormat).
					print(new DateTime(after));
			String beforeString = RedmineConstants.getDateFormatter(alternativeDateFormat).
					print(new DateTime(before));
			dateString = "><" + afterString + '|' + beforeString;
		} else if (null != after) {
			String afterString = RedmineConstants.getDateFormatter(alternativeDateFormat).
					print(new DateTime(after));
			dateString = ">=" + afterString;
		} else if (before != null) {
			String beforeString = RedmineConstants.getDateFormatter(alternativeDateFormat).
					print(new DateTime(before));
			dateString = "<=" + beforeString;
		}

		if (null != dateString) {

			Set<Integer> issueIds = new HashSet<Integer>();

			// If no before date is specified, then we automatically get all
			// issues that have been created and/or updated from just a call
			// using 'updated_on' filter.
			if (before != null) {
				filter.put("created_on", dateString);
				RedmineIssueIds ids = new RedmineIssueIds(this, filter);
				for (Integer id : ids) {
					issueIds.add(id);
				}
				filter.remove("created_on");
			}

			filter.put("updated_on", dateString);
			RedmineIssueIds ids = new RedmineIssueIds(this, filter);
			for (Integer id : ids) {
				issueIds.add(id);
			}
			
			return new RedmineIssueIterator(this, issueIds.iterator());

		} else {
			return new RedmineIssueIterator(this, new RedmineIssueIds(this,
					filter).iterator());
		}
	}

	public RedmineIssue getIssue(int id) throws UnirestException,
			JsonProcessingException, IOException {
		String url = apiUrl + "issues/" + id + JSON_SUFFIX;

		GetRequest getRequest = Unirest.get(url).field("include", "journals");
		HttpResponse<InputStream> response = executeRestCall(getRequest);

		RedmineIssueResult result = issueReader
				.readValue(response.getRawBody());
		if (null != result) {
			return result.getIssue();
		}
		return null;
	}

	public RedmineComment getComment(int issueId, int commentId)
			throws JsonProcessingException, UnirestException, IOException {
		RedmineIssue issue = getIssue(issueId);
		if (null != issue) {
			for (BugTrackingSystemComment comment : issue.getComments()) {
				if (comment.getCommentId().equals(commentId)) {
					return (RedmineComment) comment;
				}
			}
		}

		return null;
	}

	/**
	 * WARNING: Issues within RedmineSearchResult do NOT contain comments. For
	 * issues with comments, use basicSearch() and retrieve each issue
	 * individually with getIssue.
	 * 
	 * @param filter
	 * @param startAt
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws UnirestException
	 */
	public RedmineExtendedSearchResult extendedSearch(
			Map<String, String> filter, int startAt)
			throws JsonProcessingException, IOException, UnirestException {
		return extendedSearch(filter, startAt,
				RedmineConstants.DEFAULT_PAGE_SIZE);
	}

	/**
	 * 
	 * WARNING: Issues within RedmineSearchResult do NOT contain comments. For
	 * issues with comments, use basicSearch and retrieve each issue
	 * individually with getIssue.
	 * 
	 * @param filter
	 * @param startAt
	 * @param pageSize
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws UnirestException
	 */
	public RedmineExtendedSearchResult extendedSearch(
			Map<String, String> filter, int startAt, int pageSize)
			throws JsonProcessingException, IOException, UnirestException {
		InputStream is = search(filter, startAt, pageSize);
		return extendedSearchReader.readValue(is);
	}

	public RedmineBasicSearchResult basicSearch(Map<String, String> filter,
			int startAt) throws UnirestException, JsonProcessingException,
			IOException {
		return basicSearch(filter, startAt, RedmineConstants.DEFAULT_PAGE_SIZE);
	}

	public RedmineBasicSearchResult basicSearch(Map<String, String> filter,
			int startAt, int pageSize) throws UnirestException,
			JsonProcessingException, IOException {
		InputStream is = search(filter, startAt, pageSize);
		return basicSearchReader.readValue(is);
	}

	protected InputStream search(Map<String, String> filter, int startAt,
			int pageSize) throws UnirestException {
		String url = apiUrl + "issues" + JSON_SUFFIX;
		GetRequest getRequest = Unirest.get(url);
		for (Entry<String, String> entry : filter.entrySet()) {
			getRequest = getRequest.field(entry.getKey(), entry.getValue());
		}
		getRequest = getRequest.field("offset", startAt);
		getRequest = getRequest.field("limit", pageSize);

		HttpResponse<InputStream> response = executeRestCall(getRequest);
		return response.getRawBody();
	}

	protected HttpResponse<InputStream> executeRestCall(GetRequest getRequest)
			throws UnirestException {
		if (null != login && null != password) {
			getRequest = getRequest.basicAuth(login, password);
		}
		getRequest.header("accept", "application/json");

		// System.out.println(getRequest.getUrl());

		HttpResponse<InputStream> response = getRequest.asBinary();
		if (response.getCode() != 200) {
			throw new RuntimeException("Error executing call to webservice. ["
					+ response.getCode() + "," + getRequest.getUrl() + "]");
		}

		return response;
	}

	public static void main(String[] args) throws JsonProcessingException,
			UnirestException, IOException {
		RedmineRestClient redmine = new RedmineRestClient(
				"http://www.redmine.org/");
		RedmineIssue issue = redmine.getIssue(17452);
		System.out.println(issue.getDescription());

		RedmineRestClient.shutdown();
	}
}
