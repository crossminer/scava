/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.factoid.documentation.sentiment;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.documentation.sentiment.DocumentationSentimentHistoricMetricProvider;
import org.eclipse.scava.metricprovider.historic.documentation.sentiment.model.DocumentationHistoricSentiment;
import org.eclipse.scava.metricprovider.historic.documentation.sentiment.model.DocumentationSentimentHistoricMetric;
import org.eclipse.scava.platform.AbstractFactoidMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.factoids.Factoid;
import org.eclipse.scava.platform.factoids.StarRating;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.VcsRepository;
import org.eclipse.scava.repository.model.documentation.gitbased.DocumentationGitBased;
import org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic;

import com.googlecode.pongo.runtime.Pongo;

public class DocumentationSentimentFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "factoid.documentation.sentiment";
	}

	@Override
	public String getFriendlyName() {
		return "Documentation Sentiment";
	}

	@Override
	public String getSummaryInformation() {
		return "This plugin generates the factoid regarding sentiment for documentation."; 
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
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(DocumentationSentimentHistoricMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
		
		factoid.setName(getFriendlyName());

		DocumentationSentimentHistoricMetricProvider sentimentProvider = null;

		for (IMetricProvider m : this.uses) {
			if (m instanceof DocumentationSentimentHistoricMetricProvider) {
				sentimentProvider = (DocumentationSentimentHistoricMetricProvider) m;
				continue;
			}
		}

		List<Pongo> sentimentList = sentimentProvider.getHistoricalMeasurements(context, project, delta.getDate(), delta.getDate());
		
		double averageSentiment = getAverageSentiment(sentimentList);

		if ( averageSentiment > 0.5 ){
			factoid.setStars(StarRating.FOUR);
		} else if (averageSentiment > 0.25 ) {
			factoid.setStars(StarRating.THREE);
		} else if ( averageSentiment > 0 ) {
			factoid.setStars(StarRating.TWO);
		} else {
			factoid.setStars(StarRating.ONE);
		}
		
		StringBuffer stringBuffer = new StringBuffer();
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		
		stringBuffer.append("The average sentimental polarity in the documentation " +
							"associated with the project is ");
		if ( averageSentiment > 0.3 )
			stringBuffer.append("positive");
		else if ( averageSentiment > 0.15 )
			stringBuffer.append("weakly positive");
		else if ( averageSentiment > -0.15 )
			stringBuffer.append("neutral");
		else if ( averageSentiment > -0.3 )
			stringBuffer.append("weakly negative");
		else 
			stringBuffer.append("negative");
		stringBuffer.append(" (");
		stringBuffer.append(decimalFormat.format(averageSentiment));
		stringBuffer.append(" with -1 designating entirely negative sentiment " +
							"and 1 designating entirely positive sentiment).\n");
		
		factoid.setFactoid(stringBuffer.toString());

	}
		
	private double getAverageSentiment(List<Pongo> sentimentList) {
		if ( sentimentList.size() > 0 )
		{
			double overallAverage=0.0;
			double counter=0.0;
			DocumentationSentimentHistoricMetric sentimentPongo = (DocumentationSentimentHistoricMetric) sentimentList.get(0);
			for(DocumentationHistoricSentiment documentation : sentimentPongo.getDocumentationSentiment())
			{
				overallAverage+=documentation.getAverageDocumentationSentiment();
				counter++;
			}
			return overallAverage/counter;
		}
		return 0.0;
	}

}
