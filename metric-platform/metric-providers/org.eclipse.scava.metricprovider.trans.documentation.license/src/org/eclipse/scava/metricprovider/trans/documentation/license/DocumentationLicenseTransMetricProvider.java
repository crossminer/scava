package org.eclipse.scava.metricprovider.trans.documentation.license;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.documentation.license.model.DocumentationEntryLicense;
import org.eclipse.scava.metricprovider.trans.documentation.license.model.DocumentationLicenseTransMetric;
import org.eclipse.scava.metricprovider.trans.documentation.plaintext.DocumentationPlainTextTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.documentation.plaintext.model.DocumentationEntryPlainText;
import org.eclipse.scava.metricprovider.trans.documentation.plaintext.model.DocumentationPlainTextTransMetric;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.IndexPreparationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.model.IndexPrepTransMetric;
import org.eclipse.scava.nlp.tools.license.LicenseAnalyser;
import org.eclipse.scava.nlp.tools.license.prediction.LicensePrediction;
import org.eclipse.scava.nlp.tools.license.prediction.LicensePredictionCollection;
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

public class DocumentationLicenseTransMetricProvider implements ITransientMetricProvider<DocumentationLicenseTransMetric> {

	protected PlatformVcsManager platformVcsManager;
	protected PlatformCommunicationChannelManager communicationChannelManager;
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	@Override
	public String getIdentifier() {
		return DocumentationLicenseTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "trans.documentation.license";
	}

	@Override
	public String getFriendlyName() {
		return "Documentation license detection";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric process the plain text of a documentation entry and determines whether it contains a license or not";
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
		return Arrays.asList(IndexPreparationTransMetricProvider.class.getCanonicalName(),DocumentationPlainTextTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context=context;
		this.communicationChannelManager= context.getPlatformCommunicationChannelManager();
		this.platformVcsManager=context.getPlatformVcsManager();
	}

	@Override
	public DocumentationLicenseTransMetric adapt(DB db) {
		return new DocumentationLicenseTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta delta, DocumentationLicenseTransMetric db) {
		db.getDocumentationEntriesLicense().getDbCollection().drop();
		db.sync();
		
		//This is for the indexing
		IndexPrepTransMetric indexPrepTransMetric = ((IndexPreparationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));	
		indexPrepTransMetric.getExecutedMetricProviders().first().getMetricIdentifiers().add(getIdentifier());
		indexPrepTransMetric.sync();
		
		DocumentationPlainTextTransMetric documentationPlainTextProcessor = ((DocumentationPlainTextTransMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));
		
		Iterable<DocumentationEntryPlainText> documentationEntriesPlainText = documentationPlainTextProcessor.getDocumentationEntriesPlainText();
		
		LicensePredictionCollection licensePredictionCollection = new LicensePredictionCollection();
		
		for(DocumentationEntryPlainText documentationEntryPlainText : documentationEntriesPlainText)
		{
			DocumentationEntryLicense documentationEntryLicense = findDocumentationEntryDetectingCode(db, documentationEntryPlainText);
			if(documentationEntryLicense==null)
			{
				documentationEntryLicense = new DocumentationEntryLicense();
				documentationEntryLicense.setDocumentationId(documentationEntryPlainText.getDocumentationId());
				documentationEntryLicense.setEntryId(documentationEntryPlainText.getEntryId());
				db.getDocumentationEntriesLicense().add(documentationEntryLicense);
			}
			db.sync();
			licensePredictionCollection.addText(getDocumentationEntryClassifierId(documentationEntryLicense),
												String.join(" ", documentationEntryPlainText.getPlainText()));
		}
		
		if(licensePredictionCollection.size()!=0)
		{
			HashMap<Object, LicensePrediction> predictions = LicenseAnalyser.predict(licensePredictionCollection).getIdsWithPredictedLicenseInformation();
			LicensePrediction prediction;
			for(DocumentationEntryPlainText documentationEntryPlainText : documentationEntriesPlainText)
			{
				DocumentationEntryLicense documentationEntryLicense = findDocumentationEntryDetectingCode(db, documentationEntryPlainText);
				prediction = predictions.get(getDocumentationEntryClassifierId(documentationEntryLicense));
				documentationEntryLicense.setLicenseFound(prediction.getLicenseFound());
				if(prediction.getLicenseFound())
				{
					documentationEntryLicense.setLicenseGroup(prediction.getLicenseGroup());
					documentationEntryLicense.setLicenseName(prediction.getLicenseName());
					documentationEntryLicense.setHeaderType(prediction.getIsHeader());
					documentationEntryLicense.setScore(prediction.getScore());
				}
				db.sync();
			}
		}
		
		
	}
	
	private String getDocumentationEntryClassifierId(DocumentationEntryLicense documentationEntry)
	{
		return "DOCUMENTATION#"+documentationEntry.getDocumentationId() + "#" + documentationEntry.getEntryId();
	}
	
	
	private DocumentationEntryLicense findDocumentationEntryDetectingCode (DocumentationLicenseTransMetric db, DocumentationEntryPlainText documentationEntryPlainText)
	{
		DocumentationEntryLicense documentationEntryLicense = null;
		Iterable<DocumentationEntryLicense> documentationEntryLicenseIt = db.getDocumentationEntriesLicense().
				find(DocumentationEntryLicense.DOCUMENTATIONID.eq(documentationEntryPlainText.getDocumentationId()),
						DocumentationEntryLicense.ENTRYID.eq(documentationEntryPlainText.getEntryId()));
		for(DocumentationEntryLicense del : documentationEntryLicenseIt)
			documentationEntryLicense=del;
		return documentationEntryLicense;
	}

	

}
