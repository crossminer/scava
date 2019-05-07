/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.indexing.bugs;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.scava.index.indexer.Indexer;
import org.eclipse.scava.metricprovider.indexing.bugs.document.CommentDocument;
import org.eclipse.scava.metricprovider.indexing.bugs.document.BugDocument;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.BugMetadataTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugsBugMetadataTransMetric;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.CommentData;
import org.eclipse.scava.metricprovider.trans.detectingcode.DetectingCodeTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.BugTrackerCommentDetectingCode;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.DetectingCodeTransMetric;
import org.eclipse.scava.metricprovider.trans.emotionclassification.EmotionClassificationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.emotionclassification.model.BugTrackerCommentsEmotionClassification;
import org.eclipse.scava.metricprovider.trans.emotionclassification.model.EmotionClassificationTransMetric;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.IndexPreparationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.model.IndexPrepTransMetric;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.PlainTextProcessingTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.BugTrackerCommentPlainTextProcessing;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.PlainTextProcessingTransMetric;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.RequestReplyClassificationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.BugTrackerComments;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.RequestReplyClassificationTransMetric;
import org.eclipse.scava.metricprovider.trans.sentimentclassification.SentimentClassificationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.sentimentclassification.model.BugTrackerCommentsSentimentClassification;
import org.eclipse.scava.metricprovider.trans.sentimentclassification.model.SentimentClassificationTransMetric;
import org.eclipse.scava.metricprovider.trans.severityclassification.SeverityClassificationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.severityclassification.model.BugTrackerBugsData;
import org.eclipse.scava.metricprovider.trans.severityclassification.model.SeverityClassificationTransMetric;
import org.eclipse.scava.platform.AbstractIndexingMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.scava.platform.indexing.Indexing;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoDB;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;

public class BugsIndexingMetricProvider extends AbstractIndexingMetricProvider {

	public final static String IDENTIFIER = BugsIndexingMetricProvider.class.getCanonicalName();
	public final static String SHORT_IDENTIFIER = "bug indexing metric";
	public final static String FRIENDLY_NAME = "bug tracking system indexer";
	public final static String DESCRIPTION = "This metric prepares and indexes documents realting to bug tracking systems";

	protected MetricProviderContext context;
	protected List<IMetricProvider> uses;
	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;

	public final static String NLP = "nlp";// knowledge type.

	private Indexer indexer;

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

		return !project.getBugTrackingSystems().isEmpty();

	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		// Indexer relies upon the IndexPreparationTransMetric only.
		return Arrays.asList(IndexPreparationTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, Indexing db) {
		BugTrackingSystemProjectDelta bugTrackingSystemProjectDelta = projectDelta.getBugTrackingSystemDelta();

		indexer = new Indexer();

		for (BugTrackingSystemDelta bugTrackingSystemDelta : bugTrackingSystemProjectDelta
				.getBugTrackingSystemDeltas()) {

			String bugTrackerType = bugTrackingSystemDelta.getBugTrackingSystem().getBugTrackerType();

			switch (bugTrackerType) { // this is required if specialised data is also included during indexing

			case "github":
				prepareBugTrackingsystem(project, projectDelta, bugTrackingSystemDelta);
				break;
			case "mantis":
				prepareBugTrackingsystem(project, projectDelta, bugTrackingSystemDelta);
				break;
			case "bitbucket":
				prepareBugTrackingsystem(project, projectDelta, bugTrackingSystemDelta);
				break;
			case "redmine":
				prepareBugTrackingsystem(project, projectDelta, bugTrackingSystemDelta);
				break;
			case "gitlab":
				prepareBugTrackingsystem(project, projectDelta, bugTrackingSystemDelta);
				break;
			case "bugzilla":
				prepareBugTrackingsystem(project, projectDelta, bugTrackingSystemDelta);
				break;
			case "jira":
				prepareBugTrackingsystem(project, projectDelta, bugTrackingSystemDelta);
				break;
			}
		}
	}

