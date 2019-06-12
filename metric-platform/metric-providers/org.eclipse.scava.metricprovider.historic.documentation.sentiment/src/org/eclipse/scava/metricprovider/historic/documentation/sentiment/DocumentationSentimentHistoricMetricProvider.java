package org.eclipse.scava.metricprovider.historic.documentation.sentiment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.documentation.sentiment.model.DocumentationEntryHistoricSentiment;
import org.eclipse.scava.metricprovider.historic.documentation.sentiment.model.DocumentationHistoricSentiment;
import org.eclipse.scava.metricprovider.historic.documentation.sentiment.model.DocumentationSentimentHistoricMetric;
import org.eclipse.scava.metricprovider.trans.documentation.sentiment.DocumentationSentimentTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.documentation.sentiment.model.DocumentationEntrySentiment;
import org.eclipse.scava.metricprovider.trans.documentation.sentiment.model.DocumentationSentimentTransMetric;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.VcsRepository;
import org.eclipse.scava.repository.model.documentation.gitbased.DocumentationGitBased;
import org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic;

import com.googlecode.pongo.runtime.Pongo;

public class DocumentationSentimentHistoricMetricProvider extends AbstractHistoricalMetricProvider {

	protected MetricProviderContext context;
	protected List<IMetricProvider> uses;

	@Override
	public String getIdentifier() {
		return DocumentationSentimentHistoricMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "historic.documentation.sentiment";
	}

	@Override
	public String getFriendlyName() {
		return "Documentation sentiment polarity Historic Metric";
	}

	@Override
	public String getSummaryInformation() {
		return "Historic metric for that stores the evolution of the documentation sentiment polarity.";
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
		return Arrays.asList(DocumentationSentimentTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;	
	}
	
	@Override
	public Pongo measure(Project project) {
		DocumentationSentimentHistoricMetric documentationHistoricMetric = new DocumentationSentimentHistoricMetric();
		
		DocumentationSentimentTransMetric analyzedDocumentationSentiment = ((DocumentationSentimentTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		HashMap<String, Double> sentimentSum = new HashMap<String, Double>();
		HashMap<String, Integer> documentationEntryCounter = new HashMap<String, Integer>();
		
		for(DocumentationEntrySentiment documentationEntry : analyzedDocumentationSentiment.getDocumentationEntriesSentiment())
		{
			DocumentationEntryHistoricSentiment documentationEntryHistoric =  new DocumentationEntryHistoricSentiment();
			documentationEntryHistoric.setDocumentationId(documentationEntry.getDocumentationId());
			documentationEntryHistoric.setEntryId(documentationEntry.getEntryId());
			documentationEntryHistoric.setPolarity(documentationEntry.getPolarity());
			sentimentSum.compute(documentationEntry.getDocumentationId(), (k,v)-> {
				if(v==null)
					v=0.0;
				switch(documentationEntry.getPolarity())
				{
					case "__label__positive":
						v+=1.0;
						break;
					case "__label__negative":
						v-=1.0;
						break; 
				}
				return v;
			});
			documentationEntryCounter.compute(documentationEntry.getDocumentationId(), (k,v)-> v==null ? 1 : v+1);
			
			documentationHistoricMetric.getDocumentationEntriesSentiment().add(documentationEntryHistoric);
		}
		
		if(documentationEntryCounter.size()>0)
		{
			Double sentimentAvg;
			for(String documentationId : documentationEntryCounter.keySet())
			{
				DocumentationHistoricSentiment documentationHistoric = new DocumentationHistoricSentiment();
				documentationHistoric.setDocumentationId(documentationId);
				documentationHistoric.setNumberOfDocumentationEntries(documentationEntryCounter.get(documentationId));
				sentimentAvg=sentimentSum.get(documentationId)/(double) documentationEntryCounter.get(documentationId);
				documentationHistoric.setAverageDocumentationSentiment(sentimentAvg);
				
				documentationHistoricMetric.getDocumentationSentiment().add(documentationHistoric);
			}
		}
		
		
		return documentationHistoricMetric;
	}

}
