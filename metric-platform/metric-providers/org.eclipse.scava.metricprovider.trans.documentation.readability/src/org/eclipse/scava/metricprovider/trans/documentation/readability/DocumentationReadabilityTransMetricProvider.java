package org.eclipse.scava.metricprovider.trans.documentation.readability;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.documentation.detectingcode.DocumentationDetectingCodeTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.documentation.detectingcode.model.DocumentationDetectingCodeTransMetric;
import org.eclipse.scava.metricprovider.trans.documentation.detectingcode.model.DocumentationEntryDetectingCode;
import org.eclipse.scava.metricprovider.trans.documentation.readability.model.DocumentationEntryReadability;
import org.eclipse.scava.metricprovider.trans.documentation.readability.model.DocumentationReadabilityTransMetric;
import org.eclipse.scava.nlp.tools.readability.Readability;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.vcs.PlatformVcsManager;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.VcsRepository;
import org.eclipse.scava.repository.model.github.GitHubWiki;

import com.mongodb.DB;

public class DocumentationReadabilityTransMetricProvider implements ITransientMetricProvider<DocumentationReadabilityTransMetric> {

	protected PlatformVcsManager platformVcsManager;
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	@Override
	public String getIdentifier() {
		return DocumentationReadabilityTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "trans.documentation.readability";
	}

	@Override
	public String getFriendlyName() {
		return null;
	}

	@Override
	public String getSummaryInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean appliesTo(Project project) {
		for(VcsRepository repository : project.getVcsRepositories())
			if(repository instanceof GitHubWiki) return true;
		return false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses=uses;
		
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(DocumentationDetectingCodeTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context=context;
		this.platformVcsManager=context.getPlatformVcsManager();
	}

	@Override
	public DocumentationReadabilityTransMetric adapt(DB db) {
		return new DocumentationReadabilityTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta delta, DocumentationReadabilityTransMetric db) {
		
		db.getDocumentationEntriesReadability().getDbCollection().drop();
		db.sync();
		
		DocumentationDetectingCodeTransMetric documentationDetectingCode = ((DocumentationDetectingCodeTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		Iterable<DocumentationEntryDetectingCode> documentationEntriesDetectingCode = documentationDetectingCode.getDocumentationEntriesDetectingCode();
		
		for(DocumentationEntryDetectingCode documentationEntry : documentationEntriesDetectingCode)
		{
			DocumentationEntryReadability documentationEntryReadability = findDocumentationEntryDetectingCode(db, documentationEntry);
			if(documentationEntryReadability==null)
			{
				documentationEntryReadability= new DocumentationEntryReadability();
				documentationEntryReadability.setDocumentationId(documentationEntry.getDocumentationId());
				documentationEntryReadability.setEntryId(documentationEntry.getEntryId());
				db.getDocumentationEntriesReadability().add(documentationEntryReadability);
			}
			documentationEntryReadability.setReadability(Readability.calculateDaleChall(documentationEntry.getNaturalLanguage()));
			db.sync();
		}
	}
	
	private DocumentationEntryReadability findDocumentationEntryDetectingCode (DocumentationReadabilityTransMetric db, DocumentationEntryDetectingCode documentationEntry)
	{
		DocumentationEntryReadability documentationEntryReadability = null;
		Iterable<DocumentationEntryReadability> documentationEntryRIt = db.getDocumentationEntriesReadability().
				find(DocumentationEntryReadability.DOCUMENTATIONID.eq(documentationEntry.getDocumentationId()),
						DocumentationEntryReadability.ENTRYID.eq(documentationEntry.getEntryId()));
		for(DocumentationEntryReadability der : documentationEntryRIt)
			documentationEntryReadability=der;
		return documentationEntryReadability;
	}

}
