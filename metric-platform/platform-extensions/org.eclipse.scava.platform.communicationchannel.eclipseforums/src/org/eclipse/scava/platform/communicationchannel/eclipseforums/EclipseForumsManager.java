/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.eclipseforums;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.communicationchannel.eclipseforums.utils.EclipseForumUtils;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.scava.platform.delta.communicationchannel.ICommunicationChannelManager;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;

import com.fasterxml.jackson.core.JsonProcessingException;
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

public class EclipseForumsManager implements ICommunicationChannelManager<EclipseForum> {

	public EclipseForumsManager() {
		
		this.forum = new EclipseForumsForum();
		this.open_id = "";
		this.callsRemaning = 0;
		this.rateLimit = 0;
		this.timeToReset = 0;
		this.current_page = 0;
		this.last_page = 0;
		this.next_request_url = "";
		this.client = new OkHttpClient();
		this.temporalFlag = false;
	}

	private boolean temporalFlag;
	private EclipseForumsForum forum;
	private int callsRemaning;
	private int timeToReset;
	private int rateLimit;
	private final static String host = "https://api.eclipse.org/";
	private final static String PAGE_SIZE = "100";
	private final static Pattern lastPagePattern = Pattern.compile("\\d>; rel=\"last\"");
	private final static Pattern currentPagePattern = Pattern.compile("\\d>; rel=\"self\"");
	private final static Pattern nextPageUrlPattern = Pattern.compile("(?<=\\<)(.*?)(?=\\>)>; rel=\"next\"");
	private String next_request_url;
	private int current_page;
	private int last_page;
	private String open_id;
	private OkHttpClient client;

	@Override
	public boolean appliesTo(CommunicationChannel eclipseForum) {

		return eclipseForum instanceof EclipseForum;
	}

	/*
	 * TODO 1. allow token client - basically a Bearer Token is generated using a
	 * client ID and client secret. Once the token has generated it is alive for
	 * 3600 seconds. Every hour a new token is required else it defaults to
	 * annoymous
	 * 
	 * This manually generates a token for me via CURL. curl -X POST
	 * https://accounts.eclipse.org/oauth2/token -d
	 * 'grant_type=client_credentials&client_id=H13SyatW6qPuXZ77Oorwc4i4Xw5&
	 * client_secret=wpl9NBtLn2KxA0r9lmPxWoDbb5I'
	 * 
	 * This functionality is required in to be automated and needs to be a stand
	 * alone method . Also Add this ability into the check headers method. - Method
	 * has been created and works need to create a client intercepter and return the
	 * correct client.
	 */

	/*
	 * [ R E A D M E ]
	 * 
	 * 1. Expect the unexpected - Due to errors with Eclipse's REST API (during the
	 * development of this reader May 2018) some requests return data (values) that
	 * do not correspond with the Id that was requested. We don't know why. These
	 * are handled as and when we experienced them.
	 * 
	 * 2. Due to the small rate limited of 1000 calls per hour. We have to make a
	 * check before each call to see if we have calls remaining. If not we wait the
	 * number of seconds from the previous response we received until they have been
	 * reset. Also every response we take the header and modify the X values
	 * associated with the call limit, calls remaining and time until reset.
	 */

