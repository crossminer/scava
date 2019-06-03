package org.eclipse.scava.metricprovider.trans.commits.message.topics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.carrot2.clustering.lingo.LingoClusteringAlgorithm;
import org.carrot2.core.Cluster;
import org.carrot2.core.Controller;
import org.carrot2.core.ControllerFactory;
import org.carrot2.core.Document;
import org.carrot2.core.ProcessingResult;
import org.eclipse.scava.metricprovider.trans.commits.message.plaintext.CommitsMessagePlainTextTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.commits.message.plaintext.model.CommitMessagePlainText;
import org.eclipse.scava.metricprovider.trans.commits.message.plaintext.model.CommitsMessagePlainTextTransMetric;
import org.eclipse.scava.metricprovider.trans.commits.message.topics.model.CommitMessage;
import org.eclipse.scava.metricprovider.trans.commits.message.topics.model.CommitsMessageTopicsTransMetric;
import org.eclipse.scava.metricprovider.trans.commits.message.topics.model.CommitsTopic;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.vcs.PlatformVcsManager;
import org.eclipse.scava.platform.delta.vcs.VcsCommit;
import org.eclipse.scava.platform.delta.vcs.VcsProjectDelta;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.scava.repository.model.Project;

import com.mongodb.DB;

public class CommitsMessageTopicsTransMetricProvider implements ITransientMetricProvider<CommitsMessageTopicsTransMetric> {

	protected PlatformVcsManager vcsManager;
	
	int STEP = 30; // DAYS
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	@Override
	public String getIdentifier() {
		return CommitsMessageTopicsTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "trans.commits.message.topics";
	}

	@Override
	public String getFriendlyName() {
		return "Commits Messages Topic Clustering";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes topic clusters for each commit message.";
	}

