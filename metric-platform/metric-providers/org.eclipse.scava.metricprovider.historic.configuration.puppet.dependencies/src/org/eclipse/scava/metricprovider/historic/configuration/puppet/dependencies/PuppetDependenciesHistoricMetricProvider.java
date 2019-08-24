package org.eclipse.scava.metricprovider.historic.configuration.puppet.dependencies;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.configuration.puppet.dependencies.model.PuppetDependenciesHistoricMetric;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.PuppetDependenciesTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.model.PuppetDependencies;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.model.PuppetDependencyCollection;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class PuppetDependenciesHistoricMetricProvider extends AbstractHistoricalMetricProvider {
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	private final OssmeterLogger logger;
	
	public final static String IDENTIFIER = "org.eclipse.scava.metricprovider.historic.configuration.puppet.dependencies";
	
	public PuppetDependenciesHistoricMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.historic.configuration.puppet.dependencies.PuppetDependenciesHistoricMetricProvider");
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
    	return Arrays.asList(PuppetDependenciesTransMetricProvider.class.getCanonicalName());
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
    	
    	PuppetDependencies puppetDependencies = 
				((PuppetDependenciesTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
    	
    	PuppetDependenciesHistoricMetric puppetHistoricDependencies = new PuppetDependenciesHistoricMetric();
    	
    	PuppetDependencyCollection col = puppetDependencies.getDependencies();
    	
    	int numDependencies = 0;
    	
    	numDependencies = (int)col.size();
    	
    	puppetHistoricDependencies.setNumberOfPuppetDependencies(numDependencies);
    	
    	return puppetHistoricDependencies;
    }
    
    @Override
    public String getShortIdentifier() {
    	return "HistoricPuppetDependencies";
    }
    
    @Override
    public String getFriendlyName() {
    	return "Historic Puppet Dependencies Metric";
    }
    
    @Override
    public String getSummaryInformation() {
    	return "This is the historic version of the metric that returns the dependencies that are defined in the puppet files of a project";
    }
}