	/**
	 * Prepares a Bug Tracking System elements (new bugs, updated bugs and comments)
	 * for indexing
	 * 
	 * @param project
	 * @param bugTrackingSystemDelta
	 * @param projectDelta
	 */
	private void prepareBugTrackingsystem(Project project, ProjectDelta projectDelta, BugTrackingSystemDelta delta) {

		ObjectMapper mapper = new ObjectMapper();

		for (BugTrackingSystemBug bug : delta.getNewBugs()) // NEW BUGS
		{
			String documentType = "bug";
			String indexName = Indexer.generateIndexName(delta.getBugTrackingSystem().getBugTrackerType(), documentType,
					NLP);

			String uniqueBugIdentifier = generateUniqueDocumentId(projectDelta, bug.getBugId(),
					delta.getBugTrackingSystem().getBugTrackerType());

			String mapping = loadMapping(documentType, NLP);

			EnrichmentData enrichmentData = getEnrichmentData(bug, project);

			BugDocument enrichedDocument = enrichIssueDocument(new BugDocument(uniqueBugIdentifier, bug.getBugId(),
					project.getName(), bug.getSummary(), bug.getCreationTime(), bug.getCreator()), enrichmentData);

			String document;

			try {
				
				document = mapper.writeValueAsString(enrichedDocument);
				indexer.indexDocument(indexName, mapping, documentType, uniqueBugIdentifier, document);
				
			} catch (JsonProcessingException e) { 

				e.printStackTrace();
			}

		}

		for (BugTrackingSystemBug bug : delta.getUpdatedBugs()) // UPDATED BUGS
		{
			String documentType = "bug";
			String indexName = Indexer.generateIndexName(delta.getBugTrackingSystem().getBugTrackerType(), documentType,
					NLP);

			String uniqueBugIdentifier = generateUniqueDocumentId(projectDelta, bug.getBugId(),
					delta.getBugTrackingSystem().getBugTrackerType());

			String mapping = loadMapping(documentType, NLP);

			EnrichmentData enrichmentData = getEnrichmentData(bug, project);

			BugDocument enrichedDocument = enrichIssueDocument(new BugDocument(uniqueBugIdentifier, bug.getBugId(),
					project.getName(), bug.getSummary(), bug.getCreationTime(), bug.getCreator()), enrichmentData);

			String document;
			
			try {
			
				document = mapper.writeValueAsString(enrichedDocument);
				indexer.indexDocument(indexName, mapping, documentType, uniqueBugIdentifier, document);
			
			} catch (JsonProcessingException e) {

				e.printStackTrace();
			}

		}

		for (BugTrackingSystemComment bugComment : delta.getComments()) { // COMMENTS

			String documentType = "comment";
			String indexName = Indexer.generateIndexName(delta.getBugTrackingSystem().getBugTrackerType(), documentType,
					NLP);

			String uniqueBugIdentifier = generateUniqueDocumentId(projectDelta, bugComment.getCommentId(),
					delta.getBugTrackingSystem().getBugTrackerType());

			String mapping = loadMapping(documentType, NLP);

			EnrichmentData enrichmentData = getEnrichmentData(bugComment, project);

			CommentDocument enrichedDocument = enrichCommentDocument(new CommentDocument(uniqueBugIdentifier,
					bugComment.getCommentId(), bugComment.getBugId(), project.getName(), bugComment.getText(),
					bugComment.getCreator(), bugComment.getCreationTime()), enrichmentData);

			String document;
			try {
				
				document = mapper.writeValueAsString(enrichedDocument);
				indexer.indexDocument(indexName, mapping, documentType, uniqueBugIdentifier, document);

			} catch (JsonProcessingException e) {

				e.printStackTrace();
			}
		}

	}

	// ------------------------------------------------------------------------------------------
	// Utility Methods
	// ------------------------------------------------------------------------------------------

