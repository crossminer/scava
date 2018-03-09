/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.factoid.newsgroups.status;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.newsgroups.requestsreplies.RequestsRepliesHistoricMetricProvider;
import org.eclipse.scava.metricprovider.historic.newsgroups.requestsreplies.average.RequestsRepliesAverageHistoricMetricProvider;
import org.eclipse.scava.metricprovider.historic.newsgroups.requestsreplies.average.model.NewsgroupsRequestsRepliesAverageHistoricMetric;
import org.eclipse.scava.metricprovider.historic.newsgroups.requestsreplies.model.NewsgroupsRequestsRepliesHistoricMetric;
import org.eclipse.scava.metricprovider.historic.newsgroups.unansweredthreads.UnansweredThreadsHistoricMetricProvider;
import org.eclipse.scava.metricprovider.historic.newsgroups.unansweredthreads.model.NewsgroupsUnansweredThreadsHistoricMetric;
import org.eclipse.scava.platform.AbstractFactoidMetricProvider;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.factoids.Factoid;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class NewsgroupsChannelStatusFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "NewsgroupChannelStatus";
	}

	@Override
	public String getFriendlyName() {
		return "Newsgroup Channel Status";
		// This method will NOT be removed in a later version.
	}

	@Override
	public String getSummaryInformation() {
		return "summaryblah"; // This method will NOT be removed in a later version.
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
		}
		return false;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(UnansweredThreadsHistoricMetricProvider.IDENTIFIER,
							 RequestsRepliesHistoricMetricProvider.IDENTIFIER,
							 RequestsRepliesAverageHistoricMetricProvider.IDENTIFIER);
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
//		factoid.setCategory(FactoidCategory.BUGS);
		factoid.setName(getFriendlyName());

		UnansweredThreadsHistoricMetricProvider unansweredThreadsProvider = null;
		RequestsRepliesHistoricMetricProvider requestsRepliesProvider = null;
		RequestsRepliesAverageHistoricMetricProvider requestsRepliesAverageProvider = null; 
		
		for (IMetricProvider m : this.uses) {
			if (m instanceof UnansweredThreadsHistoricMetricProvider) {
				unansweredThreadsProvider = (UnansweredThreadsHistoricMetricProvider) m;
				continue;
			}
			if (m instanceof RequestsRepliesHistoricMetricProvider) {
				requestsRepliesProvider = (RequestsRepliesHistoricMetricProvider) m;
				continue;
			}
			if (m instanceof RequestsRepliesAverageHistoricMetricProvider) {
				requestsRepliesAverageProvider = (RequestsRepliesAverageHistoricMetricProvider) m;
				continue;
			}
		}

		Date end = new Date();
		Date start = (new Date()).addDays(-30);
