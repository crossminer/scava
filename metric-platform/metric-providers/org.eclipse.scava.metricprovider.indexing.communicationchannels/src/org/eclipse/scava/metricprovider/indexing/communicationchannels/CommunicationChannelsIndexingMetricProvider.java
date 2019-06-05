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
import java.util.List;

import org.eclipse.scava.index.indexer.Indexer;
import org.eclipse.scava.metricprovider.indexing.communicationchannels.document.ArticleDocument;
import org.eclipse.scava.metricprovider.indexing.communicationchannels.document.ForumPostDocument;
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
			if (communicationChannel instanceof Irc) return true;
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
		System.err.println("Indexing Communication Channels");
		CommunicationChannelProjectDelta communicationChannelProjectDelta = projectDelta.getCommunicationChannelDelta();

		for (CommunicationChannelDelta delta : communicationChannelProjectDelta.getCommunicationChannelSystemDeltas()) {

			CommunicationChannel communicationChannel = delta.getCommunicationChannel();

			if (communicationChannel instanceof NntpNewsGroup) {

				NntpNewsGroup newsGroup = (NntpNewsGroup) communicationChannel;
				String newsgroupName = newsGroup.getNewsGroupName();
				prepareArticle(project, projectDelta, delta, "newsgroup.article", newsgroupName);

			} else if (communicationChannel instanceof EclipseForum) {

				// forums are handled in a unquie way due to the way we want them stored in ES
				prepareForum(project, projectDelta, delta);

			} else if (communicationChannel instanceof Discussion) {
				// FIXME - how do we handle these? As they do not have a name defined in the
				// Discussion object
				String discussionName = projectDelta.getProject().getName();
				prepareArticle(project, projectDelta, delta, "discussion.post", discussionName);

			} else if (communicationChannel instanceof SympaMailingList) {

				SympaMailingList sympaMailingList = (SympaMailingList) communicationChannel;
				String mailinglistName = sympaMailingList.getMailingListName();
				prepareArticle(project, projectDelta, delta, "sympa.mail", mailinglistName);

			}else if (communicationChannel instanceof Irc) {
				 Irc ircChat = (Irc) communicationChannel;
				 String chatName = ircChat.getName();
				 prepareArticle(project, projectDelta, delta, "irc.message", chatName);

				// }else if (communicationChannel instanceof MailingList) {
				// MailingList mailingList = (MailingList) communicationChannel;
				// String mailinglistName = mailingList.getMailingListName();
				// prepareArticle(project, projectDelta, delta, "mbox.mail", mailinglistName);

			} else {

				System.out.println("this " + delta.getCommunicationChannel().getCommunicationChannelType()
						+ "of communication channel is currently not supported by the indexer");
			}

		}

	}

	/**
	 * Prepares a elements relating to Forums
	 * 
	 * @param project
	 * @param communicationChannelDelta
	 * @param projectDelta
	 */
	private void prepareForum(Project project, ProjectDelta projectDelta, CommunicationChannelDelta delta) {

		ObjectMapper mapper = new ObjectMapper();

		// Note - Forums are treated internally as Articles, but are indexed as 'posts

		for (CommunicationChannelArticle article : delta.getArticles()) {

			String documentType = "forum.post";
			EclipseForum eclipseForum = (EclipseForum) article.getCommunicationChannel();

			String indexName = Indexer.generateIndexName(
					delta.getCommunicationChannel().getCommunicationChannelType().toLowerCase(), documentType, NLP);

			String uniqueIdentifier = generateUniqueDocumentId(projectDelta, article.getArticleId(),
					delta.getCommunicationChannel().getCommunicationChannelType());

			String mapping = Mapping.getMapping(documentType);

			EnrichmentData enrichmentData = getEnrichmentData(article, project);

			ForumPostDocument enrichedDocument = enrichForumPostDocument(
					new ForumPostDocument(uniqueIdentifier, project.getName(), article.getText(), article.getUser(),
							article.getDate(), eclipseForum.getForum_id(), article.getMessageThreadId(),
							article.getArticleId(), article.getSubject(), eclipseForum.getForum_name()),
					enrichmentData);

			String document;

			try {

				document = mapper.writeValueAsString(enrichedDocument);
				Indexer.indexDocument(indexName, mapping, documentType, uniqueIdentifier, document);

			} catch (JsonProcessingException e) {
				logger.error("Error while indexing document: ", e);
				e.printStackTrace();
			}

		}

	}

	/**
	 * Prepares a elements relating to NewsGroups
	 * 
	 * @param project
	 * @param communicationChannelDelta
	 * @param projectDelta
	 */
	private void prepareArticle(Project project, ProjectDelta projectDelta, CommunicationChannelDelta delta,
			String docType, String collectionName) {

		ObjectMapper mapper = new ObjectMapper();

		for (CommunicationChannelArticle article : delta.getArticles()) { // Articles

			String documentType = docType;

			String name = collectionName;

			String indexName = Indexer.generateIndexName(
					delta.getCommunicationChannel().getCommunicationChannelType().toLowerCase(), documentType, NLP);

			String uniqueIdentifier = generateUniqueDocumentId(projectDelta, article.getArticleId(),
					delta.getCommunicationChannel().getCommunicationChannelType());

			String mapping = Mapping.getMapping(documentType);

			EnrichmentData enrichmentData = getEnrichmentData(article, project);

			ArticleDocument enrichedDocument = enrichNewsGroupDocument(
					new ArticleDocument(uniqueIdentifier, project.getName(), article.getText(), article.getUser(),
							article.getDate(), name, article.getSubject(), article.getMessageThreadId(),
							article.getArticleNumber(), article.getArticleId()),
					enrichmentData);

			String document;

			try {

				document = mapper.writeValueAsString(enrichedDocument);
				Indexer.indexDocument(indexName, mapping, documentType, uniqueIdentifier, document);

			} catch (JsonProcessingException e) {
				logger.error("Error while indexing document: ", e);
				e.printStackTrace();
			}

		}

	}

	/**
	 * This method returns an EnrichmentData object which contains additional fields
	 * and values from various metrics.
	 * 
	 * @param article
	 * @param project
	 * @return
	 */

	private EnrichmentData getEnrichmentData(CommunicationChannelArticle article, Project project) {

		EnrichmentData enrichmentData = new EnrichmentData();

		IndexPrepTransMetric indexPrepTransMetric = ((IndexPreparationTransMetricProvider) uses.get(0))
				.adapt(context.getProjectDB(project));

		for (String metricIdentifier : indexPrepTransMetric.getExecutedMetricProviders().first()
				.getMetricIdentifiers()) {

			switch (metricIdentifier) {

			// TODO - add Threads

			case "org.eclipse.scava.metricprovider.trans.plaintextprocessing.PlainTextProcessingTransMetricProvider":

				PlainTextProcessingTransMetric commChannelPlainTextData = new PlainTextProcessingTransMetricProvider()
						.adapt(context.getProjectDB(project));

					NewsgroupArticlePlainTextProcessing plainTextData = findCollection(commChannelPlainTextData,
							NewsgroupArticlePlainTextProcessing.class, commChannelPlainTextData.getNewsgroupArticles(),
							article);

					if (!plainTextData.getPlainText().isEmpty()) {

						String plaintext = String.join(" ", plainTextData.getPlainText());
						enrichmentData.setPlain_text(plaintext);

					}
				break;
			// EMOTION
			case "org.eclipse.scava.metricprovider.trans.emotionclassification.EmotionClassificationTransMetricProvider":

				EmotionClassificationTransMetric commChannelEmotionData = new EmotionClassificationTransMetricProvider()
						.adapt(context.getProjectDB(project));

					List<String> emotionData = findCollection(commChannelEmotionData,
							NewsgroupArticlesEmotionClassification.class, commChannelEmotionData.getNewsgroupArticles(),
							article).getEmotions();

					for (String dimension : emotionData) {

						enrichmentData.addEmotionalDimension(dimension);

					}

				break;

			// SENTIMENT
			case "org.eclipse.scava.metricprovider.trans.sentimentclassification.SentimentClassificationTransMetricProvider":

				SentimentClassificationTransMetric commChannelCommentsSentimentData = new SentimentClassificationTransMetricProvider()
						.adapt(context.getProjectDB(project));

					NewsgroupArticlesSentimentClassification sentimentData = findCollection(
							commChannelCommentsSentimentData, NewsgroupArticlesSentimentClassification.class,
							commChannelCommentsSentimentData.getNewsgroupArticles(), article);

					if (sentimentData != null) {

						enrichmentData.setSentiment(sentimentData.getPolarity());

					}

				break;

			// DETECTING CODE
			case "org.eclipse.scava.metricprovider.trans.detectingcode.DetectingCodeTransMetricProvider":

				DetectingCodeTransMetric commChannelDetectingCodeData = new DetectingCodeTransMetricProvider()
						.adapt(context.getProjectDB(project));

					NewsgroupArticleDetectingCode detectingcodeData = findCollection(commChannelDetectingCodeData,
							NewsgroupArticleDetectingCode.class, commChannelDetectingCodeData.getNewsgroupArticles(),
							article);

					if (detectingcodeData != null) {

						if (!detectingcodeData.getCode().isEmpty()) {

							enrichmentData.setCode(true);
							
						} else {
							
							enrichmentData.setCode(false);

						}
					}

				break;

			// REQUEST REPLY
			case "org.eclipse.scava.metricprovider.trans.requestreplyclassification.RequestReplyClassificationTransMetricProvider":
				RequestReplyClassificationTransMetric commChannelRequestReplyData = new RequestReplyClassificationTransMetricProvider()
						.adapt(context.getProjectDB(project));


					NewsgroupArticles requestReplyData = findCollection(commChannelRequestReplyData,
							NewsgroupArticles.class, commChannelRequestReplyData.getNewsgroupArticles(), article);

					if (requestReplyData!= null) {

						enrichmentData.setRequest_reply_classification(requestReplyData.getClassificationResult());

					}

				break;

			// Content Classification
			case "org.eclipse.scava.metricprovider.trans.newsgroups.contentclasses.ContentClassesTransMetricProvider":
				NewsgroupsContentClassesTransMetric commChannelContentClassificationData = new ContentClassesTransMetricProvider()
						.adapt(context.getProjectDB(project));


					ContentClass contentClassData = findCollection(commChannelContentClassificationData,
							ContentClass.class, commChannelContentClassificationData.getContentClasses(), article);

					if (contentClassData != null) {

						enrichmentData.setContent_class(contentClassData.getClassLabel());

					}

				break;
			}
		}

		return enrichmentData;

	}

	private ForumPostDocument enrichForumPostDocument(ForumPostDocument document, EnrichmentData enrichmentData) {

		// emotions
		if (enrichmentData.getEmotionalDimensions() != null) {
			for (String emotion : enrichmentData.getEmotionalDimensions()) {
				document.getEmotional_dimension().add(emotion);
			}
		}

		// plainText
		if (enrichmentData.getPlain_text() != null) {
			document.setPlain_text(enrichmentData.getPlain_text());
		}

		// detecting Code
		if (enrichmentData.getCode() != null) {
			document.setContains_code(enrichmentData.getCode());
		}

		// request Reply
		if (enrichmentData.getCode() != null) {
			document.setRequest_reply_classification(enrichmentData.getRequest_reply_classification());
		}

		// content class
		if (enrichmentData.getContent_class() != null) {
			document.setContent_class(enrichmentData.getContent_class());
		}

		// sentiment
		if (enrichmentData.getSentiment() != null) {
			document.setSentiment(enrichmentData.getSentiment());
		}
		return document;
	}

	private ArticleDocument enrichNewsGroupDocument(ArticleDocument document, EnrichmentData enrichmentData) {

		// emotions
		if (enrichmentData.getEmotionalDimensions() != null) {
			for (String emotion : enrichmentData.getEmotionalDimensions()) {
				document.getEmotional_dimension().add(emotion);
			}
		}

		// plainText
		if (enrichmentData.getPlain_text() != null) {
			document.setPlain_text(enrichmentData.getPlain_text());
		}

		// detecting Code
		if (enrichmentData.getCode() != null) {
			document.setContains_code(enrichmentData.getCode());
		}

		// request Reply
		if (enrichmentData.getCode() != null) {
			document.setRequest_reply_classification(enrichmentData.getRequest_reply_classification());
		}

		// content class
		if (enrichmentData.getContent_class() != null) {
			document.setContent_class(enrichmentData.getContent_class());
		}

		// sentiment
		if (enrichmentData.getSentiment() != null) {
			document.setSentiment(enrichmentData.getSentiment());
		}
		return document;
	}

	/**
	 * This method returns a unique Identifier based upon the SourceType, Project,
	 * Document Type and source ID;
	 * 
	 * @return String uid - a uniquely identifiable string.
	 */
	private String generateUniqueDocumentId(ProjectDelta projectDelta, String id, String bugTrackerType) {

		String projectName = projectDelta.getProject().getName();
		String uid = bugTrackerType + " " + projectName + " " + id;
		return uid;
	}

	/**
	 * This method finds a collection created by a metric provider relating to
	 * newsgroup articles
	 * 
	 * 
	 * @param db
	 * @param type
	 * @param collection
	 * @param article
	 * @return
	 */

	// TODO add support for IRC, Sympa, MailBox
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

}