	@Override
	public boolean appliesTo(Project project) {
		return project.getVcsRepositories().size() >0 ;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(CommitsMessagePlainTextTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.vcsManager = context.getPlatformVcsManager();
		this.context=context;
	}

	@Override
	public CommitsMessageTopicsTransMetric adapt(DB db) {
		return new CommitsMessageTopicsTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta delta, CommitsMessageTopicsTransMetric db) {
		
		VcsProjectDelta vcsd = delta.getVcsDelta();
		if (vcsd.getRepoDeltas().size()>0)
		{	
			cleanCommitsMessages(delta.getDate(),db);
			for (VcsRepositoryDelta vcsRepositoryDelta : vcsd.getRepoDeltas())
			{
				processCommitsMessages(project, vcsRepositoryDelta, db);
				List<Cluster> commitsMessagesTopics = produceCommitsMessagesTopics(db);
				storeCommitsMessagesTopics(commitsMessagesTopics, vcsRepositoryDelta, db);
			}
		}
		
	}
	
	private void cleanCommitsMessages(Date projectDate, CommitsMessageTopicsTransMetric db) {

		Set<CommitMessage> toBeRemoved = new HashSet<CommitMessage>();

		for (CommitMessage comment : db.getCommitsMessages()) {
				if (projectDate.compareTo(new Date(comment.getDate()).addDays(STEP)) > 0)
					toBeRemoved.add(comment);
		}

		for (CommitMessage comment : toBeRemoved)
			db.getCommitsMessages().remove(comment);

		db.sync();
	}
	
	private void processCommitsMessages(Project project, VcsRepositoryDelta vcsRepositoryDelta, CommitsMessageTopicsTransMetric db) {

		CommitsMessagePlainTextTransMetric plainTextMetric = ((CommitsMessagePlainTextTransMetricProvider) uses.get(0))
				.adapt(context.getProjectDB(project));

		String url =vcsRepositoryDelta.getRepository().getUrl();
		for (VcsCommit commit : vcsRepositoryDelta.getCommits()) {
			CommitMessage commitMessageInTopic = findCommitMessageInTopic(db, commit, url);
			if (commitMessageInTopic == null) {
				commitMessageInTopic = new CommitMessage();
				commitMessageInTopic.setRepository(url);
				commitMessageInTopic.setRevision(commit.getRevision());
				List<String> plainTextList = findCommitMessagePlainText(plainTextMetric, commit, url);
				if(plainTextList.size()==0)
					continue;
				if(plainTextList.size()>1)
				{
					//The first line will be considered as the subject
					commitMessageInTopic.setSubject(plainTextList.get(0));
					plainTextList.remove(0);
					commitMessageInTopic.setMessage(String.join(" ", plainTextList));
				}
				else
				{
					commitMessageInTopic.setSubject(plainTextList.get(0));
					commitMessageInTopic.setMessage(plainTextList.get(0));
				}
				commitMessageInTopic.setDate(commit.getJavaDate());
				db.getCommitsMessages().add(commitMessageInTopic);
			}
		}
		db.sync();
	}
	
	private CommitMessage findCommitMessageInTopic(CommitsMessageTopicsTransMetric db, VcsCommit commit, String repositoryURL) {
		CommitMessage commitMessage = null;
		Iterable<CommitMessage> commitMessageIt = db.getCommitsMessages().
				find(CommitMessage.REPOSITORY.eq(repositoryURL),
						CommitMessage.REVISION.eq(commit.getRevision()));
		for (CommitMessage cm : commitMessageIt) {
			commitMessage = cm;
		}
		return commitMessage;
	}
	
	private List<String> findCommitMessagePlainText(CommitsMessagePlainTextTransMetric db, VcsCommit commit, String repositoryURL)
	{
		CommitMessagePlainText commitMessagePlainText = null;
		Iterable<CommitMessagePlainText> commitMessagePlainTextIt = db.getCommitsMessagesPlainText().
																			find(CommitMessagePlainText.REPOSITORY.eq(repositoryURL),
																					CommitMessagePlainText.REVISION.eq(commit.getRevision()));
		for(CommitMessagePlainText cmpt : commitMessagePlainTextIt)
			commitMessagePlainText = cmpt;
		if(commitMessagePlainText==null)
			return new ArrayList<String>(0);
		else
			return commitMessagePlainText.getPlainText();
	}
	
	private List<Cluster> produceCommitsMessagesTopics(CommitsMessageTopicsTransMetric db) {
		final ArrayList<Document> documents = new ArrayList<Document>();
		for (CommitMessage commitMessage : db.getCommitsMessages())
			documents.add(new Document(commitMessage.getSubject(), commitMessage.getMessage()));
		return produceTopics(documents);
	}
	
	private List<Cluster> produceTopics(ArrayList<Document> documents) {
		/* A controller to manage the processing pipeline. */
		final Controller controller = ControllerFactory.createSimple();

		/*
		 * Perform clustering by topic using the Lingo algorithm. Lingo can take
		 * advantage of the original query, so we provide it along with the documents.
		 */
		final ProcessingResult byTopicClusters = controller.process(documents, "data mining",
				LingoClusteringAlgorithm.class);
		final List<Cluster> clustersByTopic = byTopicClusters.getClusters();

		return clustersByTopic;
	}
	
	private void storeCommitsMessagesTopics(List<Cluster> commitsMessagesTopics, VcsRepositoryDelta vcsRepositoryDelta, CommitsMessageTopicsTransMetric db) {
		db.getCommitsTopics().getDbCollection().drop();
		for (Cluster cluster : commitsMessagesTopics) {
			CommitsTopic commitsTopic = new CommitsTopic();
			db.getCommitsTopics().add(commitsTopic);
			commitsTopic.setRepository(vcsRepositoryDelta.getRepository().getUrl());
			commitsTopic.setLabel(cluster.getLabel());
			commitsTopic.setNumberOfMessages(cluster.getAllDocuments().size());
		}
		db.sync();
	}

}
