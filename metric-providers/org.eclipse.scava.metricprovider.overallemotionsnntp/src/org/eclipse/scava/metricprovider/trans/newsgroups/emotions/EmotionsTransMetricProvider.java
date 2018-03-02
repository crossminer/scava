/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.emotions;

import java.util.Collections;
import java.util.List;

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
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.sourceforge.Discussion;
import org.eclipse.scava.sentimentclassifier.opennlptartarus.libsvm.ClassificationInstance;
import org.eclipse.scava.sentimentclassifier.opennlptartarus.libsvm.EmotionalDimensions;

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
		}
		return false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		// DO NOTHING -- we don't use anything
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Collections.emptyList();
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
		CommunicationChannelProjectDelta delta = projectDelta.getCommunicationChannelDelta();
		

		for (CommunicationChannelDelta communicationChannelSystemDelta : delta.getCommunicationChannelSystemDeltas()) {
			CommunicationChannel communicationChannel = communicationChannelSystemDelta.getCommunicationChannel();
			String communicationChannelName;
			if (!(communicationChannel instanceof NntpNewsGroup))
				communicationChannelName = communicationChannel.getUrl();
			else {
				NntpNewsGroup newsgroup = (NntpNewsGroup) communicationChannel;
				communicationChannelName = newsgroup.getNewsGroupName();
			}
			Iterable<NewsgroupData> newsgroupDataIt = 
					db.getNewsgroups().find(NewsgroupData.NEWSGROUPNAME.eq(communicationChannelName));
			NewsgroupData newsgroupData = null;
			for (NewsgroupData ngd:  newsgroupDataIt) 
				newsgroupData = ngd;
			if (newsgroupData == null) {
				newsgroupData = new NewsgroupData();
				newsgroupData.setNewsgroupName(communicationChannelName);
				newsgroupData.setNumberOfArticles(0);
				newsgroupData.setCumulativeNumberOfArticles(0);
				db.getNewsgroups().add(newsgroupData);
			}
			newsgroupData.setNumberOfArticles(communicationChannelSystemDelta.getArticles().size());
			newsgroupData.setCumulativeNumberOfArticles(newsgroupData.getCumulativeNumberOfArticles() + 
														communicationChannelSystemDelta.getArticles().size());

			db.sync();

			Iterable<EmotionDimension> emotionIt = 
					db.getDimensions().find(EmotionDimension.NEWSGROUPNAME.eq(communicationChannelName));
			for (EmotionDimension emotion:  emotionIt) {
				emotion.setNumberOfArticles(0);
			}

			for (CommunicationChannelArticle article: communicationChannelSystemDelta.getArticles()) {
				ClassificationInstance instance = new ClassificationInstance();
				instance.setArticleNumber(article.getArticleNumber());
				instance.setNewsgroupName(communicationChannelName);
				instance.setText(article.getText());
				
				String[] emotionalDimensions = 
						EmotionalDimensions.getDimensions(instance).split(",");
				
				for (String dimension: emotionalDimensions) {
					dimension = dimension.trim();
					if (dimension.length()>0) {
						emotionIt = db.getDimensions().
										find(EmotionDimension.NEWSGROUPNAME.eq(communicationChannelName),
											 EmotionDimension.EMOTIONLABEL.eq(dimension));
						
						EmotionDimension emotion = null;
						for (EmotionDimension em: emotionIt) emotion = em;
						if (emotion == null) {
							emotion = new EmotionDimension();
							emotion.setNewsgroupName(communicationChannelName);
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
			}
			
			db.sync();

			emotionIt = db.getDimensions().
					find(EmotionDimension.NEWSGROUPNAME.eq(communicationChannelName));

			for (EmotionDimension emotion: db.getDimensions()) {
				if ( newsgroupData.getNumberOfArticles() > 0 )
					emotion.setPercentage( 
						((float)100*emotion.getNumberOfArticles()) / newsgroupData.getNumberOfArticles());
				else
					emotion.setPercentage( (float) 0 );
				if ( newsgroupData.getCumulativeNumberOfArticles() > 0 )
					emotion.setCumulativePercentage( 
						((float)100*emotion.getCumulativeNumberOfArticles()) / newsgroupData.getCumulativeNumberOfArticles());
				else
					emotion.setCumulativePercentage( (float) 0 );
			}

			db.sync();
		}
	}

	@Override
	public String getShortIdentifier() {
		return "newsgroupemotions";
	}

	@Override
	public String getFriendlyName() {
		return "Emotional Dimensions in Newsgroup Articles";
	}

	@Override
	public String getSummaryInformation() {
		return "Emotional Dimensions in Newsgroup Articles";
	}

}
