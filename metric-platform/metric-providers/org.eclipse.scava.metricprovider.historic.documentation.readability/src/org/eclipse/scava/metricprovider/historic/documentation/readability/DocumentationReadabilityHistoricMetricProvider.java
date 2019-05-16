package org.eclipse.scava.metricprovider.historic.documentation.readability;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.documentation.readability.model.DocumentationEntryHistoricReadability;
import org.eclipse.scava.metricprovider.historic.documentation.readability.model.DocumentationHistoricReadability;
import org.eclipse.scava.metricprovider.historic.documentation.readability.model.DocumentationReadabilityHistoricMetric;
import org.eclipse.scava.metricprovider.trans.documentation.readability.DocumentationReadabilityTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.documentation.readability.model.DocumentationEntryReadability;
import org.eclipse.scava.metricprovider.trans.documentation.readability.model.DocumentationReadabilityTransMetric;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.VcsRepository;
import org.eclipse.scava.repository.model.github.GitHubWiki;

import com.googlecode.pongo.runtime.Pongo;

public class DocumentationReadabilityHistoricMetricProvider extends AbstractHistoricalMetricProvider {

	protected MetricProviderContext context;
	protected List<IMetricProvider> uses;
	
	@Override
	public String getIdentifier() {
		return DocumentationReadabilityHistoricMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "historic.documentation.readability";
	}

	@Override
	public String getFriendlyName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSummaryInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean appliesTo(Project project) {
		for(VcsRepository repository : project.getVcsRepositories())
			if(repository instanceof GitHubWiki) return true;
		return false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(DocumentationReadabilityTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;		
	}
	
	@Override
	public Pongo measure(Project project) {
		DocumentationReadabilityHistoricMetric documentationHistoricMetric = new DocumentationReadabilityHistoricMetric();
		
		DocumentationReadabilityTransMetric analyzedDocumentationReadability = ((DocumentationReadabilityTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		HashMap<String, Double> readabilityScoreSum = new HashMap<String, Double>();
		HashMap<String, Integer> documentationEntryCounter = new HashMap<String, Integer>();
		
		for(DocumentationEntryReadability documentationEntry : analyzedDocumentationReadability.getDocumentationEntriesReadability())
		{
			DocumentationEntryHistoricReadability documentationEntryHistoric =  new DocumentationEntryHistoricReadability();
			documentationEntryHistoric.setDocumentationId(documentationEntry.getDocumentationId());
			documentationEntryHistoric.setEntryId(documentationEntry.getEntryId());
			documentationEntryHistoric.setReadability(documentationEntry.getReadability());
			readabilityScoreSum.compute(documentationEntry.getDocumentationId(), (k,v)-> v==null ? documentationEntry.getReadability() : v+documentationEntry.getReadability());
			documentationEntryCounter.compute(documentationEntry.getDocumentationId(), (k,v)-> v==null ? 1 : v+1);
			
			documentationHistoricMetric.getDocumentationEntriesReadability().add(documentationEntryHistoric);
		}
		
		if(documentationEntryCounter.size()>0)
		{
			Double readabilityScoreAvg;
			for(String documentationId : documentationEntryCounter.keySet())
			{
				DocumentationHistoricReadability documentationHistoric = new DocumentationHistoricReadability();
				documentationHistoric.setDocumentationId(documentationId);
				documentationHistoric.setNumberOfDocumentationEntries(documentationEntryCounter.get(documentationId));
				readabilityScoreAvg=readabilityScoreSum.get(documentationId)/(double) documentationEntryCounter.get(documentationId);
				documentationHistoric.setAverageDocumentationReadability(readabilityScoreAvg);
				
				documentationHistoricMetric.getDocumentationReadability().add(documentationHistoric);
			}
		}
		
		
		return documentationHistoricMetric;
	}

}
