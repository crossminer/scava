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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.index.indexer.Indexer;
import org.eclipse.scava.metricprovider.indexing.communicationchannels.document.ArticleDocument;
import org.eclipse.scava.metricprovider.indexing.communicationchannels.document.ForumPostDocument;
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
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
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
	private Indexer indexer;

	@Override
	public String getShortIdentifier() {

		return IDENTIFIER;
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
			if (communicationChannel instanceof Discussion)
				return true;
			if (communicationChannel instanceof EclipseForum)
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
		System.err.println("Indexing Communication Channels");
		CommunicationChannelProjectDelta communicationChannelProjectDelta = projectDelta.getCommunicationChannelDelta();

		indexer = new Indexer();

		for (CommunicationChannelDelta delta : communicationChannelProjectDelta.getCommunicationChannelSystemDeltas()) {

			CommunicationChannel communicationChannel = delta.getCommunicationChannel();

			if (communicationChannel instanceof NntpNewsGroup) {
				prepareNewsGroup(project, projectDelta, delta);

			} else if (communicationChannel instanceof EclipseForum) {
				prepareForum(project, projectDelta, delta);

			} else if (communicationChannel instanceof Discussion) {
				//FIXME - how do we handle these?
				//prepareDiscussion(project, projectDelta, delta);

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
			String forumName = eclipseForum.getForum_name();
			

			String indexName = Indexer.generateIndexName(
					delta.getCommunicationChannel().getCommunicationChannelType().toLowerCase(), documentType, NLP);

			String uniqueIdentifier = generateUniqueDocumentId(projectDelta, article.getArticleId(),
					delta.getCommunicationChannel().getCommunicationChannelType());

			String mapping = loadMapping(documentType, NLP);

			EnrichmentData enrichmentData = getEnrichmentData(article, project);

			ForumPostDocument enrichedDocument = 
					enrichForumPostDocument(new ForumPostDocument(uniqueIdentifier,
							project.getName(),
							article.getText(),
							article.getUser(),
							article.getDate(),
							eclipseForum.getForum_id(),
							article.getMessageThreadId(),
							article.getArticleId(),
							article.getSubject(),
							eclipseForum.getForum_name()), enrichmentData);

			String document;

			try {
			
				document = mapper.writeValueAsString(enrichedDocument);
				indexer.indexDocument(indexName, mapping, documentType, uniqueIdentifier, document);
			
			} catch (JsonProcessingException e) {

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
	private void prepareNewsGroup(Project project, ProjectDelta projectDelta, CommunicationChannelDelta delta) {

		ObjectMapper mapper = new ObjectMapper();

		for (CommunicationChannelArticle article : delta.getArticles()) { // Articles

			String documentType = "newsgroup.article";
			NntpNewsGroup newsGroup = (NntpNewsGroup) article.getCommunicationChannel();
			String newsGroupName = newsGroup.getNewsGroupName();

			String indexName = Indexer.generateIndexName(
					delta.getCommunicationChannel().getCommunicationChannelType().toLowerCase(), documentType, NLP);

			String uniqueIdentifier = generateUniqueDocumentId(projectDelta, article.getArticleId(),
					delta.getCommunicationChannel().getCommunicationChannelType());

			String mapping = loadMapping(documentType, NLP);

			EnrichmentData enrichmentData = getEnrichmentData(article, project);

			ArticleDocument enrichedDocument = enrichNewsGroupDocument(
					new ArticleDocument(uniqueIdentifier, project.getName(), article.getText(), article.getUser(),
							article.getDate(), newsGroupName, article.getSubject(), article.getMessageThreadId(),
							article.getArticleNumber(), article.getArticleId()),
					enrichmentData);

			String document;

			try {
				
				document = mapper.writeValueAsString(enrichedDocument);
				indexer.indexDocument(indexName, mapping, documentType, uniqueIdentifier, document);
			
			} catch (JsonProcessingException e) {

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
			
			//TODO - add Topics
			//TODO - add Threads

			case "org.eclipse.scava.metricprovider.trans.plaintextprocessing.PlainTextProcessingTransMetricProvider":

				PlainTextProcessingTransMetric commChannelPlainTextData = new PlainTextProcessingTransMetricProvider()
						.adapt(context.getProjectDB(project));

				try {
					NewsgroupArticlePlainTextProcessing plainTextData = findCollection(commChannelPlainTextData,
							NewsgroupArticlePlainTextProcessing.class, commChannelPlainTextData.getNewsgroupArticles(),
							article);

					if (!(plainTextData.getPlainText().equals(null))) {

						String plaintext = String.join(" ", plainTextData.getPlainText());
						enrichmentData.setPlain_text(plaintext);

					}

				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
						| NoSuchMethodException | InvocationTargetException e) {

					e.printStackTrace();
				}

				// EMOTION
			case "org.eclipse.scava.metricprovider.trans.emotionclassification.EmotionClassificationTransMetricProvider":

				EmotionClassificationTransMetric commChannelEmotionData = new EmotionClassificationTransMetricProvider()
						.adapt(context.getProjectDB(project));

				try {
					List<String> emotionData = findCollection(commChannelEmotionData,
							NewsgroupArticlesEmotionClassification.class, commChannelEmotionData.getNewsgroupArticles(),
							article).getEmotions();

					if (emotionData.equals(null)) {

					} else if (!(emotionData.isEmpty())) {

						for (String dimension : emotionData) {

							enrichmentData.addEmotionalDimension(dimension);

						}
					}

				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
						| NoSuchMethodException | InvocationTargetException e) {

					e.printStackTrace();
				}

				break;

			// SENTIMENT
			case "org.eclipse.scava.metricprovider.trans.sentimentclassification.SentimentClassificationTransMetricProvider":

				SentimentClassificationTransMetric commChannelCommentsSentimentData = new SentimentClassificationTransMetricProvider()
						.adapt(context.getProjectDB(project));

				try {
					NewsgroupArticlesSentimentClassification sentimentData = findCollection(
							commChannelCommentsSentimentData, NewsgroupArticlesSentimentClassification.class,
							commChannelCommentsSentimentData.getNewsgroupArticles(), article);

					if (!(sentimentData.equals(null))) {

						enrichmentData.setSentiment(sentimentData.getPolarity());

					}

				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
						| NoSuchMethodException | InvocationTargetException e) {

					e.printStackTrace();
				}

				break;

			// DETECTING CODE
			case "org.eclipse.scava.metricprovider.trans.detectingcode.DetectingCodeTransMetricProvider":

				DetectingCodeTransMetric commChannelDetectingCodeData = new DetectingCodeTransMetricProvider()
						.adapt(context.getProjectDB(project));

				try {

					NewsgroupArticleDetectingCode detectingcodeData = findCollection(commChannelDetectingCodeData,
							NewsgroupArticleDetectingCode.class, commChannelDetectingCodeData.getNewsgroupArticles(),
							article);
					if (!detectingcodeData.getCode().equals(null)) {

						if (detectingcodeData.getCode().equals("[]")) {

							enrichmentData.setCode(false);

						} else {

							enrichmentData.setCode(true);

						}
					}
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
						| NoSuchMethodException | InvocationTargetException e) {

					e.printStackTrace();
				}

				break;

			// REQUEST REPLY
			case "org.eclipse.scava.metricprovider.trans.requestreplyclassification.RequestReplyClassificationTransMetricProvider":
				RequestReplyClassificationTransMetric commChannelRequestReplyData = new RequestReplyClassificationTransMetricProvider()
						.adapt(context.getProjectDB(project));

				try {

					NewsgroupArticles requestReplyData = findCollection(commChannelRequestReplyData,
							NewsgroupArticles.class, commChannelRequestReplyData.getNewsgroupArticles(), article);

					if (!(requestReplyData.equals(null))) {

						enrichmentData.setRequest_reply_classification(requestReplyData.getClassificationResult());

					}

				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
						| NoSuchMethodException | InvocationTargetException e) {

					e.printStackTrace();
				}
				break;

			// Content Classification
			case "org.eclipse.scava.metricprovider.trans.newsgroups.contentclasses.ContentClassesTransMetricProvider":
				NewsgroupsContentClassesTransMetric commChannelContentClassificationData = new ContentClassesTransMetricProvider()
						.adapt(context.getProjectDB(project));

				try {

					ContentClass contentClassData = findCollection(commChannelContentClassificationData,
							ContentClass.class, commChannelContentClassificationData.getContentClasses(), article);

					if (!(contentClassData.equals(null))) {

						enrichmentData.setContent_class(contentClassData.getClassLabel());

					}

				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
						| NoSuchMethodException | InvocationTargetException e) {

					e.printStackTrace();
				}

				break;
			}
		}

		return enrichmentData;

	}

	
	private ForumPostDocument enrichForumPostDocument(ForumPostDocument document, EnrichmentData enrichmentData) {

		// emotions
		if (!(enrichmentData.getEmotionalDimensions() == null)) {
			for (String emotion : enrichmentData.getEmotionalDimensions()) {
				document.getEmotional_dimension().add(emotion);
			}
		}

		// plainText
		if (!(enrichmentData.getPlain_text() == null)) {
			document.setPlain_text(enrichmentData.getPlain_text());
		}

		// detecting Code
		if (!(enrichmentData.getCode() == null)) {
			document.setContains_code(enrichmentData.getCode());
		}

		// request Reply
		if (!(enrichmentData.getCode() == null)) {
			document.setRequest_reply_classification(enrichmentData.getRequest_reply_classification());
		}

		// content class
		if (!(enrichmentData.getContent_class() == null)) {
			document.setContent_class(enrichmentData.getContent_class());
		}

		// sentiment
		if (!(enrichmentData.getSentiment() == null)) {
			document.setSentiment(enrichmentData.getSentiment());
		}
		return document;
	}
	
	

	private ArticleDocument enrichNewsGroupDocument(ArticleDocument document, EnrichmentData enrichmentData) {

		// emotions
		if (!(enrichmentData.getEmotionalDimensions() == null)) {
			for (String emotion : enrichmentData.getEmotionalDimensions()) {
				document.getEmotional_dimension().add(emotion);
			}
		}

		// plainText
		if (!(enrichmentData.getPlain_text() == null)) {
			document.setPlain_text(enrichmentData.getPlain_text());
		}

		// detecting Code
		if (!(enrichmentData.getCode() == null)) {
			document.setContains_code(enrichmentData.getCode());
		}

		// request Reply
		if (!(enrichmentData.getCode() == null)) {
			document.setRequest_reply_classification(enrichmentData.getRequest_reply_classification());
		}

		// content class
		if (!(enrichmentData.getContent_class() == null)) {
			document.setContent_class(enrichmentData.getContent_class());
		}

		// sentiment
		if (!(enrichmentData.getSentiment() == null)) {
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
	 * Loads the mapping for a particular index from the 'mappings'directory
	 * 
	 * @param mapping
	 * @return String
	 */
	private String loadMapping(String documentType, String knowlegeType) {

		String indexmapping = "";
		File file = null;
		try {
			file = new File(locateMappings(documentType, knowlegeType));
		} catch (IllegalArgumentException | IOException e1) {
			e1.printStackTrace();
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				indexmapping = indexmapping + line;
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return indexmapping;
	}

	/**
	 * Locates the mapping file within the 'mappings' directory and returns a file
	 * path
	 * 
	 * @return String
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	private String locateMappings(String documentType, String knowledgeType)
			throws IllegalArgumentException, IOException {
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
		if (path.endsWith("bin/"))
			path = path.substring(0, path.lastIndexOf("bin/"));
		File file = new File(path + "mappings/" + documentType + "." + knowledgeType + ".json");
		checkPropertiesFilePath(file.toPath());

		return file.getPath();
	}

	/**
	 * Checks if a file exists
	 * 
	 * @param path
	 */
	private void checkPropertiesFilePath(Path path) {
		if (!Files.exists(path)) {
			System.err.println("The file " + path + " has not been found");
		}
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
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 */
	private <T extends Pongo> T findCollection(PongoDB db, Class<T> type, PongoCollection<T> collection,
			CommunicationChannelArticle article) throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		
		String collectionName = ""; 
		CommunicationChannel communicationChannel = article.getCommunicationChannel();
		
		if (communicationChannel instanceof NntpNewsGroup) {
			
			NntpNewsGroup newsGroup = (NntpNewsGroup) communicationChannel;
			collectionName = newsGroup.getNewsGroupName();
		}else if (communicationChannel instanceof EclipseForum) {
			
			EclipseForum eclipseForum = (EclipseForum) communicationChannel;
			collectionName = eclipseForum.getForum_name();
			
		}else if (communicationChannel instanceof Discussion) {
			Discussion discussion = (Discussion) communicationChannel;
			//FIXME - How do we handle discussions?
		
		}
		
		T output = null;

		Iterable<T> iterator = collection.find(
				getStringQueryProducer(type, output, "NEWSGROUPNAME").eq(collectionName),
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
			e.printStackTrace();
		}
		return null;
	}

	private <T extends Pongo> NumericalQueryProducer getNumericalQueryProducer(Class<T> type, T t, String field) {

		try {

			return (NumericalQueryProducer) type.getDeclaredField(field).get(t);

		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

}