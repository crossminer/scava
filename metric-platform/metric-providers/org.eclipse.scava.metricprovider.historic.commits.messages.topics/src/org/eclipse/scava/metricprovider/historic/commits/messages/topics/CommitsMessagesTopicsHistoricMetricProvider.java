package org.eclipse.scava.metricprovider.historic.commits.messages.topics;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.commits.messages.topics.model.CommitMessageTopic;
import org.eclipse.scava.metricprovider.historic.commits.messages.topics.model.CommitsMessagesTopicsHistoricMetric;
import org.eclipse.scava.metricprovider.trans.commits.message.topics.CommitsMessageTopicsTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.commits.message.topics.model.CommitsMessageTopicsTransMetric;
import org.eclipse.scava.metricprovider.trans.commits.message.topics.model.CommitsTopic;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class CommitsMessagesTopicsHistoricMetricProvider extends AbstractHistoricalMetricProvider  {

	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	@Override
	public String getIdentifier() {
		return CommitsMessagesTopicsHistoricMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "historic.commits.messages.topics";
	}

	@Override
	public String getFriendlyName() {
		return "Labels of topics in commits messages analyzed in the last 30 days.";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the labels of topics (thematic clusters) in commits messages "
				+ "pushed by users in the last 30 days.";
	}

	@Override
	public boolean appliesTo(Project project) {
		return project.getVcsRepositories().size() >0;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
		
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(CommitsMessageTopicsTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
		
	}
	
	@Override
	public Pongo measure(Project project) {
		CommitsMessagesTopicsHistoricMetric topics = new CommitsMessagesTopicsHistoricMetric();
		
		CommitsMessageTopicsTransMetric usedTopics = ((CommitsMessageTopicsTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		 for (CommitsTopic commitsTopic: usedTopics.getCommitsTopics()) {
			 CommitMessageTopic topic = new CommitMessageTopic();
			 topics.getCommitMessageTopics().add(topic);
			 topic.setRepository(commitsTopic.getRepository());
			 topic.setLabel(commitsTopic.getLabel());
			 topic.setNumberOfMessages(commitsTopic.getNumberOfMessages());
		 }
		return topics;
	}

}