	/**
	 * This method returns an xxxx which contains additional fields and values from
	 * various metrics.
	 * 
	 * @param bug
	 * @param project
	 * @return
	 */
	private EnrichmentData getEnrichmentData(BugTrackingSystemBug bug, Project project) {// THIS IS FOR BUGS

		EnrichmentData enrichmentData = new EnrichmentData();

		IndexPrepTransMetric indexPrepTransMetric = ((IndexPreparationTransMetricProvider) uses.get(0))
				.adapt(context.getProjectDB(project));

		for (String metricIdentifier : indexPrepTransMetric.getExecutedMetricProviders().first()
				.getMetricIdentifiers()) {

			switch (metricIdentifier) {

			case "org.eclipse.scava.metricprovider.trans.severityclassification.SeverityClassificationTransMetricProvider": // severity

				SeverityClassificationTransMetric bugTrackerSeverityData = new SeverityClassificationTransMetricProvider()
						.adapt(context.getProjectDB(project));

				try {

					BugTrackerBugsData severityData = findCollection(bugTrackerSeverityData, BugTrackerBugsData.class,
							bugTrackerSeverityData.getBugTrackerBugs(), bug);

					if (!(severityData.getSeverity() == (null))) {
						enrichmentData.setSeverity(severityData.getSeverity());
					}

				} catch (NullPointerException np) {

					enrichmentData.setSeverity("unable to calculate severity at this time (reason: no comments)");

				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
						| NoSuchMethodException | InvocationTargetException e) {

					e.printStackTrace();
				}

				break;

			}

		}

		return enrichmentData;

	}

