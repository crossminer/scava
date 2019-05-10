package org.eclipse.scava.metricprovider.historic.configuration.puppet.implementationsmells;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.configuration.puppet.implementationsmells.model.PuppetImplementationSmellsHistoricMetric;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.PuppetImplementationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.ImplementationSmellCollection;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.ImplementationSmells;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.Smell;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class PuppetImplementationHistoricMetricProvider extends AbstractHistoricalMetricProvider {
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	private final OssmeterLogger logger;
	
	public final static String IDENTIFIER = "org.eclipse.scava.metricprovider.historic.configuration.puppet.implementationsmells";
	
	public PuppetImplementationHistoricMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.historic.configuration.puppet.PuppetImplementationHistoricMetricProvider");
	}
    
    @Override
    public String getIdentifier() {
    	return IDENTIFIER;
    }
    
    @Override
    public boolean appliesTo(Project project) {
    	return project.getVcsRepositories().size() > 0;
    }
    
    @Override
    public void setUses(List<IMetricProvider> uses) {
    	this.uses = uses;
    }
        
    @Override
    public List<String> getIdentifiersOfUses() {
    	return Arrays.asList(PuppetImplementationTransMetricProvider.class.getCanonicalName());
    }
    
    public void setMetricProviderContext(MetricProviderContext context) {
    	this.context = context;
    }
    
    public Pongo measure(Project project) {
    	
    	if (uses.size()!=1) {
			System.err.println("Metric: " + getIdentifier() + " failed to retrieve " + 
								"the transient metric it needs!");
			System.exit(-1);
		}
    	
    	ImplementationSmells implementationSmells = 
				((PuppetImplementationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
    	
    	PuppetImplementationSmellsHistoricMetric implementationHistoricSmells = new PuppetImplementationSmellsHistoricMetric();
    	
    	ImplementationSmellCollection col = implementationSmells.getSmells();
    	
    	int missingDefaultCase = 0;
    	int inconsistentNaming = 0;
    	int duplicateEntity = 0;
    	int misplacedAttribute = 0;
    	int improperAlignment = 0;
    	int invalidProperty = 0;
    	int improperQuote = 0;
    	int longStatements = 0;
    	int unguardedVariable = 0;
    	int missingDoc = 0;
    	int deprecatedStatements = 0;
    	int incompleteTasks = 0;
    	int complexExpression = 0;
    	int missingElse = 0;
    	
    	for (Smell smell: col) {
    		switch (smell.getSmellName()) {
	            case "Missing default case":  missingDefaultCase++;
	            	break;
	            case "Inconsistent naming convention":  inconsistentNaming++;
            		break;
	            case "Duplicate entity":  duplicateEntity++;
	        		break;
	            case "Misplaced attribute":  misplacedAttribute++;
	    			break;
	            case "Improper alignment":  improperAlignment++;
	    			break;
	            case "Invalid property value":  invalidProperty++;
					break;
	            case "Improper quote usage":  improperQuote++;
					break;
	            case "Long statements":  longStatements++;
					break;
	            case "Unguarded variable":  unguardedVariable++;
					break;
	            case "Missing Documentation":  missingDoc++;
					break;
	            case "Deprecated Statements":  deprecatedStatements++;
					break;
	            case "Incomplete tasks":  incompleteTasks++;
					break;
	            case "Complex Expression":  complexExpression++;
					break;
	            case "Missing Else":  missingElse++;
					break;
					
    		}
    		
    	}
    	
    	implementationHistoricSmells.setCumulativeNumberOfImplementationUsers((int)col.size());
    	implementationHistoricSmells.setNumberOfMissingDefaultCaseSmells(missingDefaultCase);
    	implementationHistoricSmells.setNumberOfInconsistentNamingSmells(inconsistentNaming);
    	implementationHistoricSmells.setNumberOfDuplicateEntitySmells(duplicateEntity);
    	implementationHistoricSmells.setNumberOfMisplacedAttributeSmells(misplacedAttribute);
    	implementationHistoricSmells.setNumberOfImproperAlignment(improperAlignment);
    	implementationHistoricSmells.setNumberOfInvalidPropertySmells(invalidProperty);
    	implementationHistoricSmells.setNumberOfImproperQuoteSmells(improperQuote);
    	implementationHistoricSmells.setNumberOfLongStatementsSmells(longStatements);
    	implementationHistoricSmells.setNumberOfUnguardedVariableSmells(unguardedVariable);
    	implementationHistoricSmells.setNumberOfMissingDocSmells(missingDoc);
    	implementationHistoricSmells.setNumberOfDeprecatedStatementsSmells(deprecatedStatements);
    	implementationHistoricSmells.setNumberOfIncompleteTasksSmells(incompleteTasks);
    	implementationHistoricSmells.setNumberOfComplexExpressionSmells(complexExpression);
    	implementationHistoricSmells.setNumberOfMissingElseSmells(missingElse);
    	
    	return implementationHistoricSmells;
    }
    
    @Override
    public String getShortIdentifier() {
    	return "puppet";
    }
    
    @Override
    public String getFriendlyName() {
    	return "Puppet";
    }
    
    @Override
    public String getSummaryInformation() {
    	return "TODO";
    }

}