	@Override
	public Date getFirstDate(DB db, EclipseForum eclipseForum) throws JsonProcessingException, IOException {

		// Due to a bug with eclipse forum creation dates we have to estimate the forum
		// creation time by finding the oldest post from all the topics

		System.out.println("[Eclipse Forum] - getFirstDate()");
		setClient(eclipseForum);
		// Get Forum
		// ---------------------------------------------------------------------------------------------------------------------------------------
		System.out.println("\t- Getting Forum");

		HttpUrl.Builder getForumBuilder = HttpUrl.parse(host).newBuilder();
		getForumBuilder.addEncodedPathSegment("forums");
		getForumBuilder.addEncodedPathSegment("forum");
		getForumBuilder.addPathSegment(eclipseForum.getForum_id());

		Request getForum = new Request.Builder().url(getForumBuilder.build().toString()).build();
		Response getForumResponse;
		try {
			getForumResponse = this.client.newCall(getForum).execute();
			checkHeader(getForumResponse.headers(), eclipseForum);
			JsonNode jsonNode = new ObjectMapper().readTree(getForumResponse.body().string());
			this.forum.setForum_id(EclipseForumUtils.fixString(jsonNode.findValues("id").toString()));
			this.forum.setCreation_date(EclipseForumUtils
					.convertStringToDate(EclipseForumUtils.fixString(jsonNode.findValues("created_date").toString()))
					.toJavaDate());
			this.forum.setDescription(EclipseForumUtils.fixString(jsonNode.findValues("description").toString()));
			this.forum.setForum_id(EclipseForumUtils.fixString(jsonNode.findValues("id").toString()));
			this.forum.setName(EclipseForumUtils.fixString(jsonNode.findValues("name").toString()));
			this.forum.setPost_count(
					Integer.parseInt(EclipseForumUtils.fixString(jsonNode.findValues("post_count").toString())));
			this.forum.setTopic_count(
					Integer.parseInt(EclipseForumUtils.fixString(jsonNode.findValues("topic_count").toString())));
			this.forum.setUrl(EclipseForumUtils.fixString(jsonNode.findValues("html_url").toString()));
			getForumResponse.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

		// GETS ALL TOPICS WITHIN FORUM
		// ----------------------------------------------------------------------------------------------------------------------
		System.out.println("\t- Getting Topics");
		

		System.out.println("\t- Getting Root Post for each topic (this may take some time)");
		List<EclipseForumsTopic> topics = getTopics(eclipseForum);
		for (EclipseForumsTopic topic : topics ) {

			HttpUrl.Builder getPostsBuilder = HttpUrl.parse(host).newBuilder();
			getPostsBuilder.addEncodedPathSegment("forums");
			getPostsBuilder.addEncodedPathSegment("post");
			getPostsBuilder.addQueryParameter("order_by", "ASC");
			getPostsBuilder.addEncodedPathSegment(topic.getRoot_post_id());

			// A SIMPLE MECHANISIM FOR HANDLING CALL LIMITS/RESETS WITHOUT LOOSING DATA
			if (this.callsRemaning == 0) {
				waitUntilCallReset(this.timeToReset);
			}

			Request getRootPost = new Request.Builder().url(getPostsBuilder.build().toString()).build();
			Response getRootPostResponse = this.client.newCall(getRootPost).execute();
			checkHeader(getRootPostResponse.headers(), eclipseForum);
			JsonNode postsJsonNode = new ObjectMapper().readTree(getRootPostResponse.body().string());
			if (!(postsJsonNode == null)) {
				if (!postsJsonNode.isArray()) {
					topic.setFirst_post_date(EclipseForumUtils
							.convertStringToDate(
									EclipseForumUtils.fixString(postsJsonNode.findValue("created_date").toString()))
							.toJavaDate());
					topic.setFirst_post_unix_timestamp(
							EclipseForumUtils.fixString(postsJsonNode.findValue("created_date").toString()));
				}
			}
			getRootPostResponse.close();

		}
		System.out.println("\t- Calculating first date");
		List<java.util.Date> dates = new ArrayList<>();
		for (EclipseForumsTopic topic_information : topics) {
			if (!(topic_information.getFirst_post_date() == null)) {
				dates.add(topic_information.getFirst_post_date());
			}
		}

		Collections.sort(dates);// Quick and dirty way to sort a list
		java.util.Date forumCreationEstimation = dates.get(0); // get the first element from the list as this will be
																// the oldest Date
		Date firstDate = new Date(forumCreationEstimation);
		System.out.println("\t\t- First Date :" + firstDate);
		System.out.println("\t- Calls remaining : " + this.callsRemaning + " / " + this.rateLimit);

		return firstDate;
	}

	@Override
	public CommunicationChannelDelta getDelta(DB db, EclipseForum forum, Date date) throws Exception {

		if (temporalFlag == false) {
			getFirstDate(db, forum);
			temporalFlag = true;
		}

		System.out.println("[Eclipse Forum] - getDelta() Calls remaining" + callsRemaning);

		CommunicationChannelDelta delta = new CommunicationChannelDelta();

		delta.setCommunicationChannel(forum);

		for (EclipseForumsTopic topic : getTopics(forum)) {

			// The is checks if the 'current processing date' is within range of the posts
			// in the forum
			if (compareDate(topic.getFirst_post_date(), topic.getLast_post_date(), date) == true) {
				for (EclipseForumsPost post : getPosts(forum, topic)) {

					if (post.getDate().compareTo(date.toJavaDate()) == 0) { // if post date is equal to the 'current
																			// processing date' add to delta
						delta.getArticles().add(post);
					}
				}
			}
		}

		return delta;
	}

	@Override
	public String getContents(DB db, EclipseForum communicationChannel, CommunicationChannelArticle article)
			throws Exception {

		// NOT USED
		return null;
	}

	/**
	 * retrieves all the posts from a topic between a specific date range and
	 * returns them in a list
	 * 
	 * @throws JsonProcessingException,
	 *             IOException
	 * @param topic_id
	 * @param since
	 * @param until
	 * @return
	 */
	private List<EclipseForumsPost> getPosts(EclipseForum eclipseForum, EclipseForumsTopic topic)
			throws JsonProcessingException, IOException {

		List<EclipseForumsPost> posts = new ArrayList<>();

		HttpUrl.Builder getPostsBuilder = HttpUrl.parse(host).newBuilder();
		getPostsBuilder.addEncodedPathSegment("forums");
		getPostsBuilder.addEncodedPathSegment("post");
		getPostsBuilder.addQueryParameter("order_by", "ASC");
		getPostsBuilder.addQueryParameter("topic_id", topic.getTopic_id());

		/*
		 * checks if either first post or last post time stamps are not null - if they
		 * are the time range will not be included in the query
		 */
		if (!(topic.getFirst_post_unix_timestamp() == null) || (topic.getLast_post_unix_timestamp() == null)) {
			getPostsBuilder.addQueryParameter("since", topic.getFirst_post_unix_timestamp());
			getPostsBuilder.addQueryParameter("until", topic.getLast_post_unix_timestamp());
		}

		getPostsBuilder.addQueryParameter("pagesize", PAGE_SIZE);

		// A SIMPLE MECHANISIM FOR HANDLING CALL LIMITS/RESETS WITHOUT LOOSING DATA
		if (this.callsRemaning == 0) {
			waitUntilCallReset(this.timeToReset);
		}

		Request getPostsRequest = new Request.Builder().url(getPostsBuilder.build().toString()).build();
		Response getPostsResponse = this.client.newCall(getPostsRequest).execute();
		checkHeader(getPostsResponse.headers(), eclipseForum);

		for (EclipseForumsPost post : processPosts(getPostsResponse, eclipseForum, topic)) {
			posts.add(post);
		}

		// pagination
		while (this.current_page != this.last_page) {
			System.out.println("\t\t-page " + this.current_page);
			getPostsResponse = getNextPage(this.next_request_url, eclipseForum);

			for (EclipseForumsPost post : processPosts(getPostsResponse, eclipseForum, topic)) {
				posts.add(post);
			}
		}
		return posts;
	}

	/**
	 * Maps information relating to a Eclipse forum post
	 * 
	 * @param jsonNode
	 * @param forum
	 * @param topic
	 * @return List<EclipseForumsPost>
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	private EclipseForumsPost mapPost(JsonNode jsonNode, EclipseForum forum, EclipseForumsTopic topic) {

		EclipseForumsPost eclipseForumsPost = new EclipseForumsPost();
		eclipseForumsPost.setCommunicationChannel(forum);
		eclipseForumsPost.setArticleId(EclipseForumUtils.fixString(jsonNode.findValue("id").toString()));
		eclipseForumsPost
				.setArticleNumber(Long.parseLong(EclipseForumUtils.fixString(jsonNode.findValue("id").toString())));
		// eclipseForumsPost.setForumId(forum.getForum_id());
		eclipseForumsPost.setMessageThreadId(topic.getTopic_id());
		eclipseForumsPost.setUser(EclipseForumUtils.fixString(jsonNode.findValue("poster_id").toString()));
		eclipseForumsPost.setDate(EclipseForumUtils
				.convertStringToDate(EclipseForumUtils.fixString(jsonNode.findValue("created_date").toString()))
				.toJavaDate());
		eclipseForumsPost.setHtml_url(EclipseForumUtils.fixString(jsonNode.findValue("html_url").toString()));
		eclipseForumsPost.setSubject(EclipseForumUtils.fixString(jsonNode.findValue("subject").toString()));
		eclipseForumsPost.setText(EclipseForumUtils.fixString(jsonNode.findValue("body").toString()));
		eclipseForumsPost.setForum(this.forum);
		eclipseForumsPost.setTopic(topic);

		return eclipseForumsPost;
	}

	/**
	 * Processes a response relating to EclipseFourm posts
	 * 
	 * @param response
	 * @param forum
	 * @param topic
	 * @return List<EclipseForumsPost>
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	private List<EclipseForumsPost> processPosts(Response response, EclipseForum forum, EclipseForumsTopic topic)
			throws JsonProcessingException, IOException {

		List<EclipseForumsPost> posts = new ArrayList<>();
		JsonNode jsonNode = new ObjectMapper().readTree(response.body().string()).get("result");

		if (jsonNode.isArray()) {
			if (jsonNode.size() == 0) {

			} else if (jsonNode.size() == 1) {
				posts.add(mapPost(jsonNode, forum, topic));
			} else {
				for (JsonNode node : jsonNode) {
					posts.add(mapPost(node, forum, topic));
				}
			}
		}

		return posts;
	}

	/**
	 * Method for suspending the current thread until the call limit has been reset.
	 * The time is based on the last received response.
	 * 
	 * @param timeToReset
	 */
	private void waitUntilCallReset(int timeToReset) {

		try {
			System.err.println("[Eclipse Forum] The rate limit has been reached. This thread will be suspended for "
					+ this.timeToReset + " seconds until the limit has been reset");
			Thread.sleep((this.timeToReset * 1000l) + 2);

		} catch (InterruptedException e) {

		}
	}

	/**
	 * This method checks the HTTP response headers for current values associated
	 * with the call limit of eclipse forums and updates them. It Also retrieves
	 * information relating to pagination.
	 * 
	 * @param responseHeader
	 * @param eclipseForum
	 */
	private void checkHeader(Headers responseHeader, EclipseForum eclipseForum) {

		if (!(responseHeader.get("Link") == null)) {

			Matcher matcher;
			matcher = nextPageUrlPattern.matcher(responseHeader.get("Link"));

			if (matcher.find()) {
				String url = matcher.group(0);
				String[] split = url.split(">");
				url = split[0];
				this.next_request_url = url;
			}

			matcher = currentPagePattern.matcher(responseHeader.get("Link"));

			if (matcher.find()) {
				String current = matcher.group(0);
				String[] split = current.split(">");
				current = split[0];
				this.current_page = Integer.parseInt(current);
			}

			matcher = lastPagePattern.matcher(responseHeader.get("Link"));

			if (matcher.find()) {
				String last = matcher.group(0);
				String[] split = last.split(">");
				last = split[0];
				this.last_page = Integer.parseInt(last);
			}
		}

		this.rateLimit = Integer.parseInt(responseHeader.get("X-Rate-Limit-Limit"));
		this.callsRemaning = Integer.parseInt(responseHeader.get("X-Rate-Limit-Remaining"));
		this.timeToReset = Integer.parseInt(responseHeader.get("X-Rate-Limit-Reset"));
	}

	/**
	 * Retrieves the next page of a request
	 * 
	 * @param next_request_url
	 * @return jsonNode
	 * @throws IOException
	 */
	private Response getNextPage(String next_request_url, EclipseForum eclipseForum) throws IOException {
		Request request = new Request.Builder().url(next_request_url).build();
		if (this.callsRemaning == 0) {
			this.waitUntilCallReset(this.timeToReset);
		}
		Response response = this.client.newCall(request).execute();
		this.checkHeader(response.headers(), eclipseForum);
		return response;
	}

	/**
	 * Maps information relating to EclipseFourmTopic
	 * 
	 * @param response
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	private List<EclipseForumsTopic> mapTopic(Response response, EclipseForum eclipseForum)
			throws JsonProcessingException, IOException {

		List<EclipseForumsTopic> topicList = new ArrayList<EclipseForumsTopic>();
		JsonNode topicJsonNode = new ObjectMapper().readTree(response.body().string()).get("result");

		if (topicJsonNode.isArray()) {
			for (JsonNode node : topicJsonNode) {
				EclipseForumsTopic topic = new EclipseForumsTopic();
				topic.setLast_post_unix_timestamp(
						EclipseForumUtils.fixString(node.findValue("last_post_date").toString()));
				topic.setViews(Integer.parseInt(EclipseForumUtils.fixString(node.findValue("views").toString())));
				topic.setTopic_subject(EclipseForumUtils.fixString(node.findValue("subject").toString()));
				topic.setUrl(EclipseForumUtils.fixString(node.findValue("html_url").toString()));
				topic.setReplies(Integer.parseInt(EclipseForumUtils.fixString(node.findValue("replies").toString())));
				topic.setRoot_post_id(EclipseForumUtils.fixString(node.findValue("root_post_id").toString()));
				topic.setTopic_id(EclipseForumUtils.fixString(node.findValue("id").toString()));
				if (!(node.findValue("last_post_date").toString().isEmpty())) {
					topic.setLast_post_date(EclipseForumUtils
							.convertStringToDate(
									EclipseForumUtils.fixString(node.findValue("last_post_date").toString()))
							.toJavaDate());
					topicList.add(topic);
				}
			}
		}
		return topicList;
	}

	private void setClient(EclipseForum eclipseForum) throws IOException {

		OkHttpClient.Builder newClient = new OkHttpClient.Builder();
		System.out.println("[Eclipse Forum] - setClient()");
		// If it has a client_id and Client_secert add an interceptor
		if (eclipseForum.getClient_id().isEmpty() && eclipseForum.getClient_secret().isEmpty()) {

			System.out.println("[Eclipse Forum] - Using authenticated Client");
			// This is triggered every hour!
			Runnable runnable = new Runnable() {
				public void run() {
					// task to run goes here
					try {
						setClient(eclipseForum);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};

			// creates a single threaded service with a fixed rate that will generate a
			// newClient every hour.
			ScheduledExecutorService newClientService = Executors.newSingleThreadScheduledExecutor();
			newClientService.scheduleAtFixedRate(runnable, 1, 1, TimeUnit.HOURS);

			generateOAuth2Token(eclipseForum);

			if (open_id.contains("Too Many Requests") == false) {
				newClient.addInterceptor(new Interceptor() {

					@Override
					public Response intercept(Chain chain) throws IOException {

						Request request = chain.request();
						Request.Builder newRequest = request.newBuilder().header("Authorization",
								" Bearer " + getOAuth2Token());
						return chain.proceed(newRequest.build());
					}
				});

			}

		} else {
			System.out.println("[Eclipse Forum] - Using unauthenticated Client");

		}

		// sets client to a new client (with or without an interceptor)
		this.client = newClient.build();
	}

	private void generateOAuth2Token(EclipseForum eclipseForum) throws IOException {

		System.out.println("[Eclipse Forum] - generateOAuth2Token()");
		OkHttpClient genClient = new OkHttpClient();
		// HttpUrl.Builder httpurlBuilder =
		// HttpUrl.parse("https://accounts.eclipse.org/oauth2/token").newBuilder();

		FormBody.Builder formBodyBuilder = new FormBody.Builder();
		formBodyBuilder.add("grant_type", "client_credentials");
		formBodyBuilder.add("client_id", eclipseForum.getClient_id());
		formBodyBuilder.add("client_secret", eclipseForum.getClient_secret());

		FormBody body = formBodyBuilder.build();

		// Used for a POST request
		Request.Builder builder = new Request.Builder();
		builder = builder.url("https://accounts.eclipse.org/oauth2/token");
		builder = builder.post(body);
		Request request = builder.build();
		Response response = genClient.newCall(request).execute();
		checkHeader(response.headers(), eclipseForum);

		JsonNode jsonNode = new ObjectMapper().readTree(response.body().string());
		String open_id = EclipseForumUtils.fixString(jsonNode.get("access_token").toString());

		this.open_id = open_id;
	}

	/**
	 * gets the OAuth2Token
	 * 
	 * @return open_id
	 */
	private String getOAuth2Token() {

		return this.open_id;
	}

	private List<EclipseForumsTopic> getTopics(EclipseForum eclipseForum) throws IOException {

		List<EclipseForumsTopic> topics = new ArrayList<EclipseForumsTopic>();

		HttpUrl.Builder getTopicBuilder = HttpUrl.parse(host).newBuilder();
		getTopicBuilder.addEncodedPathSegment("forums");
		getTopicBuilder.addEncodedPathSegment("topic");
		getTopicBuilder.addQueryParameter("forum_id", eclipseForum.getForum_id());
		getTopicBuilder.addQueryParameter("pagesize", PAGE_SIZE);
		getTopicBuilder.addQueryParameter("order_by", "ASC");

		// A SIMPLE MECHANISIM FOR HANDLING CALL LIMITS/RESETS WITHOUT LOOSING DATA
		if (this.callsRemaning == 0) {
			waitUntilCallReset(this.timeToReset);
		}

		Request getTopicsRequest = new Request.Builder().url(getTopicBuilder.build().toString())
				.header("Authorization", String.format("Bearer %s", this.open_id)).build();

		Response getTopicResponse = this.client.newCall(getTopicsRequest).execute();
		checkHeader(getTopicResponse.headers(), eclipseForum);
		// first
		for (EclipseForumsTopic topic : mapTopic(getTopicResponse, eclipseForum)) {
			topics.add(topic);
		}

		// pagination
		while (this.current_page != this.last_page) {
			System.out.println("\t\t-page " + this.current_page);
			getTopicResponse = getNextPage(this.next_request_url, eclipseForum);

			for (EclipseForumsTopic topic : mapTopic(getTopicResponse, eclipseForum)) {
				topics.add(topic);
			}
		}
		getTopicResponse.close();

		return topics;
	}

	/**
	 * Method used to determine if a day is between two dates. false = date is out
	 * of the scope true = date is within the scope
	 * 
	 * @param min
	 *            start date
	 * @param max
	 *            end date
	 * @param date
	 *            date in question
	 * @return result
	 * 
	 */
	private Boolean compareDate(java.util.Date min, java.util.Date max, Date date) {

		Boolean result = false;

		if ((date.compareTo(min) >= 0) && (date.compareTo(max) <= 0)) {
			result = true;
		}

		return result;
	}

}