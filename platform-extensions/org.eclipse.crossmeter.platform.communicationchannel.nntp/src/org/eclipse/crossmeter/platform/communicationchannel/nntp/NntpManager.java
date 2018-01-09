/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.platform.communicationchannel.nntp;

import java.io.Reader;

import org.apache.commons.net.nntp.Article;
import org.apache.commons.net.nntp.NNTPClient;
import org.apache.commons.net.nntp.NewsgroupInfo;
import org.eclipse.crossmeter.platform.Date;
import org.eclipse.crossmeter.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.crossmeter.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.crossmeter.platform.delta.communicationchannel.ICommunicationChannelManager;
import org.eclipse.crossmeter.repository.model.CommunicationChannel;
import org.eclipse.crossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.mongodb.DB;

public class NntpManager implements ICommunicationChannelManager<NntpNewsGroup> {
	
	private final static int RETRIEVAL_STEP = 50;

	@Override
	public boolean appliesTo(CommunicationChannel newsgroup) {
		return newsgroup instanceof NntpNewsGroup;
	}

	@Override
	public CommunicationChannelDelta getDelta(DB db, NntpNewsGroup newsgroup, Date date) throws Exception {
		NNTPClient nntpClient = NntpUtil.connectToNntpServer(newsgroup);
		
		NewsgroupInfo newsgroupInfo = NntpUtil.selectNewsgroup(nntpClient, newsgroup);
		int lastArticle = newsgroupInfo.getLastArticle();

//		 The following statement is not really needed, but I added it to speed up running,
//		 in the date is far latter than the first day of the newsgroup.
//		if (Integer.parseInt(newsgroup.getLastArticleChecked())<134500)
//			newsgroup.setLastArticleChecked("134500"); //137500");

		String lac = newsgroup.getLastArticleChecked();
		if (lac == null || lac.equals("") || lac.equals("null")) lac = "-1";
		int lastArticleChecked = Integer.parseInt(lac);
		if (lastArticleChecked<0) lastArticleChecked = newsgroupInfo.getFirstArticle();

		CommunicationChannelDelta delta = new CommunicationChannelDelta();
		delta.setNewsgroup(newsgroup);

		// FIXME: certain eclipse newsgroups return 0 for both FirstArticle and LastArticle which causes exceptions
		if (lastArticleChecked == 0) {
			nntpClient.disconnect(); 
			return null;
		}
		
		int retrievalStep = RETRIEVAL_STEP;
		Boolean dayCompleted = false;
		while (!dayCompleted) {
			if (lastArticleChecked + retrievalStep > lastArticle) {
				retrievalStep = lastArticle - lastArticleChecked;
				dayCompleted = true;
			}
			Article[] articles;
			Date articleDate=date;
			// The following loop discards messages for days earlier than the required one.
			do {
				articles = NntpUtil.getArticleInfo(nntpClient, 
						lastArticleChecked + 1, lastArticleChecked + retrievalStep);
				if (articles.length > 0) {
					Article lastArticleRetrieved = articles[articles.length-1];
					java.util.Date javaArticleDate = NntpUtil.parseDate(lastArticleRetrieved.getDate());
					articleDate = new Date(javaArticleDate);
					if (date.compareTo(articleDate) > 0)
						lastArticleChecked = lastArticleRetrieved.getArticleNumber();
				}
			} while (date.compareTo(articleDate) > 0);

			for (Article article: articles) {
				java.util.Date javaArticleDate = NntpUtil.parseDate(article.getDate());
				if (javaArticleDate!=null) {
					articleDate = new Date(javaArticleDate);
					if (date.compareTo(articleDate) < 0) {
						dayCompleted = true;
//						System.out.println("dayCompleted");
					}
					else if (date.compareTo(articleDate) == 0) {
						CommunicationChannelArticle communicationChannelArticle = new CommunicationChannelArticle();
						communicationChannelArticle.setArticleId(article.getArticleId());
						communicationChannelArticle.setArticleNumber(article.getArticleNumber());
						communicationChannelArticle.setDate(javaArticleDate);
//						I haven't seen any messageThreadIds on NNTP servers, yet.
//						communicationChannelArticle.setMessageThreadId(article.messageThreadId());
						NntpNewsGroup newNewsgroup = new NntpNewsGroup();
						newNewsgroup.setUrl(newsgroup.getUrl());
						newNewsgroup.setAuthenticationRequired(newsgroup.getAuthenticationRequired());
						newNewsgroup.setUsername(newsgroup.getUsername());
						newNewsgroup.setPassword(newsgroup.getPassword());
						newNewsgroup.setNewsGroupName(newsgroup.getNewsGroupName());
						newNewsgroup.setPort(newsgroup.getPort());
						newNewsgroup.setInterval(newsgroup.getInterval());
						communicationChannelArticle.setNewsgroup(newNewsgroup);
						communicationChannelArticle.setReferences(article.getReferences());
						communicationChannelArticle.setSubject(article.getSubject());
						communicationChannelArticle.setUser(article.getFrom());
						communicationChannelArticle.setText(
								getContents(db, newNewsgroup, communicationChannelArticle));
						delta.getArticles().add(communicationChannelArticle);
						lastArticleChecked = article.getArticleNumber();
//						System.out.println("dayNOTCompleted");
					} 
					else {

							//TODO: In this case, there are unprocessed articles whose date is earlier than the date requested.
							//      This means that the deltas of those article dates are incomplete, 
							//		i.e. the deltas did not contain all articles of those dates.
					}
				} else {
					// If an article has no correct date, then ignore it
					System.err.println("\t\tUnparsable article date: " + article.getDate());
				}
			}
		}
		nntpClient.disconnect(); 
		newsgroup.setLastArticleChecked(lastArticleChecked+"");
		System.out.println("delta ("+newsgroup.getNewsGroupName() + " on " + date.toString()+") contains:\t"+
								delta.getArticles().size() + " nntp articles");
		
		return delta;
	}

	@Override
	public Date getFirstDate(DB db, NntpNewsGroup newsgroup) throws Exception {
		NNTPClient nntpClient = NntpUtil.connectToNntpServer(newsgroup);
		NewsgroupInfo newsgroupInfo = NntpUtil.selectNewsgroup(nntpClient, newsgroup);
		int firstArticleNumber = newsgroupInfo.getFirstArticle();
		
		if (firstArticleNumber == 0) {
			return new Date(); // This is to deal with message-less newsgroups.
		}
				
		Reader reader = nntpClient.retrieveArticle(firstArticleNumber);
		while (reader == null) {
			firstArticleNumber++;
			reader = nntpClient.retrieveArticle(firstArticleNumber);
			if (firstArticleNumber >= newsgroupInfo.getLastArticle()) break;
		}
		
		ArticleHeader articleHeader = new ArticleHeader(reader);
//		Article article = NntpUtil.getArticleInfo(nntpClient, articleId);
		nntpClient.disconnect();
//		String date = article.getDate();
		return new Date(NntpUtil.parseDate(articleHeader.getDate().trim()));
	}

	@Override
	public String getContents(DB db, NntpNewsGroup newsgroup, CommunicationChannelArticle article) throws Exception {
		NNTPClient nntpClient = NntpUtil.connectToNntpServer(newsgroup);
		NntpUtil.selectNewsgroup(nntpClient, newsgroup);		
		String contents = NntpUtil.getArticleBody(nntpClient, article.getArticleNumber());
		nntpClient.disconnect();
		return contents;
	}

}
