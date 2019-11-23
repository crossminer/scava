/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.contentclasses;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.indexing.preparation.IndexPreparationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.model.IndexPrepTransMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.contentclasses.model.ContentClass;
import org.eclipse.scava.metricprovider.trans.newsgroups.contentclasses.model.NewsgroupArticleContentClass;
import org.eclipse.scava.metricprovider.trans.newsgroups.contentclasses.model.NewsgroupData;
import org.eclipse.scava.metricprovider.trans.newsgroups.contentclasses.model.NewsgroupsContentClassesTransMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.ThreadsTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ArticleData;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.NewsgroupsThreadsTransMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ThreadData;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;
import org.eclipse.scava.repository.model.cc.irc.Irc;
import org.eclipse.scava.repository.model.cc.mbox.Mbox;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.cc.sympa.SympaMailingList;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.mongodb.DB;

public class ContentClassesTransMetricProvider implements ITransientMetricProvider<NewsgroupsContentClassesTransMetric>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;

	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;

	@Override
	public String getIdentifier() {
		return ContentClassesTransMetricProvider.class.getCanonicalName();
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
		return Arrays.asList(ThreadsTransMetricProvider.class.getCanonicalName(), IndexPreparationTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
	}

	@Override
	public NewsgroupsContentClassesTransMetric adapt(DB db) {
		return new NewsgroupsContentClassesTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, NewsgroupsContentClassesTransMetric db) {

		if (uses.size()!=2) {
			System.err.println("Metric: " + getIdentifier() + " failed to retrieve " + 
								"the transient metric it needs!");
			System.exit(-1);
		}
		
		
		//This is for indexing
		IndexPrepTransMetric indexPrepTransMetric = ((IndexPreparationTransMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));	
		indexPrepTransMetric.getExecutedMetricProviders().first().getMetricIdentifiers().add(getIdentifier());
		indexPrepTransMetric.sync();

		NewsgroupsThreadsTransMetric usedThreads = 
				((ThreadsTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		db.getNewsgroups().getDbCollection().drop();
		db.getContentClasses().getDbCollection().drop();
		db.sync();
		
		for (ThreadData thread: usedThreads.getThreads()) {
			
			for (ArticleData articleData: thread.getArticles()) {
				
				Iterable<NewsgroupData> newsgroupDataIt = 
					db.getNewsgroups().findByNewsgroupName(articleData.getNewsgroupName());
				NewsgroupData newsgroupData = null;
				for (NewsgroupData ngd:  newsgroupDataIt) 
					newsgroupData = ngd;
				if (newsgroupData == null) {
					newsgroupData = new NewsgroupData();
					newsgroupData.setNewsgroupName(articleData.getNewsgroupName());
					newsgroupData.setNumberOfArticles(0);
					db.getNewsgroups().add(newsgroupData);
				}
				newsgroupData.setNumberOfArticles(newsgroupData.getNumberOfArticles() + 1);
				
				NewsgroupArticleContentClass articleContentClass = findArticleContentClass(db, articleData);
				if(articleContentClass==null)
				{
					articleContentClass = new NewsgroupArticleContentClass();
					articleContentClass.setArticleId(articleData.getArticleId());
					articleContentClass.setNewsgroupName(articleData.getNewsgroupName());
					articleContentClass.setClassLabel(articleData.getContentClass());
					db.getArticlesContentClass().add(articleContentClass);
				}
				
				Iterable<ContentClass> contentClassIt =db.getContentClasses().find(
								ContentClass.NEWSGROUPNAME.eq(articleData.getNewsgroupName()),
								ContentClass.CLASSLABEL.eq(articleData.getContentClass()));
				ContentClass contentClass = null;
				for (ContentClass cc:  contentClassIt) contentClass = cc;
				if (contentClass == null) {
					contentClass = new ContentClass();
					contentClass.setNewsgroupName(articleData.getNewsgroupName());
					contentClass.setClassLabel(articleData.getContentClass());
					contentClass.setNumberOfArticles(0);
					db.getContentClasses().add(contentClass);
				}
				contentClass.setNumberOfArticles(contentClass.getNumberOfArticles() + 1);
				db.sync();
			}
		}

		for (NewsgroupData newsgroupData: db.getNewsgroups()) {
			Iterable<ContentClass> contentClassIt = 
					db.getContentClasses().find(ContentClass.NEWSGROUPNAME.eq(newsgroupData.getNewsgroupName()));
			for (ContentClass contentClass:  contentClassIt)
				contentClass.setPercentage( ((float) 100 * contentClass.getNumberOfArticles()) / 
														newsgroupData.getNumberOfArticles());
		}
		db.sync();
	}

	@Override
	public String getShortIdentifier() {
		return "trans.newsgroups.contentclasses";
	}

	@Override
	public String getFriendlyName() {
		return "Content classes in newsgroup articles";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the content classes in newgroup articles, per newsgroup";
	}
	
	private NewsgroupArticleContentClass findArticleContentClass(NewsgroupsContentClassesTransMetric db, ArticleData articleData)
	{
		NewsgroupArticleContentClass newsgroupArticleInContentClass = null;
		
		Iterable<NewsgroupArticleContentClass> newsgroupArticleIt = db.getArticlesContentClass().
				find(NewsgroupArticleContentClass.NEWSGROUPNAME.eq(articleData.getNewsgroupName()),
						NewsgroupArticleContentClass.ARTICLEID.eq(articleData.getArticleId()));
		for (NewsgroupArticleContentClass nacc:  newsgroupArticleIt) {
			newsgroupArticleInContentClass = nacc;
		}
		return newsgroupArticleInContentClass;
	}

}
