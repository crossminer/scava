package org.eclipse.scava.metricprovider.trans.newversion.maven;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.metricprovider.rascal.trans.model.*;
import org.eclipse.scava.metricprovider.trans.newversion.maven.model.NewMavenVersions;
import org.eclipse.scava.metricprovider.rascal.RascalMetricProvider;

import com.mongodb.DB;

public class NewVersionMavenTransMetricProvider implements ITransientMetricProvider<NewMavenVersions> {
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	private final OssmeterLogger logger;
	
	public NewVersionMavenTransMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.trans.newversion.maven.NewVersionMavenTransMetricProvider");
	}
    
    @Override
    public String getIdentifier() {
    return NewVersionMavenTransMetricProvider.class.getCanonicalName();
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
    	return Arrays.asList(RascalMetricProvider.class.getCanonicalName());
    }
    
    public void setMetricProviderContext(MetricProviderContext context) {
    	this.context = context;
    }
    
    @Override
    public NewMavenVersions adapt(DB db) {
    	return new NewMavenVersions(db);
    }
    
    @Override
	public void measure(Project project, ProjectDelta delta, NewMavenVersions db) {
    	for (IMetricProvider used : uses) {

    		RascalMetrics metrics =  ((RascalMetricProvider)used).adapt(context.getProjectDB(project));

    		for (Measurement mes : metrics.getMeasurements().findURIMeasurementsByUri("maven.allMavenDependencies")) {

    			URIMeasurement dep = (URIMeasurement)mes;
    			
    			System.out.println("\n\n\n\n" + dep.getValue() + "\n\n\n\n");
    			logger.info("\n\n\n\n" + dep.getValue() + "\n\n\n\n");
    			logger.error("\n\n\n\n" + dep.getValue() + "\n\n\n\n");

    		}

    	} 
    }
    
    @Override
    public String getShortIdentifier() {
    	return "newVersionMaven";
    }
    
    @Override
    public String getFriendlyName() {
    	return "NewVersionMaven";
    }
    
    @Override
    public String getSummaryInformation() {
    	return "TODO";
    }

}
