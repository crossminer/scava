package org.eclipse.scava.metricprovider.trans.configuration.docker.smells;

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
import org.eclipse.scava.platform.jadolint.model.Dockerfile;
import org.eclipse.scava.platform.jadolint.violations.Violation;
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
					
					String wcpath = workingCopyFolders.get(repoUrl).getPath().toString();
					
					Files.walk(Paths.get(workingCopyFolders.get(repoUrl).getPath()))
                    .filter(Files::isRegularFile)
                    .filter(f -> f.getFileName().toString().equals("Dockerfile"))
                    .forEach(s -> {
					
						j.run(s.toString());
						
						Dockerfile dockerfile = j.getDoc();
						
						List<Violation> violations = dockerfile.getViolations();
						
						for(Violation v : violations) {
							
							Smell smell = new Smell();
							
							if(v.getCode().equals("DL3005") || v.getCode().equals("DL3009") || v.getCode().equals("DL3014") || v.getCode().equals("DL3015") || v.getCode().equals("DL3017") || v.getCode().equals("DL3019")) {
								smell.setSmellName("Improper Upgrade");
							}
							
							if(v.getCode().equals("DL3007") || v.getCode().equals("DL3008") || v.getCode().equals("DL3013") || v.getCode().equals("DL3016") || v.getCode().equals("DL3018")) {
								smell.setSmellName("Unknown Package Version");
							}
							
							if(v.getCode().equals("DL3006")) {
								smell.setSmellName("Untagged Image");
							}
							
							if(v.getCode().equals("DL3002") || v.getCode().equals("DL3004")) {
								smell.setSmellName("Improper sudo Use");
							}
							
							if(v.getCode().equals("DL3010") || v.getCode().equals("DL3020") || v.getCode().equals("DL3021") || v.getCode().equals("DL3022") || v.getCode().equals("DL3023")) {
								smell.setSmellName("Improper COPY Use");
							}
							
							if(v.getCode().equals("DL3024")) {
								smell.setSmellName("Improper FROM Use");
							}
							
							if(v.getCode().equals("DL3025") || v.getCode().equals("DL4003")) {
								smell.setSmellName("Improper CMD Use");
							}
							
							if(v.getCode().equals("DL3001") || v.getCode().equals("DL4001")) {
								smell.setSmellName("Meaningless Commands");
							}
							
							if(v.getCode().equals("DL3011")) {
								smell.setSmellName("Invalid Ports");
							}
							
							if(v.getCode().equals("DL4005") || v.getCode().equals("DL4006")) {
								smell.setSmellName("Improper SHELL Use");
							}
							
							if(v.getCode().equals("DL4004")) {
								smell.setSmellName("Improper ENTRYPOINT Use");
							}
							
							if(v.getCode().equals("DL4000")) {
								smell.setSmellName("Deprecated Instruction");
							}
							
							smell.setReason(v.getMessage());
							smell.setCode(v.getCode());
							smell.setFileName(v.getFileName().replace(wcpath, ""));
							smell.setLine(String.valueOf(v.getLineNumber()));
							db.getSmells().add(smell);
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
