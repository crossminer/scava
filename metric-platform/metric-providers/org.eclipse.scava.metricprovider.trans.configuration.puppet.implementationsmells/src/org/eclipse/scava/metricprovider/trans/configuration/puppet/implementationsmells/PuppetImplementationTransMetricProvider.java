package org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells;

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

import org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.CustomSmell;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.ImplementationCustomSmellCollection;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.ImplementationSmellCollection;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.ImplementationSmells;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model.Smell;
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

public class PuppetImplementationTransMetricProvider implements ITransientMetricProvider<ImplementationSmells> {
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	private static String lastRevision = null;
	
	private static Map<String, File> workingCopyFolders = new HashMap<>();
	private static Map<String, File> scratchFolders = new HashMap<>();
	
	private final OssmeterLogger logger;
	
	public PuppetImplementationTransMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.trans.configuration.puppet.PuppetImplementationTransMetricProvider");
	}
    
    @Override
    public String getIdentifier() {
    return PuppetImplementationTransMetricProvider.class.getCanonicalName();
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
    public ImplementationSmells adapt(DB db) {
    	return new ImplementationSmells(db);
    }
    
    public void measure(Project project, ProjectDelta projectDelta, ImplementationSmells db) {
    	
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
					
					ImplementationSmellCollection sc = db.getSmells();
					for (Smell s : sc) {
						sc.remove(s);
					}
					
					ImplementationCustomSmellCollection csc = db.getCustomSmells();
					for (CustomSmell s : csc) {
						csc.remove(s);
					}
					db.sync();
					
					//FIXME: It doesn't work because we can not find the full path of a resource inside a bundle so for now we use full path
					Process p = Runtime.getRuntime().exec(prop.getProperty("python") + " " + prop.getProperty("puppet-lint") + " " + workingCopyFolders.get(repoUrl) + "/");
					BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String ins = in.readLine();
					while(ins != null){
		                if(ins.contains("- WARNING:")) {
		                	String filename[] = ins.split("- WARNING:");
		                	String f = filename[0];
		                	if(filename[1].contains("ensure found on line")) {
		                		String rest[] = filename[1].split(" ");
		                		Smell smell = new Smell();
								smell.setLine(rest[rest.length - 1]);
								smell.setReason("ensure found on line but it's not the first attribute");
								smell.setFileName(f);
								smell.setSmellName("Misplaced attribute");
								db.getSmells().add(smell);
								db.sync();
		                	}
		                	else {
			                	String rest[] = filename[1].split("on line");
			                	Smell smell = new Smell();
								smell.setLine(rest[1]);
								smell.setReason(rest[0]);
								smell.setFileName(f);
								
								if(rest[0].contains("case statement without a default case"))
									smell.setSmellName("Missing default case");
								if(rest[0].contains("name containing a dash"))
									smell.setSmellName("Inconsistent naming convention");
								if(rest[0].contains("contains a dash"))
									smell.setSmellName("Inconsistent naming convention");
								if(rest[0].contains("not in autoload module layout"))
									smell.setSmellName("Inconsistent naming convention");
								if(rest[0].contains("duplicate parameter found in resource"))
									smell.setSmellName("Duplicate entity");
								if(rest[0].contains("optional parameter listed before required parameter"))
									smell.setSmellName("Misplaced attribute");
								if(rest[0].contains("=> is not properly aligned"))
									smell.setSmellName("Improper alignment");
								if(rest[0].contains("tab character found"))
									smell.setSmellName("Improper alignment");
								if(rest[0].contains("two-space soft tabs not used"))
									smell.setSmellName("Improper alignment");
								if(rest[0].contains("right-to-left (<-) relationship"))
									smell.setSmellName("Improper alignment");
								if(rest[0].contains("mode should be represented as a 4 digit octal value"))
									smell.setSmellName("Invalid property value");
								if(rest[0].contains("symlink target specified in ensure attr"))
									smell.setSmellName("Invalid property value");
								if(rest[0].contains("puppet:// URL without modules/ found"))
									smell.setSmellName("Invalid property value");
								if(rest[0].contains("double quoted string containing no variables"))
									smell.setSmellName("Improper quote usage");
								if(rest[0].contains("unquoted file mode"))
									smell.setSmellName("Improper quote usage");
								if(rest[0].contains("quoted boolean value found"))
									smell.setSmellName("Improper quote usage");
								if(rest[0].contains("string containing only a variable"))
									smell.setSmellName("Improper quote usage");
								if(rest[0].contains("unquoted resource title"))
									smell.setSmellName("Improper quote usage");
								if(rest[0].contains("single quoted string containing a variable"))
									smell.setSmellName("Improper quote usage");
								if(rest[0].contains("line has more than 80 characters"))
									smell.setSmellName("Long statements");
								if(rest[0].contains("variable not enclosed in {}"))
									smell.setSmellName("Unguarded variable");
								if(rest[0].contains("class not documented"))
									smell.setSmellName("Missing Documentation");
								
								db.getSmells().add(smell);
								db.sync();
		                	}
		                }
		                if(ins.contains("- ERROR:")) {
		                	String filename[] = ins.split("- ERROR:");
		                	String f = filename[0];
		                	String rest[] = filename[1].split("on line");
		                	Smell smell = new Smell();
							smell.setLine(rest[1]);
							smell.setReason(rest[0]);
							smell.setFileName(f);
							
							if(rest[0].contains("case statement without a default case"))
								smell.setSmellName("Missing default case");
							if(rest[0].contains("name containing a dash"))
								smell.setSmellName("Inconsistent naming convention");
							if(rest[0].contains("contains a dash"))
								smell.setSmellName("Inconsistent naming convention");
							if(rest[0].contains("not in autoload module layout"))
								smell.setSmellName("Inconsistent naming convention");
							if(rest[0].contains("duplicate parameter found in resource"))
								smell.setSmellName("Duplicate entity");
							if(rest[0].contains("optional parameter listed before required parameter"))
								smell.setSmellName("Misplaced attribute");
							if(rest[0].contains("=> is not properly aligned"))
								smell.setSmellName("Improper alignment");
							if(rest[0].contains("tab character found"))
								smell.setSmellName("Improper alignment");
							if(rest[0].contains("two-space soft tabs not used"))
								smell.setSmellName("Improper alignment");
							if(rest[0].contains("right-to-left (<-) relationship"))
								smell.setSmellName("Improper alignment");
							if(rest[0].contains("mode should be represented as a 4 digit octal value"))
								smell.setSmellName("Invalid property value");
							if(rest[0].contains("symlink target specified in ensure attr"))
								smell.setSmellName("Invalid property value");
							if(rest[0].contains("puppet:// URL without modules/ found"))
								smell.setSmellName("Invalid property value");
							if(rest[0].contains("double quoted string containing no variables"))
								smell.setSmellName("Improper quote usage");
							if(rest[0].contains("unquoted file mode"))
								smell.setSmellName("Improper quote usage");
							if(rest[0].contains("quoted boolean value found"))
								smell.setSmellName("Improper quote usage");
							if(rest[0].contains("string containing only a variable"))
								smell.setSmellName("Improper quote usage");
							if(rest[0].contains("unquoted resource title"))
								smell.setSmellName("Improper quote usage");
							if(rest[0].contains("single quoted string containing a variable"))
								smell.setSmellName("Improper quote usage");
							if(rest[0].contains("line has more than 80 characters"))
								smell.setSmellName("Long statements");
							if(rest[0].contains("variable not enclosed in {}"))
								smell.setSmellName("Unguarded variable");
							if(rest[0].contains("class not documented"))
								smell.setSmellName("Missing Documentation");
							
							db.getSmells().add(smell);
							db.sync();
		                }
		                ins = in.readLine();
		            }
					
					Process p2 = Runtime.getRuntime().exec(prop.getProperty("python") + " " + prop.getProperty("puppet-custom-lint") + " " + workingCopyFolders.get(repoUrl) + "/");
					BufferedReader in2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
					String ins2 = in2.readLine();
					while(ins2 != null){
		                if(ins2.split(",").length == 4) {
		                	CustomSmell customSmell = new CustomSmell();
		                	customSmell.setSmellName(ins2.split(",")[1]);
		                	customSmell.setReason(ins2.split(",")[2]);
		                	customSmell.setFileName(ins2.split(",")[3]);
							db.getCustomSmells().add(customSmell);
							db.sync();
		                }
		                ins2 = in2.readLine();
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
