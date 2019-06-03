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
import java.util.List;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.bugtrackingsystem.gitlab.model.Comment;
import org.eclipse.scava.platform.bugtrackingsystem.gitlab.model.Issue;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.IBugTrackingSystemManager;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.gitlab.GitLabTracker;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DB;

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
	private final static String PAGE_SIZE = "100";
	private int current_page;
	private int next_page;
	private int last_page;
	private String builder;
	private OkHttpClient client;
	
	protected OssmeterLogger logger;

	public GitLabManager()
	{

		logger = (OssmeterLogger) OssmeterLogger.getLogger("platform.bugtrackingsystem.gitlab");
		this.builder = "";
		this.callsRemaning = -1;
		this.rateLimit = -1;
		this.timeToReset = -1;
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
	
		setClient(gitlabTracker);
		
		delta.setBugTrackingSystem(gitlabTracker);

		
		for (Issue issue : getAllIssues(date, gitlabTracker)) {
			
			
			Date created = new Date(convertStringToDate(issue.getCreated_at()));
			Date modified = new Date(convertStringToDate(issue.getUpdated_at()));
			
			if(created.compareTo(date) == 0) {
				
				delta.getNewBugs().add(gitlabIissueToBtsBug(issue, gitlabTracker));// adds new issues to delta
				
			}
			
			if ( (modified.compareTo(date) == 0) && (modified.toJavaDate().after(created.toJavaDate()))) {
				
				delta.getUpdatedBugs().add(gitlabIissueToBtsBug(issue, gitlabTracker));// adds modified issues to delta
				
			}
			
			if (issue.getUser_notes_count() > 0) {
				for (Comment gitlabComment : getComments(date, issue.getIid(), gitlabTracker)) {
					
					Date commentDate = new Date(convertStringToDate(gitlabComment.getCreated_at()));
					
					if (commentDate.compareTo(date) == 0) {//Prevents past and future comments being added to the detla
						
						delta.getComments().add(gitLabCommentToBtsComment(gitlabComment, gitlabTracker, issue.getId(), issue.getIid()));//adds comments to delta
						
					}				
					
				}
				
			}
		
		}


		return delta;
	}


	
	@Override
	public Date getFirstDate(DB db, GitLabTracker gitlabTracker) throws Exception {
		
		Date firstDate = null;
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		setClient(gitlabTracker);

		HttpUrl.Builder builder = HttpUrl.parse(gitlabTracker.getUrl()).newBuilder();
		builder.addEncodedPathSegment("issues");
		builder.addEncodedQueryParameter("scope", "all");
		builder.addEncodedQueryParameter("sort", "asc");
		builder.addEncodedQueryParameter("per_page", PAGE_SIZE);

		Request request = new Request.Builder().url(builder.build().toString()).build();

		Response response = this.client.newCall(request).execute();
		
		JsonNode rootNode = mapper.readTree(response.body().string());

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
	

	private List<Issue> getAllIssues(Date date, GitLabTracker gitlabTracker) throws IOException {
		List<Issue> issues = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		HttpUrl.Builder builder = HttpUrl.parse(gitlabTracker.getUrl()).newBuilder();
		builder.addEncodedPathSegment("issues");
		builder.addEncodedQueryParameter("scope", "all");
		builder.addEncodedQueryParameter("sort", "asc");

		builder.addEncodedQueryParameter("per_page", PAGE_SIZE);
	
		this.builder = builder.toString();

		Request request = new Request.Builder().url(builder.build().toString()).build();

		Response response = this.client.newCall(request).execute();

		checkHeader(response.headers(), gitlabTracker);

		JsonNode rootNode = mapper.readTree(response.body().string());

		if (rootNode.isArray()) {

			for (JsonNode element : rootNode) {
				
				Issue issue = mapper.treeToValue(element, Issue.class);
				
				issues.add(issue);
					
				}
			}
		
		if (this.last_page > 1) {
			while (this.current_page != this.last_page) {

				response = getNextPage(gitlabTracker);
				rootNode = new ObjectMapper().readTree(response.body().string());

				if (rootNode.isArray()) {

					for (JsonNode e : rootNode) {
						
						Issue issue = mapper.treeToValue(e, Issue.class);
							
						issues.add(issue);
						
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

		// projects/:id/issues/:issue_iid/notes
		HttpUrl.Builder builder = HttpUrl.parse(gitlabTracker.getUrl()).newBuilder();
		builder.addEncodedPathSegment("issues");
		builder.addEncodedPathSegment(issueID);
		builder.addEncodedPathSegment("notes");
		builder.addEncodedQueryParameter("scope", "all");
		builder.addEncodedQueryParameter("sort", "asc");
		builder.addEncodedQueryParameter("per_page", PAGE_SIZE);
		
		this.builder = builder.toString();
		
		Request request = new Request.Builder().url(builder.build().toString()).build();

		Response response = this.client.newCall(request).execute();
		
	
		checkHeader(response.headers(), gitlabTracker);
		
		JsonNode rootNode = new ObjectMapper().readTree(response.body().string());
	
		if (rootNode.isArray()) {
	
			for (JsonNode element : rootNode) {
		
				Comment comment = mapper.treeToValue(element, Comment.class);
				comments.add(comment);
			
		}
	
	
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


			newClient.addInterceptor(new Interceptor() {

				@Override
				public Response intercept(Chain chain) throws IOException {

					Request request = chain.request();
					Request.Builder newRequest = null;

					if (!(gitlabTracker.getPersonal_access_token() == null)) {
						
						newRequest = request.newBuilder().header("Private-Token",
						gitlabTracker.getPersonal_access_token());

					}

					return chain.proceed(newRequest.build());
				}
			});

			
		// sets client to a new client (with or without an interceptor)
		this.client = newClient.build();
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

		if(responseHeader.get("X-Page")!=null)
			this.current_page = Integer.parseInt(responseHeader.get("X-Page"));
		else
		{
			logger.error("Couldn't find the current page in json header. Considering there is just one page (page 0).");
			this.current_page=0;
		}

		if(responseHeader.get("X-Total-Pages")!=null)
			this.last_page = Integer.parseInt(responseHeader.get("X-Total-Pages"));
		else
		{
			logger.error("Couldn't find the total number of pages in json header. Considering there is just one page (page 0).");
			this.last_page=0;
		}

		if(responseHeader.get("X-Next-Page")!=null)
		{
			if (!(responseHeader.get("X-Next-Page").equals(""))) {
	
				this.next_page = Integer.parseInt(responseHeader.get("X-Next-Page"));
			}
		}

		if(responseHeader.get("RateLimit-Limit")!=null)
		{
			this.rateLimit = Integer.parseInt(responseHeader.get("RateLimit-Limit"));
			this.callsRemaning = Integer.parseInt(responseHeader.get("RateLimit-Remaining"));
			this.timeToReset = Integer.parseInt(responseHeader.get("RateLimit-Reset"));
		}
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

		if(timeToReset==-1)
			return;
		try {
			logger.info("The rate limit has been reached. This thread will be suspended for "
					+ this.timeToReset + " seconds until the limit has been reset");
			Thread.sleep((this.timeToReset * 1000l) + 2);

		} catch (InterruptedException e) {

		}
	}

	
	// ------------------------------------------------------------------------------------------
	// NOT USED
	// ------------------------------------------------------------------------------------------
	
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