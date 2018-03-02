/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.jira.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

public class JiraRestClient {

	public static final int DEFAULT_PAGE_SIZE = 50;

	private static final String API_PATH = "rest/api/2/";

	private static final DateTimeFormatter jqlDateTimeFormatter = DateTimeFormat
			.forPattern("yyyy/MM/dd HH:mm");

	private String apiUrl;

	private int pageSize = DEFAULT_PAGE_SIZE;

	private static final ObjectMapper objectMapper;
	private static final ObjectReader issueReader;
	private static final ObjectReader commentReader;
	private static final ObjectReader searchReader;

	static {
		// http://wiki.fasterxml.com/JacksonBestPracticesPerformance
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		SimpleModule module = new SimpleModule();
		module.addDeserializer(JiraIssue.class, new JiraIssueDeserialiser());
		module.addDeserializer(JiraComment.class, new JiraCommentDeserialiser());
		objectMapper.registerModule(module);

		issueReader = objectMapper.reader(JiraIssue.class);
		searchReader = objectMapper.reader(JiraSearchResult.class);
		commentReader = objectMapper.reader(JiraComment.class);
	}

	private String login;
	private String password;

	public JiraRestClient(String serverBaseUrl) {
		if (!serverBaseUrl.endsWith("/")) {
			serverBaseUrl += '/';
		}

		this.apiUrl = serverBaseUrl + API_PATH;
	}

	public static void shutdown() throws IOException {
		Unirest.shutdown();
	}

	public void setCredentials(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	// TODO think we don't need the created clauses in here, for our caching purposes.
	public Iterator<JiraIssue> getIssues(String projectId, Date after,
			Date before) throws UnirestException {

		String projectClause = createClause("project", "=", projectId, true);

		String updatedAfterClause = null;
		String createdAfterClause = null;
		if (null != after) {
			DateTime date = new DateTime(after);
			String dateString = jqlDateTimeFormatter.print(date);
			updatedAfterClause = createClause("updated", ">=", dateString, true);
			createdAfterClause = createClause("created", ">=", dateString, true);
		}

		String updatedBeforeClause = null;
		String createdBeforeClause = null;
		if (null != before) {
			DateTime date = new DateTime(before);
			String dateString = jqlDateTimeFormatter.print(date);
			updatedBeforeClause = createClause("updated", "<", dateString, true);
			createdBeforeClause = createClause("created", "<", dateString, true);
		}

		String updatedClause = combineClauses("AND", true, updatedAfterClause,
				updatedBeforeClause);
		String createdCaluse = combineClauses("AND", true, createdAfterClause,
				createdBeforeClause);

		String query = combineClauses("OR", true, updatedClause, createdCaluse);
		query = combineClauses("AND", false, projectClause, query);

		// See here https://docs.atlassian.com/jira/REST/latest/#d2e379
		// This makes sure we return comments
		String fieldsToRetrieve = "*all,-issuelinks";
		
		return new JiraIssueIterator(this, query, fieldsToRetrieve);
	}

	public JiraIssue getIssue(String issueIdOrKey) throws UnirestException,
			JsonProcessingException, IOException {
		return getIssue(issueIdOrKey, null);
	}

	public JiraIssue getIssue(String issueIdOrKey, String fields)
			throws UnirestException, JsonProcessingException, IOException {
		String url = apiUrl + "issue/" + issueIdOrKey;

		GetRequest getRequest = Unirest.get(url);
		HttpResponse<InputStream> response = executeRestCall(getRequest, fields);

		return issueReader.readValue(response.getRawBody());
	}

	public JiraComment getComment(String issueIdOrKey, String commentId)
			throws JsonProcessingException, IOException, UnirestException {
		String url = apiUrl + "issue/" + issueIdOrKey + "/comment/" + commentId;
		GetRequest getRequest = Unirest.get(url);

		HttpResponse<InputStream> response = executeRestCall(getRequest, null);

		JiraComment comment = commentReader.readValue(response.getRawBody());
		comment.setBugId(issueIdOrKey);
		return comment;
	}

	public JiraSearchResult search(String jql, int startAt, String fields)
			throws UnirestException, JsonProcessingException, IOException {
		return search(jql, startAt, pageSize, fields);
	}

	public JiraSearchResult search(String jql, int startAt, int pageSize,
			String fields) throws UnirestException, JsonProcessingException,
			IOException {
		String url = apiUrl + "search";
		GetRequest getRequest = Unirest.get(url).field("startAt", startAt)
				.field("maxResults", pageSize).field("jql", jql);

		HttpResponse<InputStream> response = executeRestCall(getRequest, fields);
		return searchReader.readValue(response.getRawBody());
	}

	protected HttpResponse<InputStream> executeRestCall(GetRequest getRequest,
			String fields) throws UnirestException {
		if (null != login && null != password) {
			getRequest = getRequest.basicAuth(login, password);
		}
		if (null != fields) {
			getRequest = getRequest.field("fields", fields);
		}

		getRequest.header("accept", "application/json");

		HttpResponse<InputStream> response = getRequest.asBinary();
		if (response.getCode() != 200) {
			throw new RuntimeException("Error executing call to webservice. ["
					+ response.getCode() + "," + getRequest.getUrl() + "]");
		}

		return response;
	}

	protected String createClause(String field, String operator, String value,
			boolean quotes) {
		StringBuilder sb = new StringBuilder();
		sb.append(field);
		sb.append(' ');
		sb.append(operator);
		sb.append(' ');
		if (quotes) {
			sb.append('"');
		}
		sb.append(value);
		if (quotes) {
			sb.append('"');
		}
		return sb.toString();
	}

	protected String combineClauses(String operator, boolean parentheses,
			String clause1, String clause2) {

		if (clause1 == null && clause2 == null) {
			return null;
		} else if (clause1 == null) {
			return clause2;
		} else if (clause2 == null) {
			return clause1;
		}

		StringBuilder sb = new StringBuilder();

		if (parentheses) {
			sb.append('(');
		}
		sb.append(clause1);
		sb.append(' ');
		sb.append(operator);
		sb.append(' ');
		sb.append(clause2);

		if (parentheses) {
			sb.append(')');
		}
		return sb.toString();
	}

	@Deprecated
	protected void appendToQuery(String field, String operator, String value,
			StringBuilder sb, boolean quotesAroundValue) {
		if (null == value) {
			return;
		}

		if (sb.length() > 0) {
			sb.append(" AND ");
		}

		sb.append(field);
		sb.append(' ');
		sb.append(operator);
		sb.append(' ');
		if (quotesAroundValue) {
			sb.append('"');
		}
		sb.append(value);
		if (quotesAroundValue) {
			sb.append('"');
		}
	}

	public static void main(String[] args) throws UnirestException,
			JsonProcessingException, IOException {
		// System.out.println(UriBuilder.fromUri("http://a.com").path("ffff").path("fff"));
		JiraRestClient jira = new JiraRestClient("http://jira.codehaus.org/");
		// jira.getIssue("MNG-5649");
		// jira.getIssue("MNG-122");
		// jira.getIssue("GROOVY-6845");
		// jira.getComment("MNG-2205", "162059");

		JiraIssue issue = jira.getIssue("MNG-122");
		System.out.println(issue.getDescription());

		JiraComment comment = jira.getComment("MNG-220", "38768");
		System.out.println(comment.getText());

		/*
		 * for (JiraIssue issue : jira.getIssues("MNG", new DateTime(2014, 6,
		 * 20, 0, 0).toDate())) { System.out.println(issue.getDescription()); }
		 */

		JiraRestClient.shutdown();

	}
}
