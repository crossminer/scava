package org.eclipse.scava.metricprovider.trans.configuration.puppet;

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

import org.eclipse.scava.metricprovider.trans.configuration.puppet.model.PuppetMetadata;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.model.Metadata;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.model.MetadataCollection;
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

public class PuppetTransMetricProvider implements ITransientMetricProvider<PuppetMetadata> {
	
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
    public PuppetMetadata adapt(DB db) {
    	return new PuppetMetadata(db);
    }
    
    public void measure(Project project, ProjectDelta projectDelta, PuppetMetadata db) {
    	
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
					
					MetadataCollection sc = db.getSmells();
					for (Metadata s : sc) {
						sc.remove(s);
					}
					db.sync();
					
					//FIXME: It doesn't work because we can not find the full path of a resource inside a bundle so for now we use full path
					Process p = Runtime.getRuntime().exec(prop.getProperty("python") + " " + prop.getProperty("puppeteer") + " " + workingCopyFolders.get(repoUrl) + "/ 1");
					BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String ins = in.readLine();
		            while(ins != null){
		                String line[] = ins.split(",");
		                if(line.length == 3) {
		                	Metadata metadata = new Metadata();
		                	
		                	switch (line[1]) {
			                    case "PuppetFileCount":  metadata.setPuppetFileCount(Integer.parseInt(line[2]));
			                    	break;
			                    case "TotalClasses":  metadata.setClassCount(Integer.parseInt(line[2]));
		                    		break;
			                    case "TotalDefines":  metadata.setDefineCount(Integer.parseInt(line[2]));
	                    			break;
			                    case "TotalFiles":  metadata.setFileResourceCount(Integer.parseInt(line[2]));
	                    			break;
			                    case "TotalPackages":  metadata.setPackageCount(Integer.parseInt(line[2]));
                    				break;
			                    case "TotalServices":  metadata.setServiceCount(Integer.parseInt(line[2]));
                					break;
			                    case "TotalExecs":  metadata.setExecCount(Integer.parseInt(line[2]));
            						break;
			                    case "TotalLOC":  metadata.setLoc(Integer.parseInt(line[2]));
        							break;
					        }
							db.getSmells().add(metadata);
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
