package org.eclipse.scava.metricprovider.historic.configuration.docker.dependencies;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.configuration.docker.dependencies.model.DockerDependenciesHistoricMetric;
import org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.DockerDependenciesTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.model.DockerDependencies;
import org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.model.DockerDependency;
import org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.model.DockerDependencyCollection;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class DockerDependenciesHistoricMetricProvider extends AbstractHistoricalMetricProvider {
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	private final OssmeterLogger logger;
	
	public final static String IDENTIFIER = "org.eclipse.scava.metricprovider.historic.configuration.docker.dependencies";
	
	public DockerDependenciesHistoricMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.historic.configuration.docker.dependencies.DockerDependenciesHistoricMetricProvider");
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
    	return Arrays.asList(DockerDependenciesTransMetricProvider.class.getCanonicalName());
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
    	
    	DockerDependencies dockerDependencies = 
				((DockerDependenciesTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
    	
    	DockerDependenciesHistoricMetric dockerHistoricDependencies = new DockerDependenciesHistoricMetric();
    	
    	DockerDependencyCollection col = dockerDependencies.getDependencies();
    	
    	int numDependencies = 0;
    	int numPackageDependencies = 0;
    	int numImageDependencies = 0;
    	
    	for (DockerDependency dockerDependency: col) {
    		numDependencies++;
    		if(dockerDependency.getSubType().equals("package"))
    			numPackageDependencies++;
    		else
    			numImageDependencies++;
    	}
    	
    	dockerHistoricDependencies.setNumberOfDockerDependencies(numDependencies);
    	dockerHistoricDependencies.setNumberOfDockerPackageDependencies(numPackageDependencies);
    	dockerHistoricDependencies.setNumberOfDockerImageDependencies(numImageDependencies);
    	
    	return dockerHistoricDependencies;
    }
    
    @Override
    public String getShortIdentifier() {
    	return "HistoricDockerDependencies";
    }
    
    @Override
    public String getFriendlyName() {
    	return "Historic Docker Dependencies Metric";
    }
    
    @Override
    public String getSummaryInformation() {
    	return "This is the historic vetsion of the metric that returns the dependencies (packages, images) that are deifned in the Dockerfiles of a project";
    }
}