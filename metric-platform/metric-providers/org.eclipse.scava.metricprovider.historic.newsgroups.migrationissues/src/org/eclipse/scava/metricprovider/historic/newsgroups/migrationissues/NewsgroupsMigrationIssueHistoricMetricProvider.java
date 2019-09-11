package org.eclipse.scava.metricprovider.historic.newsgroups.migrationissues;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.metricprovider.historic.newsgroups.migrationissues.model.DailyNewsgroupsMigrationData;
import org.eclipse.scava.metricprovider.historic.newsgroups.migrationissues.model.NewsgroupsMigrationIssueHistoricMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.NewsgroupsMigrationIssueTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.model.NewsgroupsMigrationIssue;
import org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.model.NewsgroupsMigrationIssueTransMetric;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;
import org.eclipse.scava.repository.model.cc.irc.Irc;
import org.eclipse.scava.repository.model.cc.mbox.Mbox;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.cc.sympa.SympaMailingList;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.googlecode.pongo.runtime.Pongo;

public class NewsgroupsMigrationIssueHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = NewsgroupsMigrationIssueHistoricMetricProvider.class.getCanonicalName();

	protected MetricProviderContext context;
	
	/**
	 * List of MPs that are used by this MP. These are MPs who have specified that 
	 * they 'provide' data for this MP.
	 */
	protected List<IMetricProvider> uses;
	
	@Override
	public String getIdentifier() {
		return IDENTIFIER;
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
	public Pongo measure(Project project) {

		if (uses.size()!=1) {
			System.err.println("Metric: " + IDENTIFIER + " failed to retrieve " + 
					"the transient metric it needs!");
			System.exit(-1);
		}
			NewsgroupsMigrationIssueTransMetric migration = ((NewsgroupsMigrationIssueTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			HashSet<Integer> threadIdSet = new HashSet<Integer>();
			for (NewsgroupsMigrationIssue migrationIssue: migration.getNewsgroupsMigrationIssues())
				threadIdSet.add(migrationIssue.getThreadId());
			
			NewsgroupsMigrationIssueHistoricMetric dailyMigration = new NewsgroupsMigrationIssueHistoricMetric();
			
			Map<String, Integer> sumOfIssues = new HashMap<String, Integer>();
			
			for (NewsgroupsMigrationIssue newsgroupMigrationIssue: migration.getNewsgroupsMigrationIssues()) {
				int sum = 1;
				if (sumOfIssues.containsKey(newsgroupMigrationIssue.getNewsgroupName()))
					sum += sumOfIssues.get(newsgroupMigrationIssue.getNewsgroupName());
				sumOfIssues.put(newsgroupMigrationIssue.getNewsgroupName(), sum);
			}
			int totalIssues = (int) migration.getNewsgroupsMigrationIssues().size();
			
			for (String newsgroupName: sumOfIssues.keySet()) {
				DailyNewsgroupsMigrationData migrationData = new DailyNewsgroupsMigrationData();
				dailyMigration.getDailyNewsgroupsMigrationData().add(migrationData);
				migrationData.setNewsgroupName(newsgroupName);
				migrationData.setNumberOfIssues(sumOfIssues.get(newsgroupName));
			}
			dailyMigration.setNumberOfIssues(totalIssues);
			int numberOfIssues = dailyMigration.getNumberOfIssues();
			int cumulativeNumberOfIssues = dailyMigration.getCumulativeNumberOfIssues();
			dailyMigration.setCumulativeNumberOfIssues(cumulativeNumberOfIssues + numberOfIssues);
			return dailyMigration;
		}

			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(NewsgroupsMigrationIssueTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "historic.newsgroups.migrationissues";
	}

	@Override
	public String getFriendlyName() {
		return "Migration Issues Detection in Newsgroups per day per bug tracker";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric detects migration issues in Newsgroups per day for each bug tracker.";
	}
}
