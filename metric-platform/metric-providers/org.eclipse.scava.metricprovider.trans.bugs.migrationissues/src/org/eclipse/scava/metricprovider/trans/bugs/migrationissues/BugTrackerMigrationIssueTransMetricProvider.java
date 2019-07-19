package org.eclipse.scava.metricprovider.trans.bugs.migrationissues;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.bugs.migrationissues.model.BugTrackerMigrationIssue;
import org.eclipse.scava.metricprovider.trans.bugs.migrationissues.model.BugTrackerMigrationIssueTransMetric;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.IndexPreparationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.model.IndexPrepTransMetric;
import org.eclipse.scava.metricprovider.trans.topics.TopicsTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.topics.model.BugTrackerTopic;
import org.eclipse.scava.metricprovider.trans.topics.model.TopicsTransMetric;
import org.eclipse.scava.nlp.classifiers.migrationpatternsdetector.MigrationPatternDetector;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.scava.repository.model.Project;

import com.mongodb.DB;

public class BugTrackerMigrationIssueTransMetricProvider  implements ITransientMetricProvider<BugTrackerMigrationIssueTransMetric> {

	protected PlatformBugTrackingSystemManager bugTrackingSystemManager;

	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	@Override
	public String getIdentifier() {
		return BugTrackerMigrationIssueTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "trans.bugs.migrationissues";
	}

	@Override
	public String getFriendlyName() {
		return "Migration Issues Detection in Bug Trackers";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric detects migration issues in Bug Tracking Systems.";
	}

	@Override
	public boolean appliesTo(Project project) {
		return !project.getBugTrackingSystems().isEmpty();
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(IndexPreparationTransMetricProvider.class.getCanonicalName(),
				TopicsTransMetricProvider.class.getCanonicalName());
		//Add Maracas
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.bugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
		this.context = context;
	}

	@Override
	public BugTrackerMigrationIssueTransMetric adapt(DB db) {
		return new BugTrackerMigrationIssueTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, BugTrackerMigrationIssueTransMetric db) {
		
		// This is for indexing
		IndexPrepTransMetric indexPrepTransMetric = ((IndexPreparationTransMetricProvider) uses.get(0)).adapt(context.getProjectDB(project));
		indexPrepTransMetric.getExecutedMetricProviders().first().getMetricIdentifiers().add(getIdentifier());
		indexPrepTransMetric.sync();
		
		TopicsTransMetric topicsMetric = ((TopicsTransMetricProvider) uses.get(1)).adapt(context.getProjectDB(project));
		
		boolean migrationIssueFound;
		String[] uid;
		
		MigrationPatternDetector detector = new MigrationPatternDetector();
		
		for (BugTrackingSystemDelta delta: projectDelta.getBugTrackingSystemDelta().getBugTrackingSystemDeltas())
		{
			for (BugTrackingSystemBug bug: delta.getNewBugs()) {
				if(detector.analyzeTitle(bug.getSummary()))
				{
					createMigrationIssue(db, bug.getBugTrackingSystem().getOSSMeterId(), bug.getBugId());
				}
			}
			for (BugTrackingSystemBug bug: delta.getUpdatedBugs()) {
				if(detector.analyzeTitle(bug.getSummary()))
				{
					createMigrationIssue(db, bug.getBugTrackingSystem().getOSSMeterId(), bug.getBugId());
				}
			}
		}
		
		for(BugTrackerTopic bugTopic : topicsMetric.getBugTrackerTopics())
		{
			migrationIssueFound=false;
			for(String label : bugTopic.getLabels())
			{
				if(detector.analyzeTitle(label))
				{
					migrationIssueFound=true;
					continue;
				}
			}
			if(migrationIssueFound)
			{
				for(String source : bugTopic.getCommentsId())
				{
					uid=source.split("\t");
					createMigrationIssue(db, bugTopic.getBugTrackerId(), uid[0]);
				}
			}
		}
		
	}
	
	private void createMigrationIssue(BugTrackerMigrationIssueTransMetric db, String bugTrackerId, String bugId)
	{
		BugTrackerMigrationIssue migrationIssue = findBugTrackerComment(db, bugTrackerId, bugId);
		if(migrationIssue==null)
		{
			migrationIssue = new BugTrackerMigrationIssue();
			migrationIssue.setBugTrackerId(bugTrackerId);
			migrationIssue.setBugId(bugId);
			db.getBugTrackerMigrationIssues().add(migrationIssue);
			db.sync();
		}
	}
	
	private BugTrackerMigrationIssue findBugTrackerComment(BugTrackerMigrationIssueTransMetric db, String bugTrackerId, String bugId) {
		BugTrackerMigrationIssue bugTrackerIssues = null;
		Iterable<BugTrackerMigrationIssue> issuesIt = db.getBugTrackerMigrationIssues().find(
				BugTrackerMigrationIssue.BUGTRACKERID.eq(bugTrackerId),
				BugTrackerMigrationIssue.BUGID.eq(bugId));
		for (BugTrackerMigrationIssue btmi : issuesIt) {
			bugTrackerIssues = btmi;
		}
		return bugTrackerIssues;
	}

}
