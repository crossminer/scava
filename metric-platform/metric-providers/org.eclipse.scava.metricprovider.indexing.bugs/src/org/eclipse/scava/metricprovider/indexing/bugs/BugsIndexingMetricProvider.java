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

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.index.indexer.Indexer;
import org.eclipse.scava.metricprovider.indexing.bugs.document.BugDocument;
import org.eclipse.scava.metricprovider.indexing.bugs.document.CommentDocument;
import org.eclipse.scava.metricprovider.indexing.bugs.document.DocumentAbstract;
import org.eclipse.scava.metricprovider.indexing.bugs.mapping.Mapping;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.BugMetadataTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugsBugMetadataTransMetric;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.CommentData;
import org.eclipse.scava.metricprovider.trans.bugs.migrationissues.BugTrackerMigrationIssueTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.migrationissues.model.BugTrackerMigrationIssue;
import org.eclipse.scava.metricprovider.trans.bugs.migrationissues.model.BugTrackerMigrationIssueTransMetric;
import org.eclipse.scava.metricprovider.trans.bugs.migrationissuesmaracas.BugTrackerMigrationIssueMaracasTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.migrationissuesmaracas.model.BugTrackerMigrationIssueMaracas;
import org.eclipse.scava.metricprovider.trans.bugs.migrationissuesmaracas.model.BugTrackerMigrationIssueMaracasTransMetric;
import org.eclipse.scava.metricprovider.trans.bugs.references.BugsReferenceTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.references.model.BugReferringTo;
import org.eclipse.scava.metricprovider.trans.bugs.references.model.BugsReferenceTransMetric;
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
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.Project;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	protected OssmeterLogger logger;
	
	
	private List<String> metricIdentifiers;
	private SeverityClassificationTransMetric bugTrackerSeverityData;
	private EmotionClassificationTransMetric bugTrackerEmotionData;
	private SentimentClassificationTransMetric bugTrackerCommentsSentimentData;
	private PlainTextProcessingTransMetric bugTrackerCommentsPlainTextData;
	private DetectingCodeTransMetric bugTrackerDetectingCodeData;
	private RequestReplyClassificationTransMetric bugtrackerRequestReplyData;
	private BugsBugMetadataTransMetric bugTrackerContentClassData;
	private BugTrackerMigrationIssueTransMetric migrationData;
	private BugTrackerMigrationIssueMaracasTransMetric migrationMaracasData;
	private BugsReferenceTransMetric referringData;
	
	public final static String NLP = "nlp";// knowledge type.
	
	public BugsIndexingMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.indexing.bugs");
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

		String projectName = project.getName();
		String btsType;
		
		loadMetricsDB(project);
		
		for (BugTrackingSystemDelta btsDelta : bugTrackingSystemProjectDelta.getBugTrackingSystemDeltas())
		{
			btsType=btsDelta.getBugTrackingSystem().getBugTrackerType();
			
			for (BugTrackingSystemBug bug : btsDelta.getNewBugs()) // NEW BUGS
			{
				processBugs(projectName, btsType, bug);
			}
			for (BugTrackingSystemBug bug : btsDelta.getUpdatedBugs()) // UPDATED BUGS
			{
				processBugs(projectName, btsType, bug);
			}
			for (BugTrackingSystemComment bugComment : btsDelta.getComments()) // COMMENTS
			{ 
				processComments(projectName, btsType, bugComment);
			}

		}
	}
	
	private void processComments(String projectName, String bugTrackerType, BugTrackingSystemComment bugComment)
	{
		String uid=generateUniqueDocumentId(projectName, bugComment.getCommentId(),bugTrackerType);
		CommentDocument commentDocument = new CommentDocument(uid,
															bugComment.getCommentId(),
															bugComment.getBugId(),
															projectName,
															bugComment.getText(),
															bugComment.getCreator(),
															bugComment.getCreationTime());
		enrichCommentDocument(bugComment, commentDocument);
		indexing(bugTrackerType, "bug.comment", uid, commentDocument);
		
	}
	
	private void processBugs(String projectName, String bugTrackerType, BugTrackingSystemBug bug)
	{
		String uid=generateUniqueDocumentId(projectName, bug.getBugId(),bugTrackerType);
		BugDocument bugDocument = new BugDocument(uid,
									bug.getBugId(),
									projectName,
									bug.getSummary(),
									bug.getCreationTime(),
									bug.getCreator());
		adrianEnrichment(bug, bugDocument);
		indexing(bugTrackerType, "bug.post", uid, bugDocument);
	}
	
	private void indexing(String bugTrackerType, String documentType, String uid, DocumentAbstract document)
	{
		try {
			Indexer.indexDocument(Indexer.generateIndexName(bugTrackerType, documentType, NLP),
					Mapping.getMapping(documentType),
					documentType,
					uid,
					new ObjectMapper().setSerializationInclusion(Include.NON_NULL).writeValueAsString(document));
		} catch (JsonProcessingException e) {
			logger.error("Error while processing json:", e);
			e.printStackTrace();
		}
	}
	
	private void loadMetricsDB(Project project)
	{
		IndexPrepTransMetric indexPrepTransMetric = ((IndexPreparationTransMetricProvider) uses.get(0)).adapt(context.getProjectDB(project));
		metricIdentifiers=indexPrepTransMetric.getExecutedMetricProviders().first().getMetricIdentifiers();
		for (String metricIdentifier : metricIdentifiers)
		{
			switch (metricIdentifier)
			{
				case "org.eclipse.scava.metricprovider.trans.severityclassification.SeverityClassificationTransMetricProvider":
					bugTrackerSeverityData = new SeverityClassificationTransMetricProvider().adapt(context.getProjectDB(project));
					break;
				case "org.eclipse.scava.metricprovider.trans.emotionclassification.EmotionClassificationTransMetricProvider":
					bugTrackerEmotionData = new EmotionClassificationTransMetricProvider().adapt(context.getProjectDB(project));
					break;
				case "org.eclipse.scava.metricprovider.trans.sentimentclassification.SentimentClassificationTransMetricProvider":
					bugTrackerCommentsSentimentData = new SentimentClassificationTransMetricProvider().adapt(context.getProjectDB(project));
					break;
				case "org.eclipse.scava.metricprovider.trans.plaintextprocessing.PlainTextProcessingTransMetricProvider":
					bugTrackerCommentsPlainTextData = new PlainTextProcessingTransMetricProvider().adapt(context.getProjectDB(project));
					break;
				case "org.eclipse.scava.metricprovider.trans.detectingcode.DetectingCodeTransMetricProvider":
					bugTrackerDetectingCodeData = new DetectingCodeTransMetricProvider().adapt(context.getProjectDB(project));
					break;
				case "org.eclipse.scava.metricprovider.trans.requestreplyclassification.RequestReplyClassificationTransMetricProvider":
					bugtrackerRequestReplyData = new RequestReplyClassificationTransMetricProvider().adapt(context.getProjectDB(project));
					break;
				case "org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.BugMetadataTransMetricProvider":
					bugTrackerContentClassData = new BugMetadataTransMetricProvider().adapt(context.getProjectDB(project));
					break;
				case "org.eclipse.scava.metricprovider.trans.bugs.migrationissues.BugTrackerMigrationIssueTransMetricProvider":
					migrationData =  new BugTrackerMigrationIssueTransMetricProvider().adapt(context.getProjectDB(project));
					break;
				case "org.eclipse.scava.metricprovider.trans.bugs.migrationissuesmaracas.BugTrackerMigrationIssueMaracasTransMetricProvider":
					migrationMaracasData = new BugTrackerMigrationIssueMaracasTransMetricProvider().adapt(context.getProjectDB(project));
					break;
				case "org.eclipse.scava.metricprovider.trans.bugs.references.BugsReferenceTransMetricProvider":
					referringData = new BugsReferenceTransMetricProvider().adapt(context.getProjectDB(project));
					break;
			}
				
		}
	}
	
	private void adrianEnrichment(BugTrackingSystemBug bug, BugDocument bd)
	{
		for (String metricIdentifier : metricIdentifiers) 
		{

			switch (metricIdentifier)
			{

				case "org.eclipse.scava.metricprovider.trans.severityclassification.SeverityClassificationTransMetricProvider": // severity
				{
					try {
						BugTrackerBugsData severityData = findCollection(bugTrackerSeverityData, BugTrackerBugsData.class,
								bugTrackerSeverityData.getBugTrackerBugs(), bug);
	
						if (!severityData.getSeverity().isEmpty())
							bd.setSeverity(severityData.getSeverity());
	
					} catch (NullPointerException np) {
						bd.setSeverity("unable to calculate severity at this time (reason: no comments)");
					}
	
					break;
				}
				case "org.eclipse.scava.metricprovider.trans.bugs.migrationissues.BugTrackerMigrationIssueTransMetricProvider":
				{
					BugTrackerMigrationIssue migrationIssue = findCollection(migrationData, BugTrackerMigrationIssue.class,
							migrationData.getBugTrackerMigrationIssues(), bug);
					if(migrationIssue!=null)
					{
						bd.setMigration_issue(true);
					}
					break;
				}
				case "org.eclipse.scava.metricprovider.trans.bugs.migrationissuesmaracas.BugTrackerMigrationIssueMaracasTransMetricProvider":
				{
					BugTrackerMigrationIssueMaracas migrationIssueMaracas = findCollection(migrationMaracasData, BugTrackerMigrationIssueMaracas.class,
							migrationMaracasData.getBugTrackerMigrationIssuesMaracas(), bug);
					if(migrationIssueMaracas!=null)
					{
						List<String> changes = migrationIssueMaracas.getChanges();
						List<Double> scores = migrationIssueMaracas.getMatchingPercentage();
						for(int i=0; i<changes.size(); i++)
						{
							bd.addProblematic_change(changes.get(i), scores.get(i));
						}
					}
					break;
				}
			}

		}
	}
	
	private void enrichCommentDocument(BugTrackingSystemComment comment, CommentDocument commentDocument) {

		for (String metricIdentifier : metricIdentifiers)
		{
			switch (metricIdentifier)
			{
				// EMOTION
				case "org.eclipse.scava.metricprovider.trans.emotionclassification.EmotionClassificationTransMetricProvider":
				{
					List<String> emotionData = findCollection(bugTrackerEmotionData,
							BugTrackerCommentsEmotionClassification.class,
							bugTrackerEmotionData.getBugTrackerComments(), comment).getEmotions();
					for (String dimension : emotionData)
						commentDocument.getEmotional_dimension().add(dimension);
					break;
				}
				// SENTIMENT
				case "org.eclipse.scava.metricprovider.trans.sentimentclassification.SentimentClassificationTransMetricProvider":
				{
					BugTrackerCommentsSentimentClassification sentimentData = findCollection(
							bugTrackerCommentsSentimentData, BugTrackerCommentsSentimentClassification.class,
							bugTrackerCommentsSentimentData.getBugTrackerComments(), comment);
	
					if (sentimentData!=null)
						commentDocument.setSentiment(sentimentData.getPolarity());
					break;
				}
				// PLAIN TEXT
				case "org.eclipse.scava.metricprovider.trans.plaintextprocessing.PlainTextProcessingTransMetricProvider":
				{
					BugTrackerCommentPlainTextProcessing plainTextData = findCollection(bugTrackerCommentsPlainTextData,
							BugTrackerCommentPlainTextProcessing.class,
							bugTrackerCommentsPlainTextData.getBugTrackerComments(), comment);
					if (plainTextData.getPlainText() != null) {
						String plaintext = String.join(" ", plainTextData.getPlainText());
						commentDocument.setPlain_text(plaintext);
					}
					break;
				}
				// DETECTING CODE
				case "org.eclipse.scava.metricprovider.trans.detectingcode.DetectingCodeTransMetricProvider":
				{
					BugTrackerCommentDetectingCode detectingcodeData = findCollection(bugTrackerDetectingCodeData,
							BugTrackerCommentDetectingCode.class, bugTrackerDetectingCodeData.getBugTrackerComments(),
							comment);			
					if (detectingcodeData != null) {					
						
						if (!detectingcodeData.getCode().isEmpty())
							commentDocument.setContains_code(true);
						else
							commentDocument.setContains_code(false);
					}
					break;
				}
				// REQUEST REPLY
				case "org.eclipse.scava.metricprovider.trans.requestreplyclassification.RequestReplyClassificationTransMetricProvider":
				{
					BugTrackerComments requestReplyData = findCollection(bugtrackerRequestReplyData,
							BugTrackerComments.class, bugtrackerRequestReplyData.getBugTrackerComments(), comment);
					if (requestReplyData != null) 
						commentDocument.setRequest_reply_classification(requestReplyData.getClassificationResult());
					break;
				}
				// Content Classification
				case "org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.BugMetadataTransMetricProvider":
				{
					CommentData contentClassData = findCollection(bugTrackerContentClassData, CommentData.class,
							bugTrackerContentClassData.getComments(), comment);
		
					if (contentClassData != null) {
						commentDocument.setContent_class(contentClassData.getContentClass());
					}
					break;
				}
				// Referring To
				case "org.eclipse.scava.metricprovider.trans.bugs.references.BugsReferenceTransMetricProvider":
				{
					BugReferringTo referringToData = findCollection(referringData, BugReferringTo.class,
							referringData.getBugsReferringTo(), comment);
					if(referringData != null)
					{
						for(String bugReference : referringToData.getBugsReferred())
							commentDocument.addBugReference(bugReference);
						for(String commitReference : referringToData.getCommitsReferred())
							commentDocument.addCommitReference(commitReference);
					}
					break;
				}

			}

		}

	}


	/**
	 * This method returns a unique Identifier based upon the SourceType, Project,
	 * Document Type and source ID;
	 * 
	 * @return String uid - a uniquely identifiable string.
	 */
	private String generateUniqueDocumentId(String projectName, String id, String bugTrackerType) {
		return bugTrackerType + " " + projectName + " " + id;
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
	 */
	private <T extends Pongo> T findCollection(PongoDB db, Class<T> type, PongoCollection<T> collection,
			BugTrackingSystemComment comment) {

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
	 * 
	 * @param db
	 * @param type
	 * @param collection
	 * @param issue
	 * @return
	 */
	private <T extends Pongo> T findCollection(PongoDB db, Class<T> type, PongoCollection<T> collection,
			BugTrackingSystemBug issue) {

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
			logger.error("Error while searching data in MongoBD:", e);
			e.printStackTrace();
		}
		return null;
	}
}