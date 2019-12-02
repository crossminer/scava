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

	protected OssmeterLogger logger;
	private final static String host = "https://api.eclipse.org/";
	private final static String PAGE_SIZE = "100";
	
	private final static Pattern lastPagePattern = Pattern.compile("\\d>; rel=\"last\"");
	private final static Pattern currentPagePattern = Pattern.compile("\\d>; rel=\"self\"");
	private final static Pattern nextPageUrlPattern = Pattern.compile("(?<=\\<)(.*?)(?=\\>)>; rel=\"next\"");
	
	public EclipseForumsManager()
	{
		this.logger = (OssmeterLogger) OssmeterLogger.getLogger("platform.communicationchannel.eclipseforums");
	}

	@Override
	public boolean appliesTo(CommunicationChannel eclipseForum) {

		return eclipseForum instanceof EclipseForum;
	}

	@Override
	public Date getFirstDate(DB db, EclipseForum eclipseForum) throws JsonProcessingException, IOException {

		// Due to a bug with eclipse forum creation dates we have to estimate the forum
		// creation time by finding the oldest post from all the topics

		logger.info("Getting first date");
		
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

//	/**
//	 * Retrieves information relating to a forum.
//	 * 
//	 * @param eclipseForum
//	 * @return forum
//	 */
//
//	@SuppressWarnings("unused")
//	private EclipseForumsForum getForum(EclipseForum eclipseForum) {
//
//		System.out.println("\t- Getting Forum");
//
//		HttpUrl.Builder getForumBuilder = HttpUrl.parse(host).newBuilder();
//		getForumBuilder.addEncodedPathSegment("forums");
//		getForumBuilder.addEncodedPathSegment("forum");
//		getForumBuilder.addPathSegment(eclipseForum.getForum_id());
//
//		Request getForum = new Request.Builder().url(getForumBuilder.build().toString()).build();
//		Response getForumResponse;
//		EclipseForumsForum forum = null;
//
//		try {
//			forum = new EclipseForumsForum();
//			getForumResponse = ClientManager.executeRequest(eclipseForum, getForum);
//			
//			
//			JsonNode jsonNode = new ObjectMapper().readTree(getForumResponse.body().string());
//
//			forum.setForum_id(EclipseForumUtils.fixString(jsonNode.findValues("id").toString()));
//			forum.setCreation_date(EclipseForumUtils
//					.convertStringToDate(EclipseForumUtils.fixString(jsonNode.findValues("created_date").toString()))
//					.toJavaDate());
//			forum.setDescription(EclipseForumUtils.fixString(jsonNode.findValues("description").toString()));
//			forum.setForum_id(EclipseForumUtils.fixString(jsonNode.findValues("id").toString()));
//			forum.setName(EclipseForumUtils.fixString(jsonNode.findValues("name").toString()));
//			forum.setPost_count(
//					Integer.parseInt(EclipseForumUtils.fixString(jsonNode.findValues("post_count").toString())));
//			forum.setTopic_count(
//					Integer.parseInt(EclipseForumUtils.fixString(jsonNode.findValues("topic_count").toString())));
//			forum.setUrl(EclipseForumUtils.fixString(jsonNode.findValues("html_url").toString()));
//			getForumResponse.close();
//		} catch (IOException | InterruptedException e) {
//			e.printStackTrace();
//		}
//
//		return forum;
//	}

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
	{
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
		
		Request getPostsRequest = new Request.Builder().url(getPostsBuilder.build().toString()).build();
		Response getPostsResponse;
		Pagination pagination;
		boolean morePages;
		do
		{	
			morePages=false;
			try {
				
				getPostsResponse =  ClientManager.executeRequest(eclipseForum, getPostsRequest);
				pagination = new Pagination(getPostsResponse.headers());
			
				try {
					for (EclipseForumsPost post : processPosts(getPostsResponse, eclipseForum, topic))
						posts.add(post);
				}
				catch (IOException e)
				{
					logger.error("Error while processing posts: ", e);
				}
				getPostsResponse.close();
				
				if(pagination.getCurrent_page()!=null && pagination.getLast_page()!=null)
				{
					if(pagination.getCurrent_page() != pagination.getLast_page())
					{
						morePages=true;
						getPostsRequest =  new Request.Builder().url(pagination.getNext_request_url()).build();
					}
				}
			
			} catch (IOException | InterruptedException e) {
				logger.error("Error while calling Eclipse Forums API: ", e);
			}
			
		}
		while(morePages);

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
		
		JsonNode resultJson = new ObjectMapper().readTree(response.body().string()).get("result");
		

		if (resultJson.isArray()) {
			if (resultJson.size() == 0) {

			} else if (resultJson.size() == 1) {
				posts.add(mapPost(resultJson, forum, topic));
			} else {
				for (JsonNode node : resultJson) {
					posts.add(mapPost(node, forum, topic));
				}
			}
		}
		return posts;
	}


	private class Pagination
	{
		private String next_request_url=null;
		private Integer current_page=null;
		private Integer last_page=null;
		
		public Pagination(Headers header)
		{	
			if (!(header.get("Link") == null))
			{
				Matcher matcher;
				matcher = nextPageUrlPattern.matcher(header.get("Link"));
				if (matcher.find()) {
					String url = matcher.group(0);
					String[] split = url.split(">");
					url = split[0];
					this.next_request_url = url;
				}
				matcher = currentPagePattern.matcher(header.get("Link"));
				if (matcher.find()) {
					String current = matcher.group(0);
					String[] split = current.split(">");
					current = split[0];
					this.current_page = Integer.parseInt(current);
				}
				matcher = lastPagePattern.matcher(header.get("Link"));
				if (matcher.find()) {
					String last = matcher.group(0);
					String[] split = last.split(">");
					last = split[0];
					this.last_page = Integer.parseInt(last);
				}
			}
		}

		public String getNext_request_url() {
			return next_request_url;
		}

		public Integer getCurrent_page() {
			return current_page;
		}

		public Integer getLast_page() {
			return last_page;
		}
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

	private List<EclipseForumsTopic> getTopics(EclipseForum eclipseForum) throws IOException {

		List<EclipseForumsTopic> topics = new ArrayList<EclipseForumsTopic>();

		HttpUrl.Builder getTopicBuilder = HttpUrl.parse(host).newBuilder();
		getTopicBuilder.addEncodedPathSegment("forums");
		getTopicBuilder.addEncodedPathSegment("topic");
		getTopicBuilder.addQueryParameter("forum_id", eclipseForum.getForum_id());
		getTopicBuilder.addQueryParameter("pagesize", PAGE_SIZE);
		getTopicBuilder.addQueryParameter("order_by", "ASC");

		Request getTopicsRequest = new Request.Builder().url(getTopicBuilder.build().toString()).build();

		Response getTopicResponse;
		Pagination pagination;
		boolean morePages;
		do
		{	
			morePages=false;
			try {
				
				getTopicResponse =  ClientManager.executeRequest(eclipseForum, getTopicsRequest);
				pagination = new Pagination(getTopicResponse.headers());
			
				try {
					for (EclipseForumsTopic topic : mapTopic(getTopicResponse, eclipseForum))
						topics.add(topic);
				}
				catch (IOException e)
				{
					logger.error("Error while processing posts: ", e);
				}
				getTopicResponse.close();
				
				if(pagination.getCurrent_page()!=null && pagination.getLast_page()!=null)
				{
					if(pagination.getCurrent_page() != pagination.getLast_page())
					{
						morePages=true;
						getTopicsRequest =  new Request.Builder().url(pagination.getNext_request_url()).build();
					}
				}
			
			} catch (IOException | InterruptedException e) {
				logger.error("Error while calling Eclipse Forums API: ", e);
			}
			
		}
		while(morePages);
		
		for (EclipseForumsTopic topic : topics) {

			HttpUrl.Builder getPostsBuilder = HttpUrl.parse(host).newBuilder();
			getPostsBuilder.addEncodedPathSegment("forums");
			getPostsBuilder.addEncodedPathSegment("post");
			getPostsBuilder.addQueryParameter("order_by", "ASC");
			getPostsBuilder.addEncodedPathSegment(topic.getRoot_post_id());

			Request getRootPost = new Request.Builder().url(getPostsBuilder.build().toString()).build();
			Response getRootPostResponse;
			try {
				getRootPostResponse = ClientManager.executeRequest(eclipseForum, getRootPost);
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
			} catch (InterruptedException e) {
				logger.error("Error while searching first post in Eclipse Topic:", e);
			}
			

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