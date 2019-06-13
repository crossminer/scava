package org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.model.PuppetDependencies;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.model.PuppetDependency;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.model.PuppetDependencyCollection;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.vcs.VcsCommit;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.platform.vcs.workingcopy.manager.WorkingCopyCheckoutException;
import org.eclipse.scava.platform.vcs.workingcopy.manager.WorkingCopyFactory;
import org.eclipse.scava.platform.vcs.workingcopy.manager.WorkingCopyManagerUnavailable;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.VcsRepository;

import com.mongodb.DB;

public class PuppetDependenciesTransMetricProvider implements ITransientMetricProvider<PuppetDependencies> {
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	private static String lastRevision = null;
	
	private static Map<String, File> workingCopyFolders = new HashMap<>();
	private static Map<String, File> scratchFolders = new HashMap<>();
	
	private final OssmeterLogger logger;
	
	public PuppetDependenciesTransMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.trans.configuration.puppet.dependencies.PuppetDependenciesTransMetricProvider");
	}
    
    @Override
    public String getIdentifier() {
    return PuppetDependenciesTransMetricProvider.class.getCanonicalName();
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
    	return Collections.emptyList();
    }
    
    public void setMetricProviderContext(MetricProviderContext context) {
    	this.context = context;
    }
    
    @Override
    public PuppetDependencies adapt(DB db) {
    	return new PuppetDependencies(db);
    }
    
    public void measure(Project project, ProjectDelta projectDelta, PuppetDependencies db) {
    	
    	try {
    		
				List<VcsRepositoryDelta> repoDeltas = projectDelta.getVcsDelta().getRepoDeltas();
				
				// don't continue if there isn't anything to do
				if (repoDeltas.isEmpty()) {
					return;
				}
				
				InputStream input = getClass().getClassLoader().getResourceAsStream("/META-INF/puppeteer.properties");
	    		Properties prop = new Properties();
	    		prop.load(input);
	
				computeFolders(project, projectDelta, workingCopyFolders, scratchFolders);
				
				for (VcsRepository repo : project.getVcsRepositories()) {
					
					String repoUrl = repo.getUrl();
					
					PuppetDependencyCollection pc = db.getDependencies();
					for (PuppetDependency p : pc) {
						pc.remove(p);
					}
					db.sync();
					
					Process p = Runtime.getRuntime().exec(prop.getProperty("python") + " " + prop.getProperty("puppeteer") + " " + workingCopyFolders.get(repoUrl) + "/ 2");
					BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String ins = in.readLine();
					while(ins != null){
		                if(ins.startsWith("package:")) {
		                	if(!ins.split(":")[1].contains("$")) {
			                	PuppetDependency puppetDependency = new PuppetDependency();
			                	puppetDependency.setDependencyName(ins.split(":")[1]);
								puppetDependency.setDependencyVersion("N/A");
								puppetDependency.setType("puppet");
	
								db.getDependencies().add(puppetDependency);
								db.sync();
		                	}
		                }
		                ins = in.readLine();
		            }
					
	    		}
	    	}
	    	catch (WorkingCopyManagerUnavailable | WorkingCopyCheckoutException e) {
	    		logger.error("unexpected exception while measuring", e);
				throw new RuntimeException("Metric failed due to missing working copy", e);
			} catch (IOException e) {
				logger.error(e.getMessage());
				throw new RuntimeException(e);
			}
    }
    
    @Override
    public String getShortIdentifier() {
    	return "TransientPuppetDependencies";
    }
    
    @Override
    public String getFriendlyName() {
    	return "Transient Puppet Dependencies Metric";
    }
    
    @Override
    public String getSummaryInformation() {
    	return "This metric returns the dependencies that are defined in the puppet files of a project";
    }
    
    private void computeFolders(Project project, ProjectDelta delta, Map<String, File> wc, Map<String, File> scratch) throws WorkingCopyManagerUnavailable, WorkingCopyCheckoutException {
		WorkingCopyFactory.getInstance().checkout(project, getLastRevision(delta), wc, scratch);
	}
    
    private String getLastRevision(ProjectDelta delta) {
		List<VcsRepositoryDelta> repoDeltas = delta.getVcsDelta().getRepoDeltas();
		if (repoDeltas.isEmpty()) {
			return lastRevision;
		}
		VcsRepositoryDelta deltas = repoDeltas.get(repoDeltas.size() - 1);
		List<VcsCommit> commits = deltas.getCommits();
		String revision = commits.get(commits.size() - 1).getRevision();
		return revision;
	}

}
