/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.hourlyrequestsreplies;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.newsgroups.hourlyrequestsreplies.model.HourArticles;
import org.eclipse.scava.metricprovider.trans.newsgroups.hourlyrequestsreplies.model.NewsgroupsHourlyRequestsRepliesTransMetric;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.RequestReplyClassificationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.NewsgroupArticles;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.RequestReplyClassificationTransMetric;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelProjectDelta;
import org.eclipse.scava.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.mongodb.DB;

public class HourlyRequestsRepliesTransMetricProvider implements ITransientMetricProvider<NewsgroupsHourlyRequestsRepliesTransMetric>{

	protected PlatformCommunicationChannelManager communicationChannelManager;

	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;

	@Override
	public String getIdentifier() {
		return HourlyRequestsRepliesTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
			if (communicationChannel instanceof Discussion) return true;
		}
		return false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(RequestReplyClassificationTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
	}

	@Override
	public NewsgroupsHourlyRequestsRepliesTransMetric adapt(DB db) {
		return new NewsgroupsHourlyRequestsRepliesTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, NewsgroupsHourlyRequestsRepliesTransMetric db) {
		
		String[] hoursOfDay = new String[]{"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
		for (String hour : hoursOfDay) {
			HourArticles hourArticles = db.getHourArticles().findOneByHour(hour+":00");
			if (hourArticles == null) {
				hourArticles = new HourArticles();
				hourArticles.setHour(hour+":00");
				hourArticles.setNumberOfArticles(0);
				hourArticles.setNumberOfRequests(0);
				hourArticles.setNumberOfReplies(0);
				db.getHourArticles().add(hourArticles);
				db.sync();
			}
		}

		CommunicationChannelProjectDelta delta = projectDelta.getCommunicationChannelDelta();
		
		RequestReplyClassificationTransMetric usedClassifier = 
				((RequestReplyClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		for ( CommunicationChannelDelta communicationChannelDelta: delta.getCommunicationChannelSystemDeltas()) {
			CommunicationChannel communicationChannel = communicationChannelDelta.getCommunicationChannel();
			String communicationChannelName;
			if (!(communicationChannel instanceof NntpNewsGroup))
				communicationChannelName = communicationChannel.getUrl();
			else {
				NntpNewsGroup newsgroup = (NntpNewsGroup) communicationChannel;
				communicationChannelName = newsgroup.getNewsGroupName();
			}
			
			List<CommunicationChannelArticle> articles = communicationChannelDelta.getArticles();
			for (CommunicationChannelArticle article: articles) {
				@SuppressWarnings("deprecation")
				String hourNumber = String.format("%02d", article.getDate().getHours());
				
				HourArticles hourArticles = db.getHourArticles().findOneByHour(hourNumber + ":00");
				hourArticles.setNumberOfArticles(hourArticles.getNumberOfArticles()+1);
				String requestReplyClass = 
						getRequestReplyClass(usedClassifier, communicationChannelName, article);
				if (requestReplyClass.equals("Request"))
					hourArticles.setNumberOfRequests(hourArticles.getNumberOfRequests()+1);
				else if (requestReplyClass.equals("Reply"))
					hourArticles.setNumberOfReplies(hourArticles.getNumberOfReplies()+1);
				db.sync();
			}
		}

		int sumOfArticles = 0,
			sumOfRequests = 0,
			sumOfReplies = 0;

		for (HourArticles hourArticles: db.getHourArticles()) {
			sumOfArticles += hourArticles.getNumberOfArticles();
			sumOfRequests += hourArticles.getNumberOfRequests();
			sumOfReplies += hourArticles.getNumberOfReplies();
		}

		for (HourArticles hourArticles: db.getHourArticles()) {
			
			float percentageOfComments;
			if (sumOfArticles == 0)
				percentageOfComments = ( (float) 100 ) / 7;
			else
				percentageOfComments = ( (float) 100 * hourArticles.getNumberOfArticles() ) / sumOfArticles;
			hourArticles.setPercentageOfArticles(percentageOfComments);
			
			float percentageOfRequests;
			if (sumOfRequests == 0)
				percentageOfRequests = ( (float) 100 ) / 7;
			else
				percentageOfRequests = ( (float) 100 * hourArticles.getNumberOfRequests() ) / sumOfRequests;
			hourArticles.setPercentageOfRequests(percentageOfRequests);
			
			float percentageOfReplies;
			if (sumOfReplies == 0)
				percentageOfReplies = ( (float) 100 ) / 7;
			else
				percentageOfReplies = ( (float) 100 * hourArticles.getNumberOfReplies() ) / sumOfReplies;
			hourArticles.setPercentageOfReplies(percentageOfReplies);
		}
		
		db.sync();

	}

	private String getRequestReplyClass(RequestReplyClassificationTransMetric usedClassifier, 
			String communicationChannelName, CommunicationChannelArticle article) {
		Iterable<NewsgroupArticles> newsgroupArticlesIt = usedClassifier.getNewsgroupArticles().
				find(NewsgroupArticles.NEWSGROUPNAME.eq(communicationChannelName), 
						NewsgroupArticles.ARTICLENUMBER.eq(article.getArticleNumber()));
		NewsgroupArticles newsgroupArticle = null;
		for (NewsgroupArticles art:  newsgroupArticlesIt) {
			newsgroupArticle = art;
		}
		if (newsgroupArticle == null) {
			System.err.println("Newsgroups - Hourly Requests Replies -\t" + 
					"there is no classification for article: " + article.getArticleNumber() +
					"\t of newsgroup: " + communicationChannelName);
//			System.exit(-1);
		} else{
			return newsgroupArticle.getClassificationResult();
		}
		return "";
	}

	@Override
	public String getShortIdentifier() {
		return "hourlyrequestsreplies";
	}

	@Override
	public String getFriendlyName() {
		return "Number of Articles, Requests and Replies per Hour of the Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric stores the number of articles, " +
				"requests and replies for each hour of the day.";
	}

}
