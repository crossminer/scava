/*******************************************************************************
* Copyright (c) 2019 Edge Hill University
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.gitlab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.bugtrackingsystem.gitlab.model.Comment;
import org.eclipse.scava.platform.bugtrackingsystem.gitlab.model.Issue;
import org.eclipse.scava.platform.bugtrackingsystem.gitlab.utils.GitLabUtils;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.IBugTrackingSystemManager;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.bts.gitlab.GitLabTracker;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DB;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GitLabManager implements IBugTrackingSystemManager<GitLabTracker> {

	private int callsRemaning;
	private int timeToReset;
	@SuppressWarnings("unused")
	private int rateLimit;
	private static String host = "http://gitlab.com/";
	private static String exthost = "api/v4/projects/";
	private final static String PAGE_SIZE = "100";
	private int current_page;
	private int next_page;
	private int last_page;
	private String open_id;
	private String builder;
	private OkHttpClient client;

	public GitLabManager() {

		this.open_id = "";
		this.builder = "";
		this.callsRemaning = 0;
		this.rateLimit = 0;
		this.timeToReset = 0;
		this.current_page = 0;
		this.last_page = 0;
		this.next_page = 0;
		this.client = new OkHttpClient();

	}
	
	@Override
	public boolean appliesTo(BugTrackingSystem bugTrackingSystem) {
	
		return bugTrackingSystem instanceof GitLabTracker;
	}

	@Override
	public BugTrackingSystemDelta getDelta(DB db, GitLabTracker gitlabTracker, Date date) throws Exception {

		BugTrackingSystemDelta delta = new BugTrackingSystemDelta();
		Map<String, String> tempBugIds = new HashMap<>();

		delta.setBugTrackingSystem(gitlabTracker);

		for (Issue issue : getIssuesCreatedAt(date, gitlabTracker)) {

			tempBugIds.put(issue.getIid(), issue.getIid());// a map that contains global issue ID and local (repository) iid
			delta.getNewBugs().add(gitlabIissueToBtsBug(issue, gitlabTracker));// adds new issues to delta
		
		}
		
		for (Issue issue : getIssuesUpdatedAt(date, gitlabTracker)) {
			
			tempBugIds.put(issue.getIid(), issue.getIid());	
			delta.getUpdatedBugs().add(gitlabIissueToBtsBug(issue, gitlabTracker));// adds modified issues to delta
		
		}

		for (String bugID : tempBugIds.keySet()) {
			
			for (Comment gitlabComment : getComments(date, bugID, gitlabTracker)) {
				
				delta.getComments().add(gitLabCommentToBtsComment(gitlabComment, gitlabTracker, bugID, tempBugIds.get(bugID)));//adds comments to delta
			
			}
		}

		return delta;
	}

	@Override
	public Date getFirstDate(DB db, GitLabTracker gitlabTracker) throws Exception {
		
		Date firstDate = null;

		setClient(gitlabTracker);

		HttpUrl.Builder builder = HttpUrl.parse(host + exthost).newBuilder();
		builder.addEncodedPathSegment(gitlabTracker.getProject_id());
		builder.addEncodedPathSegment("issues");
		builder.addEncodedQueryParameter("scope", "all");
		builder.addEncodedQueryParameter("sort", "asc");
		builder.addEncodedQueryParameter("per_page", PAGE_SIZE);

		Request request = new Request.Builder().url(builder.build().toString()).build();

		Response response = this.client.newCall(request).execute();
		JsonNode rootNode = new ObjectMapper().readTree(response.body().string());

		if (rootNode.isArray()) {
			String firstDateStr = rootNode.get(0).path("created_at").toString().replaceAll("\"", "");
			firstDate = new Date(convertStringToDate(firstDateStr).toString());
		}

		response.close();
		return firstDate;
	}

	// ------------------------------------------------------------------------------------------
	// UTILITY METHODS
	// ------------------------------------------------------------------------------------------

	public static java.util.Date convertStringToDate(String isoDate) {
		
		isoDate = isoDate.replaceAll("\"", "");
		DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
		DateTime date = parser.parseDateTime(isoDate);
		return date.toDate();
	}

	public static String createISODateString(Date date) {

		String year = date.toString().substring(0, 4);
		String month = date.toString().substring(4, 6);
		String day = date.toString().substring(6, 8);

		return year + "-" + month + "-" + day;
	}

	private GitLabIssue gitlabIissueToBtsBug(Issue issue, GitLabTracker gitLabTracker) {
		
		GitLabIssue gitLabIssue = new GitLabIssue(issue, gitLabTracker);
		
		return gitLabIssue;	
	}
	
	private GitLabComment gitLabCommentToBtsComment(Comment comment, GitLabTracker gitLabTracker, String id, String iid) {
		
		GitLabComment gitlabComment = new GitLabComment(comment, gitLabTracker, id, iid);
	
		return gitlabComment;	
	}
	
	// ------------------------------------------------------------------------------------------
	// GET METHODS
	// ------------------------------------------------------------------------------------------
	
	private List<Issue> getIssuesUpdatedAt(Date date, GitLabTracker gitlabTracker) throws IOException {
		
		List<Issue> issues = new ArrayList<>();

		String after = createISODateString(date) + "T00:00:00.00Z";
		String before = createISODateString(date) + "T23:59:59.999Z";

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		HttpUrl.Builder builder = HttpUrl.parse(host + exthost).newBuilder();
		builder.addEncodedPathSegment(gitlabTracker.getProject_id());
		builder.addEncodedPathSegment("issues");
		builder.addEncodedQueryParameter("scope", "all");
		builder.addEncodedQueryParameter("sort", "asc");
		builder.addEncodedQueryParameter("updated_after", after);
		builder.addEncodedQueryParameter("updated_before", before);
		builder.addEncodedQueryParameter("order_by", "updated_at");
		builder.addEncodedQueryParameter("per_page", PAGE_SIZE);

		this.builder = builder.toString();

		Request request = new Request.Builder().url(builder.build().toString()).build();

		Response response = this.client.newCall(request).execute();

		// check header
		checkHeader(response.headers(), gitlabTracker);

		JsonNode rootNode = new ObjectMapper().readTree(response.body().string());
		// first page
		if (rootNode.isArray()) {

			for (JsonNode element : rootNode) {
				
				Issue issue = mapper.treeToValue(element, Issue.class);
				
				Date created = new Date(convertStringToDate(issue.getCreated_at().toString()));
				Date updated = new Date(convertStringToDate(issue.getUpdated_at().toString()));
				
				if((created.toJavaDate().before(updated.toJavaDate())) && (updated.toJavaDate().compareTo(date.toJavaDate()) == 0)) {
		
					issues.add(issue);
					}
				}
			}
		

		// pagination
		if (this.last_page > 1) {
			while (this.current_page != this.last_page) {

				response = getNextPage(gitlabTracker);
				rootNode = new ObjectMapper().readTree(response.body().string());

				if (rootNode.isArray()) {

					for (JsonNode e : rootNode) {
						
						Issue issue = mapper.treeToValue(e, Issue.class);
						
						Date created = new Date(convertStringToDate(issue.getCreated_at().toString()));
						Date updated = new Date(convertStringToDate(issue.getUpdated_at().toString()));
						
						if((created.toJavaDate().before(updated.toJavaDate())) && (updated.toJavaDate().compareTo(date.toJavaDate()) == 0)) {
							
							issues.add(issue);
						
						}
					}
				}
			}
		}

		response.close();

		return issues;

	}
	
	private List<Issue> getIssuesCreatedAt(Date date, GitLabTracker gitlabTracker) throws IOException {
		
		List<Issue> issues = new ArrayList<>();

		String after = createISODateString(date) + "T00:00:00.00Z";
		String before = createISODateString(date) + "T23:59:59.999Z";

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		HttpUrl.Builder builder = HttpUrl.parse(host + exthost).newBuilder();
		builder.addEncodedPathSegment(gitlabTracker.getProject_id());
		builder.addEncodedPathSegment("issues");
		builder.addEncodedQueryParameter("scope", "all");
		builder.addEncodedQueryParameter("sort", "asc");
		builder.addEncodedQueryParameter("created_after", after);
		builder.addEncodedQueryParameter("created_before", before);
		builder.addEncodedQueryParameter("per_page", PAGE_SIZE);
		//Default order is created_at no need to include as a query parameter

		this.builder = builder.toString();

		Request request = new Request.Builder().url(builder.build().toString()).build();

		Response response = this.client.newCall(request).execute();

		// check header
		checkHeader(response.headers(), gitlabTracker);

		JsonNode rootNode = new ObjectMapper().readTree(response.body().string());
		// first page
		if (rootNode.isArray()) {

			for (JsonNode element : rootNode) {

				issues.add(mapper.treeToValue(element, Issue.class));
			}
		}

		// pagination
		if (this.last_page > 1) {
			while (this.current_page != this.last_page) {

				response = getNextPage(gitlabTracker);
				rootNode = new ObjectMapper().readTree(response.body().string());

				if (rootNode.isArray()) {

					for (JsonNode e : rootNode) {

						issues.add(mapper.treeToValue(e, Issue.class));

					}
				}
			}
		}

		response.close();

		return issues;

	}

	private List<Comment> getComments(Date date, String issueID, GitLabTracker gitlabTracker) throws IOException {
		
		List<Comment> comments = new ArrayList<>();
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		String after = createISODateString(date) + "T00:00:00.00Z";
		String before = createISODateString(date) + "T23:59:59.999Z";

		// projects/:id/issues/:issue_iid/notes
		HttpUrl.Builder builder = HttpUrl.parse(host + exthost).newBuilder();
		builder.addEncodedPathSegment(gitlabTracker.getProject_id());
		builder.addEncodedPathSegment("issues");
		builder.addEncodedPathSegment(issueID);
		builder.addEncodedPathSegment("notes");
		builder.addEncodedQueryParameter("scope", "all");
		builder.addEncodedQueryParameter("sort", "asc");
		builder.addEncodedQueryParameter("created_after", after);
		builder.addEncodedQueryParameter("created_before", before);
		builder.addEncodedQueryParameter("per_page", PAGE_SIZE);
		
		this.builder = builder.toString();
		
		Request request = new Request.Builder().url(builder.build().toString()).build();

		Response response = this.client.newCall(request).execute();
		
		// check header
		checkHeader(response.headers(), gitlabTracker);
		
		JsonNode rootNode = new ObjectMapper().readTree(response.body().string());
		
		// first page
		if (rootNode.isArray()) {
	
			for (JsonNode element : rootNode) {
		
				Comment comment = mapper.treeToValue(element, Comment.class);
				comments.add(comment);
			
		}
	
		// pagination
				if (this.last_page > 1) {
					while (this.current_page != this.last_page) {

						response = getNextPage(gitlabTracker);
						rootNode = new ObjectMapper().readTree(response.body().string());

						if (rootNode.isArray()) {

							for (JsonNode e : rootNode) {

								comments.add(mapper.treeToValue(e, Comment.class));

							}
						}
					}
				}
		}
		response.close();
		return comments;
	}

	// ------------------------------------------------------------------------------------------
	// REST CLIENT METHODS
	// ------------------------------------------------------------------------------------------
	
	public void setClient(GitLabTracker gitlabTracker) throws IOException {

		OkHttpClient.Builder newClient = new OkHttpClient.Builder();
		
	
		Runnable runnable = new Runnable() {	// This is triggered at a fixed rate, see scheduled executor service below
			
			public void run() {
			
				try {
					
					setClient(gitlabTracker);
			
				} catch (IOException e) {
					
					e.printStackTrace();
					
				}
			}
		};

		if (open_id.contains("Too Many Requests") == false) {

			newClient.addInterceptor(new Interceptor() {

				@Override
				public Response intercept(Chain chain) throws IOException {

					Request request = chain.request();
					Request.Builder newRequest = null;

					if (!(gitlabTracker.getPersonal_access_token() == null)) {
						
						newRequest = request.newBuilder().header("Private-Token",
						gitlabTracker.getPersonal_access_token());

					} else if (!((gitlabTracker.getClient_id() == null)
							&& (gitlabTracker.getClient_secret() == null))) {

						generateOAuth2Token(gitlabTracker);
						newRequest = request.newBuilder().header("Authorization", " Bearer " + getOAuth2Token());

					}

					return chain.proceed(newRequest.build());
				}
			});

			// creates a single threaded service with a fixed rate that will generate a
			// newClient per specified interval.

			// TODO Change unit of time to reflect the the reset limit of each
			// authentication method
			ScheduledExecutorService newClientService = Executors.newSingleThreadScheduledExecutor();
			newClientService.scheduleAtFixedRate(runnable, 1, 1, TimeUnit.MINUTES);

		}

		// sets client to a new client (with or without an interceptor)
		this.client = newClient.build();
	}

	// TODO - Modify to generate Token using GitLabs requirements
	private void generateOAuth2Token(GitLabTracker gitlabTracker) throws IOException {

		System.out.println("Generating OAuth token");
		OkHttpClient genClient = new OkHttpClient();
		// HttpUrl.Builder httpurlBuilder =
		// HttpUrl.parse("https://accounts.eclipse.org/oauth2/token").newBuilder();

		FormBody.Builder formBodyBuilder = new FormBody.Builder();
		formBodyBuilder.add("grant_type", "client_credentials");
		formBodyBuilder.add("client_id", gitlabTracker.getClient_id());
		formBodyBuilder.add("client_secret", gitlabTracker.getClient_secret());

		FormBody body = formBodyBuilder.build();

		// Used for a POST request
		Request.Builder builder = new Request.Builder();
		builder = builder.url("https://accounts.eclipse.org/oauth2/token");// Modify
		builder = builder.post(body);
		Request request = builder.build();
		Response response = genClient.newCall(request).execute();
		checkHeader(response.headers(), gitlabTracker);

		JsonNode jsonNode = new ObjectMapper().readTree(response.body().string());
		String open_id = GitLabUtils.fixString(jsonNode.get("access_token").toString());

		this.open_id = open_id;
	}

	/**
	 * This method checks the HTTP response headers for current values associated
	 * with the call limit of eclipse forums and updates them. It Also retrieves
	 * information relating to pagination.
	 * 
	 * @param responseHeader
	 * @param gitlabTracker
	 */
	private void checkHeader(Headers responseHeader, GitLabTracker gitlabTracker) {

		this.current_page = Integer.parseInt(responseHeader.get("X-Page"));

		this.last_page = Integer.parseInt(responseHeader.get("X-Total-Pages"));

		if (!(responseHeader.get("X-Next-Page").equals(""))) {

			this.next_page = Integer.parseInt(responseHeader.get("X-Next-Page"));
		}

		this.rateLimit = Integer.parseInt(responseHeader.get("RateLimit-Limit"));
		this.callsRemaning = Integer.parseInt(responseHeader.get("RateLimit-Remaining"));
		this.timeToReset = Integer.parseInt(responseHeader.get("RateLimit-Reset"));
	}

	/**
	 * gets the OAuth2Token
	 * 
	 * @return open_id
	 */
	private String getOAuth2Token() {

		return this.open_id;
	}

	/**
	 * Retrieves the next page of a request
	 * 
	 * @param next_request_url
	 * @return jsonNode
	 * @throws IOException
	 */
	private Response getNextPage(GitLabTracker gitlabTracker) throws IOException {

		String requestUrl = this.builder + "&page=" + this.next_page;
		Request request = new Request.Builder().url(requestUrl).build();

		if (this.callsRemaning == 0) {
			this.waitUntilCallReset(this.timeToReset);
		}

		Response response = this.client.newCall(request).execute();

		this.checkHeader(response.headers(), gitlabTracker);

		return response;
	}

	/**
	 * Method for suspending the current thread until the call limit has been reset.
	 * The time is based on the last received response.
	 * 
	 * @param timeToReset
	 */
	private void waitUntilCallReset(int timeToReset) {

		try {
			
			System.err.println("[Git Lab Reader] The rate limit has been reached. This thread will be suspended for "
					+ this.timeToReset + " seconds until the limit has been reset");
			Thread.sleep((this.timeToReset * 1000l) + 2);

		} catch (InterruptedException e) {

		}
	}
	
	// ------------------------------------------------------------------------------------------
	// NOT USED

	
	@Override
	public String getContents(DB db, GitLabTracker bugTrackingSystem, BugTrackingSystemBug bug) throws Exception {
		
		return null;
	}

	@Override
	public String getContents(DB db, GitLabTracker bugTrackingSystem, BugTrackingSystemComment comment)
			throws Exception {
	
		return null;
	}

}