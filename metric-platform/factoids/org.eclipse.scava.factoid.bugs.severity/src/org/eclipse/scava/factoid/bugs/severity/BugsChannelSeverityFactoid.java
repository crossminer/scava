/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.factoid.bugs.severity;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.bugs.severity.SeverityHistoricMetricProvider;
import org.eclipse.scava.metricprovider.historic.bugs.severity.model.BugData;
import org.eclipse.scava.metricprovider.historic.bugs.severity.model.BugsSeveritiesHistoricMetric;
import org.eclipse.scava.metricprovider.historic.bugs.severity.model.SeverityLevel;
import org.eclipse.scava.metricprovider.historic.bugs.severitybugstatus.SeverityBugStatusHistoricMetricProvider;
import org.eclipse.scava.metricprovider.historic.bugs.severitybugstatus.model.BugsSeverityBugStatusHistoricMetric;
import org.eclipse.scava.metricprovider.historic.bugs.severityresponsetime.SeverityResponseTimeHistoricMetricProvider;
import org.eclipse.scava.metricprovider.historic.bugs.severityresponsetime.model.BugsSeverityResponseTimeHistoricMetric;
import org.eclipse.scava.metricprovider.historic.bugs.severitysentiment.SeveritySentimentHistoricMetricProvider;
import org.eclipse.scava.metricprovider.historic.bugs.severitysentiment.model.BugsSeveritySentimentHistoricMetric;
import org.eclipse.scava.platform.AbstractFactoidMetricProvider;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.factoids.Factoid;
import org.eclipse.scava.platform.factoids.StarRating;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class BugsChannelSeverityFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "BugChannelSeverity";
	}

	@Override
	public String getFriendlyName() {
		return "Bug Tracker Severity";
		// This method will NOT be removed in a later version.
	}

	@Override
	public String getSummaryInformation() {
		return "summaryblah"; // This method will NOT be removed in a later version.
	}

	@Override
	public boolean appliesTo(Project project) {
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(SeverityHistoricMetricProvider.IDENTIFIER,
							 SeverityBugStatusHistoricMetricProvider.IDENTIFIER,
							 SeverityResponseTimeHistoricMetricProvider.IDENTIFIER,
							 SeveritySentimentHistoricMetricProvider.IDENTIFIER);
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
//		factoid.setCategory(FactoidCategory.BUGS);
		factoid.setName(getFriendlyName());
	
		SeverityHistoricMetricProvider severityProvider = null;
		SeverityBugStatusHistoricMetricProvider severityBugStatusProvider = null;
		SeverityResponseTimeHistoricMetricProvider severityResponseTimeProvider = null;
		SeveritySentimentHistoricMetricProvider severitySentimentProvider = null;

		for (IMetricProvider m : this.uses) {
			if (m instanceof SeverityHistoricMetricProvider) {
				severityProvider = (SeverityHistoricMetricProvider) m;
				continue;
			}
			if (m instanceof SeverityBugStatusHistoricMetricProvider) {
				severityBugStatusProvider = (SeverityBugStatusHistoricMetricProvider) m;
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
//			start = new Date("20050301");
//			end = new Date("20060301");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		List<Pongo> severityList = 
						severityProvider.getHistoricalMeasurements(context, project, start, end),
					severityStatusList = 
						severityBugStatusProvider.getHistoricalMeasurements(context, project, start, end),
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
		
		float seriousBugsPercentage = ( (float) 100 * (numberOfSeriousBugs) ) / numberOfBugs,
			  enhancementBugsPercentage = ( (float) 100 * (numberOfEnhancementBugs) ) / numberOfBugs;
		
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
		stringBuffer.append(" %) bugs that report serious (i.e. major," +
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
//	 		 responseTimeEnhancementBugs = getResponseTimeOfSeverityBugs(severityResponseTimeList, "enhancement"),
			 responseTimeSeriousBugs = 0,
			 responseTimeNonSeriousBugs = 0; 
		
		if ( numberOfRTSeriousBugs > 0 )
			responseTimeSeriousBugs = 
			 	( ( numberOfRTBlockerBugs * responseTimeBlockerBugs ) + 
			 	  ( numberOfRTCriticalBugs * responseTimeCriticalBugs ) + 
			 	  ( numberOfRTMajorBugs * responseTimeMajorBugs ) ) / numberOfRTSeriousBugs;
		
		if ( numberOfRTNonSeriousBugs > 0 )
			responseTimeNonSeriousBugs = 
				( ( numberOfRTNormalBugs * responseTimeNormalBugs ) + 
				  ( numberOfRTMinorBugs * responseTimeMinorBugs ) + 
				  ( numberOfRTTrivialBugs * responseTimeTrivialBugs ) ) / numberOfRTNonSeriousBugs;
		
		int eightHoursMilliSeconds = 8 * 60 * 60 * 1000, 
			dayMilliSeconds = 3 * eightHoursMilliSeconds,
			weekMilliSeconds = 7 * dayMilliSeconds;

		if ( (responseTimeSeriousBugs > 0) && (responseTimeNonSeriousBugs > 0 ) ) {
			stringBuffer.append("These bugs typically receive a response ");
			if ( responseTimeSeriousBugs < eightHoursMilliSeconds )
				stringBuffer.append("very quickly (within 8 hours).\n");
			else if ( responseTimeSeriousBugs < dayMilliSeconds )
				stringBuffer.append("quickly (within a day).\n");
			else if ( responseTimeSeriousBugs < weekMilliSeconds )
				stringBuffer.append("not so quickly (within a week).\n");
			else
				stringBuffer.append("quite slowly (in more than a week).\n");
			
			stringBuffer.append("On average, bugs about serious issues are addressed ");
			if ( Math.abs( responseTimeSeriousBugs - responseTimeNonSeriousBugs ) < eightHoursMilliSeconds )
				stringBuffer.append("equally quickly to");
			else if ( responseTimeSeriousBugs > responseTimeNonSeriousBugs )
				stringBuffer.append("more quickly than");
			else 
				stringBuffer.append("less quickly than");
			stringBuffer.append(" bugs about less serious issues.\n");
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
//			  sentimentEnhancementBugs = getSentimentOfSeverityBugs(severitySentimentList, "enhancement"),
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
		
		int numberOfStatusBlockerBugs = getNumberOfSeverityStatusBugs(severityStatusList, "blocker"),
			numberOfStatusBlockerFixedBugs = getNumberOfSeverityStatusFixedBugs(severityStatusList, "blocker"),
			numberOfStatusCriticalBugs = getNumberOfSeverityStatusBugs(severityStatusList, "critical"),
			numberOfStatusCriticalFixedBugs = getNumberOfSeverityStatusFixedBugs(severityStatusList, "critical"),
			numberOfStatusMajorBugs = getNumberOfSeverityStatusBugs(severityStatusList, "major"),
			numberOfStatusMajorFixedBugs = getNumberOfSeverityStatusFixedBugs(severityStatusList, "major"),
			numberOfStatusEnhancementBugs = getNumberOfSeverityStatusBugs(severityStatusList, "enhancement"),
			numberOfStatusEnhancementFixedBugs = getNumberOfSeverityStatusFixedBugs(severityStatusList, "enhancement"),
			numberOfStatusSeriousBugs = numberOfStatusBlockerBugs + numberOfStatusCriticalBugs + numberOfStatusMajorBugs,
			numberOfStatusSeriousFixedBugs = numberOfStatusBlockerFixedBugs + numberOfStatusCriticalFixedBugs + numberOfStatusMajorFixedBugs;
			
		float percentageOfStatusSeriousFixedBugs = 0,
			  percentageOfStatusEnhancementFixedBugs = 0;
		
		if ( numberOfStatusSeriousBugs > 0 )
			percentageOfStatusSeriousFixedBugs = ( (float) 100 * numberOfStatusSeriousFixedBugs ) / numberOfStatusSeriousBugs;
		
		if ( numberOfStatusEnhancementBugs > 0 )
			percentageOfStatusEnhancementFixedBugs = ( (float) 100 * numberOfStatusEnhancementFixedBugs ) / numberOfStatusEnhancementBugs;
		
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
		stringBuffer.append(decimalFormat.format(percentageOfStatusSeriousFixedBugs));
		stringBuffer.append(" % of serious bugs were fixed.\n");

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
		stringBuffer.append(" %) enhancement requests, of which ");
		stringBuffer.append(decimalFormat.format(percentageOfStatusEnhancementFixedBugs));
		stringBuffer.append(" % were fixed.\n");

		factoid.setFactoid(stringBuffer.toString());

	}

	private int getNumberOfSeverityStatusBugs(List<Pongo> severityStatusList, String severityType) {
		if ( severityStatusList.size() > 0 ) {
			int numberOfBugs = 0;
			BugsSeverityBugStatusHistoricMetric severityPongo =
					(BugsSeverityBugStatusHistoricMetric) severityStatusList.get(severityStatusList.size()-1);
			for (org.eclipse.scava.metricprovider.historic.bugs.severitybugstatus.model.SeverityLevel 
					severityLevel: severityPongo.getSeverityLevels())
				if (severityLevel.getSeverityLevel().equals(severityType))
					numberOfBugs += severityLevel.getNumberOfBugs();
			return numberOfBugs;
		}
		return 0;
	}

	private int getNumberOfSeverityStatusFixedBugs(List<Pongo> severityStatusList, String severityType) {
		if ( severityStatusList.size() > 0 ) {
			int numberOfBugs = 0;
			BugsSeverityBugStatusHistoricMetric severityPongo =
					(BugsSeverityBugStatusHistoricMetric) severityStatusList.get(severityStatusList.size()-1);
			for (org.eclipse.scava.metricprovider.historic.bugs.severitybugstatus.model.SeverityLevel 
					severityLevel: severityPongo.getSeverityLevels())
				if (severityLevel.getSeverityLevel().equals(severityType))
					numberOfBugs += severityLevel.getNumberOfFixedBugs();
			return numberOfBugs;
		}
		return 0;
	}

//	private int getNumberOfSentimentBugs(List<Pongo> severitySentimentList) {
//		if ( severitySentimentList.size() > 0 ) {
//			int numberOfBugs = 0;
//			BugsSeveritySentimentHistoricMetric severityPongo = 
//					(BugsSeveritySentimentHistoricMetric) 
//					severitySentimentList.get(severitySentimentList.size()-1);
//			for (org.eclipse.scava.metricprovider.historic.bugs.severitysentiment.model.SeverityLevel 
//					severityLevel: severityPongo.getSeverityLevels())
//				numberOfBugs += severityLevel.getNumberOfBugs();
//			return numberOfBugs;
//		}
//		return 0;
//	}

	private int getNumberOfSeveritySentimentBugs(List<Pongo> severitySentimentList, String severityType) {
		if ( severitySentimentList.size() > 0 ) {
			int numberOfBugs = 0;
			BugsSeveritySentimentHistoricMetric severityPongo = 
					(BugsSeveritySentimentHistoricMetric) 
					severitySentimentList.get(severitySentimentList.size()-1);
			for (org.eclipse.scava.metricprovider.historic.bugs.severitysentiment.model.SeverityLevel 
					severityLevel: severityPongo.getSeverityLevels())
				if (severityLevel.getSeverityLevel().equals(severityType))
					numberOfBugs += severityLevel.getNumberOfBugs();
			return numberOfBugs;
		}
		return 0;
	}
	
	private float getSentimentOfSeverityBugs(List<Pongo> severitySentimentList, String severityType) {
		if ( severitySentimentList.size() > 0 ) {
			int numberOfBugs = 0;
			float bugsSentimentProduct = 0;
			BugsSeveritySentimentHistoricMetric severityPongo = 
					(BugsSeveritySentimentHistoricMetric) 
					severitySentimentList.get(severitySentimentList.size()-1);
			for (org.eclipse.scava.metricprovider.historic.bugs.severitysentiment.model.SeverityLevel 
					severityLevel: severityPongo.getSeverityLevels())
				if (severityLevel.getSeverityLevel().equals(severityType)) {
					int bugs = severityLevel.getNumberOfBugs();
					numberOfBugs += bugs;
					float responseTime = severityLevel.getAverageSentiment();
					bugsSentimentProduct += ( bugs * responseTime );
				}
			return bugsSentimentProduct / numberOfBugs;
		}
		return 0;
	}
	
//	private int getNumberOfResponseTimeBugs(List<Pongo> severityResponseTimeList) {
//		if ( severityResponseTimeList.size() > 0 ) {
//			int numberOfThreads = 0;
//			BugsSeverityResponseTimeHistoricMetric severityPongo = 
//					(BugsSeverityResponseTimeHistoricMetric) 
//					severityResponseTimeList.get(severityResponseTimeList.size()-1);
//			for (org.eclipse.scava.metricprovider.historic.bugs.severityresponsetime.model.SeverityLevel 
//					severityLevel: severityPongo.getSeverityLevels())
//				numberOfThreads += severityLevel.getNumberOfBugs();
//			return numberOfThreads;
//		}
//		return 0;
//	}

	private int getNumberOfSeverityResponseTimeBugs(List<Pongo> severityResponseTimeList, String severityType) {
		if ( severityResponseTimeList.size() > 0 ) {
			int numberOfBugs = 0;
			BugsSeverityResponseTimeHistoricMetric severityPongo = 
					(BugsSeverityResponseTimeHistoricMetric) 
					severityResponseTimeList.get(severityResponseTimeList.size()-1);
			for (org.eclipse.scava.metricprovider.historic.bugs.severityresponsetime.model.SeverityLevel 
					severityLevel: severityPongo.getSeverityLevels())
				if (severityLevel.getSeverityLevel().equals(severityType))
					numberOfBugs += severityLevel.getNumberOfBugs();
			return numberOfBugs;
		}
		return 0;
	}

	private long getResponseTimeOfSeverityBugs(List<Pongo> severityResponseTimeList, String severityType) {
		if ( severityResponseTimeList.size() > 0 ) {
			int numberOfBugs = 0;
			long bugsResponseTimeProduct = 0;
			BugsSeverityResponseTimeHistoricMetric severityPongo = 
					(BugsSeverityResponseTimeHistoricMetric) 
					severityResponseTimeList.get(severityResponseTimeList.size()-1);
			for (org.eclipse.scava.metricprovider.historic.bugs.severityresponsetime.model.SeverityLevel 
					severityLevel: severityPongo.getSeverityLevels())
				if (severityLevel.getSeverityLevel().equals(severityType)) {
					int bugs = severityLevel.getNumberOfBugs();
					numberOfBugs += bugs;
					long responseTime = severityLevel.getAvgResponseTime();
					bugsResponseTimeProduct += ( bugs * responseTime );
				}
			if (numberOfBugs>0)
				return bugsResponseTimeProduct / numberOfBugs;
			else
				return 0;
		}
		return 0;
	}
	
	private int getNumberOfBugs(List<Pongo> severityList) {
		if ( severityList.size() > 0 ) {
			int numberOfBugs = 0;
			BugsSeveritiesHistoricMetric severityPongo = 
					(BugsSeveritiesHistoricMetric) severityList.get(severityList.size()-1);
			for (BugData newsgroupData: severityPongo.getBugData())
				numberOfBugs += newsgroupData.getNumberOfBugs();
			return numberOfBugs;
		}
		return 0;
	}
	
	private int getNumberOfSeverityBugs(List<Pongo> severityList, String severityType) {
		if ( severityList.size() > 0 ) {
			int numberOfBugs = 0;
			BugsSeveritiesHistoricMetric severityPongo = 
					(BugsSeveritiesHistoricMetric) severityList.get(severityList.size()-1);
			for (SeverityLevel severityLevel: severityPongo.getSeverityLevels())
				if (severityLevel.getSeverityLevel().equals(severityType))
					numberOfBugs += severityLevel.getNumberOfBugs();
			return numberOfBugs;
		}
		return 0;
	}

}
