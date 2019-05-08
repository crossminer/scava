package org.eclipse.scava.metricprovider.trans.configuration.docker.smells;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.scava.metricprovider.trans.configuration.docker.smells.model.DockerSmellCollection;
import org.eclipse.scava.metricprovider.trans.configuration.docker.smells.model.DockerSmells;
import org.eclipse.scava.metricprovider.trans.configuration.docker.smells.model.Smell;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.vcs.VcsCommit;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.scava.platform.jadolint.Jadolint;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.platform.vcs.workingcopy.manager.WorkingCopyCheckoutException;
import org.eclipse.scava.platform.vcs.workingcopy.manager.WorkingCopyFactory;
import org.eclipse.scava.platform.vcs.workingcopy.manager.WorkingCopyManagerUnavailable;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.VcsRepository;

import com.mongodb.DB;

public class DockerTransMetricProvider implements ITransientMetricProvider<DockerSmells> {
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	private static String lastRevision = null;
	
	private static Map<String, File> workingCopyFolders = new HashMap<>();
	private static Map<String, File> scratchFolders = new HashMap<>();
	
	private final OssmeterLogger logger;
	
	public DockerTransMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.trans.configuration.docker.smells.DockerTransMetricProvider");
	}
    
    @Override
    public String getIdentifier() {
    return DockerTransMetricProvider.class.getCanonicalName();
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
    public DockerSmells adapt(DB db) {
    	return new DockerSmells(db);
    }
    
    public void measure(Project project, ProjectDelta projectDelta, DockerSmells db) {
    	
    	try {
    		
				List<VcsRepositoryDelta> repoDeltas = projectDelta.getVcsDelta().getRepoDeltas();
				
				// don't continue if there isn't anything to do
				if (repoDeltas.isEmpty()) {
					return;
				}
	
				computeFolders(project, projectDelta, workingCopyFolders, scratchFolders);
				
				for (VcsRepository repo : project.getVcsRepositories()) {
					
					String repoUrl = repo.getUrl();
					
					DockerSmellCollection sc = db.getSmells();
					for (Smell s : sc) {
						sc.remove(s);
					}
					db.sync();
					
					Jadolint j = new Jadolint();
					
					j.run("/Users/blue/Downloads/dockerfile_tests/3020");
					
					Smell smell = new Smell();
					smell.setSmellName("yeah");
					smell.setReason("yeah");
					smell.setFileName("yeah");
					db.getSmells().add(smell);
					db.sync();
					
	    		}
	    	}
	    	catch (WorkingCopyManagerUnavailable | WorkingCopyCheckoutException e) {
	    		logger.error("unexpected exception while measuring", e);
				throw new RuntimeException("Metric failed due to missing working copy", e);
			}
    }
    
    @Override
    public String getShortIdentifier() {
    	return "docker";
    }
    
    @Override
    public String getFriendlyName() {
    	return "Docker";
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
