/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.indexing.communicationchannels;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.scava.index.indexer.Indexer;
import org.eclipse.scava.metricprovider.indexing.communicationchannels.document.ArticleDocument;
import org.eclipse.scava.metricprovider.indexing.communicationchannels.document.DocumentAbstract;
import org.eclipse.scava.metricprovider.indexing.communicationchannels.document.ThreadDocument;
import org.eclipse.scava.metricprovider.indexing.communicationchannels.mapping.Mapping;
import org.eclipse.scava.metricprovider.trans.detectingcode.DetectingCodeTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.DetectingCodeTransMetric;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.NewsgroupArticleDetectingCode;
import org.eclipse.scava.metricprovider.trans.emotionclassification.EmotionClassificationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.emotionclassification.model.EmotionClassificationTransMetric;
import org.eclipse.scava.metricprovider.trans.emotionclassification.model.NewsgroupArticlesEmotionClassification;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.IndexPreparationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.model.IndexPrepTransMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.contentclasses.ContentClassesTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.newsgroups.contentclasses.model.ContentClass;
import org.eclipse.scava.metricprovider.trans.newsgroups.contentclasses.model.NewsgroupsContentClassesTransMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.ThreadsTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.NewsgroupsThreadsTransMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ThreadData;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.PlainTextProcessingTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.NewsgroupArticlePlainTextProcessing;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.PlainTextProcessingTransMetric;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.RequestReplyClassificationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.NewsgroupArticles;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.RequestReplyClassificationTransMetric;
import org.eclipse.scava.metricprovider.trans.sentimentclassification.SentimentClassificationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.sentimentclassification.model.NewsgroupArticlesSentimentClassification;
import org.eclipse.scava.metricprovider.trans.sentimentclassification.model.SentimentClassificationTransMetric;
import org.eclipse.scava.platform.AbstractIndexingMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelProjectDelta;
import org.eclipse.scava.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.eclipse.scava.platform.indexing.Indexing;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;
import org.eclipse.scava.repository.model.cc.irc.Irc;
import org.eclipse.scava.repository.model.cc.mbox.Mbox;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.cc.sympa.SympaMailingList;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoDB;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;

public class CommunicationChannelsIndexingMetricProvider extends AbstractIndexingMetricProvider {

	public final static String IDENTIFIER = CommunicationChannelsIndexingMetricProvider.class.getCanonicalName();
	public final static String SHORT_IDENTIFIER = "communication channels indexing metric";
	public final static String FRIENDLY_NAME = "communication channels indexer";
	public final static String DESCRIPTION = "This metric prepares and indexes documents realting to communication channels";

	protected MetricProviderContext context;
	protected List<IMetricProvider> uses;
	protected PlatformCommunicationChannelManager platformCommunicationChannelManager;
	
	
	private List<String> metricsToIndex;
	private PlainTextProcessingTransMetric commChannelPlainTextData;
	private EmotionClassificationTransMetric commChannelEmotionData;
	private SentimentClassificationTransMetric commChannelCommentsSentimentData;
	private DetectingCodeTransMetric commChannelDetectingCodeData;
	private RequestReplyClassificationTransMetric commChannelRequestReplyData;
	private NewsgroupsContentClassesTransMetric commChannelContentClassificationData;
	private NewsgroupsThreadsTransMetric commChannelThreadsData;
	
	private ThreadsByArticle threadsByArticle;
	
	public final static String NLP = "nlp";// knowledge type.

	protected OssmeterLogger logger;
	
	public CommunicationChannelsIndexingMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.indexing.communicationchannels");
	}
	
	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	}
	
	@Override
	public String getShortIdentifier() {

		return SHORT_IDENTIFIER;
	}

	@Override
	public String getFriendlyName() {

		return FRIENDLY_NAME;
	}

	@Override
	public String getSummaryInformation() {

		return DESCRIPTION;
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel : project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup)
				return true;
			if (communicationChannel instanceof EclipseForum)
				return true;
			if (communicationChannel instanceof Discussion)
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

		return Arrays.asList(IndexPreparationTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
		this.platformCommunicationChannelManager = context.getPlatformCommunicationChannelManager();

	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, Indexing db) {
		
		loadMetricsDB(project);
		if(metricsToIndex.size()>0)
		{
			String projectName = project.getName();
			String ccType;
			String collectionName;
			String documentType;
			
			CommunicationChannelProjectDelta communicationChannelProjectDelta = projectDelta.getCommunicationChannelDelta();
	
			for (CommunicationChannelDelta delta : communicationChannelProjectDelta.getCommunicationChannelSystemDeltas()) {
	
				CommunicationChannel communicationChannel = delta.getCommunicationChannel();
	
				if (communicationChannel instanceof NntpNewsGroup) {
					collectionName = ((NntpNewsGroup) communicationChannel).getNewsGroupName();
					ccType="newsgroup";
					documentType="article";
				} else if (communicationChannel instanceof EclipseForum) {
					collectionName = ((EclipseForum) communicationChannel).getForum_name();
					ccType="eclipse-forum";
					documentType="post";
				} else if (communicationChannel instanceof Discussion) {
					collectionName = communicationChannel.getUrl();
					ccType="discussion";
					documentType="post";
				} else if (communicationChannel instanceof SympaMailingList) {
					collectionName = ((SympaMailingList) communicationChannel).getMailingListName();
					ccType="sympa";
					documentType="mail";
				}else if (communicationChannel instanceof Irc) {
					collectionName=((Irc) communicationChannel).getName();
					ccType="irc";
					documentType="message";
				} else if (communicationChannel instanceof Mbox) {
					collectionName=((Mbox) communicationChannel).getMboxName();
					ccType="mbox";
					documentType="mail";
				} else {
					System.out.println("this " + delta.getCommunicationChannel().getCommunicationChannelType()
							+ "of communication channel is currently not supported by the indexer");
					continue;
				}
				if(metricsToIndex.contains("org.eclipse.scava.metricprovider.trans.newsgroups.threads.ThreadsTransMetricProvider"))
				{
					//We prevent indexing when there has not been any new articles, and the threads will be still the same
					if(delta.getArticles().size()>0)
					{
						for(ThreadData threadData :searchThreads(communicationChannel.getOSSMeterId()))
						{
							prepareThread(projectName, ccType, collectionName, threadData);
						}
					}
				}
				for (CommunicationChannelArticle article : delta.getArticles()) {
					prepareArticle(projectName, ccType, documentType, collectionName, article);
				}
			}
		}

	}
	
	private Iterable<ThreadData> searchThreads(String OSSMeterId)
	{
		Iterable<ThreadData> iterator = commChannelThreadsData.getThreads().find(
				ThreadData.NEWSGROUPNAME.eq(OSSMeterId)
				);
		return iterator;
	}
	
	private void prepareThread(String projectName, String ccType, String collectionName, ThreadData threadData)
	{
		String uid = generateUniqueDocumentId(projectName, ccType, collectionName, String.valueOf(threadData.getThreadId()));
		ThreadDocument td = new ThreadDocument(uid,
											projectName,
											collectionName,
											threadData.getThreadId(),
											threadData.getSubject());
		threadsByArticle = new ThreadsByArticle();
		for(long articleId : threadData.getArticleNumbers())
		{
			threadsByArticle.addThread(articleId, threadData.getThreadId());
		}
		
		indexing(ccType, "thread", uid, td);
	}
		
	private void prepareArticle(String projectName, String ccType, String documentType, String collectionName, CommunicationChannelArticle article)
	{
		String uid = generateUniqueDocumentId(projectName, ccType, collectionName, String.valueOf(article.getArticleNumber()));
		ArticleDocument ad = new ArticleDocument(uid,
												projectName,
												collectionName,
												article.getArticleNumber(),
												article.getSubject(),
												article.getText(),
												article.getUser(),
												article.getDate());
		enrichArticle(article, ad);
		indexing(ccType, documentType, uid, ad);
	}
	
	private void loadMetricsDB(Project project)
	{
		IndexPrepTransMetric indexPrepTransMetric = ((IndexPreparationTransMetricProvider) uses.get(0)).adapt(context.getProjectDB(project));
		metricsToIndex=indexPrepTransMetric.getExecutedMetricProviders().first().getMetricIdentifiers();
		for(String metricIdentifier : metricsToIndex)
		{
			switch (metricIdentifier)
			{
				case "org.eclipse.scava.metricprovider.trans.plaintextprocessing.PlainTextProcessingTransMetricProvider":
				{
					commChannelPlainTextData = new PlainTextProcessingTransMetricProvider().adapt(context.getProjectDB(project));
					break;
				}
				case "org.eclipse.scava.metricprovider.trans.emotionclassification.EmotionClassificationTransMetricProvider":
				{
					commChannelEmotionData = new EmotionClassificationTransMetricProvider().adapt(context.getProjectDB(project));
					break;
				}
				case "org.eclipse.scava.metricprovider.trans.sentimentclassification.SentimentClassificationTransMetricProvider":
				{
					commChannelCommentsSentimentData = new SentimentClassificationTransMetricProvider().adapt(context.getProjectDB(project));
					break;
				}
				case "org.eclipse.scava.metricprovider.trans.detectingcode.DetectingCodeTransMetricProvider":
				{
					commChannelDetectingCodeData = new DetectingCodeTransMetricProvider().adapt(context.getProjectDB(project));
					break;
				}
				case "org.eclipse.scava.metricprovider.trans.requestreplyclassification.RequestReplyClassificationTransMetricProvider":
				{
					commChannelRequestReplyData = new RequestReplyClassificationTransMetricProvider().adapt(context.getProjectDB(project));
					break;
				}
				case "org.eclipse.scava.metricprovider.trans.newsgroups.contentclasses.ContentClassesTransMetricProvider":
				{
					commChannelContentClassificationData = new ContentClassesTransMetricProvider().adapt(context.getProjectDB(project));
					break;
				}
				case "org.eclipse.scava.metricprovider.trans.newsgroups.threads.ThreadsTransMetricProvider":
				{
					commChannelThreadsData = new ThreadsTransMetricProvider().adapt(context.getProjectDB(project));
					break;
				}
				
			}
		}
	}
	
	private void enrichArticle(CommunicationChannelArticle article, ArticleDocument articleDocument)
	{
		for (String metricIdentifier : metricsToIndex)
		{
			switch (metricIdentifier)
			{
				case "org.eclipse.scava.metricprovider.trans.plaintextprocessing.PlainTextProcessingTransMetricProvider":
				{
					NewsgroupArticlePlainTextProcessing plainTextData = findCollection(commChannelPlainTextData,
							NewsgroupArticlePlainTextProcessing.class, commChannelPlainTextData.getNewsgroupArticles(),
							article);

					if (!plainTextData.getPlainText().isEmpty()) {
						String plaintext = String.join(" ", plainTextData.getPlainText());
						articleDocument.setPlain_text(plaintext);
					}
					break;
				}
				case "org.eclipse.scava.metricprovider.trans.newsgroups.threads.ThreadsTransMetricProvider":
				{
					for(Integer threadId : threadsByArticle.getThreads(article.getArticleNumber()))
					{
						if(threadId!=null)
							articleDocument.addThread_id(threadId);
					}	
					break;
				}
				// EMOTION
				case "org.eclipse.scava.metricprovider.trans.emotionclassification.EmotionClassificationTransMetricProvider":
				{
	
					List<String> emotionData = findCollection(commChannelEmotionData,
							NewsgroupArticlesEmotionClassification.class, commChannelEmotionData.getNewsgroupArticles(),
							article).getEmotions();

					for (String dimension : emotionData)
						articleDocument.addEmotional_dimension(dimension);
					break;
				}
				// SENTIMENT
				case "org.eclipse.scava.metricprovider.trans.sentimentclassification.SentimentClassificationTransMetricProvider":
				{
					NewsgroupArticlesSentimentClassification sentimentData = findCollection(
							commChannelCommentsSentimentData, NewsgroupArticlesSentimentClassification.class,
							commChannelCommentsSentimentData.getNewsgroupArticles(), article);

					if (sentimentData != null)
						articleDocument.setSentiment(sentimentData.getPolarity());
					break;
				}
				// DETECTING CODE
				case "org.eclipse.scava.metricprovider.trans.detectingcode.DetectingCodeTransMetricProvider":
				{
					NewsgroupArticleDetectingCode detectingcodeData = findCollection(commChannelDetectingCodeData,
							NewsgroupArticleDetectingCode.class, commChannelDetectingCodeData.getNewsgroupArticles(),
							article);

					if (detectingcodeData != null) {
						if (!detectingcodeData.getCode().isEmpty())
							articleDocument.setContains_code(true);
						else
							articleDocument.setContains_code(false);
					}
	
					break;
				}
				// REQUEST REPLY
				case "org.eclipse.scava.metricprovider.trans.requestreplyclassification.RequestReplyClassificationTransMetricProvider":
				{
					NewsgroupArticles requestReplyData = findCollection(commChannelRequestReplyData,
							NewsgroupArticles.class, commChannelRequestReplyData.getNewsgroupArticles(), article);

					if (requestReplyData!= null)
						articleDocument.setRequest_reply_classification(requestReplyData.getClassificationResult());
					break;
				}
				// Content Classification
				case "org.eclipse.scava.metricprovider.trans.newsgroups.contentclasses.ContentClassesTransMetricProvider":
				{
					ContentClass contentClassData = findCollection(commChannelContentClassificationData,
								ContentClass.class, commChannelContentClassificationData.getContentClasses(), article);
	
					if (contentClassData != null)
						articleDocument.setContent_class(contentClassData.getClassLabel());
	
					break;
				}
			}
		}
	}
	
	private void indexing(String ccType, String documentType, String uid, DocumentAbstract document)
	{
		try {
			Indexer.indexDocument(Indexer.generateIndexName(ccType, documentType, NLP),
					Mapping.getMapping(documentType),
					documentType,
					uid,
					new ObjectMapper().writeValueAsString(document));
		} catch (JsonProcessingException e) {
			logger.error("Error while processing json:", e);
			e.printStackTrace();
		}
	}

	/**
	 * This method returns a unique Identifier based upon the SourceType, Project,
	 * Document Type and source ID;
	 * @param string 
	 * 
	 * @return String uid - a uniquely identifiable string.
	 */
	private String generateUniqueDocumentId(String projectName, String type, String mainTypeId, String subTypeId) {
		return type + " " + projectName + " " + mainTypeId+ " "+ subTypeId;
	}

	private <T extends Pongo> T findCollection(PongoDB db, Class<T> type, PongoCollection<T> collection,
			CommunicationChannelArticle article) {

		T output = null;

		Iterable<T> iterator = collection.find(getStringQueryProducer(type, output, "NEWSGROUPNAME").eq(article.getCommunicationChannel().getOSSMeterId()),
				getNumericalQueryProducer(type, output, "ARTICLENUMBER").eq(article.getArticleNumber()));

		for (T t : iterator) {
			output = t;
		}

		return output;
	}
	
	private <T extends Pongo> StringQueryProducer getStringQueryProducer(Class<T> type, T t, String field) {

		try {

			return (StringQueryProducer) type.getDeclaredField(field).get(t);

		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			logger.error("Error while searching data in MongoBD:", e);
			e.printStackTrace();
		}
		return null;
	}

	private <T extends Pongo> NumericalQueryProducer getNumericalQueryProducer(Class<T> type, T t, String field) {

		try {

			return (NumericalQueryProducer) type.getDeclaredField(field).get(t);

		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			logger.error("Error while searching data in MongoBD:", e);
			e.printStackTrace();
		}
		return null;
	}
	
	private class ThreadsByArticle
	{
		HashMap<Long, Set<Integer>> threadsByArticle;
		
		public ThreadsByArticle() {
			threadsByArticle = new HashMap<Long, Set<Integer>>(); 
		}
		
		public void addThread(long articleId, int threadId)
		{
			Set<Integer> threads;
			if(!threadsByArticle.containsKey(articleId))
				threads = new HashSet<Integer>();
			else
				threads=threadsByArticle.get(articleId);
			if(!threads.contains(threadId))
			{
				threads.add(threadId);
				threadsByArticle.put(articleId, threads);
			}
		}
		
		public Set<Integer> getThreads(long articleId)
		{
			if(threadsByArticle.containsKey(articleId))
				return threadsByArticle.get(articleId);
			else
				return null;
		}

		
	}

}