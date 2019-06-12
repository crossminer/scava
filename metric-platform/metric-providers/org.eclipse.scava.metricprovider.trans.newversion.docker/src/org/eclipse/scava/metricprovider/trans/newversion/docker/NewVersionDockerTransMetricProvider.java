package org.eclipse.scava.metricprovider.trans.newversion.docker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.DockerDependenciesTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.model.DockerDependencies;
import org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.model.DockerDependency;
import org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.model.DockerDependencyCollection;
import org.eclipse.scava.metricprovider.trans.newversion.docker.model.NewDockerVersion;
import org.eclipse.scava.metricprovider.trans.newversion.docker.model.NewDockerVersions;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.Project;

import com.mongodb.DB;

public class NewVersionDockerTransMetricProvider implements ITransientMetricProvider<NewDockerVersions> {
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	private final OssmeterLogger logger;
	
	public NewVersionDockerTransMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.trans.newversion.docker.NewVersionDockerTransMetricProvider");
	}
    
    @Override
    public String getIdentifier() {
    return NewVersionDockerTransMetricProvider.class.getCanonicalName();
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
    
    @Override
    public NewDockerVersions adapt(DB db) {
    	return new NewDockerVersions(db);
    }
    
    @Override
	public void measure(Project project, ProjectDelta projectDelta, NewDockerVersions db) {
    	
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
		

		DockerDependencies dockerDependencies = 
				((DockerDependenciesTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
    	
		DockerDependencyCollection col = dockerDependencies.getDependencies();
		
        
		for (DockerDependency dockerDependency: col) {
			
			if(dockerDependency.getSubType().equals("image"))
				continue;
            
            String newVersion = testAptCache(dockerDependency.getDependencyName());
            
            if(newVersion == null || dockerDependency.getDependencyVersion().equals("N/A"))
            		continue;
            
            if(testNewerVersion(dockerDependency.getDependencyVersion(), newVersion)) {
            	NewDockerVersion dv = new NewDockerVersion();
            	
            	dv.setPackageName(dockerDependency.getDependencyName());
            	dv.setOldVersion(dockerDependency.getDependencyVersion());
            	dv.setNewVersion(newVersion);

				db.getNewVersions().add(dv);
				db.sync();
            }
        }
    }
    
    @Override
    public String getShortIdentifier() {
    	return "newVersionDocker";
    }
    
    @Override
    public String getFriendlyName() {
    	return "NewVersionDocker";
    }
    
    @Override
    public String getSummaryInformation() {
    	return "TODO";
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
