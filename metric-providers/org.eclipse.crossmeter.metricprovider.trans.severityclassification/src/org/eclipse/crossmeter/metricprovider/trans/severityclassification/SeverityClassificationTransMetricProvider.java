/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.metricprovider.trans.severityclassification;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.eclipse.crossmeter.metricprovider.trans.newsgroups.threads.ThreadsTransMetricProvider;
import org.eclipse.crossmeter.metricprovider.trans.newsgroups.threads.model.ArticleData;
import org.eclipse.crossmeter.metricprovider.trans.newsgroups.threads.model.NewsgroupsThreadsTransMetric;
import org.eclipse.crossmeter.metricprovider.trans.newsgroups.threads.model.ThreadData;
import org.eclipse.crossmeter.metricprovider.trans.severityclassification.model.BugTrackerBugsData;
import org.eclipse.crossmeter.metricprovider.trans.severityclassification.model.NewsgroupArticleData;
import org.eclipse.crossmeter.metricprovider.trans.severityclassification.model.NewsgroupThreadData;
import org.eclipse.crossmeter.metricprovider.trans.severityclassification.model.SeverityClassificationTransMetric;
import org.eclipse.crossmeter.platform.IMetricProvider;
import org.eclipse.crossmeter.platform.ITransientMetricProvider;
import org.eclipse.crossmeter.platform.MetricProviderContext;
import org.eclipse.crossmeter.platform.delta.ProjectDelta;
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemProjectDelta;
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.crossmeter.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.crossmeter.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.crossmeter.platform.delta.communicationchannel.CommunicationChannelProjectDelta;
import org.eclipse.crossmeter.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.eclipse.crossmeter.repository.model.BugTrackingSystem;
import org.eclipse.crossmeter.repository.model.CommunicationChannel;
import org.eclipse.crossmeter.repository.model.Project;
import org.eclipse.crossmeter.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.crossmeter.repository.model.sourceforge.Discussion;
import org.eclipse.crossmeter.severityclassifier.opennlptartarus.libsvm.ClassificationInstance;
import org.eclipse.crossmeter.severityclassifier.opennlptartarus.libsvm.Classifier;
import org.eclipse.crossmeter.severityclassifier.opennlptartarus.libsvm.ClassifierMessage;
import org.eclipse.crossmeter.severityclassifier.opennlptartarus.libsvm.FeatureIdCollection;

import com.mongodb.DB;