//		Date start=null, end=null;
//		try {
//			start = new Date("20040801");
//			end = new Date("20050801");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		List<Pongo> unansweredThreadsList = 
						unansweredThreadsProvider.getHistoricalMeasurements(context, project, start, end),
					requestsRepliesList = 
						requestsRepliesProvider.getHistoricalMeasurements(context, project, start, end),
					averageRequestsRepliesList = 
						requestsRepliesAverageProvider.getHistoricalMeasurements(context, project, start, end);
		
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		StringBuffer stringBuffer = new StringBuffer();		

		int numberOfRequests = getNumberOfRequests(requestsRepliesList),
			numberOfReplies = getNumberOfReplies(requestsRepliesList),
			numberOfArticles = numberOfReplies + numberOfRequests,
			numberOfUnsweredThreads = getNumberOfUnansweredThreads(unansweredThreadsList);
				
		float percentageOfRequests = 0,
			  percentageOfReplies = 0,
			  numberOfRequestsPerDay = getNumberOfRequestsPerDay(averageRequestsRepliesList),
			  numberOfRepliesPerDay = getNumberOfRepliesPerDay(averageRequestsRepliesList),
			  numberOfArticlesPerDay = numberOfRequestsPerDay + numberOfRepliesPerDay,
			  percentageOfRequestsPerDay = 0,
			  percentageOfRepliesPerDay = 0;

		if (numberOfArticles>0) {
			percentageOfRequests = ( (float) 100 * numberOfRequests ) / numberOfArticles;
			percentageOfReplies = ( (float) 100 * numberOfReplies ) / numberOfArticles;
		}
		
		if (numberOfArticlesPerDay>0) {
			percentageOfRequestsPerDay = ( (float) 100 * numberOfRequestsPerDay ) / numberOfArticlesPerDay;
			percentageOfRepliesPerDay = ( (float) 100 * numberOfRepliesPerDay ) / numberOfArticlesPerDay;
		}

		stringBuffer.append("The project's newsgroup contains ");
		stringBuffer.append(numberOfRequests);
		stringBuffer.append(" requests (");
		stringBuffer.append(decimalFormat.format(percentageOfRequests));
		stringBuffer.append(" %) and ");
		stringBuffer.append(numberOfReplies);
		stringBuffer.append(" replies (");
		stringBuffer.append(decimalFormat.format(percentageOfReplies));
		stringBuffer.append(" %).\n");
		
		stringBuffer.append("In total, ");
		stringBuffer.append(numberOfUnsweredThreads);
		stringBuffer.append(" threads are unanswered.\n");
		
		stringBuffer.append("There are approximately ");
		stringBuffer.append(decimalFormat.format(numberOfRequestsPerDay));
		stringBuffer.append(" requests (");
		stringBuffer.append(decimalFormat.format(percentageOfRequestsPerDay));
		stringBuffer.append(" %) and ");
		stringBuffer.append(decimalFormat.format(numberOfRepliesPerDay));
		stringBuffer.append(" replies (");
		stringBuffer.append(decimalFormat.format(percentageOfRepliesPerDay));
		stringBuffer.append(" %) per day.\n");
		
		factoid.setFactoid(stringBuffer.toString());

	}

	private float getNumberOfRequestsPerDay(
			List<Pongo> averageRequestsRepliesList) {
		if (averageRequestsRepliesList.size() > 0) {
			NewsgroupsRequestsRepliesAverageHistoricMetric threadsPongo = 
					(NewsgroupsRequestsRepliesAverageHistoricMetric) 
							averageRequestsRepliesList.get(averageRequestsRepliesList.size() - 1);
			return threadsPongo.getAverageRequestsPerDay();
		}
		return 0;
	}

	private float getNumberOfRepliesPerDay(
			List<Pongo> averageRequestsRepliesList) {
		if (averageRequestsRepliesList.size() > 0) {
			NewsgroupsRequestsRepliesAverageHistoricMetric threadsPongo = 
					(NewsgroupsRequestsRepliesAverageHistoricMetric) 
							averageRequestsRepliesList.get(averageRequestsRepliesList.size() - 1);
			return threadsPongo.getAverageRepliesPerDay();
		}
		return 0;
	}

	private int getNumberOfRequests(List<Pongo> requestsRepliesList) {
		if (requestsRepliesList.size() > 0) {
			NewsgroupsRequestsRepliesHistoricMetric threadsPongo = 
					(NewsgroupsRequestsRepliesHistoricMetric) 
							requestsRepliesList.get(requestsRepliesList.size() - 1);
			return threadsPongo.getCumulativeNumberOfRequests();
		}
		return 0;
	}

	private int getNumberOfReplies(List<Pongo> requestsRepliesList) {
		if (requestsRepliesList.size() > 0) {
			NewsgroupsRequestsRepliesHistoricMetric threadsPongo = 
					(NewsgroupsRequestsRepliesHistoricMetric) 
							requestsRepliesList.get(requestsRepliesList.size() - 1);
			return threadsPongo.getCumulativeNumberOfReplies();
		}
		return 0;
	}

	private int getNumberOfUnansweredThreads(List<Pongo> unansweredThreadsList) {
		if (unansweredThreadsList.size() > 0) {
			NewsgroupsUnansweredThreadsHistoricMetric unansweredThreadsPongo = 
					(NewsgroupsUnansweredThreadsHistoricMetric) 
							unansweredThreadsList.get(unansweredThreadsList.size() - 1);
			return unansweredThreadsPongo.getNumberOfUnansweredThreads();
		}
		return 0;
	}
	
}
