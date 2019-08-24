package org.eclipse.scava.metricprovider.trans.newversion.puppet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.PuppetDependenciesTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.model.PuppetDependencies;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.model.PuppetDependency;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.model.PuppetDependencyCollection;
import org.eclipse.scava.metricprovider.trans.newversion.puppet.model.NewPuppetVersion;
import org.eclipse.scava.metricprovider.trans.newversion.puppet.model.NewPuppetVersions;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.Project;

import com.mongodb.DB;

public class NewVersionPuppetTransMetricProvider implements ITransientMetricProvider<NewPuppetVersions> {
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	private final OssmeterLogger logger;
	
	public NewVersionPuppetTransMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.trans.newversion.puppet.NewVersionPuppetTransMetricProvider");
	}
    
    @Override
    public String getIdentifier() {
    return NewVersionPuppetTransMetricProvider.class.getCanonicalName();
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
    
    @Override
    public NewPuppetVersions adapt(DB db) {
    	return new NewPuppetVersions(db);
    }
    
    @Override
	public void measure(Project project, ProjectDelta projectDelta, NewPuppetVersions db) {
    	
    	if (uses.size()!=1) {
			System.err.println("Metric: " + getIdentifier() + " failed to retrieve " + 
								"the transient metric it needs!");
			System.exit(-1);
		}
		
		List<VcsRepositoryDelta> repoDeltas = projectDelta.getVcsDelta().getRepoDeltas();
		
		// don't continue if there isn't anything to do
		if (repoDeltas.isEmpty()) {
			return;
		}
		
		db.getNewVersions().getDbCollection().drop();
		db.sync();
		

		PuppetDependencies puppetDependencies = 
				((PuppetDependenciesTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
    	
		PuppetDependencyCollection col = puppetDependencies.getDependencies();
		
        
		for (PuppetDependency puppetDependency: col) {
            
            String newVersion = testAptCache(puppetDependency.getDependencyName());
            
            if(newVersion == null || puppetDependency.getDependencyVersion().equals("N/A"))
            		continue;
            
            if(testNewerVersion(puppetDependency.getDependencyVersion(), newVersion)) {
            	NewPuppetVersion pv = new NewPuppetVersion();
            	
            	pv.setPackageName(puppetDependency.getDependencyName());
            	pv.setOldVersion(puppetDependency.getDependencyVersion());
            	pv.setNewVersion(newVersion);

				db.getNewVersions().add(pv);
				db.sync();
            }
        }
    }
    
    @Override
    public String getShortIdentifier() {
    	return "newVersionPuppet";
    }
    
    @Override
    public String getFriendlyName() {
    	return "New Version Puppet";
    }
    
    @Override
    public String getSummaryInformation() {
    	return "This is the transient version of the metric that returns the number of the packages that have a new version and are defined in Puppet manifests";
    }
    
    public String testAptCache(String packageName){
        try {
        	
        	String version = null;
        	
        	Process p = Runtime.getRuntime().exec("apt-cache policy " + packageName);
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String ins = in.readLine();
			while(ins != null){
				if(ins.contains("Candidate:")){
	                version = ins;
	                break;
	            }
                ins = in.readLine();
            }
			
			if(version != null) {
				version = version.split("Candidate: ")[1];
		        
				version = version.split("-")[0];
		        
				version = version.split("_")[0];
				
				if(version.contains(":")) {
					version = version.split(":")[1];
		        }
				
				return version;
			} else
				return null;
        	
        } catch (IOException e) {
        	logger.error("unexpected exception while measuring", e);
			throw new RuntimeException(e);
        }
    }
    
    public boolean testNewerVersion(String oldVersion, String newVersion){
        oldVersion = oldVersion.split("-")[0];
        
        oldVersion = oldVersion.split("_")[0];
        
        String[] oldVersionParts = oldVersion.split("\\.");
        String[] newVersionParts = newVersion.split("\\.");
        
        int length;
        
        if(newVersionParts[0].contains(":")) {
        	newVersionParts[0] = newVersionParts[0].split(":")[1];
        }
        
        if(oldVersionParts.length < newVersionParts.length)
            length = oldVersionParts.length;
        else
            length = newVersionParts.length;
        
        if(newVersionParts[0].contains("none"))
        	return false;
        
        for(int i = 0; i < length; i++){
        	if(Integer.parseInt(newVersionParts[i]) > Integer.parseInt(oldVersionParts[i]))
                return true;
            else if(Integer.parseInt(newVersionParts[i]) == Integer.parseInt(oldVersionParts[i]))
                continue;
            else
                return false;
        }
        
        return false;
    }

}
