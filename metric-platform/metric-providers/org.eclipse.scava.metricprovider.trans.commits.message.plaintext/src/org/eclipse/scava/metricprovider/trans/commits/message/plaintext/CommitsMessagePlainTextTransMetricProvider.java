package org.eclipse.scava.metricprovider.trans.commits.message.plaintext;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.commits.message.plaintext.model.CommitMessagePlainText;
import org.eclipse.scava.metricprovider.trans.commits.message.plaintext.model.CommitsMessagePlainTextTransMetric;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.IndexPreparationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.model.IndexPrepTransMetric;
import org.eclipse.scava.nlp.tools.plaintext.PlainTextObject;
import org.eclipse.scava.nlp.tools.plaintext.commitsmessages.PlainTextCommitsMessagesMarkdownBased;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.vcs.PlatformVcsManager;
import org.eclipse.scava.platform.delta.vcs.VcsCommit;
import org.eclipse.scava.platform.delta.vcs.VcsProjectDelta;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.VcsRepository;

import com.mongodb.DB;

public class CommitsMessagePlainTextTransMetricProvider implements ITransientMetricProvider<CommitsMessagePlainTextTransMetric> {

	protected PlatformVcsManager vcsManager;
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	@Override
	public String getIdentifier() {
		return CommitsMessagePlainTextTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "trans.commits.message.plaintext";
	}

	@Override
	public String getFriendlyName() {
		return "Commits message plain text";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric preprocess each commit message to get a split plain text version.";
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
		return Arrays.asList(IndexPreparationTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.vcsManager = context.getPlatformVcsManager();
		this.context = context;
	}

	@Override
	public CommitsMessagePlainTextTransMetric adapt(DB db) {
		return new CommitsMessagePlainTextTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta delta, CommitsMessagePlainTextTransMetric db) {
		clearDB(db);
		
		//This is for the indexing
		IndexPrepTransMetric indexPrepTransMetric = ((IndexPreparationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));	
		indexPrepTransMetric.getExecutedMetricProviders().first().getMetricIdentifiers().add(getIdentifier());
		indexPrepTransMetric.sync();
		
		VcsProjectDelta vcsd = delta.getVcsDelta();
		
		PlainTextObject plainTextObject;
		
		for (VcsRepositoryDelta vcsRepositoryDelta : vcsd.getRepoDeltas())
		{	
			VcsRepository repository = vcsRepositoryDelta.getRepository();
			for (VcsCommit commit : vcsRepositoryDelta.getCommits())
			{
				CommitMessagePlainText commitMessagePlainText = findCommitMessage(db, commit, repository.getUrl());
				if(commitMessagePlainText==null)
				{
					commitMessagePlainText = new CommitMessagePlainText();
					commitMessagePlainText.setRepository(repository.getUrl());
					commitMessagePlainText.setRevision(commit.getRevision());
					db.getCommitsMessagesPlainText().add(commitMessagePlainText);
				}
				
				plainTextObject=PlainTextCommitsMessagesMarkdownBased.process(commit.getMessage());
				commitMessagePlainText.getPlainText().addAll(plainTextObject.getPlainTextAsList());
				db.sync();
			}
		}
	}
	
	private void clearDB(CommitsMessagePlainTextTransMetric db)
	{
		db.getCommitsMessagesPlainText().getDbCollection().drop();
		db.sync();
	}
	
	private CommitMessagePlainText findCommitMessage(CommitsMessagePlainTextTransMetric db, VcsCommit commit, String repositoryURL)
	{
		CommitMessagePlainText commitMessagePlainText = null;
		Iterable<CommitMessagePlainText> commitMessagePlainTextIt = db.getCommitsMessagesPlainText().
																			find(CommitMessagePlainText.REPOSITORY.eq(repositoryURL),
																					CommitMessagePlainText.REVISION.eq(commit.getRevision()));
		for(CommitMessagePlainText cmpt : commitMessagePlainTextIt)
			commitMessagePlainText = cmpt;
		return commitMessagePlainText;
	}

}
