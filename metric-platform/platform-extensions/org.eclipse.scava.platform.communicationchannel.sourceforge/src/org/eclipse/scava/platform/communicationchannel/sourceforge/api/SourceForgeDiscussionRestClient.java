/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.sourceforge.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.scava.platform.Date;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

public class SourceForgeDiscussionRestClient {

	private int pageSize = SourceForgeConstants.DEFAULT_PAGE_SIZE;

	private static final ObjectMapper objectMapper;
	private static final ObjectReader getForumReader;
	private static final ObjectReader getTopicReader;
	private static final ObjectReader topicReader;

	static {
		// http://wiki.fasterxml.com/JacksonBestPracticesPerformance
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		SimpleModule module = new SimpleModule();
		module.addDeserializer(SourceForgeForumSearch.class,
				new SourceForgeForumSearchDeserialiser());
		module.addDeserializer(SourceForgeTopicSearch.class,
				new SourceForgeTopicSearchDeserialiser());
		module.addDeserializer(SourceForgeTopic.class,
				new SourceForgeTopicDeserialiser());
		module.addDeserializer(SourceForgeArticle.class,
				new SourceForgeArticleDeserialiser());
		objectMapper.registerModule(module);

		getForumReader = objectMapper.reader(SourceForgeForumSearch.class);
		getTopicReader = objectMapper.reader(SourceForgeTopicSearch.class);
		topicReader = objectMapper.reader(SourceForgeTopic.class);
	}

	private String trackerUrl;
	private String login;
	private String password;

	public SourceForgeDiscussionRestClient(String trackerUrl)
			throws URISyntaxException {
		if (!trackerUrl.endsWith("/")) {
			trackerUrl += '/';
		}

		this.trackerUrl = trackerUrl;
	}

	public void shutdown() throws IOException {
		Unirest.shutdown();
	}
	
