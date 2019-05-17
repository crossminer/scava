package org.eclipse.scava.metricprovider.trans.documentation.sentiment;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.documentation.DocumentationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.documentation.detectingcode.DocumentationDetectingCodeTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.documentation.detectingcode.model.DocumentationDetectingCodeTransMetric;
import org.eclipse.scava.metricprovider.trans.documentation.detectingcode.model.DocumentationEntryDetectingCode;
import org.eclipse.scava.metricprovider.trans.documentation.model.Documentation;
import org.eclipse.scava.metricprovider.trans.documentation.model.DocumentationTransMetric;
import org.eclipse.scava.metricprovider.trans.documentation.sentiment.model.DocumentationEntrySentiment;
import org.eclipse.scava.metricprovider.trans.documentation.sentiment.model.DocumentationSentimentTransMetric;
import org.eclipse.scava.nlp.classifiers.sentimentanalyzer.SentimentAnalyzer;
import org.eclipse.scava.nlp.tools.predictions.singlelabel.SingleLabelPredictionCollection;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.vcs.PlatformVcsManager;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.VcsRepository;
import org.eclipse.scava.repository.model.documentation.gitbased.DocumentationGitBased;

import com.mongodb.DB;

public class DocumentationSentimentTransMetricProvider implements ITransientMetricProvider<DocumentationSentimentTransMetric> {

	protected PlatformVcsManager platformVcsManager;
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	@Override
	public String getIdentifier() {
		return DocumentationSentimentTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "trans.documentation.sentiment";
	}

	@Override
	public String getFriendlyName() {
		return "Documentation Sentiment Analysis";
	}

	@Override
	public String getSummaryInformation() {
		return null;
	}

	@Override
	public boolean appliesTo(Project project) {
		for(VcsRepository repository : project.getVcsRepositories())
			if(repository instanceof DocumentationGitBased) return true;
		return false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses=uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(DocumentationTransMetricProvider.class.getCanonicalName(), DocumentationDetectingCodeTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context=context;
		this.platformVcsManager=context.getPlatformVcsManager();
	}

	@Override
	public DocumentationSentimentTransMetric adapt(DB db) {
		return new DocumentationSentimentTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta delta, DocumentationSentimentTransMetric db) {
		
		DocumentationEntrySentiment documentationEntrySentiment;
		
		DocumentationTransMetric documentationProcess = ((DocumentationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		SingleLabelPredictionCollection instancesCollection = new SingleLabelPredictionCollection();
		
		for(Documentation documentation : documentationProcess.getDocumentation())
		{
			//We check that the information regarding the documentation concerns the date of analysis
			if(documentation.getLastUpdateDate().equals(delta.getDate().toString()))
			{
				for(String entryId : documentation.getRemovedEntriesId())
				{
					documentationEntrySentiment = findDocumentationEntrySentiment(db, documentation.getDocumentationId(), entryId);
					db.getDocumentationEntriesSentiment().remove(documentationEntrySentiment);
				}
				db.sync();
			}
		}
		
		DocumentationDetectingCodeTransMetric documentationDetectingCode = ((DocumentationDetectingCodeTransMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));
		Iterable<DocumentationEntryDetectingCode> documentationEntriesDetectingCode = documentationDetectingCode.getDocumentationEntriesDetectingCode();
			
		for(DocumentationEntryDetectingCode documentationEntry : documentationEntriesDetectingCode)
		{
			documentationEntrySentiment = findDocumentationEntrySentiment(db, documentationEntry);
			if(documentationEntrySentiment==null)
			{
				documentationEntrySentiment= new DocumentationEntrySentiment();
				documentationEntrySentiment.setEntryId(documentationEntry.getEntryId());
				documentationEntrySentiment.setDocumentationId(documentationEntry.getDocumentationId());
				db.getDocumentationEntriesSentiment().add(documentationEntrySentiment);
			}
			db.sync();
			instancesCollection.addText(getDocumentationEntryClassifierId(documentationEntry), documentationEntry.getNaturalLanguage());
		}
		
		if(instancesCollection.size()!=0)
		{
			HashMap<Object, String> predictions=null;
			
			try {
				predictions = SentimentAnalyzer.predict(instancesCollection).getIdsWithPredictedLabel();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
			
			for(DocumentationEntryDetectingCode documentationEntry : documentationEntriesDetectingCode)
			{
				documentationEntrySentiment = findDocumentationEntrySentiment(db, documentationEntry);
				documentationEntrySentiment.setPolarity(predictions.get(getDocumentationEntryClassifierId(documentationEntry)));
				db.sync();
			}
		}
		
		
	}
	
	private DocumentationEntrySentiment findDocumentationEntrySentiment (DocumentationSentimentTransMetric db, DocumentationEntryDetectingCode documentationEntry)
	{
		return findDocumentationEntrySentiment(db, documentationEntry.getDocumentationId(), documentationEntry.getEntryId());
	}
	
	private DocumentationEntrySentiment findDocumentationEntrySentiment (DocumentationSentimentTransMetric db, String documentationId, String entryId)
	{
		DocumentationEntrySentiment documentationEntryReadability = null;
		Iterable<DocumentationEntrySentiment> documentationEntryRIt = db.getDocumentationEntriesSentiment().
				find(DocumentationEntrySentiment.DOCUMENTATIONID.eq(documentationId),
						DocumentationEntrySentiment.ENTRYID.eq(entryId));
		for(DocumentationEntrySentiment der : documentationEntryRIt)
			documentationEntryReadability=der;
		return documentationEntryReadability;
	}
	
	private String getDocumentationEntryClassifierId(DocumentationEntryDetectingCode documentationEntry)
	{
		return "DOCUMENTATION#"+documentationEntry.getDocumentationId() + "#" + documentationEntry.getEntryId();
	}

}
