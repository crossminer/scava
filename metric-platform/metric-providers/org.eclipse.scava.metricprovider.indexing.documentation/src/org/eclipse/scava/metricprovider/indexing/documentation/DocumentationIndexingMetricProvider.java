package org.eclipse.scava.metricprovider.indexing.documentation;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.index.indexer.Indexer;
import org.eclipse.scava.metricprovider.indexing.documentation.document.DocumentationDocument;
import org.eclipse.scava.metricprovider.indexing.documentation.document.DocumentationEntryDocument;
import org.eclipse.scava.metricprovider.indexing.documentation.mapping.Mapping;
import org.eclipse.scava.metricprovider.trans.documentation.DocumentationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.documentation.detectingcode.DocumentationDetectingCodeTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.documentation.detectingcode.model.DocumentationDetectingCodeTransMetric;
import org.eclipse.scava.metricprovider.trans.documentation.detectingcode.model.DocumentationEntryDetectingCode;
import org.eclipse.scava.metricprovider.trans.documentation.model.Documentation;
import org.eclipse.scava.metricprovider.trans.documentation.model.DocumentationEntry;
import org.eclipse.scava.metricprovider.trans.documentation.model.DocumentationTransMetric;
import org.eclipse.scava.metricprovider.trans.documentation.readability.DocumentationReadabilityTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.documentation.readability.model.DocumentationEntryReadability;
import org.eclipse.scava.metricprovider.trans.documentation.readability.model.DocumentationReadabilityTransMetric;
import org.eclipse.scava.metricprovider.trans.documentation.sentiment.DocumentationSentimentTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.documentation.sentiment.model.DocumentationEntrySentiment;
import org.eclipse.scava.metricprovider.trans.documentation.sentiment.model.DocumentationSentimentTransMetric;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.IndexPreparationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.model.IndexPrepTransMetric;
import org.eclipse.scava.platform.AbstractIndexingMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.eclipse.scava.platform.delta.vcs.PlatformVcsManager;
import org.eclipse.scava.platform.indexing.Indexing;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.VcsRepository;
import org.eclipse.scava.repository.model.documentation.gitbased.DocumentationGitBased;
import org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoDB;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;

public class DocumentationIndexingMetricProvider extends AbstractIndexingMetricProvider {

	protected MetricProviderContext context;
	protected List<IMetricProvider> uses;
	
	protected PlatformVcsManager platformVcsManager;
	protected PlatformCommunicationChannelManager communicationChannelManager;
	
	private Indexer indexer;
	
	protected OssmeterLogger logger;
	
	private final static String NLP = "nlp";// knowledge type.
	
