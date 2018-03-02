/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.nntp.local;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.net.nntp.Article;
import org.apache.commons.net.nntp.NNTPClient;
import org.apache.commons.net.nntp.NewsgroupInfo;
import org.eclipse.scava.platform.communicationchannel.nntp.local.model.ArticleData;
import org.eclipse.scava.platform.communicationchannel.nntp.local.model.Messages;
import org.eclipse.scava.platform.communicationchannel.nntp.local.model.NewsgroupData;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class NNTPDownloader {
	
	private final static int RETRIEVAL_STEP = 250;
	
	public static void main(String[] args) {
		
		NntpNewsGroup newsgroup = new NntpNewsGroup();
		newsgroup.setName("eclipsePlatform");
		newsgroup.setUrl("news.eclipse.org/eclipse.platform");
		newsgroup.setAuthenticationRequired(true);
		newsgroup.setUsername("exquisitus");
		newsgroup.setPassword("flinder1f7");
		newsgroup.setPort(80);
		newsgroup.setInterval(10000);

		NNTPDownloader downloader = new NNTPDownloader();
		try {
			downloader.downloadMessages(newsgroup);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void downloadMessages(NntpNewsGroup newsgroup) throws Exception {
		final long startTime = System.currentTimeMillis();
		long previousTime = startTime;
		previousTime = printTimeMessage(startTime, previousTime, "Download started");

		NNTPClient nntpClient = NntpUtil.connectToNntpServer(newsgroup);
		
		NewsgroupInfo newsgroupInfo = NntpUtil.selectNewsgroup(nntpClient, newsgroup);
		
		int lastArticleChecked = newsgroupInfo.getFirstArticle();
		previousTime = printTimeMessage(startTime, previousTime,
				"First message in newsgroup:\t" + lastArticleChecked);
		
		int lastArticle = newsgroupInfo.getLastArticle();
		previousTime = printTimeMessage(startTime, previousTime,
				"Last message in newsgroup:\t" + lastArticle);
		
		previousTime = printTimeMessage(startTime, previousTime,
				"Articles in newsgroup:\t" + newsgroupInfo.getArticleCount());
		System.err.println();
		
		Mongo mongo = new Mongo();
		DB db = mongo.getDB(newsgroup.getName() + "LocalStorage");
		Messages dbMessages = new Messages(db);
			
		NewsgroupData newsgroupData = dbMessages.getNewsgroup().findOneByName(newsgroup.getName());
		if (newsgroupData != null) {
			int newsgroupLastArticleChecked = Integer.parseInt(newsgroupData.getLastArticleChecked());
			if (newsgroupLastArticleChecked > lastArticleChecked) {
				lastArticleChecked = newsgroupLastArticleChecked;
			}
			previousTime = printTimeMessage(startTime, previousTime,
					"Last article checked set to:\t" + lastArticleChecked);
		} else {
			newsgroupData = new NewsgroupData();
			newsgroupData.setName(newsgroup.getName());
			newsgroupData.setUrl(newsgroup.getUrl());
			newsgroupData.setAuthenticationRequired(newsgroup.getAuthenticationRequired());
			newsgroupData.setUsername(newsgroup.getUsername());
			newsgroupData.setPassword(newsgroup.getPassword());
			newsgroupData.setPort(newsgroup.getPort());
			newsgroupData.setInterval(newsgroup.getInterval());
			newsgroupData.setFirstArticle(lastArticleChecked+"");
			dbMessages.getNewsgroup().add(newsgroupData);
		}

		int retrievalStep = RETRIEVAL_STEP;
		while (lastArticleChecked < lastArticle) {
			if (lastArticleChecked + retrievalStep > lastArticle) {
				retrievalStep = lastArticle - lastArticleChecked;
			}
			Article[] articles = NntpUtil.getArticleInfo(nntpClient, 
						lastArticleChecked + 1, lastArticleChecked + retrievalStep);
			if (articles.length > 0) {
				Article lastArticleRetrieved = articles[articles.length-1];
				lastArticleChecked = lastArticleRetrieved.getArticleNumber();
				newsgroupData.setLastArticleChecked(lastArticleChecked+"");
			}
			previousTime = printTimeMessage(startTime, previousTime,
					"downloaded:\t"+ articles.length + " nntp articles");
			previousTime = printTimeMessage(startTime, previousTime,
					"downloading contents\t");
			
			for (Article article: articles) {
				ArticleData articleData = new ArticleData();
				articleData.setUrl(newsgroup.getUrl());
				articleData.setArticleNumber(article.getArticleNumber());
				articleData.setArticleId(article.getArticleId());
				articleData.setDate(article.getDate());
				articleData.setFrom(article.getFrom());
				articleData.setSubject(article.getSubject());
				for (String referenceId: article.getReferences())
					articleData.getReferences().add(referenceId);
				articleData.setBody(NntpUtil.getArticleBody(nntpClient, article.getArticleNumber()));
				dbMessages.getArticles().add(articleData);
			}
			dbMessages.sync();
			previousTime = printTimeMessage(startTime, previousTime,
					"stored:\t"+ dbMessages.getArticles().size() + 
					" / " + newsgroupInfo.getArticleCount() + " nntp articles sofar");
			System.err.println();
		}
		nntpClient.disconnect(); 
		dbMessages.sync();
		previousTime = printTimeMessage(startTime, previousTime,
				"stored:\t"+ dbMessages.getArticles().size() + 
				" / " + newsgroupInfo.getArticleCount() + " nntp articles");
	}

	public String getContents(NntpNewsGroup newsgroup, int articleNumber) throws Exception {
//		System.out.print(" " + articleNumber);
		NNTPClient nntpClient = NntpUtil.connectToNntpServer(newsgroup);
		NntpUtil.selectNewsgroup(nntpClient, newsgroup);		
		String contents = NntpUtil.getArticleBody(nntpClient, articleNumber);
		nntpClient.disconnect();
		return contents;
	}

	private long printTimeMessage(long startTime, long previousTime, String message) {
		long currentTime = System.currentTimeMillis();
		System.err.println(time(currentTime - previousTime) + "\t" +
						   time(currentTime - startTime) + "\t" +
						   message);
		return currentTime;
	}

	private String time(long timeInMS) {
		return DurationFormatUtils.formatDuration(timeInMS, "HH:mm:ss,SSS");
	}

}
