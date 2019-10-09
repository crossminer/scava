package org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.indexing.preparation.IndexPreparationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.model.IndexPrepTransMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.model.NewsgroupsMigrationIssue;
import org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.model.NewsgroupsMigrationIssueTransMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.ThreadsTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.NewsgroupsThreadsTransMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ThreadData;
import org.eclipse.scava.metricprovider.trans.topics.TopicsTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.topics.model.NewsgroupTopic;
import org.eclipse.scava.metricprovider.trans.topics.model.TopicsTransMetric;
import org.eclipse.scava.nlp.classifiers.migrationpatternsdetector.MigrationPatternDetector;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.scava.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;
import org.eclipse.scava.repository.model.cc.irc.Irc;
import org.eclipse.scava.repository.model.cc.mbox.Mbox;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.cc.sympa.SympaMailingList;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.mongodb.DB;

public class NewsgroupsMigrationIssueTransMetricProvider  implements ITransientMetricProvider<NewsgroupsMigrationIssueTransMetric> {

	protected PlatformCommunicationChannelManager communicationChannelManager;

	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	protected OssmeterLogger logger;
	
	public NewsgroupsMigrationIssueTransMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.trans.newsgroups.migrationissues");
	}
	
	@Override
	public String getIdentifier() {
		return NewsgroupsMigrationIssueTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "trans.newsgroups.migrationissues";
	}

	@Override
	public String getFriendlyName() {
		return "Migration Issues Detection in Communication Channels";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric detects migration issues in Communication Channels articles.";
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel : project.getCommunicationChannels()) {
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
		return Arrays.asList(IndexPreparationTransMetricProvider.class.getCanonicalName(),
				TopicsTransMetricProvider.class.getCanonicalName(),
				ThreadsTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
		this.context = context;
	}

	@Override
	public NewsgroupsMigrationIssueTransMetric adapt(DB db) {
		return new NewsgroupsMigrationIssueTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, NewsgroupsMigrationIssueTransMetric db) {
		
		// This is for indexing
		IndexPrepTransMetric indexPrepTransMetric = ((IndexPreparationTransMetricProvider) uses.get(0)).adapt(context.getProjectDB(project));
		indexPrepTransMetric.getExecutedMetricProviders().first().getMetricIdentifiers().add(getIdentifier());
		indexPrepTransMetric.sync();
		
		TopicsTransMetric topicsMetric = ((TopicsTransMetricProvider) uses.get(1)).adapt(context.getProjectDB(project));
		
		NewsgroupsThreadsTransMetric threadsMetric = ((ThreadsTransMetricProvider) uses.get(2)).adapt(context.getProjectDB(project));
		
		boolean migrationIssueFound;
		
		MigrationPatternDetector detector = new MigrationPatternDetector();
		
		//Communication channels
		
		for (CommunicationChannelDelta delta : projectDelta.getCommunicationChannelDelta().getCommunicationChannelSystemDeltas())
		{
			for(CommunicationChannelArticle article : delta.getArticles())
			{
				ThreadData threadData = findSourceThread(threadsMetric, article.getCommunicationChannel().getOSSMeterId(), article.getArticleId());
				if(threadData!=null)
				{
					if(detector.analyzeTitle(threadData.getSubject()))
					{
						createMigrationIssue(db, article.getCommunicationChannel().getOSSMeterId(), threadData.getThreadId(),article.getArticleId(), threadData.getSubject());
					}
				}
				else
					logger.error("No thread could be found for: "+article.getCommunicationChannel().getOSSMeterId()+"\tarticle: "+article.getArticleId());
			}
		}
		
		for(NewsgroupTopic ccTopic : topicsMetric.getNewsgroupTopics())
		{
			migrationIssueFound=false;
			for(String label : ccTopic.getLabels())
			{
				if(detector.analyzeTitle(label))
				{
					migrationIssueFound=true;
					continue;
				}
			}
			if(migrationIssueFound)
			{
				for(String source : ccTopic.getArticlesId())
				{
					ThreadData threadData = findSourceThread(threadsMetric, ccTopic.getNewsgroupName(), source);
					if(threadData!=null)
						createMigrationIssue(db, ccTopic.getNewsgroupName(), threadData.getThreadId(), source, threadData.getSubject());
					else
						logger.error("No thread could be found for: "+ccTopic.getNewsgroupName()+"\tarticle: "+source);
				}
			}
		}
	}
	
	private ThreadData findSourceThread(NewsgroupsThreadsTransMetric db, String newsgroupName, String articleId)
	{
		ThreadData threadData = null;
		Iterable<ThreadData> issuesIt = db.getThreads().find(
				ThreadData.NEWSGROUPNAME.eq(newsgroupName),
				ThreadData.ARTICLESID.in(articleId));
		for (ThreadData btmi : issuesIt) {
			threadData = btmi;
		}
		return threadData;
	}
	
	private void createMigrationIssue(NewsgroupsMigrationIssueTransMetric db, String newsgroupName, int threadId, String articleId, String subject)
	{
		NewsgroupsMigrationIssue migrationIssue = findNewsgroupArticle(db, newsgroupName, threadId, articleId);
		if(migrationIssue==null)
		{
			migrationIssue = new NewsgroupsMigrationIssue();
			migrationIssue.setNewsgroupName(newsgroupName);
			migrationIssue.setThreadId(threadId);
			migrationIssue.setArticleId(articleId);
			migrationIssue.setSummary(subject);
			db.getNewsgroupsMigrationIssues().add(migrationIssue);
			db.sync();
		}
	}
	
	
	private NewsgroupsMigrationIssue findNewsgroupArticle(NewsgroupsMigrationIssueTransMetric db, String newsgroupName, int threadId, String articleId) {
		NewsgroupsMigrationIssue newsgroupsIssues = null;
		Iterable<NewsgroupsMigrationIssue> issuesIt = db.getNewsgroupsMigrationIssues().find(
				NewsgroupsMigrationIssue.NEWSGROUPNAME.eq(newsgroupName),
				NewsgroupsMigrationIssue.THREADID.eq(threadId),
				NewsgroupsMigrationIssue.ARTICLEID.eq(articleId));
		for (NewsgroupsMigrationIssue nmi : issuesIt) {
			newsgroupsIssues = nmi;
		}
		return newsgroupsIssues;
	}
	
	

}
