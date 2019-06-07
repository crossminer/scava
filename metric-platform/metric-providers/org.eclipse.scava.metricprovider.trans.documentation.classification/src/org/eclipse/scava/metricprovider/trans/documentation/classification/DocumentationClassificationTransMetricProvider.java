package org.eclipse.scava.metricprovider.trans.documentation.classification;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.documentation.DocumentationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.documentation.classification.model.DocumentationClassificationTransMetric;
import org.eclipse.scava.metricprovider.trans.documentation.classification.model.DocumentationEntryClassification;
import org.eclipse.scava.metricprovider.trans.documentation.model.Documentation;
import org.eclipse.scava.metricprovider.trans.documentation.model.DocumentationEntry;
import org.eclipse.scava.metricprovider.trans.documentation.model.DocumentationTransMetric;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.IndexPreparationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.model.IndexPrepTransMetric;
import org.eclipse.scava.nlp.classifiers.documentation.DocumentationClassifier;
import org.eclipse.scava.nlp.tools.predictions.multilabel.MultiLabelPrediction;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.eclipse.scava.platform.delta.vcs.PlatformVcsManager;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.VcsRepository;
import org.eclipse.scava.repository.model.documentation.gitbased.DocumentationGitBased;
import org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic;

import com.mongodb.DB;

public class DocumentationClassificationTransMetricProvider implements ITransientMetricProvider<DocumentationClassificationTransMetric> {

	protected PlatformVcsManager platformVcsManager;
	protected PlatformCommunicationChannelManager communicationChannelManager;
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	@Override
	public String getIdentifier() {
		return DocumentationClassificationTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "trans.documentation.classification";
	}

	@Override
	public String getFriendlyName() {
		return "Documentation classification.";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric determines which type of documentation is present.";
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
		this.uses=uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(IndexPreparationTransMetricProvider.class.getCanonicalName(),DocumentationTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context=context;
		this.communicationChannelManager= context.getPlatformCommunicationChannelManager();
		this.platformVcsManager=context.getPlatformVcsManager();
	}

	@Override
	public DocumentationClassificationTransMetric adapt(DB db) {
		return new DocumentationClassificationTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta delta, DocumentationClassificationTransMetric db) {
		

		//This is for the indexing
		IndexPrepTransMetric indexPrepTransMetric = ((IndexPreparationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));	
		indexPrepTransMetric.getExecutedMetricProviders().first().getMetricIdentifiers().add(getIdentifier());
		indexPrepTransMetric.sync();
		
		DocumentationTransMetric documentationProcessor = ((DocumentationTransMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));
		DocumentationEntryClassification documentationEntryClass;
		
		for(Documentation documentation : documentationProcessor.getDocumentation())
		{
			//We check that the information regarding the documentation concerns the date of analysis
			if(documentation.getLastUpdateDate().equals(delta.getDate().toString()))
			{
				for(String entryId : documentation.getRemovedEntriesId())
				{
					documentationEntryClass = findDocumentationEntryClassification(db, documentation.getDocumentationId(), entryId);
					db.getDocumentationEntriesClassification().remove(documentationEntryClass);
				}
				db.sync();
			}
		}
		
		Iterable<DocumentationEntry> documentationEntries = documentationProcessor.getDocumentationEntries();
		MultiLabelPrediction multiLabelPrediction;
		boolean pdfDocument;
		for(DocumentationEntry documentationEntry : documentationEntries)
		{
			documentationEntryClass = findDocumentationEntryClassification(db, documentationEntry);
			if(documentationEntryClass==null)
			{
				documentationEntryClass = new DocumentationEntryClassification();
				documentationEntryClass.setDocumentationId(documentationEntry.getDocumentationId());
				documentationEntryClass.setEntryId(documentationEntry.getEntryId());
				db.getDocumentationEntriesClassification().add(documentationEntryClass);
			}
			if(documentationEntry.getHtmlFormatted())
			{
				multiLabelPrediction = new MultiLabelPrediction(documentationEntry.getBody());
				
				if(documentationEntry.getOriginalFormatMime().equals("application/pdf"))
					pdfDocument=true;
				else
					pdfDocument=false;
				multiLabelPrediction = DocumentationClassifier.classify(multiLabelPrediction, pdfDocument);
				documentationEntryClass.getTypes().addAll(multiLabelPrediction.getLabels());
			}
			else
			{
				documentationEntryClass.getTypes().add("Unknown");
			}
			db.sync();
		}
		
		
	}
	
	private DocumentationEntryClassification findDocumentationEntryClassification (DocumentationClassificationTransMetric db, DocumentationEntry documentationEntry)
	{
		return findDocumentationEntryClassification(db, documentationEntry.getDocumentationId(), documentationEntry.getEntryId());
	}
	
	private DocumentationEntryClassification findDocumentationEntryClassification (DocumentationClassificationTransMetric db, String documentationId, String entryId)
	{
		DocumentationEntryClassification documentationEntryClassification = null;
		Iterable<DocumentationEntryClassification> documentationEntryCIt = db.getDocumentationEntriesClassification().
				find(DocumentationEntryClassification.DOCUMENTATIONID.eq(documentationId),
						DocumentationEntryClassification.ENTRYID.eq(entryId));
		for(DocumentationEntryClassification dec : documentationEntryCIt)
			documentationEntryClassification=dec;
		return documentationEntryClassification;
	}

}
