/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.scava.contentclassifier.opennlptartarus.libsvm.ClassificationInstance;
import org.eclipse.scava.contentclassifier.opennlptartarus.libsvm.Classifier;
import org.eclipse.scava.metricprovider.trans.detectingcode.DetectingCodeTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.DetectingCodeTransMetric;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.NewsgroupArticleDetectingCode;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.IndexPreparationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.model.IndexPrepTransMetric;
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
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;
import org.eclipse.scava.repository.model.cc.irc.Irc;
import org.eclipse.scava.repository.model.cc.mbox.Mbox;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.cc.sympa.SympaMailingList;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.mongodb.DB;

public class ThreadsTransMetricProvider implements ITransientMetricProvider<NewsgroupsThreadsTransMetric> {

	protected PlatformCommunicationChannelManager communicationChannelManager;

	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;

	@Override
	public String getIdentifier() {
		return ThreadsTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel : project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup)
				return true;
			if (communicationChannel instanceof Discussion)
				return true;
			if (communicationChannel instanceof EclipseForum)
				return true;
			if (communicationChannel instanceof SympaMailingList) 
				return true;
			if (communicationChannel instanceof Irc) 
				return true;
			if (communicationChannel instanceof Mbox)
				return true;
		}
		return false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(DetectingCodeTransMetricProvider.class.getCanonicalName(), IndexPreparationTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
	}

	@Override
	public NewsgroupsThreadsTransMetric adapt(DB db) {
		return new NewsgroupsThreadsTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, NewsgroupsThreadsTransMetric db) {

		if (uses.size() != getIdentifiersOfUses().size()) {
			System.err
					.println("Metric: " + getIdentifier() + " failed to retrieve " + "the transient metrics it needs!");
			System.exit(-1);
		}
		
		//This is for indexing
		IndexPrepTransMetric indexPrepTransMetric = ((IndexPreparationTransMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));	
		indexPrepTransMetric.getExecutedMetricProviders().first().getMetricIdentifiers().add(getIdentifier());
		indexPrepTransMetric.sync();

		DetectingCodeTransMetric detectingCodeMetric = ((DetectingCodeTransMetricProvider) uses.get(0))
				.adapt(context.getProjectDB(project));
		
		// Threading preparation begin
		Iterable<CurrentDate> currentDateIt = db.getDate();
		CurrentDate currentDate = null;
		for (CurrentDate cd : currentDateIt)
			currentDate = cd;
		if (currentDate != null)
			currentDate.setDate(projectDelta.getDate().toString());
		else {
			currentDate = new CurrentDate();
			currentDate.setDate(projectDelta.getDate().toString());
			db.getDate().add(currentDate);
		}

		for (NewsgroupData newsgroupData : db.getNewsgroups())
			newsgroupData.setPreviousThreads(newsgroupData.getThreads());

		CommunicationChannelProjectDelta delta = projectDelta.getCommunicationChannelDelta();

		Map<String, Set<String>> articleIdsPerNewsgroup = new HashMap<String, Set<String>>();
		Map<String, Set<Integer>> threadsPerNewsgroup = new HashMap<String, Set<Integer>>();

		// Threading preparation end
		
//		CommunicationChannelProjectDelta ccpDelta = projectDelta.getCommunicationChannelDelta();
		for (CommunicationChannelDelta communicationChannelDelta : delta.getCommunicationChannelSystemDeltas()) {
			CommunicationChannel communicationChannel = communicationChannelDelta.getCommunicationChannel();
			
			//Process for forums and irc
			if (communicationChannel instanceof EclipseForum || communicationChannel instanceof Irc) {

//-------------------------------------------------------------------------
	
				if (communicationChannelDelta.getArticles().size() > 0) {
					String ossmeterID = communicationChannel.getOSSMeterId();
					
//					I am retrieving the threads and the articles each of them contains from the db.
					Map<Integer, Set<String>> articleIdsPerThread = new HashMap<Integer, Set<String>>();
									
					for (ThreadData threadData : db.getThreads()) {
						int threadId = threadData.getThreadId();
						for (ArticleData articleData : threadData.getArticles()) {
							if (articleData.getNewsgroupName().equals(ossmeterID)) {
								Set<String> articleIds = null;
								if (articleIdsPerThread.containsKey(threadId))
									articleIds = articleIdsPerThread.get(threadId);
								else {
									articleIds = new HashSet<String>();
									articleIdsPerThread.put(threadId, articleIds);
								}
								articleIds.add(articleData.getArticleId());
							}
						}
					}

					
					HashMap<Integer,List<CommunicationChannelArticle>> newArticles = new HashMap<Integer,List<CommunicationChannelArticle>>();
					HashMap<Integer,String> subjects = new HashMap<Integer,String>();
					Classifier classifier = new Classifier();
					Map<String, ClassificationInstance> instanceIndex = new HashMap<String, ClassificationInstance>();
					
					
					for (CommunicationChannelArticle deltaArticle : communicationChannelDelta.getArticles()) {
						int threadId = Integer.parseInt(deltaArticle.getMessageThreadId());
						Boolean articleExists = false;
						if (articleIdsPerThread.containsKey(threadId) && 
							articleIdsPerThread.get(threadId).contains(deltaArticle.getArticleId()))
							articleExists = true;
						if (!articleExists) {
							if(!newArticles.containsKey(threadId))
								newArticles.put(threadId, new ArrayList<CommunicationChannelArticle>());
							newArticles.get(threadId).add(deltaArticle);
							ClassificationInstance instance = prepareClassificationInstance(ossmeterID, deltaArticle, detectingCodeMetric);
							int positionInThread = 1;
							if (articleIdsPerThread.containsKey(threadId))
								positionInThread += articleIdsPerThread.get(threadId).size();
							if(positionInThread==1)
								subjects.put(threadId, deltaArticle.getSubject());
							instance.setPositionFromThreadBeginning(positionInThread);
							instanceIndex.put(instance.getArticleId(), instance);
							classifier.add(instance);
						}
					}			
					
					classifier.classify();
					
					
					for(Integer threadToProcess : newArticles.keySet())
					{
						ThreadData threadData = getThreaData(db, threadToProcess);
						if(threadData==null) {
							threadData = new ThreadData();
							threadData.setThreadId(threadToProcess);
							threadData.setNewsgroupName(ossmeterID);
							threadData.setSubject(subjects.get(threadToProcess));
						}
						for (CommunicationChannelArticle newArticle : newArticles.get(threadToProcess)) {// Thread data
							
							threadData.getArticlesId().add(newArticle.getArticleId());
							threadData.getArticles().add(prepareArticleData(newArticle, ossmeterID,  
									classifier, instanceIndex));

							

							if (threadsPerNewsgroup.containsKey(ossmeterID))
								threadsPerNewsgroup.get(ossmeterID).add(threadToProcess);
							else {
								Set<Integer> threadSet = new HashSet<Integer>();
								threadSet.add(threadToProcess);
								threadsPerNewsgroup.put(ossmeterID, threadSet);
							}
							
						}
						db.getThreads().add(threadData);
						
					}
					
						
					db.sync();
				}

				db.sync();

				// updates existing threads with new data (OSSid == ossmeterID
				for (String OSSid : threadsPerNewsgroup.keySet()) {
					Iterable<NewsgroupData> newsgroupDataIt = db.getNewsgroups().find(NewsgroupData.NEWSGROUPNAME.eq(OSSid));
					NewsgroupData newsgroupData = null;
					for (NewsgroupData ngd : newsgroupDataIt)
						newsgroupData = ngd;
					if (newsgroupData != null) {
						newsgroupData.setThreads(threadsPerNewsgroup.get(OSSid).size());
					} else {
						newsgroupData = new NewsgroupData();
						newsgroupData.setNewsgroupName(OSSid);
						newsgroupData.setPreviousThreads(0);
						newsgroupData.setThreads(threadsPerNewsgroup.get(OSSid).size());
						db.getNewsgroups().add(newsgroupData);
					}
				}
				db.sync();
//-------------------------------------------------------------------------
	
			}

			else  {

				if (communicationChannelDelta.getArticles().size() > 0) {
					String ossmeterID = communicationChannel.getOSSMeterId();
					

					Map<String, String> previousClassAssignments = new HashMap<String, String>();
									
					List<Article> articles = new ArrayList<Article>();
					for (ThreadData threadData : db.getThreads()) {
						for (ArticleData articleData : threadData.getArticles()) {
							if (articleData.getNewsgroupName().equals(ossmeterID)) {
								previousClassAssignments.put(articleData.getArticleId(),
										articleData.getContentClass());
								articles.add(prepareArticle(articleData));
								Set<String> articleIds = null;
								if (articleIdsPerNewsgroup.containsKey(articleData.getNewsgroupName()))
									articleIds = articleIdsPerNewsgroup.get(articleData.getNewsgroupName());
								else {
									articleIds = new HashSet<String>();
									articleIdsPerNewsgroup.put(articleData.getNewsgroupName(), articleIds);
								}
								articleIds.add(articleData.getArticleId());
							}
						}
					}

					Map<String, ClassificationInstance> instanceIndex = new HashMap<String, ClassificationInstance>();
					for (CommunicationChannelArticle deltaArticle : communicationChannelDelta.getArticles()) {

						Boolean articleExists = false;
						if (articleIdsPerNewsgroup.containsKey(ossmeterID) && articleIdsPerNewsgroup
								.get(ossmeterID).contains(deltaArticle.getArticleId()))
							articleExists = true;

						if (!articleExists) {
							articles.add(prepareArticle(deltaArticle));
							ClassificationInstance instance = prepareClassificationInstance(ossmeterID,
									deltaArticle, detectingCodeMetric);
							instanceIndex.put(instance.getArticleId(), instance);
						}
					}

					System.out.println("Building message thread tree... (" + articles.size() + ")");
					Threader threader = new Threader();
					Article root = (Article) threader.thread(articles);
					List<List<Article>> articleList = zeroLevelCall(root);

					db.getThreads().getDbCollection().drop();
					db.sync();

					Classifier classifier = new Classifier();
					for (List<Article> list : articleList) {
						int positionInThread = 0;
						for (Article article : list) {
							positionInThread++;
							if (instanceIndex.containsKey(article.getArticleId())) {
								ClassificationInstance instance = instanceIndex.get(article.getArticleId());
								instance.setPositionFromThreadBeginning(positionInThread);
								// instance.setPositionFromThreadEnd(list.size()+1-positionInThread);
								classifier.add(instance);
							}
						}
					}
					classifier.classify();
					int index = 0;
					for (List<Article> list : articleList) {// Thread data
						index++;

						ThreadData threadData = new ThreadData();
						threadData.setThreadId(index);
						threadData.setNewsgroupName(ossmeterID);
						threadData.setSubject(list.get(0).getSubject());
						for (Article article : list) {
							threadData.getArticlesId().add(article.getArticleId());
							threadData.getArticles().add(prepareArticleData(article, ossmeterID,
									classifier, previousClassAssignments, instanceIndex));

							if (threadsPerNewsgroup.containsKey(ossmeterID))
								threadsPerNewsgroup.get(ossmeterID).add(index);
							else {
								Set<Integer> threadSet = new HashSet<Integer>();
								threadSet.add(index);
								threadsPerNewsgroup.put(ossmeterID, threadSet);
							}
						}
						db.getThreads().add(threadData);
					}
					db.sync();
				}

				db.sync();

				// updates existing threads with new data
				for (String OSSid : threadsPerNewsgroup.keySet()) {
					Iterable<NewsgroupData> newsgroupDataIt = db.getNewsgroups().find(NewsgroupData.NEWSGROUPNAME.eq(OSSid));
					NewsgroupData newsgroupData = null;
					for (NewsgroupData ngd : newsgroupDataIt)
						newsgroupData = ngd;
					if (newsgroupData != null) {
						newsgroupData.setThreads(threadsPerNewsgroup.get(OSSid).size());
					} else {
						newsgroupData = new NewsgroupData();
						newsgroupData.setNewsgroupName(OSSid);
						newsgroupData.setPreviousThreads(0);
						newsgroupData.setThreads(threadsPerNewsgroup.get(OSSid).size());
						db.getNewsgroups().add(newsgroupData);
					}
				}
				db.sync();

			} 
		}
		
		

	}
	
	private ThreadData getThreaData (NewsgroupsThreadsTransMetric db, int threadId)
	{
		ThreadData threadData=null;
		Iterable<ThreadData> threadDataIt = db.getThreads().find(ThreadData.THREADID.eq(threadId));
		for(ThreadData td : threadDataIt)
			threadData=td;
		return threadData;
	}


	private String getNaturalLanguage(CommunicationChannelArticle article, DetectingCodeTransMetric db, 
			String newsgroupName) {
		NewsgroupArticleDetectingCode newsgroupArticleInDetectionCode = null;
		Iterable<NewsgroupArticleDetectingCode> newsgroupArticleIt = db.getNewsgroupArticles().find(
				NewsgroupArticleDetectingCode.NEWSGROUPNAME.eq(newsgroupName),
				NewsgroupArticleDetectingCode.ARTICLEID.eq(article.getArticleId()));
		for (NewsgroupArticleDetectingCode nadc : newsgroupArticleIt) {
			newsgroupArticleInDetectionCode = nadc;
		}
		if (newsgroupArticleInDetectionCode.getNaturalLanguage() != null)
			return newsgroupArticleInDetectionCode.getNaturalLanguage();
		else
			return "";
	}

	private ClassificationInstance prepareClassificationInstance(String ossmeterID,
			CommunicationChannelArticle article, DetectingCodeTransMetric db) {
		ClassificationInstance instance = new ClassificationInstance();
		instance.setArticleId(article.getArticleId());
		instance.setNewsgroupName(ossmeterID);
		instance.setSubject(article.getSubject());
		instance.setText(getNaturalLanguage(article, db, ossmeterID));
		return instance;
	}

	private Article prepareArticle(CommunicationChannelArticle deltaArticle) {
		Article article = new Article();
		article.setArticleId(deltaArticle.getArticleId());
		article.setArticleNumber(deltaArticle.getArticleNumber());
		article.setDate(deltaArticle.getDate().toString());
		article.setFrom(deltaArticle.getUser());
		article.setSubject(deltaArticle.getSubject());
		for (String reference : deltaArticle.getReferences())
			article.addReference(reference);
		return article;
	}

	private ArticleData prepareArticleData(Article article, String ossmeterId, Classifier classifier,
			Map<String, String> previousClassAssignments, Map<String, ClassificationInstance> instanceIndex) {
		ArticleData articleData = new ArticleData();
		articleData.setNewsgroupName(ossmeterId);
		articleData.setArticleId(article.getArticleId());
		articleData.setArticleNumber(article.getArticleNumberLong());
		articleData.setDate(article.getDate());
		articleData.setFrom(article.getFrom());
		articleData.setSubject(article.getSubject());
		String references = "";
		for (String reference : article.getReferences())
			references += " " + reference;
		articleData.setReferences(references.trim());
		if (previousClassAssignments.containsKey(article.getArticleId())) {
			articleData.setContentClass(previousClassAssignments.get(article.getArticleId()));
		} else {
			articleData.setContentClass(
					classifier.getClassificationResult(instanceIndex.get(article.getArticleId())));
		}
		return articleData;
	}

	private ArticleData prepareArticleData(CommunicationChannelArticle article, String ossmeterID, Classifier classifier,
			Map<String, ClassificationInstance> instanceIndex) {
		ArticleData articleData = new ArticleData();
		articleData.setNewsgroupName(ossmeterID);
		articleData.setArticleId(article.getArticleId());
		articleData.setArticleNumber(article.getArticleNumber());
		articleData.setDate(article.getDate().toString());
		articleData.setFrom(article.getUser());
		articleData.setSubject(article.getSubject());
		String references = "";
		if (article.getReferences() != null) {
			for (String reference : article.getReferences())
				references += " " + reference;
		}
		articleData.setReferences(references.trim());
		articleData.setContentClass(classifier.getClassificationResult(instanceIndex.get(article.getArticleId())));
		return articleData;
	}

	private Article prepareArticle(ArticleData articleData) {
		Article article = new Article();
		article.setArticleId(articleData.getArticleId());
		article.setArticleNumber(articleData.getArticleNumber());
		article.setDate(articleData.getDate());
		article.setFrom(articleData.getFrom());
		article.setSubject(articleData.getSubject());
		for (String reference : articleData.getReferences().split(" "))
			article.addReference(reference);
		return article;
	}
	

	@Override
	public String getShortIdentifier() {
		return "trans.newsgroups.threads";
	}

	@Override
	public String getFriendlyName() {
		return "Assigns newsgroup articles to threads";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric holds information for assigning newsgroup articles to threads. "
				+ "The threading algorithm is executed from scratch everytime.";
	}

	public static List<List<Article>> zeroLevelCall(Article article) {
		// Article root = article;
		List<List<Article>> threadList = new ArrayList<List<Article>>();
		while (article != null) {
			List<Article> articleNumbers = new ArrayList<Article>();
			if (article.getArticleNumberLong() > 0)
				articleNumbers.add(article);
			if (article.kid != null)
				articleNumbers.addAll(higherLevelCall(article.kid));
			Collections.sort(articleNumbers);
			if (threadList.size() == 0)
				threadList.add(articleNumbers);
			else {
				int index = 0;
				while ((index < threadList.size()) && (articleNumbers.get(0).getArticleNumberLong() > threadList
						.get(index).get(0).getArticleNumberLong()))
					index++;
				threadList.add(index, articleNumbers);
			}

			article = article.next;
		}
		// printThreadList(root, threadList);
		return threadList;
	}

	public static List<Article> higherLevelCall(Article article) {
		List<Article> articleNumbers = new ArrayList<Article>();
		if (article.getArticleNumberLong() > 0)
			articleNumbers.add(article);
		if (article.kid != null)
			articleNumbers.addAll(higherLevelCall(article.kid));
		if (article.next != null)
			articleNumbers.addAll(higherLevelCall(article.next));
		return articleNumbers;
	}

}
