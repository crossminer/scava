package org.eclipse.scava.metricprovider.indexing.commits;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.index.indexer.Indexer;
import org.eclipse.scava.metricprovider.indexing.commits.document.CommitDocument;
import org.eclipse.scava.metricprovider.indexing.commits.mapping.Mapping;
import org.eclipse.scava.metricprovider.trans.commits.message.plaintext.CommitsMessagePlainTextTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.commits.message.plaintext.model.CommitMessagePlainText;
import org.eclipse.scava.metricprovider.trans.commits.message.plaintext.model.CommitsMessagePlainTextTransMetric;
import org.eclipse.scava.metricprovider.trans.commits.message.references.CommitsMessageReferencesTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.commits.message.references.model.CommitMessageReferringTo;
import org.eclipse.scava.metricprovider.trans.commits.message.references.model.CommitsMessageReferenceTransMetric;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.IndexPreparationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.model.IndexPrepTransMetric;
import org.eclipse.scava.platform.AbstractIndexingMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.vcs.PlatformVcsManager;
import org.eclipse.scava.platform.delta.vcs.VcsCommit;
import org.eclipse.scava.platform.delta.vcs.VcsProjectDelta;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.scava.platform.indexing.Indexing;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.VcsRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoDB;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;

public class CommitsIndexingMetricProvider extends AbstractIndexingMetricProvider {

	protected MetricProviderContext context;
	protected List<IMetricProvider> uses;
	
	protected PlatformVcsManager platformVcsManager;
	
	protected OssmeterLogger logger;
	
	private final static String KNOWLEDGE = "code-nlp";
	
	public CommitsIndexingMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.indexing.commits");
	}
	
	@Override
	public String getIdentifier() {
		return CommitsIndexingMetricProvider.class.getCanonicalName();
	}
	
	@Override
	public String getShortIdentifier() {
		return "metricprovider.indexing.commits";
	}

	@Override
	public String getFriendlyName() {
		return "Commits indexer";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric prepares and indexes documents relating to commits.";
	}

	@Override
	public boolean appliesTo(Project project) {
		if(project.getVcsRepositories().size()>0)
			return true;
		return false; 
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
		this.platformVcsManager=context.getPlatformVcsManager();
		this.context = context;
	}

	@Override
	public void measure(Project project, ProjectDelta delta, Indexing db) {
		
		String projectName = delta.getProject().getName();
		ObjectMapper mapper = new ObjectMapper();
		String documentType="commit";
		String indexName;
		String uid;
		String mapping;
		String document;
		
		
		VcsProjectDelta vcsd = delta.getVcsDelta();
		for (VcsRepositoryDelta vcsRepositoryDelta : vcsd.getRepoDeltas())
		{	
			VcsRepository repository = vcsRepositoryDelta.getRepository();
			for (VcsCommit commit : vcsRepositoryDelta.getCommits())
			{
				indexName = Indexer.generateIndexName("vcs", documentType, KNOWLEDGE);
				uid = generateUniqueDocumentationId(projectName, repository.getUrl(), commit.getRevision());
				mapping = Mapping.getMapping(documentType);
				
				
				CommitDocument cd = new CommitDocument(projectName,
														uid,
														repository.getUrl(),
														commit);
				enrichCommitDocument(project, commit, repository.getUrl(), cd);
				try {
					document = mapper.writeValueAsString(cd);
					Indexer.indexDocument(indexName, mapping, documentType, uid, document);
				} catch (JsonProcessingException e) {
					logger.error("Error while processing json:", e);
					e.printStackTrace();
				}
			}
		}
	}
	
	private String generateUniqueDocumentationId(String projectName, String repository, String revision) {

		return "Commit "+ projectName + " " + repository+ " " + revision;
	}
	
	private void enrichCommitDocument(Project project, VcsCommit commit, String repositoryURL, CommitDocument cd) {

		IndexPrepTransMetric indexPrepTransMetric = ((IndexPreparationTransMetricProvider) uses.get(0)).adapt(context.getProjectDB(project));

		for (String metricIdentifier : indexPrepTransMetric.getExecutedMetricProviders().first().getMetricIdentifiers())
		{

			switch (metricIdentifier) 
			{
				case "org.eclipse.scava.metricprovider.trans.commits.message.plaintext.CommitsMessagePlainTextTransMetricProvider":
				{
					CommitsMessagePlainTextTransMetric plainTextCollection = new CommitsMessagePlainTextTransMetricProvider().adapt(context.getProjectDB(project));
					CommitMessagePlainText plainText = findCollection(plainTextCollection,
																	CommitMessagePlainText.class,
																	plainTextCollection.getCommitsMessagesPlainText(),
																	commit,
																	repositoryURL);
					cd.setPlain_text(String.join(" ",plainText.getPlainText()));
					break;
				}
				case "org.eclipse.scava.metricprovider.trans.commits.message.references.CommitsMessageReferencesTransMetricProvider":
				{
					CommitsMessageReferenceTransMetric referencesDB = new CommitsMessageReferencesTransMetricProvider().adapt(context.getProjectDB(project));
					CommitMessageReferringTo references = findCollection(referencesDB,
															CommitMessageReferringTo.class,
															referencesDB.getCommitsMessagesReferringTo(),
															commit, repositoryURL);
					if(references!=null)
					{
						cd.setCommits_references(references.getCommitsReferred());
						cd.setBugs_references(references.getBugsReferred());
					}
					break;
				}
			}
		}
	}
	
	private <T extends Pongo> T findCollection(PongoDB db, Class<T> type, PongoCollection<T> collection,
			VcsCommit commit, String repositoryURL) {

		T output = null;

		Iterable<T> iterator = collection.find(
				getStringQueryProducer(type, output, "REPOSITORY").eq(repositoryURL),
				getStringQueryProducer(type, output, "REVISION").eq(commit.getRevision()));
	
		for (T t : iterator) {
			output = t;
		}
			
		return output;
	}
		
	private <T extends Pongo> StringQueryProducer getStringQueryProducer(Class<T> type, T t, String field) {

		try {
			return (StringQueryProducer) type.getDeclaredField(field).get(t);

		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			logger.error("Error while searching data in MongoBD:", e);
			e.printStackTrace();
		}
		return null;
	}

}
