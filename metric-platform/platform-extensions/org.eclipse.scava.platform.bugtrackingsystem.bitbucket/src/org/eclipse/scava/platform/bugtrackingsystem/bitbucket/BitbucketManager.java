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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.bugtrackingsystem.bitbucket.model.issue.Issue;
import org.eclipse.scava.platform.bugtrackingsystem.bitbucket.utils.BitbucketUtils;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.IBugTrackingSystemManager;
import org.eclipse.scava.platform.logging.OssmeterLogger;
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
	private static String exthost = "/2.0/repositories/";
	private final static String PAGE_SIZE = "10";
	private int current_page;
	private int next_page;
	private int last_page;
	private String open_id;
	private String builder;
	private OkHttpClient client;
	private boolean temporalFlag;

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
		
		
		if(temporalFlag==false){
		
			getFirstDate(db, bitbucketTracker);
			temporalFlag=true;
		
		}
		
		
		BugTrackingSystemDelta delta = new BugTrackingSystemDelta();
		
		delta.setBugTrackingSystem(bitbucketTracker);
		
		ProcessedBitBucketURL processedURL= new ProcessedBitBucketURL(bitbucketTracker);
		
		for (Issue issue : getIssues(processedURL)) {

			Date created_on = new Date(convertStringToDate(issue.getCreatedOn()));

			if (created_on.compareTo(date) == 0) { // New

				BitbucketIssue bug = new BitbucketIssue(issue, bitbucketTracker);

				delta.getNewBugs().add(bug);

			}

			Date updated_on = new Date(convertStringToDate(issue.getUpdatedOn()));

			if ((updated_on.compareTo(date) == 0) && (updated_on.compareTo(created_on) != 0)) { // updated

				BitbucketIssue bug = new BitbucketIssue(issue, bitbucketTracker);

				delta.getUpdatedBugs().add(bug);

			}

			for (BitbucketComment comment : getComments(bitbucketTracker, processedURL, issue.getId())) {
				
				Date created = new Date(comment.getCreationTime());
		

				if (created.compareTo(date.toJavaDate()) == 0) {
					delta.getComments().add(comment);
				}

			}

		}

		return delta;
	}

	@Override
	public Date getFirstDate(DB db, BitbucketBugTrackingSystem bitbucketTracker) throws Exception {
		
		setClient(bitbucketTracker);
		
		Date firstDate = null;
		
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		ProcessedBitBucketURL processedURL= new ProcessedBitBucketURL(bitbucketTracker);
		
		HttpUrl.Builder builder = HttpUrl.parse(processedURL.getHost() + exthost).newBuilder();
		builder.addEncodedPathSegment(processedURL.getOwner());
		builder.addEncodedPathSegment(processedURL.getRepository());
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
	// URL manager
	// ----------------------------------------------------------------------------------------
	
	private class ProcessedBitBucketURL
	{
		private String host=null;
		private String owner=null;
		private String repository=null;
		private Pattern protocolRegex=Pattern.compile("^https?://");
		private Pattern ownerRepositoryRegex=Pattern.compile("^/([^/]+)/([^/]+)/");
		private OssmeterLogger logger;
		
		public ProcessedBitBucketURL(BitbucketBugTrackingSystem bitbucketTracker)
		{
			logger = (OssmeterLogger) OssmeterLogger.getLogger("bitbucket.urlprocessor");
			String url = bitbucketTracker.getUrl();
			if(!protocolRegex.matcher(url).find())
				url = "https://"+url;
			try {
				URI projectURI = new URI(url);
				host = "https://api."+projectURI.getHost();
				Matcher m = ownerRepositoryRegex.matcher(projectURI.getPath());
				if(m.find())
				{
					owner=m.group(1);
					repository=m.group(2);
				}
				else
					throw new UnsupportedOperationException("No project owner or repository could be found in "+projectURI.getPath());
				
			} catch (URISyntaxException | UnsupportedOperationException e) {
				logger.error("Error while parsing the URL:"+e);
				e.printStackTrace();
			}
		}

		public String getHost() {
			return host;
		}

		public String getOwner() {
			return owner;
		}

		public String getRepository() {
			return repository;
		}
		
		
	}

	// ----------------------------------------------------------------------------------------
	// REQUEST METHODS
	// ----------------------------------------------------------------------------------------
	private List<Issue> getIssues(ProcessedBitBucketURL processedBitBucketURL) throws IOException {

		List<Issue> issues = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		HttpUrl.Builder builder = HttpUrl.parse(processedBitBucketURL.getHost() + exthost).newBuilder();
		// builder.addEncodedPathSegment(bitbucketTracker.getLogin());
		builder.addEncodedPathSegment(processedBitBucketURL.getOwner());
		builder.addEncodedPathSegment(processedBitBucketURL.getRepository());
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
		
		try {
		//This will handle pagination 	
		if (!(rootNode.get("next").equals(null))) {
			
			String nextPageUrl = rootNode.get("next").textValue();
			
			response.close();
				
			do {

				boolean flag = false;

				Request paigantionRequest = new Request.Builder().url(nextPageUrl).get().build();
				
				Response paginationResponse = this.client.newCall(paigantionRequest).execute();
			
				JsonNode paginationNode = new ObjectMapper().readTree(paginationResponse.body().string());
				
				
				if (paginationNode.get("values").isArray()) {
					
					for (JsonNode element : paginationNode.get("values")) {

						Issue issue = mapper.treeToValue(element, Issue.class);
						issues.add(issue);
					}
				}
				
				try {

					if (!(paginationNode.get("next").equals(null))) {
						
						nextPageUrl = paginationNode.get("next").textValue();

					} 
					
					
				}catch(NullPointerException np) {
					
					flag = true;
					
				}
			
				paginationResponse.close();

			} while (false);
		}
		}catch (NullPointerException np) {
			
			//Do nothing no extra pages
		}

		return issues;

	}

	private List<BitbucketComment> getComments(BitbucketBugTrackingSystem bitbucketTracker, ProcessedBitBucketURL processedBitBucketURL, String issue_id)
			throws IOException {

		List<BitbucketComment> comments = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		HttpUrl.Builder builder = HttpUrl.parse(processedBitBucketURL.getHost() + exthost).newBuilder();
		builder.addEncodedPathSegment(processedBitBucketURL.getOwner());
		builder.addEncodedPathSegment(processedBitBucketURL.getRepository());
		builder.addEncodedPathSegment("issues");
		builder.addEncodedPathSegment(issue_id);
		builder.addEncodedPathSegment("comments");
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
				String username = "";

				if (!(element.get("user") == null)) {

					JsonNode user = new ObjectMapper().readTree(element.get("user").toString());
					
						username = user.get("username").toString();
					
				}else {
					
					username = "Former User";
				}

				if (!(rootNode == null)) {

					JsonNode content = new ObjectMapper().readTree(element.get("content").toString());
					if (!(content.get("raw") == null)) {

						// Manual mapping of Comment
						BitbucketComment comment = new BitbucketComment();
						comment.setBugTrackingSystem(bitbucketTracker);
						comment.setCreationTime(creationTime.toJavaDate());
						comment.setCreator(username);
						comment.setText(content.get("html").asText());
						comment.setBugId(issue_id);
						comment.setCommentId(commentId);

						comments.add(comment);
					}
				}
			}
		}
		
		//This will handle pagination 	
		try {
				if (!(rootNode.get("next").equals(null))) {
					
					String nextPageUrl = rootNode.get("next").textValue();
					
					response.close();	
					
					do {

						boolean flag = false;

						Request paigantionRequest = new Request.Builder().url(nextPageUrl).get().build();
						
						Response paginationResponse = this.client.newCall(paigantionRequest).execute();
					
						JsonNode paginationNode = new ObjectMapper().readTree(paginationResponse.body().string());
		
						for (JsonNode element : paginationNode.get("values")) {
							
						
							Date creationTime = null;

							creationTime = new Date(convertStringToDate(element.get("created_on").toString()));

							String commentId = element.get("id").toString();
							String username = "";

							if (!(element.get("user") == null)) {

								JsonNode user = new ObjectMapper().readTree(element.get("user").toString());
								username = user.get("username").toString();

							}

							if (!(paginationNode == null)) {

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
					
						
						try {

							if (!(paginationNode.get("next").equals(null))) {
								
								nextPageUrl = paginationNode.get("next").textValue();

							} 
							
							
						}catch(NullPointerException np) {
							
							flag = true;
							
						}
					
						paginationResponse.close();

					} while (false);
				}
		}catch(NullPointerException np) {
			
			//Do nothing no extra pages
		}
				
		
		return comments;
	}

	// ----------------------------------------------------------------------------------------
	// CLIENT METHODS
	// ----------------------------------------------------------------------------------------

	public void setClient(final BitbucketBugTrackingSystem bitbucket) throws IOException {

		OkHttpClient.Builder newClient = new OkHttpClient.Builder();


			newClient.addInterceptor(new Interceptor() {

				@Override
				public Response intercept(Chain chain) throws IOException {

					Request request = chain.request();
					Request.Builder newRequest = null;

					if (!((bitbucket.getLogin().equals("null") && (bitbucket.getPassword().equals("null"))))) {

						newRequest = request.newBuilder().addHeader("authorization",
								Credentials.basic(bitbucket.getLogin(), bitbucket.getPassword()));

					}else {
						
						newRequest = request.newBuilder();
					}
					
			

					return chain.proceed(newRequest.build());
				}
			});


		
		this.client = newClient.build();
	}


//	}

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

			System.err.println("[Bitbucket Manager] The rate limit has been reached. This thread will be suspended for "
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
