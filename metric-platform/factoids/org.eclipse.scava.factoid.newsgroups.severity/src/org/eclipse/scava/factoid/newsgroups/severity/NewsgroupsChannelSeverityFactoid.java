/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.factoid.newsgroups.severity;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.newsgroups.severity.SeverityHistoricMetricProvider;
import org.eclipse.scava.metricprovider.historic.newsgroups.severity.model.NewsgroupData;
import org.eclipse.scava.metricprovider.historic.newsgroups.severity.model.NewsgroupsSeveritiesHistoricMetric;
import org.eclipse.scava.metricprovider.historic.newsgroups.severity.model.SeverityLevel;
import org.eclipse.scava.metricprovider.historic.newsgroups.severityresponsetime.SeverityResponseTimeHistoricMetricProvider;
import org.eclipse.scava.metricprovider.historic.newsgroups.severityresponsetime.model.NewsgroupsSeverityResponseTimeHistoricMetric;
import org.eclipse.scava.metricprovider.historic.newsgroups.severitysentiment.SeveritySentimentHistoricMetricProvider;
import org.eclipse.scava.metricprovider.historic.newsgroups.severitysentiment.model.NewsgroupsSeveritySentimentHistoricMetric;
import org.eclipse.scava.platform.AbstractFactoidMetricProvider;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.factoids.Factoid;
import org.eclipse.scava.platform.factoids.StarRating;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class NewsgroupsChannelSeverityFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "NewsgroupChannelSeverity";
	}

	@Override
	public String getFriendlyName() {
		return "Newsgroup Channel Severity";
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
		return Arrays.asList(SeverityResponseTimeHistoricMetricProvider.IDENTIFIER,
							 SeverityHistoricMetricProvider.IDENTIFIER,
							 SeveritySentimentHistoricMetricProvider.IDENTIFIER);
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
//		factoid.setCategory(FactoidCategory.BUGS);
		factoid.setName(getFriendlyName());

		SeverityHistoricMetricProvider severityProvider = null;
		SeverityResponseTimeHistoricMetricProvider severityResponseTimeProvider = null;
		SeveritySentimentHistoricMetricProvider severitySentimentProvider = null;

		for (IMetricProvider m : this.uses) {
			if (m instanceof SeverityHistoricMetricProvider) {
				severityProvider = (SeverityHistoricMetricProvider) m;
				continue;
			}
			if (m instanceof SeverityResponseTimeHistoricMetricProvider) {
				severityResponseTimeProvider = (SeverityResponseTimeHistoricMetricProvider) m;
				continue;
			}
			if (m instanceof SeveritySentimentHistoricMetricProvider) {
				severitySentimentProvider = (SeveritySentimentHistoricMetricProvider) m;
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
		List<Pongo> severityList = 
						severityProvider.getHistoricalMeasurements(context, project, start, end),
					severityResponseTimeList = 
						severityResponseTimeProvider.getHistoricalMeasurements(context, project, start, end),
					severitySentimentList = 
						severitySentimentProvider.getHistoricalMeasurements(context, project, start, end);
		
		int numberOfBugs = getNumberOfBugs(severityList),
			numberOfBlockerBugs = getNumberOfSeverityBugs(severityList, "blocker"),
			numberOfCriticalBugs = getNumberOfSeverityBugs(severityList, "critical"),
			numberOfMajorBugs = getNumberOfSeverityBugs(severityList, "major"),
//			numberOfNormalBugs = getNumberOfSeverityBugs(severityList, "normal"),
//			numberOfMinorBugs = getNumberOfSeverityBugs(severityList, "minor"),
//			numberOfTrivialBugs = getNumberOfSeverityBugs(severityList, "trivial"),
			numberOfEnhancementBugs = getNumberOfSeverityBugs(severityList, "enhancement"),
//			numberOfNonSeriousBugs = numberOfNormalBugs + numberOfMinorBugs+ numberOfTrivialBugs,
			numberOfSeriousBugs = numberOfBlockerBugs + numberOfCriticalBugs + numberOfMajorBugs;
		
		float seriousBugsPercentage = 0,
			  enhancementBugsPercentage = 0;
		
		if (numberOfBugs>0) {
			enhancementBugsPercentage = ( (float) 100 * (numberOfEnhancementBugs) ) / numberOfBugs;
			seriousBugsPercentage = 	  ( (float) 100 * (numberOfSeriousBugs) ) / numberOfBugs;
		}
		
		StringBuffer stringBuffer = new StringBuffer();
		DecimalFormat decimalFormat = new DecimalFormat("#.##");

		stringBuffer.append("Over the lifetime of the project there have been ");
		if ( seriousBugsPercentage > 50 ) {
			factoid.setStars(StarRating.ONE);
			stringBuffer.append("many");
		} else if ( seriousBugsPercentage > 25 ) {
			factoid.setStars(StarRating.TWO);
			stringBuffer.append("not so many");
		} else if ( seriousBugsPercentage > 12.5 ) {
			factoid.setStars(StarRating.THREE);
			stringBuffer.append("few");
		} else {
			factoid.setStars(StarRating.FOUR);
			stringBuffer.append("very few");
		}
		stringBuffer.append(" (");
		stringBuffer.append(decimalFormat.format(seriousBugsPercentage));
		stringBuffer.append(" %) newsgroup threads that report serious (i.e. major," +
							" critical and blocker) software defects.\n");
		
		int // numberOfRTBugs = getNumberOfResponseTimeBugs(severityResponseTimeList),
			numberOfRTBlockerBugs = getNumberOfSeverityResponseTimeBugs(severityResponseTimeList, "blocker"),
			numberOfRTCriticalBugs = getNumberOfSeverityResponseTimeBugs(severityResponseTimeList, "critical"),
			numberOfRTMajorBugs = getNumberOfSeverityResponseTimeBugs(severityResponseTimeList, "major"),
			numberOfRTNormalBugs = getNumberOfSeverityResponseTimeBugs(severityResponseTimeList, "normal"),
			numberOfRTMinorBugs = getNumberOfSeverityResponseTimeBugs(severityResponseTimeList, "minor"),
			numberOfRTTrivialBugs = getNumberOfSeverityResponseTimeBugs(severityResponseTimeList, "trivial"),
//			numberOfRTEnhancementBugs = getNumberOfSeverityResponseTimeBugs(severityResponseTimeList, "enhancement"),
			numberOfRTSeriousBugs = numberOfRTBlockerBugs + numberOfRTCriticalBugs + numberOfRTMajorBugs,
			numberOfRTNonSeriousBugs = numberOfRTNormalBugs + numberOfRTMinorBugs+ numberOfRTTrivialBugs;
		
		long responseTimeBlockerBugs = getResponseTimeOfSeverityBugs(severityResponseTimeList, "blocker"),
			 responseTimeCriticalBugs = getResponseTimeOfSeverityBugs(severityResponseTimeList, "critical"),
			 responseTimeMajorBugs = getResponseTimeOfSeverityBugs(severityResponseTimeList, "major"),
			 responseTimeNormalBugs = getResponseTimeOfSeverityBugs(severityResponseTimeList, "normal"),
			 responseTimeMinorBugs = getResponseTimeOfSeverityBugs(severityResponseTimeList, "minor"),
			 responseTimeTrivialBugs = getResponseTimeOfSeverityBugs(severityResponseTimeList, "trivial"),
//			 responseTimeEnhancementBugs = getResponseTimeOfSeverityBugs(severityResponseTimeList, "enhancement"),
			 responseTimeSeriousBugs = 0,
			 responseTimeNonSeriousBugs = 0; 
				
		if ( numberOfRTSeriousBugs > 0 )
			responseTimeSeriousBugs = ( ( numberOfRTBlockerBugs * responseTimeBlockerBugs ) + 
										( numberOfRTCriticalBugs * responseTimeCriticalBugs ) + 
										( numberOfRTMajorBugs * responseTimeMajorBugs ) ) / numberOfRTSeriousBugs;

		if ( numberOfRTNonSeriousBugs > 0 )
			responseTimeNonSeriousBugs = ( ( numberOfRTNormalBugs * responseTimeNormalBugs ) + 
										   ( numberOfRTMinorBugs * responseTimeMinorBugs ) + 
										   ( numberOfRTTrivialBugs * responseTimeTrivialBugs ) ) / numberOfRTNonSeriousBugs;

		int eightHoursMilliSeconds = 8 * 60 * 60 * 1000, 
			dayMilliSeconds = 3 * eightHoursMilliSeconds,
			weekMilliSeconds = 7 * dayMilliSeconds;

		if ( (responseTimeSeriousBugs > 0) && (responseTimeNonSeriousBugs > 0 ) ) {
			stringBuffer.append("These threads typically receive a response ");
			if ( responseTimeSeriousBugs < eightHoursMilliSeconds )
				stringBuffer.append("very quickly (within 8 hours).\n");
			else if ( responseTimeSeriousBugs < dayMilliSeconds )
				stringBuffer.append("quickly (within a day).\n");
			else if ( responseTimeSeriousBugs < weekMilliSeconds )
				stringBuffer.append("not so quickly (within a week).\n");
			else
				stringBuffer.append("quite slowly (in more than a week).\n");
			
			stringBuffer.append("On average, threads about serious issues are addressed ");
			if ( Math.abs( responseTimeSeriousBugs - responseTimeNonSeriousBugs ) < eightHoursMilliSeconds )
				stringBuffer.append("equally quickly to");
			else if ( responseTimeSeriousBugs > responseTimeNonSeriousBugs )
				stringBuffer.append("more quickly than");
			else 
				stringBuffer.append("less quickly than");
			stringBuffer.append(" threads about less serious issues.\n");
		}

		int // numberOfSentBugs = getNumberOfSentimentBugs(severitySentimentList),
			numberOfSentBlockerBugs = getNumberOfSeveritySentimentBugs(severitySentimentList, "blocker"),
			numberOfSentCriticalBugs = getNumberOfSeveritySentimentBugs(severitySentimentList, "critical"),
			numberOfSentMajorBugs = getNumberOfSeveritySentimentBugs(severitySentimentList, "major"),
			numberOfSentNormalBugs = getNumberOfSeveritySentimentBugs(severitySentimentList, "normal"),
			numberOfSentMinorBugs = getNumberOfSeveritySentimentBugs(severitySentimentList, "minor"),
			numberOfSentTrivialBugs = getNumberOfSeveritySentimentBugs(severitySentimentList, "trivial"),
//			numberOfSentEnhancementBugs = getNumberOfSeveritySentimentBugs(severitySentimentList, "enhancement"),
			numberOfSentSeriousBugs = numberOfSentBlockerBugs + numberOfSentCriticalBugs + numberOfSentMajorBugs,
			numberOfSentNonSeriousBugs = numberOfSentNormalBugs + numberOfSentMinorBugs+ numberOfSentTrivialBugs;
			
			float sentimentBlockerBugs = getSentimentOfSeverityBugs(severitySentimentList, "blocker"),
				  sentimentCriticalBugs = getSentimentOfSeverityBugs(severitySentimentList, "critical"),
				  sentimentMajorBugs = getSentimentOfSeverityBugs(severitySentimentList, "major"),
				  sentimentNormalBugs = getSentimentOfSeverityBugs(severitySentimentList, "normal"),
				  sentimentMinorBugs = getSentimentOfSeverityBugs(severitySentimentList, "minor"),
				  sentimentTrivialBugs = getSentimentOfSeverityBugs(severitySentimentList, "trivial"),
//				  sentimentEnhancementBugs = getSentimentOfSeverityBugs(severitySentimentList, "enhancement"),
				  sentimentSeriousBugs = 0,
				  sentimentNonSeriousBugs = 0;
			
			if ( numberOfSentSeriousBugs > 0 )
				sentimentSeriousBugs = 
				 	( ( numberOfSentBlockerBugs * sentimentBlockerBugs ) + 
				 	  ( numberOfSentCriticalBugs * sentimentCriticalBugs ) + 
				 	  ( numberOfSentMajorBugs * sentimentMajorBugs ) ) / numberOfSentSeriousBugs;
			
			if ( numberOfSentNonSeriousBugs > 0 )
				sentimentNonSeriousBugs = 
				 	( ( numberOfSentNormalBugs * sentimentNormalBugs ) + 
				 	  ( numberOfSentMinorBugs * sentimentMinorBugs ) + 
				 	  ( numberOfSentTrivialBugs * sentimentTrivialBugs ) ) / numberOfSentNonSeriousBugs;
					
			if ( (numberOfSentSeriousBugs > 0) && (numberOfSentNonSeriousBugs >0 ) ) {
				stringBuffer.append("On average, users express ");
				if ( Math.abs( sentimentSeriousBugs - sentimentNonSeriousBugs ) < 0.1 )
					stringBuffer.append("equally positive");
				else if ( sentimentSeriousBugs > sentimentNonSeriousBugs )
					stringBuffer.append("more positive");
				else
					stringBuffer.append("more negative");
				stringBuffer.append(" sentiments about how serious issues are being resolved " +
									"than how all other issues are being resolved.\n");
			}

			stringBuffer.append("There have been ");
			if ( enhancementBugsPercentage > 50 )
				stringBuffer.append("many");
			else if ( enhancementBugsPercentage > 25 )
				stringBuffer.append("not so many");
			else if ( enhancementBugsPercentage > 12.5 )
				stringBuffer.append("few");
			else
				stringBuffer.append("very few");
			stringBuffer.append(" (");
			stringBuffer.append(decimalFormat.format(enhancementBugsPercentage));
			stringBuffer.append(" %) enhancement requests.");

		factoid.setFactoid(stringBuffer.toString());

	}

//	private int getNumberOfSentimentBugs(List<Pongo> severitySentimentList) {
//		if ( severitySentimentList.size() > 0 ) {
//			int numberOfThreads = 0;
//			NewsgroupsSeveritySentimentHistoricMetric severityPongo = 
//					(NewsgroupsSeveritySentimentHistoricMetric) 
//							severitySentimentList.get(severitySentimentList.size()-1);
//			for (org.eclipse.scava.metricprovider.historic.newsgroups.severitysentiment.model.SeverityLevel 
//						severityLevel: severityPongo.getSeverityLevels())
//				numberOfThreads += severityLevel.getNumberOfThreads();
//			return numberOfThreads;
//		}
//		return 0;
//	}

	private int getNumberOfSeveritySentimentBugs(List<Pongo> severitySentimentList, String severityType) {
		if ( severitySentimentList.size() > 0 ) {
			int numberOfThreads = 0;
			NewsgroupsSeveritySentimentHistoricMetric severityPongo = 
					(NewsgroupsSeveritySentimentHistoricMetric) 
							severitySentimentList.get(severitySentimentList.size()-1);
			for (org.eclipse.scava.metricprovider.historic.newsgroups.severitysentiment.model.SeverityLevel 
						severityLevel: severityPongo.getSeverityLevels())
				if (severityLevel.getSeverityLevel().equals(severityType))
					numberOfThreads += severityLevel.getNumberOfThreads();
			return numberOfThreads;
		}
		return 0;
	}

	private float getSentimentOfSeverityBugs(List<Pongo> severitySentimentList, String severityType) {
		if ( severitySentimentList.size() > 0 ) {
			int numberOfThreads = 0;
			float threadsSentimentProduct = 0;
			NewsgroupsSeveritySentimentHistoricMetric severityPongo = 
					(NewsgroupsSeveritySentimentHistoricMetric) 
							severitySentimentList.get(severitySentimentList.size()-1);
			for (org.eclipse.scava.metricprovider.historic.newsgroups.severitysentiment.model.SeverityLevel 
						severityLevel: severityPongo.getSeverityLevels())
				if (severityLevel.getSeverityLevel().equals(severityType)) {
					int threads = severityLevel.getNumberOfThreads();
					numberOfThreads += threads;
					float responseTime = severityLevel.getAverageSentiment();
					threadsSentimentProduct += ( threads * responseTime );
				}
			return threadsSentimentProduct / numberOfThreads;
		}
		return 0;
	}
	
//	private int getNumberOfResponseTimeBugs(List<Pongo> severityResponseTimeList) {
//		if ( severityResponseTimeList.size() > 0 ) {
//			int numberOfThreads = 0;
//			NewsgroupsSeverityResponseTimeHistoricMetric severityPongo = 
//					(NewsgroupsSeverityResponseTimeHistoricMetric) 
//							severityResponseTimeList.get(severityResponseTimeList.size()-1);
//			for (org.eclipse.scava.metricprovider.historic.newsgroups.severityresponsetime.model.SeverityLevel 
//						severityLevel: severityPongo.getSeverityLevels())
//				numberOfThreads += severityLevel.getNumberOfThreads();
//			return numberOfThreads;
//		}
//		return 0;
//	}

	private int getNumberOfSeverityResponseTimeBugs(List<Pongo> severityResponseTimeList, String severityType) {
		if ( severityResponseTimeList.size() > 0 ) {
			int numberOfThreads = 0;
			NewsgroupsSeverityResponseTimeHistoricMetric severityPongo = 
					(NewsgroupsSeverityResponseTimeHistoricMetric) 
							severityResponseTimeList.get(severityResponseTimeList.size()-1);
			for (org.eclipse.scava.metricprovider.historic.newsgroups.severityresponsetime.model.SeverityLevel 
					severityLevel: severityPongo.getSeverityLevels())
				if (severityLevel.getSeverityLevel().equals(severityType))
					numberOfThreads += severityLevel.getNumberOfThreads();
			return numberOfThreads;
		}
		return 0;
	}

	private int getNumberOfSeverityBugs(List<Pongo> severityList, String severityType) {
		if ( severityList.size() > 0 ) {
			int numberOfThreads = 0;
			NewsgroupsSeveritiesHistoricMetric severityPongo = 
					(NewsgroupsSeveritiesHistoricMetric) severityList.get(severityList.size()-1);
			for (SeverityLevel severityLevel: severityPongo.getSeverityLevels())
				if (severityLevel.getSeverityLevel().equals(severityType))
					numberOfThreads += severityLevel.getNumberOfThreads();
			return numberOfThreads;
		}
		return 0;
	}

	private long getResponseTimeOfSeverityBugs(List<Pongo> severityResponseTimeList, String severityType) {
		if ( severityResponseTimeList.size() > 0 ) {
			int numberOfThreads = 0;
			long threadsResponseTimeProduct = 0;
			NewsgroupsSeverityResponseTimeHistoricMetric severityPongo = 
					(NewsgroupsSeverityResponseTimeHistoricMetric) 
							severityResponseTimeList.get(severityResponseTimeList.size()-1);
			
			for (org.eclipse.scava.metricprovider.historic.newsgroups.severityresponsetime.model.SeverityLevel 
						severityLevel: severityPongo.getSeverityLevels())
				if (severityLevel.getSeverityLevel().equals(severityType)) {
					int threads = severityLevel.getNumberOfThreads();
					numberOfThreads += threads;
					long responseTime = severityLevel.getAvgResponseTime();
					threadsResponseTimeProduct += ( threads * responseTime );
				}
			if (numberOfThreads>0)
				return threadsResponseTimeProduct / numberOfThreads;
			else
				return 0;
		}
		return 0;
	}

	private int getNumberOfBugs(List<Pongo> severityList) {
		if ( severityList.size() > 0 ) {
			int numberOfThreads = 0;
			NewsgroupsSeveritiesHistoricMetric severityPongo = 
					(NewsgroupsSeveritiesHistoricMetric) severityList.get(severityList.size()-1);
			for (NewsgroupData newsgroupData: severityPongo.getNewsgroupData())
				numberOfThreads += newsgroupData.getNumberOfThreads();
			return numberOfThreads;
		}
		return 0;
	}

}
