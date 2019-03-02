/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.scava.contentclassifier.opennlptartarus.libsvm.ClassificationInstance;
import org.eclipse.scava.contentclassifier.opennlptartarus.libsvm.Classifier;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ArticleData;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.CurrentDate;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.NewsgroupData;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.NewsgroupsThreadsTransMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ThreadData;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.communicationchannel.nntp.Article;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelProjectDelta;
import org.eclipse.scava.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.mongodb.DB;

public class ThreadsTransMetricProvider implements ITransientMetricProvider<NewsgroupsThreadsTransMetric>{

	protected PlatformCommunicationChannelManager communicationChannelManager;

	@Override
	public String getIdentifier() {
		return ThreadsTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
			if (communicationChannel instanceof Discussion) return true;
		}
		return false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		// DO NOTHING -- we don't use anything
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Collections.emptyList();
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
	}

	@Override
	public NewsgroupsThreadsTransMetric adapt(DB db) {
		return new NewsgroupsThreadsTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, NewsgroupsThreadsTransMetric db) {

		Iterable<CurrentDate> currentDateIt = db.getDate();
		CurrentDate currentDate = null;
		for (CurrentDate cd:  currentDateIt)
			currentDate = cd;
		if (currentDate != null)
			currentDate.setDate(projectDelta.getDate().toString());
		else {
			currentDate = new CurrentDate();
			currentDate.setDate(projectDelta.getDate().toString());
			db.getDate().add(currentDate);
		}
			
		for (NewsgroupData newsgroupData: db.getNewsgroups())
			newsgroupData.setPreviousThreads(newsgroupData.getThreads());
		
		CommunicationChannelProjectDelta delta = projectDelta.getCommunicationChannelDelta();

		Map<String, Set<Long>> articleIdsPerNewsgroup = new HashMap<String, Set<Long>>();
		Map<String, Set<Integer>> threadsPerNewsgroup = new HashMap<String, Set<Integer>>();

		for ( CommunicationChannelDelta communicationChannelDelta: delta.getCommunicationChannelSystemDeltas()) {
			CommunicationChannel communicationChannel = communicationChannelDelta.getCommunicationChannel();
			
			if (communicationChannelDelta.getArticles().size() > 0) {
				String communicationChannelName;
				if (!(communicationChannel instanceof NntpNewsGroup))
					communicationChannelName = communicationChannel.getUrl();
				else {
					NntpNewsGroup newsgroup = (NntpNewsGroup) communicationChannel;
					communicationChannelName = newsgroup.getNewsGroupName();
				}
				
				Map<Long, String> previousClassAssignments = new HashMap<Long, String>();
				
				List<Article> articles = new ArrayList<Article>();
				for (ThreadData threadData: db.getThreads()) {
					for (ArticleData articleData: threadData.getArticles()) {
						if (articleData.getNewsgroupName().equals(communicationChannelName)) {
							previousClassAssignments.put(articleData.getArticleNumber(), articleData.getContentClass());
							articles.add(prepareArticle(articleData));
							Set<Long> articleIds = null;
							if (articleIdsPerNewsgroup.containsKey(articleData.getNewsgroupName()))
								articleIds = articleIdsPerNewsgroup.get(articleData.getNewsgroupName());
							else {
								articleIds = new HashSet<Long>();
								articleIdsPerNewsgroup.put(articleData.getNewsgroupName(), articleIds);
							}
							articleIds.add(articleData.getArticleNumber());
						}
					}
				}
				
				Map<Long, ClassificationInstance> instanceIndex = new HashMap<Long, ClassificationInstance>();
				for (CommunicationChannelArticle deltaArticle :communicationChannelDelta.getArticles()) {

					Boolean articleExists = false;
					if (articleIdsPerNewsgroup.containsKey(communicationChannelName)
						&& articleIdsPerNewsgroup.get(communicationChannelName).contains(deltaArticle.getArticleNumber()))
						articleExists = true;
					
					if (!articleExists) {
						articles.add(prepareArticle(deltaArticle));
						ClassificationInstance instance = 
								prepareClassificationInstance(communicationChannelName, deltaArticle);
						instanceIndex.put(instance.getArticleNumber(), instance);
					}
				}
				
				System.out.println("Building message thread tree... (" + articles.size() + ")");
				Threader threader = new Threader();
				Article root = (Article)threader.thread(articles);
				List<List<Article>> articleList = zeroLevelCall(root);
				
				db.getThreads().getDbCollection().drop();
				db.sync();
				
				Classifier classifier = new Classifier();
				for (List<Article> list: articleList) {
					int positionInThread = 0;
					for (Article article: list) {
						positionInThread++;
						if (instanceIndex.containsKey(article.getArticleNumberLong())) {
							ClassificationInstance instance = instanceIndex.get(article.getArticleNumberLong());
							instance.setPositionFromThreadBeginning(positionInThread);
//							instance.setPositionFromThreadEnd(list.size()+1-positionInThread);
							classifier.add(instance);
						}
					}
				}
				classifier.classify();
				int index = 0;
				for (List<Article> list: articleList) {
					index++;
					
					ThreadData threadData = new ThreadData();
					threadData.setThreadId(index);
					for (Article article: list) {
						threadData.getArticles().add(
								prepareArticleData(article, communicationChannelName, classifier, 
												   previousClassAssignments, instanceIndex));
						
						if (threadsPerNewsgroup.containsKey(communicationChannelName))
							threadsPerNewsgroup.get(communicationChannelName).add(index);
						else {
							Set<Integer> threadSet = new HashSet<Integer>();
							threadSet.add(index);
							threadsPerNewsgroup.put(communicationChannelName, threadSet);
						}
					}
					db.getThreads().add(threadData);
				}
				db.sync();
			}
		}
		db.sync();
		for (String newsgroupName: threadsPerNewsgroup.keySet()) {
			Iterable<NewsgroupData> newsgroupDataIt = 
					db.getNewsgroups().find(NewsgroupData.NEWSGROUPNAME.eq(newsgroupName));
			NewsgroupData newsgroupData = null;
			for (NewsgroupData ngd:  newsgroupDataIt)
				newsgroupData = ngd;
			if (newsgroupData!=null) {
				newsgroupData.setThreads(threadsPerNewsgroup.get(newsgroupName).size());
			} else {
				newsgroupData = new NewsgroupData();
				newsgroupData.setNewsgroupName(newsgroupName);
				newsgroupData.setPreviousThreads(0);
				newsgroupData.setThreads(threadsPerNewsgroup.get(newsgroupName).size());
				db.getNewsgroups().add(newsgroupData);
			}
		}
		db.sync();
	}

	private ClassificationInstance prepareClassificationInstance(
			String communicationChannelName, CommunicationChannelArticle deltaArticle) {
		ClassificationInstance instance = new ClassificationInstance(); 
		instance.setArticleNumber(deltaArticle.getArticleNumber());
		instance.setNewsgroupName(communicationChannelName);
		instance.setSubject(deltaArticle.getSubject());
		instance.setText(deltaArticle.getText());
		return instance;
	}

	
	private Article prepareArticle(CommunicationChannelArticle deltaArticle) {
		Article article = new Article();
		article.setArticleId(deltaArticle.getArticleId());
		article.setArticleNumber(deltaArticle.getArticleNumber());
		article.setDate(deltaArticle.getDate().toString());
		article.setFrom(deltaArticle.getUser());
		article.setSubject(deltaArticle.getSubject());
		for (String reference: deltaArticle.getReferences())
			article.addReference(reference);
//		printArticle(article, "|2| ");
		return article;
	}

	
	private ArticleData prepareArticleData(Article article, 
					String communicationChannelName, Classifier classifier, 
					Map<Long, String> previousClassAssignments, Map<Long, ClassificationInstance> instanceIndex) {
		ArticleData articleData = new ArticleData();
		articleData.setNewsgroupName(communicationChannelName);
		articleData.setArticleId(article.getArticleId());
		articleData.setArticleNumber(article.getArticleNumberLong());
		articleData.setDate(article.getDate());
		articleData.setFrom(article.getFrom());
		articleData.setSubject(article.getSubject());
		String references = "";
		for (String reference: article.getReferences())
			references += " " + reference;
		articleData.setReferences(references.trim());
		if (previousClassAssignments.containsKey(article.getArticleNumberLong())) {
			articleData.setContentClass(previousClassAssignments.get(article.getArticleNumberLong()));
		}
		else {
			articleData.setContentClass(
					classifier.getClassificationResult(instanceIndex.get(article.getArticleNumberLong())));
		}
//		printArticle(article, "|3| ");
		return articleData;
	}

	
	private Article prepareArticle(ArticleData articleData) {
		Article article = new Article();
		article.setArticleId(articleData.getArticleId());
		article.setArticleNumber(articleData.getArticleNumber());
		article.setDate(articleData.getDate());
		article.setFrom(articleData.getFrom());
		article.setSubject(articleData.getSubject());
		for (String reference: articleData.getReferences().split(" "))
			article.addReference(reference);
//		printArticle(article, "|1| ");
		return article;
	}

