package org.eclipse.scava.metricprovider.trans.configuration.docker.antipatterns;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.metricprovider.trans.configuration.docker.antipatterns.model.DockerAntipattern;
import org.eclipse.scava.metricprovider.trans.configuration.docker.antipatterns.model.DockerAntipatterns;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.vcs.VcsCommit;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.scava.platform.jadolint.Jadolint;
import org.eclipse.scava.platform.jadolint.model.Dockerfile;
import org.eclipse.scava.platform.jadolint.violations.Violation;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.platform.vcs.workingcopy.manager.WorkingCopyCheckoutException;
import org.eclipse.scava.platform.vcs.workingcopy.manager.WorkingCopyFactory;
import org.eclipse.scava.platform.vcs.workingcopy.manager.WorkingCopyManagerUnavailable;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.VcsRepository;

import com.mongodb.DB;

public class DockerAntipatternTransMetricProvider implements ITransientMetricProvider<DockerAntipatterns> {
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	private static String lastRevision = null;
	
	private static Map<String, File> workingCopyFolders = new HashMap<>();
	private static Map<String, File> scratchFolders = new HashMap<>();
	
	private final OssmeterLogger logger;
	
	public DockerAntipatternTransMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.trans.configuration.docker.antipatterns.DockerTransMetricProvider");
	}
    
    @Override
    public String getIdentifier() {
    return DockerAntipatternTransMetricProvider.class.getCanonicalName();
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
    public DockerAntipatterns adapt(DB db) {
    	return new DockerAntipatterns(db);
    }
    
    public void measure(Project project, ProjectDelta projectDelta, DockerAntipatterns db) {
    	
    	try {
    		
				List<VcsRepositoryDelta> repoDeltas = projectDelta.getVcsDelta().getRepoDeltas();
				
				// don't continue if there isn't anything to do
				if (repoDeltas.isEmpty()) {
					return;
				}
	
				computeFolders(project, projectDelta, workingCopyFolders, scratchFolders);
				
				for (VcsRepository repo : project.getVcsRepositories()) {
					
					String repoUrl = repo.getUrl();
					
					String wcpath = workingCopyFolders.get(repoUrl).getPath().toString();
					
					Jadolint j = new Jadolint();
					
					Files.walk(Paths.get(workingCopyFolders.get(repoUrl).getPath()))
                    .filter(Files::isRegularFile)
                    .filter(f -> f.getFileName().toString().equals("Dockerfile"))
                    .forEach(s -> {
					
						j.run(s.toString());
						
						Dockerfile dockerfile = j.getDoc();
						
						List<Violation> violations = dockerfile.getViolations();
						
						for(Violation v : violations) {
							
							DockerAntipattern dockerAntipattern = new DockerAntipattern();
							
							if(v.getCode().equals("DL3005") || v.getCode().equals("DL3009") || v.getCode().equals("DL3014") || v.getCode().equals("DL3015") || v.getCode().equals("DL3017") || v.getCode().equals("DL3019")) {
								dockerAntipattern.setSmellName("Improper Upgrade");
							}
							
							if(v.getCode().equals("DL3007") || v.getCode().equals("DL3008") || v.getCode().equals("DL3013") || v.getCode().equals("DL3016") || v.getCode().equals("DL3018")) {
								dockerAntipattern.setSmellName("Unknown Package Version");
							}
							
							if(v.getCode().equals("DL3006")) {
								dockerAntipattern.setSmellName("Untagged Image");
							}
							
							if(v.getCode().equals("DL3002") || v.getCode().equals("DL3004")) {
								dockerAntipattern.setSmellName("Improper sudo Use");
							}
							
							if(v.getCode().equals("DL3010") || v.getCode().equals("DL3020") || v.getCode().equals("DL3021") || v.getCode().equals("DL3022") || v.getCode().equals("DL3023")) {
								dockerAntipattern.setSmellName("Improper COPY Use");
							}
							
							if(v.getCode().equals("DL3024")) {
								dockerAntipattern.setSmellName("Improper FROM Use");
							}
							
							if(v.getCode().equals("DL3025") || v.getCode().equals("DL4003")) {
								dockerAntipattern.setSmellName("Improper CMD Use");
							}
							
							if(v.getCode().equals("DL3001") || v.getCode().equals("DL4001")) {
								dockerAntipattern.setSmellName("Meaningless Commands");
							}
							
							if(v.getCode().equals("DL3011")) {
								dockerAntipattern.setSmellName("Invalid Ports");
							}
							
							if(v.getCode().equals("DL4005") || v.getCode().equals("DL4006")) {
								dockerAntipattern.setSmellName("Improper SHELL Use");
							}
							
							if(v.getCode().equals("DL4004")) {
								dockerAntipattern.setSmellName("Improper ENTRYPOINT Use");
							}
							
							if(v.getCode().equals("DL4000")) {
								dockerAntipattern.setSmellName("Deprecated Instruction");
							}
							
							dockerAntipattern.setReason(v.getMessage());
							dockerAntipattern.setCode(v.getCode());
							dockerAntipattern.setFileName(v.getFileName().replace(wcpath, ""));
							dockerAntipattern.setLine(String.valueOf(v.getLineNumber()));
							dockerAntipattern.setCommit(getLastRevision(projectDelta));
							dockerAntipattern.setDate(getLastDate(projectDelta));
							db.getSmells().add(dockerAntipattern);
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
    	return "TransientDockerAntipatterns";
    }
    
    @Override
    public String getFriendlyName() {
    	return "Transient Docker Antipatterns";
    }
    
    @Override
    public String getSummaryInformation() {
    	return "This is the metric that returns the antipatterns that are defined in the Dockerfiles of a project";
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