	public DocumentationIndexingMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.indexing.documentation");
	}
	
	@Override
	public String getIdentifier() {
		return DocumentationIndexingMetricProvider.class.getCanonicalName();
	}
	
	@Override
	public String getShortIdentifier() {
		return "metricprovider.indexing.documentation";
	}

	@Override
	public String getFriendlyName() {
		return "Documentation indexer";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric prepares and indexes documents relating to documentation.";
	}

	@Override
	public boolean appliesTo(Project project) {
		for(VcsRepository repository : project.getVcsRepositories())
			if(repository instanceof DocumentationGitBased) return true;
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels())
			if (communicationChannel instanceof DocumentationSystematic) return true;
		return false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(DocumentationTransMetricProvider.class.getCanonicalName(), IndexPreparationTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.platformVcsManager=context.getPlatformVcsManager();
		this.communicationChannelManager= context.getPlatformCommunicationChannelManager();
		this.context = context;
	}

	@Override
	public void measure(Project project, ProjectDelta delta, Indexing db) {
		/*
		 * This indexer metric, unlike other ones, will be based on the output from DocumentationTransMetricProvider
		 * The reason is that this metric is the one which indicate which files have been analyzed and not the manager for
		 * documentation. The manager only tell us the commits or urls (depending on the type of documentation) that the platform
		 * needs to analyze, but not the files, i.e. the documentation entries.
		*/
		
		DocumentationTransMetric documentationProcessor = ((DocumentationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		String projectName = delta.getProject().getName();
		ObjectMapper mapper = new ObjectMapper();
		String documentType;
		String indexName;
		String uid;
		String mapping;
		String document;
		
		//Documentation
		Iterable<Documentation> documentationIt = documentationProcessor.getDocumentation();
		documentType = "documentation";
		for(Documentation documentation : documentationIt)
		{
			
			indexName = Indexer.generateIndexName(documentation.getDocumentationId(), documentType, NLP);
			uid = generateUniqueDocumentationId(projectName, documentation.getDocumentationId());
			mapping = Mapping.getMapping(documentType);
			
			DocumentationDocument dd = new DocumentationDocument(project.getName(),
					uid,
					documentation.getDocumentationId(),
					delta.getDate().toJavaDate());
			
			dd.setDocumentationEntries(documentation.getEntriesId());
			
			try {
				document = mapper.writeValueAsString(dd);
				indexer.indexDocument(indexName, mapping, documentType, uid, document);
			} catch (JsonProcessingException e) {
				logger.error("Error while processing json:", e);
				e.printStackTrace();
			}
		}
		
		//Documentation Entries
		Iterable<DocumentationEntry> documentationEntries = documentationProcessor.getDocumentationEntries();
		
		documentType = "documentation.entry";
		
		for(DocumentationEntry documentationEntry : documentationEntries)
		{
			indexName = Indexer.generateIndexName(documentationEntry.getDocumentationId(), documentType, NLP);
			uid = generateUniqueDocumentationEntryId(projectName, documentationEntry.getDocumentationId(), documentationEntry.getEntryId());
			mapping = Mapping.getMapping(documentType);
			
			DocumentationEntryDocument ded = new DocumentationEntryDocument(project.getName(),
					uid,
					documentationEntry.getDocumentationId(),
					documentationEntry.getEntryId(),
					delta.getDate().toJavaDate());
			
			enrichDocumentationEntryDocument(project, documentationEntry, ded);
			
			try {
				document = mapper.writeValueAsString(ded);
				indexer.indexDocument(indexName, mapping, documentType, uid, document);
			} catch (JsonProcessingException e) {
				logger.error("Error while processing json:", e);
				e.printStackTrace();
			}
		}
		
	}
	
	private String generateUniqueDocumentationEntryId(String projectName, String documentationId, String entryId) {

		return "Documentation Entry "+ projectName + " " + documentationId + " " + entryId;
	}
	
	private String generateUniqueDocumentationId(String projectName, String documentationId) {

		return "Documentation "+ projectName + " " + documentationId;
	}
	
	private void enrichDocumentationEntryDocument(Project project, DocumentationEntry documentationEntry, DocumentationEntryDocument ded) {

		IndexPrepTransMetric indexPrepTransMetric = ((IndexPreparationTransMetricProvider) uses.get(1)).adapt(context.getProjectDB(project));

		for (String metricIdentifier : indexPrepTransMetric.getExecutedMetricProviders().first().getMetricIdentifiers())
		{

			switch (metricIdentifier) 
			{
				// Plain Text
				case "org.eclipse.scava.metricprovider.trans.documentation.DocumentationTransMetricProvider":
				{
					ded.setPlainText(String.join(" ",documentationEntry.getPlainText()));
					break;
				}
				// CODE
				case "org.eclipse.scava.metricprovider.trans.documentation.detectingcode.DocumentationDetectingCodeTransMetricProvider":
				{
					DocumentationDetectingCodeTransMetric detectingCodeCollection = new DocumentationDetectingCodeTransMetricProvider().adapt(context.getProjectDB(project));
					DocumentationEntryDetectingCode detectingCodeDocEntry = findCollection(detectingCodeCollection,
							 															DocumentationEntryDetectingCode.class,
							 															detectingCodeCollection.getDocumentationEntriesDetectingCode(),
							 															documentationEntry);
					if (!detectingCodeDocEntry.getCode().isEmpty())
						ded.setCode(true); 
					else
						ded.setCode(false);
					break;
				}
				//READABILITY
				case "org.eclipse.scava.metricprovider.trans.documentation.readability.DocumentationReadabilityTransMetricProvider":
				{
					DocumentationReadabilityTransMetric readabilityCollection = new DocumentationReadabilityTransMetricProvider().adapt(context.getProjectDB(project));
					DocumentationEntryReadability readabiliytyDocEntry = findCollection(readabilityCollection,
																						DocumentationEntryReadability.class,
							 															readabilityCollection.getDocumentationEntriesReadability(),
							 															documentationEntry);
					ded.setReadability(readabiliytyDocEntry.getReadability());
					break;
				}
				//SENTIMENT
				case "org.eclipse.scava.metricprovider.trans.documentation.sentiment.DocumentationSentimentTransMetricProvider":
				{
					DocumentationSentimentTransMetric sentimentCollection = new DocumentationSentimentTransMetricProvider().adapt(context.getProjectDB(project));
					DocumentationEntrySentiment detectingcodeData = findCollection(sentimentCollection,
																						DocumentationEntrySentiment.class,
							 															sentimentCollection.getDocumentationEntriesSentiment(),
							 															documentationEntry);
					ded.setSentiment(detectingcodeData.getPolarity());	 
					break;
				}
			}
		}
	}
	
	private <T extends Pongo> T findCollection(PongoDB db, Class<T> type, PongoCollection<T> collection,
			DocumentationEntry documentationEntry) {

		T output = null;

		try {
			Iterable<T> iterator = collection.find(
					getStringQueryProducer(type, output, "DOCUMENTATIONID").eq(documentationEntry.getDocumentationId()),
					getStringQueryProducer(type, output, "ENTRYID").eq(documentationEntry.getEntryId()));
		
			for (T t : iterator) {
				output = t;
			}
		
			
		} catch (SecurityException | IllegalArgumentException e) {

			e.printStackTrace();
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
