package org.eclipse.scava.metricprovider.trans.configuration.puppet.designantipatterns;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.scava.metricprovider.trans.configuration.puppet.designantipatterns.model.DesignAntipattern;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.designantipatterns.model.DesignAntipatterns;
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

public class PuppetDesignAntipatternTransMetricProvider implements ITransientMetricProvider<DesignAntipatterns> {
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	private static String lastRevision = null;
	
	private static Map<String, File> workingCopyFolders = new HashMap<>();
	private static Map<String, File> scratchFolders = new HashMap<>();
	
	private final OssmeterLogger logger;
	
	public PuppetDesignAntipatternTransMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.trans.configuration.puppet.designantipatterns.PuppetDesignAntipatternsTransMetricProvider");
	}
    
    @Override
    public String getIdentifier() {
    return PuppetDesignAntipatternTransMetricProvider.class.getCanonicalName();
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
    public DesignAntipatterns adapt(DB db) {
    	return new DesignAntipatterns(db);
    }
    
    public void measure(Project project, ProjectDelta projectDelta, DesignAntipatterns db) {
    	
    	try {
    		
	    		InputStream input = getClass().getClassLoader().getResourceAsStream("/META-INF/puppeteer.properties");
	    		Properties prop = new Properties();
	    		prop.load(input);
    		
				List<VcsRepositoryDelta> repoDeltas = projectDelta.getVcsDelta().getRepoDeltas();
				
				// don't continue if there isn't anything to do
				if (repoDeltas.isEmpty()) {
					return;
				}
	
				computeFolders(project, projectDelta, workingCopyFolders, scratchFolders);
				
				for (VcsRepository repo : project.getVcsRepositories()) {
					
					String repoUrl = repo.getUrl();
					
					String wcpath = workingCopyFolders.get(repoUrl).getPath().toString();
					
					
					//FIXME: It doesn't work because we can not find the full path of a resource inside a bundle so for now we use full path
					Process p = Runtime.getRuntime().exec(prop.getProperty("python") + " " + prop.getProperty("puppeteer") + " " + workingCopyFolders.get(repoUrl) + "/ 2");
					BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String ins = in.readLine();
					while(ins != null){
		                if(ins.split(",").length == 4) {
		                	DesignAntipattern designAntipattern = new DesignAntipattern();
		                	designAntipattern.setSmellName(ins.split(",")[1].trim());
		                	designAntipattern.setReason(ins.split(",")[2].trim());
		                	designAntipattern.setFileName(ins.split(",")[3].trim().replace(wcpath, ""));
		                	designAntipattern.setCommit(getLastRevision(projectDelta));
		                	designAntipattern.setDate(getLastDate(projectDelta));
							db.getDesignAntipatterns().add(designAntipattern);
							db.sync();
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
    	return "TransientPuppetDesignAntipatterns";
    }
    
    @Override
    public String getFriendlyName() {
    	return "Transient Puppet Design Antipatterns";
    }
    
    @Override
    public String getSummaryInformation() {
    	return "This is the metric that returns the design antipatterns that are defined in the puppet manifests of a project";
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
    
    private Date getLastDate(ProjectDelta delta) {
		List<VcsRepositoryDelta> repoDeltas = delta.getVcsDelta().getRepoDeltas();
		if (repoDeltas.isEmpty()) {
			return new Date();
		}
		VcsRepositoryDelta deltas = repoDeltas.get(repoDeltas.size() - 1);
		List<VcsCommit> commits = deltas.getCommits();
		Date date = commits.get(commits.size() - 1).getJavaDate();
		return date;
	}

}
