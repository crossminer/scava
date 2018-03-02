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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.communicationchannel.nntp.local.model.ArticleData;
import org.eclipse.scava.platform.communicationchannel.nntp.local.model.Messages;
import org.eclipse.scava.platform.communicationchannel.nntp.local.model.NewsgroupData;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.scava.platform.delta.communicationchannel.ICommunicationChannelManager;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;

import com.mongodb.DB;

public class NntpManager implements ICommunicationChannelManager<NntpNewsGroup> {
	
	private final static int RETRIEVAL_STEP = 50;
	
	private Messages dbMessages = null;
	
	@Override
	public boolean appliesTo(CommunicationChannel newsgroup) {
		return newsgroup instanceof NntpNewsGroup;
	}

	@Override
	public CommunicationChannelDelta getDelta(DB db, NntpNewsGroup newsgroup, Date date) throws Exception {

		if (dbMessages==null) dbMessages = new Messages(db);

		NewsgroupData newsgroupData = dbMessages.getNewsgroup().findOneByUrl((newsgroup.getUrl()));
		if (newsgroupData == null) {
			System.err.println("There is no local storage for newsgroup: " + newsgroup.getUrl());
//			System.exit(-1);
			return null;
		}

		int lastArticleChecked = Integer.parseInt(newsgroup.getLastArticleChecked());
		if (lastArticleChecked<0) 
			lastArticleChecked = Integer.parseInt(newsgroupData.getFirstArticle());
		int lastArticle = Integer.parseInt(newsgroupData.getLastArticleChecked());
		
		CommunicationChannelDelta delta = new CommunicationChannelDelta();
		delta.setNewsgroup(newsgroup);

		int retrievalStep = RETRIEVAL_STEP;
		Boolean dayCompleted = false;
		while (!dayCompleted) {
			if (lastArticleChecked + retrievalStep > lastArticle) {
				retrievalStep = lastArticle - lastArticleChecked;
				dayCompleted = true;
			}
			
			Date articleDate = date;
			Iterable<ArticleData> articleDataIterable = null;
			ArrayList<ArticleData> articleDataArrayList;
			// The following loop discards messages for days earlier than the required one.
			do {
				articleDataArrayList = new ArrayList<ArticleData>(retrievalStep);
				articleDataIterable = dbMessages.getArticles().find(
						ArticleData.ARTICLENUMBER.greaterThan(lastArticleChecked),
						ArticleData.ARTICLENUMBER.lessThanOrEqualTo(lastArticleChecked + retrievalStep));
				for (ArticleData articleData: articleDataIterable)
					articleDataArrayList.add(articleData);
				if (articleDataArrayList.size() > 0) {
					ArticleData lastArticleData = articleDataArrayList.get(articleDataArrayList.size() - 1);
					java.util.Date javaArticleDate = 
							NntpUtil.parseDate(lastArticleData.getDate());
					articleDate = new Date(javaArticleDate);
					if (date.compareTo(articleDate) > 0)
						lastArticleChecked = lastArticleData.getArticleNumber();
				}
			} while (date.compareTo(articleDate) > 0);

			for (ArticleData articleData: articleDataArrayList) {
				java.util.Date javaArticleDate = NntpUtil.parseDate(articleData.getDate());
				if (javaArticleDate!=null) {
					articleDate = new Date(javaArticleDate);
					if (date.compareTo(articleDate) < 0) {
						dayCompleted = true;
//						System.out.println("dayCompleted");
					}
					else if (date.compareTo(articleDate) == 0) {
						CommunicationChannelArticle communicationChannelArticle = new CommunicationChannelArticle();
						communicationChannelArticle.setArticleId(articleData.getArticleId());
						communicationChannelArticle.setArticleNumber(articleData.getArticleNumber());
						communicationChannelArticle.setDate(javaArticleDate);
//						I haven't seen any messageThreadIds on NNTP servers, yet.
//						communicationChannelArticle.setMessageThreadId(article.messageThreadId());
						NntpNewsGroup newNewsgroup = new NntpNewsGroup();
						newNewsgroup.setUrl(newsgroup.getUrl());
						newNewsgroup.setAuthenticationRequired(newsgroup.getAuthenticationRequired());
						newNewsgroup.setUsername(newsgroup.getUsername());
						newNewsgroup.setPassword(newsgroup.getPassword());
						newNewsgroup.setPort(newsgroup.getPort());
						newNewsgroup.setInterval(newsgroup.getInterval());
						communicationChannelArticle.setNewsgroup(newNewsgroup);
						List<String> referenceList = articleData.getReferences();
						String[] referenceArray = list2array(referenceList);
						communicationChannelArticle.setReferences(referenceArray);
						communicationChannelArticle.setSubject(articleData.getSubject());
						communicationChannelArticle.setUser(articleData.getFrom());
						communicationChannelArticle.setText(articleData.getBody());
						delta.getArticles().add(communicationChannelArticle);
						lastArticleChecked = articleData.getArticleNumber();
					} 
					else {

							//TODO: In this case, there are unprocessed articles whose date is earlier than the date requested.
							//      This means that the deltas of those article dates are incomplete, 
							//		i.e. the deltas did not contain all articles of those dates.
					}
				} else {
					// If an article has no correct date, then ignore it
					System.err.println("\t\tUnparsable article date: " + articleData.getDate());
				}
			}
		}
		newsgroup.setLastArticleChecked(lastArticleChecked+"");
		System.out.println("delta ("+date.toString()+") contains:\t"+
								delta.getArticles().size() + " nntp articles");
		return delta;
	}

	@Override
	public Date getFirstDate(DB db, NntpNewsGroup newsgroup) throws Exception {
		if (dbMessages==null) dbMessages = new Messages(db);
		NewsgroupData newsgroupData = dbMessages.getNewsgroup().findOneByUrl((newsgroup.getUrl()));
		
		if (newsgroupData == null) {
			System.err.println("There is no local storage for newsgroup: " + newsgroup.getUrl());
//			System.exit(-1);
			return null;
		}
		int firstArticleNumber = Integer.parseInt(newsgroupData.getFirstArticle());
		Date date = null;
		do {
			Iterable<ArticleData> articleDataIterable = 
					dbMessages.getArticles().find(ArticleData.ARTICLENUMBER.eq(firstArticleNumber));
			for (ArticleData articleData: articleDataIterable)
				date = new Date(NntpUtil.parseDate(articleData.getDate().trim()));
			firstArticleNumber++;
		} while (date==null);
		return date;
	}

	@Override
	public String getContents(DB db, NntpNewsGroup newsgroup, 
			CommunicationChannelArticle article) throws Exception {
		if (dbMessages==null) dbMessages = new Messages(db);
		NewsgroupData newsgroupData = dbMessages.getNewsgroup().findOneByUrl((newsgroup.getUrl()));
		if (newsgroupData == null) {
			System.err.println("There is no local storage for newsgroup: " + newsgroup.getUrl());
//			System.exit(-1);
			return null;
		}
		Iterable<ArticleData> articleDataIterable = 
				dbMessages.getArticles().find(ArticleData.ARTICLENUMBER.eq(article.getArticleNumber()));
//				dbMessages.getArticles().find(ArticleData.ARTICLENUMBER.eq(article.getArticleNumber()));
		String contents = "";
		for (ArticleData articleData: articleDataIterable)
			contents = articleData.getBody();
		return contents;
	}
	
	private static String[] list2array(List<String> list) {
		String[] array = new String[list.size()];
		for(int i = 0; i < list.size(); i++) array[i] = list.get(i);
		return array;
	}

}
