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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.communicationchannel.eclipseforums.client.manager.ClientManager;
import org.eclipse.scava.platform.communicationchannel.eclipseforums.utils.EclipseForumUtils;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.scava.platform.delta.communicationchannel.ICommunicationChannelManager;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DB;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

public class EclipseForumsManager implements ICommunicationChannelManager<EclipseForum> {

	public EclipseForumsManager() {

		this.open_id = "";
		this.callsRemaning = -1;
		this.rateLimit = 0;
		this.timeToReset = 0;
		this.current_page = 0;
		this.last_page = 0;
		this.next_request_url = "";
		//this.client = new OkHttpClient();
		this.temporalFlag = false;
		this.clientSet = false;
		this.logger = (OssmeterLogger) OssmeterLogger.getLogger("platform.communicationchannel.eclipseforums");
	}

	protected OssmeterLogger logger;
	private boolean temporalFlag;
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
	//private OkHttpClient client;
	private Boolean clientSet;
	private ClientManager clientManager = new ClientManager();

	@Override
	public boolean appliesTo(CommunicationChannel eclipseForum) {

		return eclipseForum instanceof EclipseForum;
	}

	@Override
	public Date getFirstDate(DB db, EclipseForum eclipseForum) throws JsonProcessingException, IOException {

		// Due to a bug with eclipse forum creation dates we have to estimate the forum
		// creation time by finding the oldest post from all the topics

		logger.info("Getting first date");
		
		if (this.clientSet == false) {
			getClient(eclipseForum);
		}

		// GETS ALL TOPICS WITHIN FORUM
		// ----------------------------------------------------------------------------------------------------------------------
		logger.info("Getting topics from " + eclipseForum.getForum_name());
		logger.info("Getting Root Post for each topic (this may take some time)");
		
		List<EclipseForumsTopic> topics = getTopics(eclipseForum);

		logger.info("Calculating first date");
		
		List<java.util.Date> dates = new ArrayList<>();
		
		for (EclipseForumsTopic topic_information : topics) {
			
			if (!(topic_information.getFirst_post_date() == null)) {
				dates.add(topic_information.getFirst_post_date());
			}
		}

		Collections.sort(dates);
		java.util.Date forumCreationEstimation = dates.get(0); 
		Date firstDate = new Date(forumCreationEstimation);
		logger.info("First Date :" + firstDate);
		

		return firstDate;
	}

	@Override
	public CommunicationChannelDelta getDelta(DB db, EclipseForum forum, Date date) throws Exception {

		if (this.clientSet == false) {
			getClient(forum);
		}

		CommunicationChannelDelta delta = new CommunicationChannelDelta();

		delta.setCommunicationChannel(forum);

		for (EclipseForumsTopic topic : getTopics(forum)) {

			if (compareDate(topic.getFirst_post_date(), topic.getLast_post_date(), date) == true) {
				for (EclipseForumsPost post : getPosts(forum, topic)) {

					// if post date is equal to the 'current processing date' add to delta
					if (post.getDate().compareTo(date.toJavaDate()) == 0) {

						delta.getArticles().add(post);
					}
				}
			}
		}

		logger.info("There has been " + delta.getArticles().size() + " articles added for analysis");

		return delta;
	}

	@Override
	public String getContents(DB db, EclipseForum communicationChannel, CommunicationChannelArticle article)
			throws Exception {

		// NOT USED
		return null;
	}

	/**
	 * Retrieves information relating to a forum.
	 * 
	 * @param eclipseForum
	 * @return forum
	 */

