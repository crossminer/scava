package org.eclipse.scava.metricprovider.trans.documentation.plaintext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.documentation.DocumentationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.documentation.model.DocumentationEntry;
import org.eclipse.scava.metricprovider.trans.documentation.model.DocumentationTransMetric;
import org.eclipse.scava.metricprovider.trans.documentation.plaintext.model.DocumentationEntryPlainText;
import org.eclipse.scava.metricprovider.trans.documentation.plaintext.model.DocumentationPlainTextTransMetric;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.IndexPreparationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.model.IndexPrepTransMetric;
import org.eclipse.scava.nlp.tools.plaintext.PlainTextObject;
import org.eclipse.scava.nlp.tools.plaintext.documentation.PlainTextDocumentationHtmlBased;
import org.eclipse.scava.nlp.tools.plaintext.documentation.PlainTextDocumentationOthers;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.eclipse.scava.platform.delta.vcs.PlatformVcsManager;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.VcsRepository;
import org.eclipse.scava.repository.model.documentation.gitbased.DocumentationGitBased;
import org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic;

import com.mongodb.DB;

public class DocumentationPlainTextTransMetricProvider implements ITransientMetricProvider<DocumentationPlainTextTransMetric> {

	protected PlatformVcsManager platformVcsManager;
	protected PlatformCommunicationChannelManager communicationChannelManager;
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	@Override
	public String getIdentifier() {
		return DocumentationPlainTextTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "trans.documentation.plaintext";
	}

	@Override
	public String getFriendlyName() {
		return "Documentation plain text processor.";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric process the body of each documentation entry and extracts the plain text";
	}

	@Override
	public boolean appliesTo(Project project) {
		for(VcsRepository repository : project.getVcsRepositories())
			if(repository instanceof DocumentationGitBased) return true;
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels())
			if (communicationChannel instanceof DocumentationSystematic) return true;
		return false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses=uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(IndexPreparationTransMetricProvider.class.getCanonicalName(),DocumentationTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context=context;
		this.communicationChannelManager= context.getPlatformCommunicationChannelManager();
		this.platformVcsManager=context.getPlatformVcsManager();
	}

	@Override
	public DocumentationPlainTextTransMetric adapt(DB db) {
		return new DocumentationPlainTextTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta delta, DocumentationPlainTextTransMetric db) {
		
		db.getDocumentationEntriesPlainText().getDbCollection().drop();
		db.sync();
		
		//This is for the indexing
		IndexPrepTransMetric indexPrepTransMetric = ((IndexPreparationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));	
		indexPrepTransMetric.getExecutedMetricProviders().first().getMetricIdentifiers().add(getIdentifier());
		indexPrepTransMetric.sync();
		
		DocumentationTransMetric documentationProcessor = ((DocumentationTransMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));
		
		Iterable<DocumentationEntry> documentationEntries = documentationProcessor.getDocumentationEntries();
		
		for(DocumentationEntry documentationEntry : documentationEntries)
		{
			DocumentationEntryPlainText documentationEntryPlainText = findDocumentationEntryPlainText(db, documentationEntry);
			if(documentationEntryPlainText==null)
			{
				documentationEntryPlainText=new DocumentationEntryPlainText();
				documentationEntryPlainText.setDocumentationId(documentationEntry.getDocumentationId());
				documentationEntryPlainText.setEntryId(documentationEntry.getEntryId());
				db.getDocumentationEntriesPlainText().add(documentationEntryPlainText);
			}
			//For a weird reason the addAll wasn't adding all the lines
			for(String line : getPlainText(documentationEntry.getBody(), documentationEntry.getHtmlFormatted()))
				documentationEntryPlainText.getPlainText().add(line);
		}
	}
	
	private List<String> getPlainText(String fileContent, boolean htmlFormatted)
	{
		PlainTextObject plainText=null;
		if(htmlFormatted)
			plainText = PlainTextDocumentationHtmlBased.process(fileContent);
		else
			plainText = PlainTextDocumentationOthers.process(fileContent);
		if(plainText==null)
			return new ArrayList<String>(0);
		return plainText.getPlainTextAsList();
	}
	
	private DocumentationEntryPlainText findDocumentationEntryPlainText (DocumentationPlainTextTransMetric db, DocumentationEntry documentationEntry)
	{
		DocumentationEntryPlainText documentationEntryPlainText = null;
		Iterable<DocumentationEntryPlainText> documentationEntryPTIt = db.getDocumentationEntriesPlainText().
				find(DocumentationEntryPlainText.DOCUMENTATIONID.eq(documentationEntry.getDocumentationId()),
						DocumentationEntryPlainText.ENTRYID.eq(documentationEntry.getEntryId()));
		for(DocumentationEntryPlainText dept : documentationEntryPTIt)
			documentationEntryPlainText=dept;
		return documentationEntryPlainText;
	}

}
