package org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.model.DockerDependencies;
import org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.model.DockerDependency;
import org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.model.DockerDependencyCollection;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.vcs.VcsCommit;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.scava.platform.jadolint.Jadolint;
import org.eclipse.scava.platform.jadolint.dependencies.Dependency;
import org.eclipse.scava.platform.jadolint.dependencies.RunDependency;
import org.eclipse.scava.platform.jadolint.model.Dockerfile;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.platform.vcs.workingcopy.manager.WorkingCopyCheckoutException;
import org.eclipse.scava.platform.vcs.workingcopy.manager.WorkingCopyFactory;
import org.eclipse.scava.platform.vcs.workingcopy.manager.WorkingCopyManagerUnavailable;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.VcsRepository;

import com.mongodb.DB;

public class DockerDependenciesTransMetricProvider implements ITransientMetricProvider<DockerDependencies> {
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	private static String lastRevision = null;
	
	private static Map<String, File> workingCopyFolders = new HashMap<>();
	private static Map<String, File> scratchFolders = new HashMap<>();
	
	private final OssmeterLogger logger;
	
	public DockerDependenciesTransMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.trans.configuration.docker.dependencies.DockerDependenciesTransMetricProvider");
	}
    
    @Override
    public String getIdentifier() {
    return DockerDependenciesTransMetricProvider.class.getCanonicalName();
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
    public DockerDependencies adapt(DB db) {
    	return new DockerDependencies(db);
    }
    
    public void measure(Project project, ProjectDelta projectDelta, DockerDependencies db) {
    	
    	try {
    		
				List<VcsRepositoryDelta> repoDeltas = projectDelta.getVcsDelta().getRepoDeltas();
				
				// don't continue if there isn't anything to do
				if (repoDeltas.isEmpty()) {
					return;
				}
	
				computeFolders(project, projectDelta, workingCopyFolders, scratchFolders);
				
				for (VcsRepository repo : project.getVcsRepositories()) {
					
					String repoUrl = repo.getUrl();
					
					DockerDependencyCollection dc = db.getDependencies();
					for (DockerDependency d : dc) {
						dc.remove(d);
					}
					db.sync();
					
					Jadolint j = new Jadolint();
					
					Files.walk(Paths.get(workingCopyFolders.get(repoUrl).getPath()))
                    .filter(Files::isRegularFile)
                    .filter(f -> f.getFileName().toString().equals("Dockerfile"))
                    .forEach(s -> {
					
						j.getDependencies(s.toString());
						
						Dockerfile dockerfile = j.getDoc();
						
						List<Dependency> dependencies = dockerfile.getDependencies();
						
						for(Dependency d : dependencies) {
							
							DockerDependency dockerDependency = new DockerDependency();
							
							dockerDependency.setDependencyName(d.getPackageName());
							if(d.getPackageVersion() != null)
								dockerDependency.setDependencyVersion(d.getPackageVersion());
							else
								dockerDependency.setDependencyVersion("N/A");
							dockerDependency.setType("docker");
							if(d instanceof RunDependency)
								dockerDependency.setSubType("package");
							else
								dockerDependency.setSubType("image");

							db.getDependencies().add(dockerDependency);
							db.sync();
						}
					
                    });
					
	    		}
	    	}
	    	catch (WorkingCopyManagerUnavailable | WorkingCopyCheckoutException e) {
	    		logger.error("unexpected exception while measuring", e);
				throw new RuntimeException("Metric failed due to missing working copy", e);
			} catch (IOException e) {
				logger.error("unexpected exception while measuring", e);
				throw new RuntimeException("Metric failed due to IO problem", e);
			}
    }
    
    @Override
    public String getShortIdentifier() {
    	return "TransientDockerDependencies";
    }
    
    @Override
    public String getFriendlyName() {
    	return "Transient Docker Dependencies Metric";
    }
    
    @Override
    public String getSummaryInformation() {
    	return "This metric returns the dependencies (packages, images) that are defined in the Dockerfiles of a project";
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
