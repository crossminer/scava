package org.eclipse.scava.factoid.documentation.entries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.scava.metricprovider.trans.documentation.classification.DocumentationClassificationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.documentation.classification.model.DocumentationClassificationTransMetric;
import org.eclipse.scava.metricprovider.trans.documentation.classification.model.DocumentationEntryClassification;
import org.eclipse.scava.platform.AbstractFactoidMetricProvider;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.factoids.Factoid;
import org.eclipse.scava.platform.factoids.StarRating;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.VcsRepository;
import org.eclipse.scava.repository.model.documentation.gitbased.DocumentationGitBased;
import org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic;

public class DocumentationEntriesFactoid extends AbstractFactoidMetricProvider {

	private final List<String> possibleSections = Arrays.asList("API", "Development", "Installation", "Started", "User");
	
	@Override
	public String getShortIdentifier() {
		return "factoid.documentation.entries";
	}

	@Override
	public String getFriendlyName() {
		return "Documentation Entries";
	}

	@Override
	public String getSummaryInformation() {
		return "This plugin generates the factoid regarding which sections have been found and which are "
				+ "missing in the documentation. "
				+ "This can help to understand which sections should be added or better indicated to have a better documentation.";
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
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(DocumentationClassificationTransMetricProvider.class.getCanonicalName());
	}
	
	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
		factoid.setName(getFriendlyName());
		
		DocumentationClassificationTransMetric documentationMetric = ((DocumentationClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		Set<String> foundSections = new HashSet<String>();
		
		for(DocumentationEntryClassification documentationEntry : documentationMetric.getDocumentationEntriesClassification())
		{
			for(String type : documentationEntry.getTypes())
			{
				foundSections.add(type);
			}
		}
		
		StringBuffer stringBuffer = new StringBuffer();
		
		if(foundSections.size()==0)
		{
			factoid.setStars(StarRating.ONE);
			stringBuffer.append("No sections were found in the documentation.\n");
		}
		else if(foundSections.size()>=5)
		{
			factoid.setStars(StarRating.FOUR);
			stringBuffer.append("The documentation contains entries regarding all the 5 sections searched ");
			stringBuffer.append("("+String.join(", ", possibleSections)+").\n");
		}
		else
		{
			if(foundSections.size()>=3)
				factoid.setStars(StarRating.THREE);
			else if(foundSections.size()>=1)
				factoid.setStars(StarRating.TWO);
			stringBuffer.append("The documentation contains some entries of the 5 sections searched ");
			stringBuffer.append("("+String.join(", ", foundSections)+") while misses ");
			List<String> missing = new ArrayList<String>();
			for(String possible : possibleSections)
			{
				if(!foundSections.contains(possible))
					missing.add(possible);
			}
			stringBuffer.append(missing.size()+" sections ("+String.join(",", missing)+")");
			
		}
		
		factoid.setFactoid(stringBuffer.toString());
	}
	
	

}
