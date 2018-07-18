package org.eclipse.scava.metricprovider.trans.configuration.puppet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.metricprovider.trans.configuration.puppet.model.PuppetSmells;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.model.Smell;
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
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.mongodb.DB;

public class PuppetTransMetricProvider implements ITransientMetricProvider<PuppetSmells> {
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	private static String lastRevision = null;
	
	private static Map<String, File> workingCopyFolders = new HashMap<>();
	private static Map<String, File> scratchFolders = new HashMap<>();
	
	private final OssmeterLogger logger;
	
	public PuppetTransMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.trans.configuration.puppet.PuppetTransMetricProvider");
	}
    
    @Override
    public String getIdentifier() {
    return PuppetTransMetricProvider.class.getCanonicalName();
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
    public PuppetSmells adapt(DB db) {
    	return new PuppetSmells(db);
    }
    
    public void measure(Project project, ProjectDelta projectDelta, PuppetSmells db) {
    	
    	try {
				List<VcsRepositoryDelta> repoDeltas = projectDelta.getVcsDelta().getRepoDeltas();
				
				// don't continue if there isn't anything to do
				if (repoDeltas.isEmpty()) {
					return;
				}
	
				computeFolders(project, projectDelta, workingCopyFolders, scratchFolders);
				
				for (VcsRepository repo : project.getVcsRepositories()) {
					
					String repoUrl = repo.getUrl();
					
					//FIXME: It doesn't work because we can not find the full path of a resource inside a bundle so for now we use full path
					Process p = Runtime.getRuntime().exec("/usr/local/bin/python3 /Users/blue/repos/balab/CrossPuppeteer/Puppeteer.py " + workingCopyFolders.get(repoUrl) + "/");
					BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		            while(in.readLine() != null){
		                String ins = in.readLine();
		                if(ins.split(",").length == 4) {
		                	Smell smell = new Smell();
							smell.setSmellName(ins.split(",")[1]);
							smell.setReason(ins.split(",")[2]);
							smell.setFileName(ins.split(",")[3]);
							db.getSmells().add(smell);
							db.sync();
		                }
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