	/**
	 * This method returns a EnrichmentData object which contains additional data
	 * from specific metrics
	 * 
	 * @param comment
	 * @param project
	 * @return
	 */
	private EnrichmentData getEnrichmentData(BugTrackingSystemComment comment, Project project) {

		EnrichmentData enrichmentData = new EnrichmentData();

		IndexPrepTransMetric indexPrepTransMetric = ((IndexPreparationTransMetricProvider) uses.get(0))
				.adapt(context.getProjectDB(project));

		for (String metricIdentifier : indexPrepTransMetric.getExecutedMetricProviders().first()
				.getMetricIdentifiers()) {

			switch (metricIdentifier) {

			// EMOTION
			case "org.eclipse.scava.metricprovider.trans.emotionclassification.EmotionClassificationTransMetricProvider":

				EmotionClassificationTransMetric bugTrackerEmotionData = new EmotionClassificationTransMetricProvider()
						.adapt(context.getProjectDB(project));
				try {
					List<String> emotionData = findCollection(bugTrackerEmotionData,
							BugTrackerCommentsEmotionClassification.class,
							bugTrackerEmotionData.getBugTrackerComments(), comment).getEmotions();

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

				SentimentClassificationTransMetric bugTrackerCommentsSentimentData = new SentimentClassificationTransMetricProvider()
						.adapt(context.getProjectDB(project));

				try {
					BugTrackerCommentsSentimentClassification sentimentData = findCollection(
							bugTrackerCommentsSentimentData, BugTrackerCommentsSentimentClassification.class,
							bugTrackerCommentsSentimentData.getBugTrackerComments(), comment);

					if (!(sentimentData.equals(null))) {

						enrichmentData.setSentiment(sentimentData.getPolarity());

					}

				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
						| NoSuchMethodException | InvocationTargetException e) {

					e.printStackTrace();
				}

				break;

			// PLAIN TEXT
			case "org.eclipse.scava.metricprovider.trans.plaintextprocessing.PlainTextProcessingTransMetricProvider":

				PlainTextProcessingTransMetric bugTrackerCommentsPlainTextData = new PlainTextProcessingTransMetricProvider()
						.adapt(context.getProjectDB(project));

				try {
					BugTrackerCommentPlainTextProcessing plainTextData = findCollection(bugTrackerCommentsPlainTextData,
							BugTrackerCommentPlainTextProcessing.class,
							bugTrackerCommentsPlainTextData.getBugTrackerComments(), comment);

					if (!(plainTextData.getPlainText().equals(null))) {

						String plaintext = String.join(" ", plainTextData.getPlainText());
						enrichmentData.setPlain_text(plaintext);

					}

				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
						| NoSuchMethodException | InvocationTargetException e) {

					e.printStackTrace();
				}

				break;

			// DETECTING CODE
			case "org.eclipse.scava.metricprovider.trans.detectingcode.DetectingCodeTransMetricProvider":

				DetectingCodeTransMetric bugTrackerDetectingCodeData = new DetectingCodeTransMetricProvider()
						.adapt(context.getProjectDB(project));

				try {

					BugTrackerCommentDetectingCode detectingcodeData = findCollection(bugTrackerDetectingCodeData,
							BugTrackerCommentDetectingCode.class, bugTrackerDetectingCodeData.getBugTrackerComments(),
							comment);
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

				RequestReplyClassificationTransMetric bugtrackerRequestReplyData = new RequestReplyClassificationTransMetricProvider()
						.adapt(context.getProjectDB(project));

				try {

					BugTrackerComments requestReplyData = findCollection(bugtrackerRequestReplyData,
							BugTrackerComments.class, bugtrackerRequestReplyData.getBugTrackerComments(), comment);

					if (!(requestReplyData.equals(null))) {

						enrichmentData.setRequest_reply_classification(requestReplyData.getClassificationResult());

					}

				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
						| NoSuchMethodException | InvocationTargetException e) {

					e.printStackTrace();
				}

				break;

			// Content Classification
			case "org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.BugMetadataTransMetricProvider":

				BugsBugMetadataTransMetric bugTrackerContentClassData = new BugMetadataTransMetricProvider()
						.adapt(context.getProjectDB(project));

				try {
					CommentData contentClassData = findCollection(bugTrackerContentClassData, CommentData.class,
							bugTrackerContentClassData.getComments(), comment);

					if (!(contentClassData.equals(null))) {
						enrichmentData.setContent_class(contentClassData.getContentClass());
					}

				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
						| NoSuchMethodException | InvocationTargetException e) {

					e.printStackTrace();

				}
			}

		}

		return enrichmentData;

	}

	private BugDocument enrichIssueDocument(BugDocument document, EnrichmentData enrichmentData) {

		// severity
		if (!(enrichmentData.getSeverity() == (null))) {
			document.setSeverity(enrichmentData.getSeverity());
		}

		return document;
	}

	private CommentDocument enrichCommentDocument(CommentDocument document, EnrichmentData enrichmentData) {

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
	 * This method finds a collection relating to a metric provider
	 * 
	 * 
	 * @param db
	 * @param type
	 * @param collection
	 * @param comment
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 */
	private <T extends Pongo> T findCollection(PongoDB db, Class<T> type, PongoCollection<T> collection,
			BugTrackingSystemComment comment) throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException, InvocationTargetException {

		T output = null;

		Iterable<T> iterator = collection.find(
				getStringQueryProducer(type, output, "BUGTRACKERID").eq(comment.getBugTrackingSystem().getOSSMeterId()),
				getStringQueryProducer(type, output, "BUGID").eq(comment.getBugId()),
				getStringQueryProducer(type, output, "COMMENTID").eq(comment.getCommentId()));

		for (T t : iterator) {
			output = t;
		}

		return output;
	}

	/**
	 * This method finds a collection relating to a metric provider
	 * 
	 * 
	 * @param db
	 * @param type
	 * @param collection
	 * @param issue
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 */
	private <T extends Pongo> T findCollection(PongoDB db, Class<T> type, PongoCollection<T> collection,
			BugTrackingSystemBug issue) throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException, InvocationTargetException {

		T output = null;

		Iterable<T> iterator = collection.find(
				getStringQueryProducer(type, output, "BUGTRACKERID").eq(issue.getBugTrackingSystem().getOSSMeterId()),
				getStringQueryProducer(type, output, "BUGID").eq(issue.getBugId()));

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
}