public class SeverityClassificationTransMetricProvider  implements ITransientMetricProvider<SeverityClassificationTransMetric>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;
	protected PlatformCommunicationChannelManager communicationChannelManager;

	protected MetricProviderContext context;

	protected List<IMetricProvider> uses;

	@Override
	public String getIdentifier() {
		return SeverityClassificationTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
			if (communicationChannel instanceof Discussion) return true;
		}
		return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(ThreadsTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
	}

	@Override
	public SeverityClassificationTransMetric adapt(DB db) {
		return new SeverityClassificationTransMetric(db);
	}
	
	@Override
	public void measure(Project project, ProjectDelta projectDelta, 
						SeverityClassificationTransMetric db) {
		final long startTime = System.currentTimeMillis();
		long previousTime = startTime;
		previousTime = printTimeMessage(startTime, previousTime, -1, "Started " + getIdentifier());

		BugTrackingSystemProjectDelta btspDelta = projectDelta.getBugTrackingSystemDelta();
		
    	Classifier classifier = new Classifier();
		for (BugTrackingSystemDelta bugTrackingSystemDelta : btspDelta.getBugTrackingSystemDeltas()) {
			BugTrackingSystem bugTracker = bugTrackingSystemDelta.getBugTrackingSystem();
			Map<String, String> bugIdsNoSeverity2Subject = new HashMap<String, String>();
			for (BugTrackingSystemBug bug: bugTrackingSystemDelta.getNewBugs()) {
				if ( (bug.getSeverity()==null) || (bug.getSeverity().equals("")) ) {
					bugIdsNoSeverity2Subject.put(bug.getBugId(), bug.getSummary());
				}
				else {
					BugTrackerBugsData bugData = findBugTrackerBug(db, bugTracker, bug.getBugId());
					if (bugData == null) {
						bugData = new BugTrackerBugsData();
						bugData.setBugTrackerId(bugTracker.getOSSMeterId());
						bugData.setBugId(bug.getBugId());
						bugData.setSeverity(bug.getSeverity());
						db.getBugTrackerBugs().add(bugData);
						db.sync();
					} 
				}
			}
			for (BugTrackingSystemBug bug: bugTrackingSystemDelta.getUpdatedBugs()) {
				if ( (bug.getSeverity()==null) || (bug.getSeverity().equals("")) )
					bugIdsNoSeverity2Subject.put(bug.getBugId(), bug.getSummary());
				else {
					BugTrackerBugsData bugData = findBugTrackerBug(db, bugTracker, bug.getBugId());
					if (bugData == null) {
						bugData = new BugTrackerBugsData();
						bugData.setBugTrackerId(bugTracker.getOSSMeterId());
						bugData.setBugId(bug.getBugId());
						bugData.setSeverity(bug.getSeverity());
						db.getBugTrackerBugs().add(bugData);
						db.sync();
					} 
				}
			}
			Set<String> alreadyEncounteredBugIds = new HashSet<String>();
			for (BugTrackingSystemComment comment: bugTrackingSystemDelta.getComments()) {
				if (bugIdsNoSeverity2Subject.containsKey(comment.getBugId())) {
					String subject = bugIdsNoSeverity2Subject.get(comment.getBugId());
					ClassifierMessage classifierMessage = 
							prepareBugTrackerClassifierMessage(bugTracker, comment, subject);
					if (alreadyEncounteredBugIds.contains(comment.getBugId())) {
						classifier.add(classifierMessage);
						alreadyEncounteredBugIds.add(comment.getBugId());
					} else {
						FeatureIdCollection featureIdCollection = 
								retrieveBugTrackerBugFeatures(db, bugTracker, comment.getBugId());
						classifier.add(classifierMessage, featureIdCollection);
					}
				}
			}
			db.sync();
		}
		
		previousTime = printTimeMessage(startTime, previousTime, 
										classifier.instanceCollectionSize(), "prepared bug comments");
		
		NewsgroupsThreadsTransMetric usedClassifier = 
				((ThreadsTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		CommunicationChannelProjectDelta ccpDelta = projectDelta.getCommunicationChannelDelta();
		for ( CommunicationChannelDelta communicationChannelDelta: ccpDelta.getCommunicationChannelSystemDeltas()) {
			CommunicationChannel communicationChannel = communicationChannelDelta.getCommunicationChannel();
			String communicationChannelName;
			if (!(communicationChannel instanceof NntpNewsGroup))
				communicationChannelName = communicationChannel.getUrl();
			else {
				NntpNewsGroup newsgroup = (NntpNewsGroup) communicationChannel;
				communicationChannelName = newsgroup.getNewsGroupName();
			}
			if (communicationChannelDelta.getArticles().size()==0) continue;

//			load all stored articles for this newsgroup
			Map<Integer, FeatureIdCollection> articlesFeatureIdCollections = 
											  retrieveNewsgroupArticleFeatures(db, communicationChannelName);
			System.err.println("articlesFeatureIdCollections.size(): " + articlesFeatureIdCollections.size());

			Map<Integer, CommunicationChannelArticle> articlesDeltaArticles = 
											new HashMap<Integer, CommunicationChannelArticle>();
			for (CommunicationChannelArticle article: communicationChannelDelta.getArticles())
				articlesDeltaArticles.put(article.getArticleNumber(), article);
			System.err.println("articlesDeltaArticles.size(): " + articlesDeltaArticles.size());
			
			for (ThreadData threadData: usedClassifier.getThreads()) {
				int threadId = threadData.getThreadId();
				for (ArticleData articleData: threadData.getArticles()) {
					if (articlesFeatureIdCollections.containsKey(articleData.getArticleNumber())) {
						FeatureIdCollection featureIdCollection  = 
								articlesFeatureIdCollections.get(articleData.getArticleNumber());
						classifier.add(articleData, threadId, featureIdCollection);
					} else {
						CommunicationChannelArticle deltaArticle = 
								articlesDeltaArticles.get(articleData.getArticleNumber());
						classifier.add(communicationChannelName, deltaArticle, threadId);
						
						NewsgroupArticleData newsgroupArticleData = 
								prepareNewsgroupArticleData(classifier, communicationChannelName, deltaArticle, threadId);

						db.getNewsgroupArticles().add(newsgroupArticleData);

					}
				}
			}

			db.sync();
		}

		previousTime = printTimeMessage(startTime, previousTime, classifier.instanceCollectionSize(),
										"prepared newsgroup articles");

		classifier.classify();

		previousTime = printTimeMessage(startTime, previousTime, classifier.instanceCollectionSize(),
										"classifier.classify() finished");

//		take classifier results put them in the db - for bugzilla
		
		for (BugTrackingSystemDelta bugTrackingSystemDelta : btspDelta.getBugTrackingSystemDeltas()) {
			
			BugTrackingSystem bugTracker = bugTrackingSystemDelta.getBugTrackingSystem();
			
			Set<String> bugIdSet = new HashSet<String>(); 
			for (BugTrackingSystemComment comment: bugTrackingSystemDelta.getComments()) {
				bugIdSet.add(comment.getBugId());
			}
				
			for (BugTrackingSystemBug bug: bugTrackingSystemDelta.getNewBugs()) {
				if (bugIdSet.contains(bug.getBugId())) {
					if ( (bug.getSeverity()==null) || (bug.getSeverity().equals("")) ) {
						BugTrackerBugsData bugTrackerBugsData = 
								prepareBugTrackerBugsData(classifier, bugTracker, bug);
						db.getBugTrackerBugs().add(bugTrackerBugsData);
						db.sync();
					}
				}
			}
			
			for (BugTrackingSystemBug bug: bugTrackingSystemDelta.getUpdatedBugs()) {
				if (bugIdSet.contains(bug.getBugId())) {
					if ( (bug.getSeverity()==null) || (bug.getSeverity().equals("")) ) {
						BugTrackerBugsData bugTrackerBugsData = 
								prepareBugTrackerBugsData(classifier, bugTracker, bug);
						db.getBugTrackerBugs().add(bugTrackerBugsData);
						db.sync();
					}
				}
			}
			db.sync();
		}

		previousTime = printTimeMessage(startTime, previousTime, classifier.instanceCollectionSize(),
				"stored classified bugs");

//		take classifier results put them in the db - for newsgroups
		
//		do not do anything if nothing has changed
		
		if (classifier.instanceCollectionSize()>0) {

			db.getNewsgroupThreads().getDbCollection().drop();
			
			for (ThreadData threadData: usedClassifier.getThreads()) {
				
				String newsgroupName = threadData.getArticles().get(0).getNewsgroupName();
				int threadId = threadData.getThreadId();
				ClassifierMessage classifierMessage =  prepareNewsgroupClassifierMessage(newsgroupName, threadId);
				String severity = classifier.getClassificationResult(classifierMessage);
				
				NewsgroupThreadData newsgroupThreadData = new NewsgroupThreadData();
				newsgroupThreadData.setNewsgroupName(newsgroupName);
				newsgroupThreadData.setThreadId(threadId);
				newsgroupThreadData.setSeverity(severity);
				db.getNewsgroupThreads().add(newsgroupThreadData);
				
				db.sync();
			}
		}
		

		previousTime = printTimeMessage(startTime, previousTime, classifier.instanceCollectionSize(),
										"stored classified newsgroup articles");
 	}
	
	private BugTrackerBugsData prepareBugTrackerBugsData(Classifier classifier,
			BugTrackingSystem bugTracker, BugTrackingSystemBug bug) {

		ClassifierMessage classifierMessage =  prepareBugTrackerClassifierMessage(bugTracker, bug);
		String severity = classifier.getClassificationResult(classifierMessage);
		
		ClassificationInstance classificationInstance = 
				classifier.getClassificationInstance(classifierMessage);
		
		BugTrackerBugsData bugTrackerBugsData = new BugTrackerBugsData();
		bugTrackerBugsData.setBugTrackerId(bugTracker.getOSSMeterId());
		bugTrackerBugsData.setBugId(bug.getBugId());
		bugTrackerBugsData.setSeverity(severity);
		
		for (int unigramId: classifier.getUnigramOrders(classificationInstance.getUnigrams()))
			bugTrackerBugsData.getUnigrams().add(unigramId);
		
		for (int bigramId: classifier.getBigramOrders(classificationInstance.getBigrams()))
			bugTrackerBugsData.getBigrams().add(bigramId);
		
		for (int trigramId: classifier.getTrigramOrders(classificationInstance.getTrigrams()))
			bugTrackerBugsData.getTrigrams().add(trigramId);
		
		for (int quadgramId: classifier.getQuadgramOrders(classificationInstance.getQuadgrams()))
			bugTrackerBugsData.getQuadgrams().add(quadgramId);
		
		for (int charTrigramId: classifier.getCharTrigramOrders(classificationInstance.getCharTrigrams()))
			bugTrackerBugsData.getCharTrigrams().add(charTrigramId);
		
		for (int charQuadgramId: classifier.getCharQuadgramOrders(classificationInstance.getCharQuadgrams()))
			bugTrackerBugsData.getCharQuadgrams().add(charQuadgramId);
		
		for (int charFivegramId: classifier.getCharFivegramOrders(classificationInstance.getCharFivegrams()))
			bugTrackerBugsData.getCharFivegrams().add(charFivegramId);

		return bugTrackerBugsData;
	}

	private NewsgroupArticleData prepareNewsgroupArticleData(Classifier classifier, String communicationChannelName,
															 CommunicationChannelArticle deltaArticle, int threadId) {
		ClassificationInstance instanceToStore = 
							new ClassificationInstance(communicationChannelName, deltaArticle, threadId);
		
		NewsgroupArticleData newsgroupArticleData = new NewsgroupArticleData();
		newsgroupArticleData.setNewsGroupName(communicationChannelName);
		newsgroupArticleData.setArticleNumber(deltaArticle.getArticleNumber());

		for (int unigramId: classifier.getUnigramOrders(instanceToStore.getUnigrams()))
			newsgroupArticleData.getUnigrams().add(unigramId);
		
		for (int bigramId: classifier.getBigramOrders(instanceToStore.getBigrams()))
			newsgroupArticleData.getBigrams().add(bigramId);
		
		for (int trigramId: classifier.getTrigramOrders(instanceToStore.getTrigrams()))
			newsgroupArticleData.getTrigrams().add(trigramId);
		
		for (int quadgramId: classifier.getQuadgramOrders(instanceToStore.getQuadgrams()))
			newsgroupArticleData.getQuadgrams().add(quadgramId);
		
		for (int charTrigramId: classifier.getCharTrigramOrders(instanceToStore.getCharTrigrams()))
			newsgroupArticleData.getCharTrigrams().add(charTrigramId);
		
		for (int charQuadgramId: classifier.getCharQuadgramOrders(instanceToStore.getCharQuadgrams()))
			newsgroupArticleData.getCharQuadgrams().add(charQuadgramId);
		
		for (int charFivegramId: classifier.getCharFivegramOrders(instanceToStore.getCharFivegrams()))
			newsgroupArticleData.getCharFivegrams().add(charFivegramId);
		
		return newsgroupArticleData;
	}

	private long printTimeMessage(long startTime, long previousTime, int size, String message) {
		long currentTime = System.currentTimeMillis();
		System.err.println(time(currentTime - previousTime) + "\t" +
						   time(currentTime - startTime) + "\t" +
						   size + "\t" + message);
		return currentTime;
	}

	private String time(long timeInMS) {
		return DurationFormatUtils.formatDuration(timeInMS, "HH:mm:ss,SSS");
	}

	private ClassifierMessage prepareBugTrackerClassifierMessage(
			BugTrackingSystem bugTracker, BugTrackingSystemComment comment, String bugSubject) {
		ClassifierMessage classifierMessage = new ClassifierMessage();
		classifierMessage.setBugTrackerId(bugTracker.getOSSMeterId());
		classifierMessage.setBugId(comment.getBugId());
		classifierMessage.setCommentId(comment.getCommentId());
		classifierMessage.setSubject(bugSubject);
		
		if (comment.getText() == null) {
			classifierMessage.setText("");			
		} else {
			classifierMessage.setText(comment.getText());
		}
        return classifierMessage;
	}

	private ClassifierMessage prepareBugTrackerClassifierMessage(
			BugTrackingSystem bugTracker, BugTrackingSystemBug bug) {
		ClassifierMessage classifierMessage = new ClassifierMessage();
		classifierMessage.setBugTrackerId(bugTracker.getOSSMeterId());
		classifierMessage.setBugId(bug.getBugId());
        return classifierMessage;
	}
	
	private ClassifierMessage prepareNewsgroupClassifierMessage(String newsgroupName, int threadId) {
		ClassifierMessage classifierMessage = new ClassifierMessage();
		classifierMessage.setNewsgroupName(newsgroupName);
		classifierMessage.setThreadId(threadId);
        return classifierMessage;
	}

	private BugTrackerBugsData findBugTrackerBug(SeverityClassificationTransMetric db, 
			BugTrackingSystem bugTracker, String bugId) {
		BugTrackerBugsData bugTrackerBugsData = null;
		Iterable<BugTrackerBugsData> bugTrackerBugsDataIt = 
				db.getBugTrackerBugs().
				find(BugTrackerBugsData.BUGTRACKERID.eq(bugTracker.getOSSMeterId()), 
						BugTrackerBugsData.BUGID.eq(bugId));
		for (BugTrackerBugsData bcd:  bugTrackerBugsDataIt) {
			bugTrackerBugsData = bcd;
		}
		return bugTrackerBugsData;
	}

	private FeatureIdCollection retrieveBugTrackerBugFeatures(
			SeverityClassificationTransMetric db, BugTrackingSystem bugTracker, String bugId) {
		BugTrackerBugsData bugTrackerBugsData = findBugTrackerBug(db, bugTracker, bugId);
		FeatureIdCollection featureIdCollection = new FeatureIdCollection();
		if (bugTrackerBugsData!=null) {
			featureIdCollection.addUnigrams(bugTrackerBugsData.getUnigrams());
			featureIdCollection.addBigrams(bugTrackerBugsData.getBigrams());
			featureIdCollection.addTrigrams(bugTrackerBugsData.getTrigrams());
			featureIdCollection.addQuadgrams(bugTrackerBugsData.getQuadgrams());

			featureIdCollection.addCharTrigrams(bugTrackerBugsData.getCharTrigrams());
			featureIdCollection.addCharQuadgrams(bugTrackerBugsData.getCharQuadgrams());
			featureIdCollection.addCharFivegrams(bugTrackerBugsData.getCharFivegrams());
		}
		return featureIdCollection;
	}

	private Map<Integer, FeatureIdCollection> retrieveNewsgroupArticleFeatures(
							SeverityClassificationTransMetric db, String communicationChannelName) {
		Map<Integer, FeatureIdCollection> articlesFeatureIdCollections = new HashMap<Integer, FeatureIdCollection>();
		Iterable<NewsgroupArticleData> newsgroupArticleDataIt = 
						db.getNewsgroupArticles().find(NewsgroupArticleData.NEWSGROUPNAME.eq(communicationChannelName));
		for (NewsgroupArticleData newsgroupArticleData:  newsgroupArticleDataIt) {
			FeatureIdCollection featureIdCollection = new FeatureIdCollection();
			if (newsgroupArticleData!=null) {
				featureIdCollection.addUnigrams(newsgroupArticleData.getUnigrams());
				featureIdCollection.addBigrams(newsgroupArticleData.getBigrams());
				featureIdCollection.addTrigrams(newsgroupArticleData.getTrigrams());
				featureIdCollection.addQuadgrams(newsgroupArticleData.getQuadgrams());

				featureIdCollection.addCharTrigrams(newsgroupArticleData.getCharTrigrams());
				featureIdCollection.addCharQuadgrams(newsgroupArticleData.getCharQuadgrams());
				featureIdCollection.addCharFivegrams(newsgroupArticleData.getCharFivegrams());
			}
			articlesFeatureIdCollections.put(newsgroupArticleData.getArticleNumber(), featureIdCollection);
		}

		return articlesFeatureIdCollections;
	}

	@Override
	public String getShortIdentifier() {
		return "severityclassification";
	}

	@Override
	public String getFriendlyName() {
		return "Severity Classification";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the severity of each bug or thread.";
	}

}
