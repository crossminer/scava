/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.sentiment;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.bugs.sentiment.model.BugsSentimentHistoricMetric;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.BugMetadataTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugsBugMetadataTransMetric;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class SentimentHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.eclipse.scava.metricprovider.historic.bugs.sentiment";

	protected MetricProviderContext context;
	
	/**
	 * List of MPs that are used by this MP. These are MPs who have specified that 
	 * they 'provide' data for this MP.
	 */
	protected List<IMetricProvider> uses;
	
	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	}
	
	@Override
	public boolean appliesTo(Project project) {
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public Pongo measure(Project project) {
		BugsSentimentHistoricMetric overallSentimentBugs = new BugsSentimentHistoricMetric();
		if (uses.size()==1) {
			 BugsBugMetadataTransMetric usedBhm = ((BugMetadataTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			 float overallSentiment = 0,
				   startSentiment = 0,
				   endSentiment = 0;
			 for (BugData bugData: usedBhm.getBugData()) {
				 overallSentiment += bugData.getAverageSentiment();
				 String start = bugData.getStartSentiment();
				 if (start.equals("__label__positive"))
					 startSentiment+=1;
				 else if (start.equals("__label__negative"))
					 startSentiment-=1;
				 String end = bugData.getEndSentiment();
				 if (end.equals("__label__positive"))
					 endSentiment+=1;
				 else if (end.equals("__label__negative"))
					 endSentiment-=1;
			 }
			 long size = usedBhm.getBugData().size();
			 if (size>0) {
				 overallSentiment /= size;
				 startSentiment /= size;
				 endSentiment /= size;
			 }
			 
			 overallSentimentBugs.setOverallAverageSentiment(overallSentiment);
			 overallSentimentBugs.setOverallSentimentAtThreadBeggining(startSentiment);
			 overallSentimentBugs.setOverallSentimentAtThreadEnd(endSentiment);
		}
		return overallSentimentBugs;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(BugMetadataTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "bugsentiment";
	}

	@Override
	public String getFriendlyName() {
		return "Overall Sentiment of Bugs";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the overall sentiment per bug repository up to the processing date." +
				"The overall sentiment score ranges from -1 (negative sentiment) to +1 (positive sentiment)." +
				"In the computation, the sentiment score of each thread contributes equally, independently of its size.";
	}
}
