package org.eclipse.scava.metricprovider.historic.configuration.puppet.designsmells;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.configuration.puppet.designsmells.model.PuppetDesignSmellsHistoricMetric;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.designsmells.PuppetDesignTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.designsmells.model.DesignSmellCollection;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.designsmells.model.DesignSmells;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.designsmells.model.Smell;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class PuppetDesignHistoricMetricProvider extends AbstractHistoricalMetricProvider {
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	private final OssmeterLogger logger;
	
	public PuppetDesignHistoricMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.historic.configuration.puppet.PuppetTransMetricProvider");
	}
    
    @Override
    public String getIdentifier() {
    	return PuppetDesignHistoricMetricProvider.class.getCanonicalName();
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
    	return Arrays.asList(PuppetDesignTransMetricProvider.class.getCanonicalName());
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
    	
    	DesignSmells designSmells = 
				((PuppetDesignTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
    	
    	PuppetDesignSmellsHistoricMetric designHistoricSmells = new PuppetDesignSmellsHistoricMetric();
    	
    	DesignSmellCollection col = designSmells.getSmells();
    	
    	int multifaceted = 0;
    	int unnecessary = 0;
    	int imperative = 0;
    	int missingab = 0;
    	int insufficient = 0;
    	int unstructured = 0;
    	int tight = 0;
    	int broken = 0;
    	int missingdep = 0;
    	int hairball = 0;
    	int deficient = 0;
    	int weaken = 0;
    	
    	for (Smell smell: col) {
    		switch (smell.getSmellName()) {
	            case "Multifaceted Abstraction - Form 1":  multifaceted++;
	            	break;
	            case "Multifaceted Abstraction - Form 2":  multifaceted++;
            		break;
	            case "Unnecessary Abstraction":  unnecessary++;
	        		break;
	            case "Imperative Abstraction":  imperative++;
	    			break;
	            case "Missing Abstraction":  missingab++;
	    			break;
	            case "Insufficient Modularization - Form 1":  insufficient++;
					break;
	            case "Insufficient Modularization - Form 2":  insufficient++;
					break;
	            case "Insufficient Modularization - Form 3":  insufficient++;
					break;
	            case "Unstructured Module - Form 1":  unstructured++;
					break;
	            case "Unstructured Module - Form 2":  unstructured++;
					break;
	            case "Unstructured Module - Form 3":  unstructured++;
					break;
	            case "Tightly-coupled Module":  tight++;
					break;
	            case "Broken Hierarchy":  broken++;
					break;
	            case "Missing Dependency":  missingdep++;
					break;
	            case "Hairball Structure":  hairball++;
					break;
	            case "Deficient Encapsulation":  deficient++;
					break;
	            case "Weakend Modularity":  weaken++;
					break;
					
    		}
    		
    	}
    	
    	designHistoricSmells.setCumulativeNumberOfDesignUsers((int)col.size());
    	designHistoricSmells.setNumberOfMultifacetedSmells(multifaceted);
    	designHistoricSmells.setNumberOfUnnecessarySmells(unnecessary);
    	designHistoricSmells.setNumberOfImperativeSmells(imperative);
    	designHistoricSmells.setNumberOfMissAbSmells(missingab);
    	designHistoricSmells.setNumberOfInsufficientSmells(insufficient);
    	designHistoricSmells.setNumberOfUnstructuredSmells(unstructured);
    	designHistoricSmells.setNumberOfTightSmells(tight);
    	designHistoricSmells.setNumberOfBrokenSmells(broken);
    	designHistoricSmells.setNumberOfMissingDepSmells(missingdep);
    	designHistoricSmells.setNumberOfHairballSmells(hairball);
    	designHistoricSmells.setNumberOfDeficientSmells(deficient);
    	designHistoricSmells.setNumberOfWeakenSmells(weaken);
    	
    	return designHistoricSmells;
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
