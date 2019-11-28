/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.emotions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.emotionclassification.EmotionClassificationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.emotionclassification.model.EmotionClassificationTransMetric;
import org.eclipse.scava.metricprovider.trans.emotionclassification.model.NewsgroupArticlesEmotionClassification;
import org.eclipse.scava.metricprovider.trans.newsgroups.emotions.model.EmotionDimension;
import org.eclipse.scava.metricprovider.trans.newsgroups.emotions.model.NewsgroupData;
import org.eclipse.scava.metricprovider.trans.newsgroups.emotions.model.NewsgroupsEmotionsTransMetric;
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
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;
import org.eclipse.scava.repository.model.cc.irc.Irc;
import org.eclipse.scava.repository.model.cc.mbox.Mbox;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.cc.sympa.SympaMailingList;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.mongodb.DB;

public class EmotionsTransMetricProvider implements ITransientMetricProvider<NewsgroupsEmotionsTransMetric>{

	protected PlatformCommunicationChannelManager communicationChannelManager;

	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;

	@Override
	public String getIdentifier() {
		return EmotionsTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
			if (communicationChannel instanceof Discussion) return true;
			if (communicationChannel instanceof EclipseForum) return true;
			if (communicationChannel instanceof SympaMailingList) return true;
			if (communicationChannel instanceof Irc) return true;
			if (communicationChannel instanceof Mbox) return true;
		}
		return false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(EmotionClassificationTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
	}

	@Override
	public NewsgroupsEmotionsTransMetric adapt(DB db) {
		return new NewsgroupsEmotionsTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, NewsgroupsEmotionsTransMetric db) {
		
		EmotionClassificationTransMetric emotionClassificationMetric = ((EmotionClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		CommunicationChannelProjectDelta delta = projectDelta.getCommunicationChannelDelta();
		
		for (CommunicationChannelDelta communicationChannelSystemDelta : delta.getCommunicationChannelSystemDeltas()) {
			CommunicationChannel communicationChannel = communicationChannelSystemDelta.getCommunicationChannel();
			String ossmeterID = communicationChannel.getOSSMeterId();
			
			Iterable<NewsgroupData> newsgroupDataIt = db.getNewsgroups().find(NewsgroupData.NEWSGROUPNAME.eq(ossmeterID));
			NewsgroupData newsgroupData = null;
			for (NewsgroupData ngd:  newsgroupDataIt) 
				newsgroupData = ngd;
			if (newsgroupData == null) {
				newsgroupData = new NewsgroupData();
				newsgroupData.setNewsgroupName(ossmeterID);
				newsgroupData.setNumberOfArticles(0);
				newsgroupData.setCumulativeNumberOfArticles(0);
				db.getNewsgroups().add(newsgroupData);
			}
			newsgroupData.setNumberOfArticles(communicationChannelSystemDelta.getArticles().size());
			newsgroupData.setCumulativeNumberOfArticles(newsgroupData.getCumulativeNumberOfArticles() + 
														communicationChannelSystemDelta.getArticles().size());

			db.sync();

			Iterable<EmotionDimension> emotionIt = db.getDimensions().find(EmotionDimension.NEWSGROUPNAME.eq(ossmeterID));
			for (EmotionDimension emotion:  emotionIt)
				emotion.setNumberOfArticles(0);

			for (CommunicationChannelArticle article: communicationChannelSystemDelta.getArticles()) {
				
				List<String> emotionalDimensions = getEmotions(emotionClassificationMetric, article, ossmeterID);
				
				for (String dimension: emotionalDimensions)
				{
					emotionIt = db.getDimensions().find(EmotionDimension.NEWSGROUPNAME.eq(ossmeterID),
										 						EmotionDimension.EMOTIONLABEL.eq(dimension));
					
					EmotionDimension emotion = null;
					for (EmotionDimension em: emotionIt)
						emotion = em;
					if (emotion == null)
					{
						emotion = new EmotionDimension();
						emotion.setNewsgroupName(ossmeterID);
						emotion.setEmotionLabel(dimension);
						emotion.setNumberOfArticles(0);
						emotion.setCumulativeNumberOfArticles(0);
						db.getDimensions().add(emotion);
					}
					emotion.setNumberOfArticles(emotion.getNumberOfArticles() + 1);
					emotion.setCumulativeNumberOfArticles(emotion.getCumulativeNumberOfArticles() + 1);
					db.sync();
				}
			}
			
			db.sync();

			emotionIt = db.getDimensions().find(EmotionDimension.NEWSGROUPNAME.eq(ossmeterID));

			for (EmotionDimension emotion: db.getDimensions())
			{
				if ( newsgroupData.getNumberOfArticles() > 0 )
					emotion.setPercentage(((float)100*emotion.getNumberOfArticles()) / newsgroupData.getNumberOfArticles());
				else
					emotion.setPercentage( (float) 0 );
				if ( newsgroupData.getCumulativeNumberOfArticles() > 0 )
					emotion.setCumulativePercentage(((float)100*emotion.getCumulativeNumberOfArticles()) / newsgroupData.getCumulativeNumberOfArticles());
				else
					emotion.setCumulativePercentage( (float) 0 );
			}

			db.sync();
		}
	}

	@Override
	public String getShortIdentifier() {
		return "trans.newsgroups.emotions";
	}

	@Override
	public String getFriendlyName() {
		return "Emotions in newsgroup articles";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the emotional dimensions in newsgroup articles, per newsgroup. "
				+ "There are 6 emotion labels (anger, fear, joy, sadness, love, surprise)";
	}
	
	private List<String> getEmotions(EmotionClassificationTransMetric db, CommunicationChannelArticle article, String ossmeterID)
	{
		NewsgroupArticlesEmotionClassification newsgroupArticleInEmotionClassification = null;
		
		Iterable<NewsgroupArticlesEmotionClassification> newsgroupArticleIt = db.getNewsgroupArticles().
				find(NewsgroupArticlesEmotionClassification.NEWSGROUPNAME.eq(ossmeterID),
						NewsgroupArticlesEmotionClassification.ARTICLEID.eq(article.getArticleId()));
		for (NewsgroupArticlesEmotionClassification naec:  newsgroupArticleIt) {
			newsgroupArticleInEmotionClassification = naec;
		}
		if(newsgroupArticleInEmotionClassification!=null)
			return newsgroupArticleInEmotionClassification.getEmotions();
		return new ArrayList<String>(0);
	}

}
