/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.nntp;

import java.io.Reader;
import java.util.List;

import org.apache.commons.net.nntp.Article;
import org.apache.commons.net.nntp.NNTPClient;
import org.apache.commons.net.nntp.NewsgroupInfo;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.communicationchannel.nntp.model.NntpDataManager;
import org.eclipse.scava.platform.communicationchannel.nntp.model.NntpDatum;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.scava.platform.delta.communicationchannel.ICommunicationChannelManager;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;

import com.mongodb.DB;

public class NntpManager implements ICommunicationChannelManager<NntpNewsGroup> {
	
	private final static long RETRIEVAL_STEP = 50;
	
	protected static OssmeterLogger logger = (OssmeterLogger) OssmeterLogger.getLogger("platform.communicationchannel.nntp");

	@Override
	public boolean appliesTo(CommunicationChannel newsgroup) {
		return newsgroup instanceof NntpNewsGroup;
	}

	@Override
	public CommunicationChannelDelta getDelta(DB db, NntpNewsGroup newsgroup, Date date) throws Exception {
		NNTPClient nntpClient = NntpUtil.connectToNntpServer(newsgroup);
		
		NewsgroupInfo newsgroupInfo = NntpUtil.selectNewsgroup(nntpClient, newsgroup);
		long lastArticle = newsgroupInfo.getLastArticleLong();
		NntpDataManager nntpDataManager = new NntpDataManager(db);
		
		NntpDatum data=null;
		Iterable<NntpDatum> dataIt = nntpDataManager.getNntpData().find(NntpDatum.OSSMERTERID.eq(newsgroup.getOSSMeterId()));
		long lastArticleChecked=-1;
		for(NntpDatum nd : dataIt)
		{
			data=nd;
		}
		if(data==null)
		{
			data = new NntpDatum();
			data.setDate(date.toString());
			data.setOssmerterId(newsgroup.getOSSMeterId());
			data.setLastArticleChecked(-1);
			nntpDataManager.getNntpData().add(data);
			nntpDataManager.sync();
		}
		else
		{
			if(date.compareTo(new Date(data.getDate()).addDays(1))<0)
			{
				data.setDate(date.toString());
				data.setOssmerterId(newsgroup.getOSSMeterId());
				data.setLastArticleChecked(-1);
				nntpDataManager.sync();
			}
			else
			{
				lastArticleChecked=data.getLastArticleChecked();
			}
		}

		if (lastArticleChecked<0) lastArticleChecked = newsgroupInfo.getFirstArticleLong();

		CommunicationChannelDelta delta = new CommunicationChannelDelta();
		delta.setNewsgroup(newsgroup);

		// FIXME: certain eclipse newsgroups return 0 for both FirstArticle and LastArticle which causes exceptions
		if (lastArticleChecked == 0) {
			logger.error("Last Article Checked is Zero. The client is closing and returning empty delta");
			nntpClient.disconnect(); 
			return delta;
		}
		
		long retrievalStep = RETRIEVAL_STEP;
		Boolean dayCompleted = false;
		boolean continueArticleDate;
		
		while (!dayCompleted)
		{
			if (lastArticleChecked + retrievalStep > lastArticle) {
				retrievalStep = lastArticle - lastArticleChecked;
				dayCompleted = true;
			}
			List<Article> articles;
			Date articleDate=date;
			int numberArticles;
			// The following loop discards messages for days earlier than the required one.
			
			try
			{
				do
				{	
					articles = NntpUtil.getArticleInfo(nntpClient, lastArticleChecked + 1, lastArticleChecked + retrievalStep);
					numberArticles=articles.size();
					if(numberArticles>0)
					{
						continueArticleDate=true;
						while (continueArticleDate)
						{
							Article lastArticleRetrieved = articles.get(numberArticles-1);
							java.util.Date javaArticleDate = NntpUtil.parseDate(lastArticleRetrieved.getDate());
							if(javaArticleDate==null)
							{
								articles.remove(numberArticles-1);
								numberArticles=articles.size();
								if(numberArticles==0)
									continueArticleDate=false;
							}
							else
							{
								articleDate = new Date(javaArticleDate);
								if (date.compareTo(articleDate) > 0)
									lastArticleChecked = lastArticleRetrieved.getArticleNumberLong();
								continueArticleDate=false;
							}
						}
					}
				}
				while (date.compareTo(articleDate) > 0);
				
				for (Article article: articles) {
					java.util.Date javaArticleDate = NntpUtil.parseDate(article.getDate());
					if (javaArticleDate!=null) {
						articleDate = new Date(javaArticleDate);
						if (date.compareTo(articleDate) < 0) {
							dayCompleted = true;
						}
						else if (date.compareTo(articleDate) == 0)
						{
							CommunicationChannelArticle communicationChannelArticle = new CommunicationChannelArticle();
							communicationChannelArticle.setArticleId(article.getArticleId());
							communicationChannelArticle.setArticleNumber(article.getArticleNumberLong());
							communicationChannelArticle.setDate(javaArticleDate);
//							I haven't seen any messageThreadIds on NNTP servers, yet.
//							communicationChannelArticle.setMessageThreadId(article.messageThreadId());
							NntpNewsGroup newNewsgroup = new NntpNewsGroup();
							newNewsgroup.setUrl(newsgroup.getUrl());
							newNewsgroup.setAuthenticationRequired(newsgroup.getAuthenticationRequired());
							newNewsgroup.setUsername(newsgroup.getUsername());
							newNewsgroup.setPassword(newsgroup.getPassword());
							newNewsgroup.setNewsGroupName(newsgroup.getNewsGroupName());
							newNewsgroup.setPort(newsgroup.getPort());
							newNewsgroup.setInterval(newsgroup.getInterval());
							communicationChannelArticle.setCommunicationChannel(newNewsgroup);
							communicationChannelArticle.setReferences(article.getReferences());
							communicationChannelArticle.setSubject(article.getSubject());
							communicationChannelArticle.setUser(article.getFrom());
							communicationChannelArticle.setText(
									getContents(db, newNewsgroup, communicationChannelArticle));
							delta.getArticles().add(communicationChannelArticle);
							lastArticleChecked = article.getArticleNumberLong();
//							System.out.println("dayNOTCompleted");
						} 
						else {
							logger.warn("It has been found an article that could mean previous deltas are incomplete " + article.getDate());
						}
					}
					else
					{
						logger.warn("Unparsable article date: " + article.getDate());
					}
				}
			}
			catch (NullPointerException e) {
				String mainMessage="Article Date has been found to be null and it is impossible to recover. ";
				if(delta.getArticles().size()==0)
				{
					logger.error(mainMessage+"Returning empty delta.");	
				}
				else
				{
					logger.error(mainMessage+"Returning partial delta.");
				}	
				dayCompleted=true;
			}
		}
		nntpClient.disconnect();
		data.setLastArticleChecked(lastArticleChecked);
		data.setDate(date.toString());
		nntpDataManager.sync();
		logger.info(newsgroup.getNewsGroupName() + " on " + date.toString()+") contains:\t"+ delta.getArticles().size() + " nntp articles");
		return delta;
	}

	@Override
	public Date getFirstDate(DB db, NntpNewsGroup newsgroup) throws Exception {
		NNTPClient nntpClient = NntpUtil.connectToNntpServer(newsgroup);
		NewsgroupInfo newsgroupInfo = NntpUtil.selectNewsgroup(nntpClient, newsgroup);
		long firstArticleNumber = newsgroupInfo.getFirstArticleLong();
		
		if (firstArticleNumber == 0) {
			return new Date(); // This is to deal with message-less newsgroups.
		}
				
		Reader reader = nntpClient.retrieveArticle(firstArticleNumber);
		while (reader == null) {
			firstArticleNumber++;
			reader = nntpClient.retrieveArticle(firstArticleNumber);
			if (firstArticleNumber >= newsgroupInfo.getLastArticleLong()) break;
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