	public void setCredentials(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public Iterator<Integer> getForumIds() {
		return new SourceForgeForumIdIterator(this);
	}

	public Iterator<String> getTopicIds(int forumId) {
		return new SourceForgeTopicIdIterator(forumId, this);
	}

	public SourceForgeTopic getTopic(int forumId,  String topicId) throws JsonParseException,
	JsonMappingException, IOException, UnirestException {
		String url = trackerUrl + forumId + "/thread/" + topicId;
		GetRequest getRequest = Unirest.get(url);
		HttpResponse<InputStream> response = executeRestCall(getRequest);
		return topicReader.readValue(response.getRawBody());
	}

	public SourceForgeForumSearch getForum(int page)
			throws JsonProcessingException, IOException, UnirestException {
		return getForum(page, pageSize);
	}

	public SourceForgeForumSearch getForum(int page, int pageSize)
			throws JsonProcessingException, IOException, UnirestException {

//		System.err.println("SourceForgeForumSearch trackerUrl: " + trackerUrl);
//		System.err.println("SourceForgeForumSearch page: " + page);
//		System.err.println("SourceForgeForumSearch limit: " + pageSize);
		
		GetRequest getRequest = Unirest.get(trackerUrl)
				.field("page", page).field("limit", pageSize);
		HttpResponse<InputStream> response = executeRestCall(getRequest);
		return getForumReader.readValue(response.getRawBody());
	}
	
	public SourceForgeTopicSearch getTopics(int forumId, int page)
			throws JsonProcessingException, IOException, UnirestException {
		return getTopics(forumId, page, pageSize);
	}

	public SourceForgeTopicSearch getTopics(int forumId, int page, int pageSize)
			throws JsonProcessingException, IOException, UnirestException {
		String url = trackerUrl + forumId;

//		System.err.println("SourceForgeTopicSearch trackerUrl: " + url);
//		System.err.println("SourceForgeTopicSearch page: " + page);
//		System.err.println("SourceForgeTopicSearch limit: " + pageSize);
		
		GetRequest getRequest = Unirest.get(url)
				.field("page", page).field("limit", pageSize);
		HttpResponse<InputStream> response = executeRestCall(getRequest);
		return getTopicReader.readValue(response.getRawBody());
	}
	
	public SourceForgeArticle getArticle(int forumId, String topicId, String slug)
			throws JsonParseException, JsonMappingException, IOException,
			UnirestException {
		SourceForgeTopic topic = getTopic(forumId, topicId);
		if (null != topic) {
			for (SourceForgeArticle article : topic.getArticles()) {
				if (article.getArticleId().equals(slug)) {
					return (SourceForgeArticle) article;
				}
			}
		}
		return null;
	}

	public Iterator<SourceForgeArticle> getArticles() 
			throws JsonParseException, JsonMappingException, IOException, UnirestException {
		List<SourceForgeArticle> articles = new ArrayList<SourceForgeArticle>();
		Iterator<Integer> forumIds = getForumIds();
//		System.out.println("forumIds.hasNext(): " + forumIds.hasNext());
		while (forumIds.hasNext()) {
			int forumId = forumIds.next();
			Iterator<String> topicIds = getTopicIds(forumId);
//			System.out.println("topicIds.hasNext(): " + topicIds.hasNext());
			while (topicIds.hasNext()) {
				String topicId = topicIds.next();
			//	System.out.println("topicId : " + topicId);
				SourceForgeTopic topic = getTopic(forumId, topicId);
				topic.setForumId(forumId);
//				System.out.println("topic.getDiscussionId() : " + topic.getDiscussionId());
//				System.out.println("topic.getSubject() : " + topic.getSubject());
				List<SourceForgeArticle> newArticles = topic.getArticles();
//				for (SourceForgeArticle article: newArticles) {
//					System.out.println("\tarticle.getForumId() : " + article.getForumId());
//					System.out.println("\tarticle.getMessageThreadId() : " + article.getMessageThreadId());
			//		System.out.println("\tarticle.getArticleId() : " + article.getArticleId());
//					System.out.println("\tarticle.getArticleNumber() : " + article.getArticleNumber());
//					System.out.println("\tarticle.getSubject() : " + article.getSubject());
//					System.out.println("\tarticle.getText() : " + article.getText());
//					System.out.println("\tarticle.getUser() : " + article.getUser());
//					System.out.println("\tarticle.getDate() : " + article.getDate());
//					System.out.println("\tarticle.getUpdateDate() : " + article.getUpdateDate() + "\n");
//				}
				articles.addAll(newArticles);
			}
		}
		return articles.iterator();
	}
	
	protected HttpResponse<InputStream> executeRestCall(GetRequest getRequest)
			throws UnirestException {
		if (null != login && null != password) {
			getRequest = getRequest.basicAuth(login, password);
		}
		
		getRequest.header("accept", "application/json");
		HttpResponse<InputStream> response = getRequest.asBinary();
		if (response.getCode() != 200) {
			throw new RuntimeException("Error executing call to webservice. ["
					+ response.getCode() + "," + getRequest.getUrl() + "]");
		}
		return response;
	}

	public static void main(String[] args) throws URISyntaxException,
			JsonParseException, UnirestException, IOException {
		SourceForgeDiscussionRestClient sourceforge = 
//				new SourceForgeDiscussionRestClient("http://sourceforge.net/rest/p/assimp/discussion/");
				new SourceForgeDiscussionRestClient("http://sourceforge.net/rest/p/gimponosx/discussion/");
		
		Iterator<SourceForgeArticle> articleIt = sourceforge.getArticles();
		int count = 0;
		Date firstDate = null;
		Date lastDate = null;
		while (articleIt.hasNext()) {
			SourceForgeArticle article = articleIt.next();
			System.out.println("article.getForumId() : " + article.getForumId());
			System.out.println("article.getMessageThreadId() : " + article.getMessageThreadId());
			System.out.println("article.getArticleId() : " + article.getArticleId());
			System.out.println("article.getArticleNumber() : " + article.getArticleNumber());
			System.out.println("article.getSubject() : " + article.getSubject());
			System.out.println("article.getText() : " + article.getText());
			System.out.println("article.getUser() : " + article.getUser());
			System.out.println("article.getDate() : " + article.getDate());
			System.out.println("article.getUpdateDate() : " + article.getUpdateDate() + "\n");
			count++;
			java.util.Date articleJaveDate = article.getDate();
			if (article.getUpdateDate() != null)
				articleJaveDate = article.getUpdateDate();
			Date articleDate = new Date(articleJaveDate);
			if ( ( firstDate == null ) 
					|| ( firstDate.compareTo(articleDate) > 0 ) ) {
				firstDate = articleDate;
			}
			if ( ( lastDate == null ) 
					|| ( lastDate.compareTo(articleDate) < 0 ) ) {
				lastDate = articleDate;
			}
		}

		System.out.println();
		System.out.println("articles.size() : " + count);
		System.out.println("firstDate : " + firstDate);
		System.out.println("lastDate : " + lastDate);
		sourceforge.shutdown();

	}

}
