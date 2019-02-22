/*******************************************************************************
 * Copyright (c) 2019 University of Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.bitbucket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.bugtrackingsystem.bitbucket.model.issue.Issue;
import org.eclipse.scava.platform.bugtrackingsystem.bitbucket.utils.BitbucketUtils;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.IBugTrackingSystemManager;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.bitbucket.BitbucketBugTrackingSystem;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DB;

import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BitbucketManager implements IBugTrackingSystemManager<BitbucketBugTrackingSystem> {

	private int callsRemaning;
	private int timeToReset;
	@SuppressWarnings("unused")
	private int rateLimit;
	private static String host = "https://api.bitbucket.org/";
	private static String exthost = "2.0/repositories/";
	private final static String PAGE_SIZE = "10";
	private int current_page;
	private int next_page;
	private int last_page;
	private String open_id;
	private String builder;
	private OkHttpClient client;

	public BitbucketManager() {

		this.open_id = "";
		this.builder = "";

		this.client = new OkHttpClient();
	}

	@Override
	public boolean appliesTo(BugTrackingSystem bugTracker) {
		return bugTracker instanceof BitbucketBugTrackingSystem;
	}

	@Override
	public BugTrackingSystemDelta getDelta(DB db, BitbucketBugTrackingSystem bitbucketTracker, Date date)
			throws Exception {

		BugTrackingSystemDelta delta = new BugTrackingSystemDelta();
		delta.setBugTrackingSystem(bitbucketTracker);
		List<String> issue_id_list = new ArrayList<>();

		

		for (Issue issue : getIssues(bitbucketTracker)) {
			issue_id_list.add(issue.getId());
			

			Date created_on = new Date(convertStringToDate(issue.getCreatedOn()));
			
			if (created_on.compareTo(date) == 0) { // New

				BitbucketIssue bug = new BitbucketIssue(issue, bitbucketTracker);
				
				delta.getNewBugs().add(bug);

			}
			
			Date updated_on = new Date(convertStringToDate(issue.getUpdatedOn()));
			
			if ((updated_on.compareTo(date) == 0) && (updated_on.compareTo(created_on) !=0)){ //updated
			
			 BitbucketIssue bug = new BitbucketIssue(issue, bitbucketTracker);
			 
			 delta.getUpdatedBugs().add(bug);
			 
			 }
			
			
			for (BitbucketComment comment : getComments(bitbucketTracker, issue.getId())){
				
				if (comment.getCreationTime().compareTo(date.toJavaDate()) == 0) {
					delta.getComments().add(comment);
				}
			
			}	

		}
	

		return delta;
	}

	@Override
	public Date getFirstDate(DB db, BitbucketBugTrackingSystem bitbucketTracker) throws Exception {

		Date firstDate = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		HttpUrl.Builder builder = HttpUrl.parse(host + exthost).newBuilder();
		builder.addEncodedPathSegment(bitbucketTracker.getLogin());
		builder.addEncodedPathSegment(bitbucketTracker.getRepository());
		builder.addEncodedPathSegment("issues");
		builder.addEncodedQueryParameter("sort", "created_on");
		builder.addEncodedQueryParameter("pagelen", PAGE_SIZE); // page size

		this.builder = builder.toString();

		Request request = new Request.Builder().url(builder.toString()).get().build();

		Response response = this.client.newCall(request).execute();

		JsonNode rootNode = new ObjectMapper().readTree(response.body().string());

		if (rootNode.get("values").isArray()) {
			Issue issue = mapper.treeToValue(rootNode.get("values").get(0), Issue.class);
			firstDate = new Date(convertStringToDate(issue.getCreatedOn()).toString());
		}

		// No need for pagination - issues are returned as an array.

		response.close();

		return firstDate;

	}

	// ----------------------------------------------------------------------------------------
	// REQUEST METHODS
	// ----------------------------------------------------------------------------------------
	private List<Issue> getIssues(BitbucketBugTrackingSystem bitbucketTracker) throws IOException {

		List<Issue> issues = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		HttpUrl.Builder builder = HttpUrl.parse(host + exthost).newBuilder();
		builder.addEncodedPathSegment(bitbucketTracker.getLogin());
		builder.addEncodedPathSegment(bitbucketTracker.getRepository());
		builder.addEncodedPathSegment("issues");
		builder.addEncodedQueryParameter("sort", "created_on");
		// Add date range
		builder.addEncodedQueryParameter("pagelen", PAGE_SIZE); // page size

		this.builder = builder.toString();

		Request request = new Request.Builder().url(builder.toString()).get().build();

		Response response = this.client.newCall(request).execute();

		JsonNode rootNode = new ObjectMapper().readTree(response.body().string());

		if (rootNode.get("values").isArray()) {

			for (JsonNode element : rootNode.get("values")) {

				Issue issue = mapper.treeToValue(element, Issue.class);
				issues.add(issue);
			
			}

		}

		// need for pagination - issues are returned as an array with a max size = to
		// page_size.

		response.close();

		return issues;

	}

	private List<BitbucketComment> getComments(BitbucketBugTrackingSystem bitbucketTracker, String issue_id)
			throws IOException {

		List<BitbucketComment> comments = new ArrayList<>();
	
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		HttpUrl.Builder builder = HttpUrl.parse(host + exthost).newBuilder();
		builder.addEncodedPathSegment(bitbucketTracker.getLogin());
		builder.addEncodedPathSegment(bitbucketTracker.getRepository());
		builder.addEncodedPathSegment("issues");
		builder.addEncodedPathSegment(issue_id);
		builder.addEncodedPathSegment("comments");
		// builder.addEncodedQueryParameter("sort", "created_on");
		// Add date range
		builder.addEncodedQueryParameter("pagelen", PAGE_SIZE); // page size

		this.builder = builder.toString();

		Request request = new Request.Builder().url(builder.toString()).get().build();

		Response response = this.client.newCall(request).execute();

		JsonNode rootNode = new ObjectMapper().readTree(response.body().string());

		if (rootNode.get("values").isArray()) {

			for (JsonNode element : rootNode.get("values")) {
				
				Date creationTime = null;
				
				creationTime = new Date(convertStringToDate(element.get("created_on").toString()));
		
				String commentId = element.get("id").toString();
				String username ="";

				if (!(rootNode.get("user") == null)) {
					
					JsonNode user = new ObjectMapper().readTree(element.get("user").toString());
					
					username = user.get("userame").toString();
					
				}
				
				if (!(rootNode == null)) {

					JsonNode content = new ObjectMapper().readTree(element.get("content").toString());
					if (!(content.get("raw") == null)) {
						
						// Manual mapping of Comment
						BitbucketComment comment = new BitbucketComment();
						comment.setBugTrackingSystem(bitbucketTracker);
						comment.setCreationTime(creationTime.toJavaDate());
						comment.setCreator(username);
						comment.setText(content.get("html").toString());
						comment.setBugId(issue_id);
						comment.setCommentId(commentId);
						
						comments.add(comment);
					}
				}
			}
		}

		response.close();
		return comments;
	}

	// ----------------------------------------------------------------------------------------
	// CLIENT METHODS
	// ----------------------------------------------------------------------------------------

	public void setClient(final BitbucketBugTrackingSystem bitbucket) throws IOException {

		OkHttpClient.Builder newClient = new OkHttpClient.Builder();

		Runnable runnable = new Runnable() { // This is triggered at a fixed rate, see scheduled executor service below

			public void run() {

				try {

					setClient(bitbucket);

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

					if (!((bitbucket.getLogin() == null) && (bitbucket.getPassword() == null))) {

						newRequest = request.newBuilder().addHeader("authorization",
								Credentials.basic(bitbucket.getLogin(), bitbucket.getPassword()));

					}
					// else if (!(bitbucket.getPersonal_access_token() == null)) {
					//
					// newRequest = request.newBuilder().header("Private-Token",
					// bitbucket.getPersonal_access_token());
					//
					// } else if (!((bitbucket.getClient_id() == null)
					// && (bitbucket.getClient_secret() == null))) {
					//
					// generateOAuth2Token(bitbucket);
					// newRequest = request.newBuilder().header("Authorization", " Bearer " +
					// getOAuth2Token());

					// }

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
	private void generateOAuth2Token(BitbucketBugTrackingSystem bitbucket) throws IOException {

		System.out.println("Generating OAuth token");
		OkHttpClient genClient = new OkHttpClient();
		// HttpUrl.Builder httpurlBuilder =
		// HttpUrl.parse("https://accounts.eclipse.org/oauth2/token").newBuilder();

		FormBody.Builder formBodyBuilder = new FormBody.Builder();
		formBodyBuilder.add("grant_type", "client_credentials");
		// formBodyBuilder.add("client_id", bitbucket.getClient_id());
		// formBodyBuilder.add("client_secret", bitbucket.getClient_secret());

		FormBody body = formBodyBuilder.build();

		// Used for a POST request
		Request.Builder builder = new Request.Builder();
		builder = builder.url("https://accounts.eclipse.org/oauth2/token");// Modify
		builder = builder.post(body);
		Request request = builder.build();
		Response response = genClient.newCall(request).execute();
		checkHeader(response.headers(), bitbucket);

		JsonNode jsonNode = new ObjectMapper().readTree(response.body().string());
		String open_id = BitbucketUtils.fixString(jsonNode.get("access_token").toString());

		this.open_id = open_id;
	}

	/**
	 * This method checks the HTTP response headers for current values associated
	 * with the call limit of eclipse forums and updates them. It Also retrieves
	 * information relating to pagination.
	 * 
	 * @param responseHeader
	 * @param bitbucket
	 */
	private void checkHeader(Headers responseHeader, BitbucketBugTrackingSystem bitbucket) {

		// this doesnt do anyting for this reader. Bitbucket does not have rate limits
		// also the pagination is handled in the body and not the header.
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
	private Response getNextPage(BitbucketBugTrackingSystem bitbucket) throws IOException {

		String requestUrl = this.builder + "&page=" + this.next_page;
		Request request = new Request.Builder().url(requestUrl).build();

		if (this.callsRemaning == 0) {
			this.waitUntilCallReset(this.timeToReset);
		}

		Response response = this.client.newCall(request).execute();

		this.checkHeader(response.headers(), bitbucket);

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

	// ----------------------------------------------------------------------------------------
	// UTILITY MEHTODS
	// ----------------------------------------------------------------------------------------

	public static java.util.Date convertStringToDate(String isoDate) {

		isoDate = isoDate.replaceAll("\"", "");
		DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
		DateTime date = parser.parseDateTime(isoDate);
		return date.toDate();
	}


	// ----------------------------------------------------------------------------------------
	// NOT USED
	// ----------------------------------------------------------------------------------------

	@Override
	public String getContents(DB db, BitbucketBugTrackingSystem bugTracker, BugTrackingSystemBug bug) throws Exception {

		return null;
	}

	@Override
	public String getContents(DB db, BitbucketBugTrackingSystem bugTracker, BugTrackingSystemComment comment)
			throws Exception {

		return null;
	}
}