//	private void printArticle(Article article, String message) {
//		System.out.println(message + 
//				article.getArticleId() + "\t" +
//				article.getArticleNumber() + "\t" +
//				article.getDate() + "\t" +
//				article.getFrom() + "\t" +
//				article.getSubject() + "\t|" +
//				article.getReferences() + "|");
//	}

	@Override
	public String getShortIdentifier() {
		return "threads";
	}

	@Override
	public String getFriendlyName() {
		return "Threads";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric holds information for assigning newsgroup articles to threads. " +
				"The threading algorithm is executed from scratch everytime.";
	}
	
	
	public static List<List<Article>> zeroLevelCall(Article article) {
//		Article root = article;
		List<List<Article>> threadList = new ArrayList<List<Article>>();
		while (article!=null) {
			List<Article> articleNumbers = new ArrayList<Article>();
			if (article.getArticleNumberLong()>0)
				articleNumbers.add(article);
			if (article.kid != null)
				articleNumbers.addAll(higherLevelCall(article.kid));
			Collections.sort(articleNumbers);
			if (threadList.size()==0)
				threadList.add(articleNumbers);
			else {
				int index=0;
				while ((index<threadList.size()) && 
						(articleNumbers.get(0).getArticleNumberLong() > 
							threadList.get(index).get(0).getArticleNumberLong()))
					index++;
				threadList.add(index, articleNumbers);
			}
				
			article = article.next;
		}
//		printThreadList(root, threadList);
		return threadList;
	}

//	private static void printThreadList(Article root, List<List<Article>> threadList) {
//		for (List<Article>list: threadList) {
//			System.out.print(" [ ");
//			for (Article art: list)
//				System.out.print(art.getArticleNumber() + " ");
//			System.out.print("] ");
//		}
//		System.out.println();
//		System.out.println("-=-=-=-=-=-=-=-");
//		Article.printThread(root, 0);
//		System.out.println("-=-=-=-=-=-=-=-");
//	}

	@SuppressWarnings("deprecation")
	public static List<Article> higherLevelCall(Article article) {
		List<Article> articleNumbers = new ArrayList<Article>();
		if (article.getArticleNumber()>0)
			articleNumbers.add(article);
		if (article.kid != null)
			articleNumbers.addAll(higherLevelCall(article.kid));
		if (article.next != null)
			articleNumbers.addAll(higherLevelCall(article.next));
		return articleNumbers;
	}
	
}