	@SuppressWarnings("unused")
	private EclipseForumsForum getForum(EclipseForum eclipseForum) {

		System.out.println("\t- Getting Forum");

		HttpUrl.Builder getForumBuilder = HttpUrl.parse(host).newBuilder();
		getForumBuilder.addEncodedPathSegment("forums");
		getForumBuilder.addEncodedPathSegment("forum");
		getForumBuilder.addPathSegment(eclipseForum.getForum_id());

		Request getForum = new Request.Builder().url(getForumBuilder.build().toString()).build();
		Response getForumResponse;
		EclipseForumsForum forum = null;

		try {
			forum = new EclipseForumsForum();
			getForumResponse =  clientManager.getClient(eclipseForum).newCall(getForum).execute();
			checkHeader(getForumResponse.headers(), eclipseForum);
			JsonNode jsonNode = new ObjectMapper().readTree(getForumResponse.body().string());

			forum.setForum_id(EclipseForumUtils.fixString(jsonNode.findValues("id").toString()));
			forum.setCreation_date(EclipseForumUtils
					.convertStringToDate(EclipseForumUtils.fixString(jsonNode.findValues("created_date").toString()))
					.toJavaDate());
			forum.setDescription(EclipseForumUtils.fixString(jsonNode.findValues("description").toString()));
			forum.setForum_id(EclipseForumUtils.fixString(jsonNode.findValues("id").toString()));
			forum.setName(EclipseForumUtils.fixString(jsonNode.findValues("name").toString()));
			forum.setPost_count(
					Integer.parseInt(EclipseForumUtils.fixString(jsonNode.findValues("post_count").toString())));
			forum.setTopic_count(
					Integer.parseInt(EclipseForumUtils.fixString(jsonNode.findValues("topic_count").toString())));
			forum.setUrl(EclipseForumUtils.fixString(jsonNode.findValues("html_url").toString()));
			getForumResponse.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return forum;
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

		
		if (this.callsRemaning == 0) {
			waitUntilCallReset(this.timeToReset);
		}

		Request getPostsRequest = new Request.Builder().url(getPostsBuilder.build().toString()).build();
		Response getPostsResponse =  clientManager.getClient(eclipseForum).newCall(getPostsRequest).execute();
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
		Response response =  clientManager.getClient(eclipseForum).newCall(request).execute();
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

	private void getClient(EclipseForum eclipseForum) throws IOException {

		logger.info("Getting Client");
			// checks if a client for the ossmeterID exists in clientMap - if not it will create one.
			if (clientManager.clientDataExists(eclipseForum) == true) {

				clientManager.checkForRefresh(eclipseForum);

			this.clientSet = true;
		}

	
	}

	private List<EclipseForumsTopic> getTopics(EclipseForum eclipseForum) throws IOException {

		List<EclipseForumsTopic> topics = new ArrayList<EclipseForumsTopic>();

		HttpUrl.Builder getTopicBuilder = HttpUrl.parse(host).newBuilder();
		getTopicBuilder.addEncodedPathSegment("forums");
		getTopicBuilder.addEncodedPathSegment("topic");
		getTopicBuilder.addQueryParameter("forum_id", eclipseForum.getForum_id());
		getTopicBuilder.addQueryParameter("pagesize", PAGE_SIZE);
		getTopicBuilder.addQueryParameter("order_by", "ASC");

		if (this.callsRemaning == 0) {
			waitUntilCallReset(this.timeToReset);
		}

		Request getTopicsRequest = new Request.Builder().url(getTopicBuilder.build().toString())
				.header("Authorization", String.format("Bearer %s", this.open_id)).build();

		Response getTopicResponse = clientManager.getClient(eclipseForum).newCall(getTopicsRequest).execute();

		checkHeader(getTopicResponse.headers(), eclipseForum);
		// first
		for (EclipseForumsTopic topic : mapTopic(getTopicResponse, eclipseForum)) {
			topics.add(topic);
		}

	
		while (this.current_page != this.last_page) {
			System.out.println("\t\t-page " + this.current_page);
			getTopicResponse = getNextPage(this.next_request_url, eclipseForum);

			for (EclipseForumsTopic topic : mapTopic(getTopicResponse, eclipseForum)) {
				topics.add(topic);
			}
		}
		getTopicResponse.close();

		for (EclipseForumsTopic topic : topics) {

			HttpUrl.Builder getPostsBuilder = HttpUrl.parse(host).newBuilder();
			getPostsBuilder.addEncodedPathSegment("forums");
			getPostsBuilder.addEncodedPathSegment("post");
			getPostsBuilder.addQueryParameter("order_by", "ASC");
			getPostsBuilder.addEncodedPathSegment(topic.getRoot_post_id());

		
			if (this.callsRemaning == 0) {
				waitUntilCallReset(this.timeToReset);
			}

			Request getRootPost = new Request.Builder().url(getPostsBuilder.build().toString()).build();
			Response getRootPostResponse = clientManager.getClient(eclipseForum).newCall(getRootPost).execute();
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

	// FIXME - data Null pointer This needs to be fixed
	private Boolean compareDate(java.util.Date min, java.util.Date max, Date date) {

		Boolean result = false;

		if ((date.compareTo(min) >= 0) && (date.compareTo(max) <= 0)) {
			result = true;
		}

		return result;
	}


}