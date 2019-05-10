package org.eclipse.scava.metricprovider.historic.configuration.docker.smells;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.configuration.docker.smells.model.DockerSmellsHistoricMetric;
import org.eclipse.scava.metricprovider.trans.configuration.docker.smells.DockerTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.configuration.docker.smells.model.DockerSmellCollection;
import org.eclipse.scava.metricprovider.trans.configuration.docker.smells.model.DockerSmells;
import org.eclipse.scava.metricprovider.trans.configuration.docker.smells.model.Smell;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class DockerHistoricMetricProvider extends AbstractHistoricalMetricProvider {
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	private final OssmeterLogger logger;
	
	public final static String IDENTIFIER = "org.eclipse.scava.metricprovider.historic.configuration.docker.smells";
	
	public DockerHistoricMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.historic.configuration.docker.DockerHistoricMetricProvider");
	}
    
    @Override
    public String getIdentifier() {
    	return IDENTIFIER;
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
    	return Arrays.asList(DockerTransMetricProvider.class.getCanonicalName());
    }
    
    public void setMetricProviderContext(MetricProviderContext context) {
    	this.context = context;
    }
    
    public Pongo measure(Project project) {
    	
    	if (uses.size()!=1) {
			System.err.println("Metric: " + getIdentifier() + " failed to retrieve " + 
								"the transient metric it needs!");
			System.exit(-1);
		}
    	
    	DockerSmells dockerSmells = 
				((DockerTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
    	
    	DockerSmellsHistoricMetric dockerHistoricSmells = new DockerSmellsHistoricMetric();
    	
    	DockerSmellCollection col = dockerSmells.getSmells();
    	
    	int improperUpgrade = 0;
    	int unknownPackageVersion = 0;
    	int untaggedImage = 0;
    	int improperSudo = 0;
    	int improperCopy = 0;
    	int improperFrom = 0;
    	int improperCmd = 0;
    	int meaninglessCommands = 0;
    	int invalidPorts = 0;
    	int improperShell = 0;
    	int improperEntrypoint = 0;
    	int deprecatedInstruction = 0;
    	
    	for (Smell smell: col) {
    		switch (smell.getCode()) {
	            case "DL3005":  improperUpgrade++;
	            	break;
	            case "DL3009":  improperUpgrade++;
            		break;
	            case "DL3014":  improperUpgrade++;
	            	break;
	            case "DL3015":  improperUpgrade++;
	        		break;
	            case "DL3017":  improperUpgrade++;
	        		break;
		        case "DL3019":  improperUpgrade++;
		    		break;
	            case "DL3007":  unknownPackageVersion++;
	        		break;
	            case "DL3008":  unknownPackageVersion++;
        			break;
	            case "DL3013":  unknownPackageVersion++;
        			break;
	            case "DL3016":  unknownPackageVersion++;
        			break;
	            case "DL3018":  unknownPackageVersion++;
        			break;
	            case "DL3006":  untaggedImage++;
	    			break;
	            case "DL3002":  improperSudo++;
	    			break;
	            case "DL3004":  improperSudo++;
    				break;
	            case "DL3010":  improperCopy++;
					break;
	            case "DL3020":  improperCopy++;
					break;
	            case "DL3021":  improperCopy++;
					break;
	            case "DL3022":  improperCopy++;
					break;
	            case "DL3023":  improperCopy++;
					break;
	            case "DL3024":  improperFrom++;
					break;
	            case "DL3025":  improperCmd++;
					break;
	            case "DL4003":  improperCmd++;
					break;
	            case "DL3001":  meaninglessCommands++;
					break;
	            case "DL4001":  meaninglessCommands++;
					break;
	            case "DL3011":  invalidPorts++;
					break;
	            case "DL4005":  improperShell++;
					break;
	            case "DL4006":  improperShell++;
					break;
	            case "DL4004":  improperEntrypoint++;
					break;
	            case "DL4000":  deprecatedInstruction++;
					break;
					
    		}
    		
    	}
    	
    	dockerHistoricSmells.setCumulativeNumberOfDockerSmells((int)col.size());
    	dockerHistoricSmells.setNumberOfImproperUpgradeSmells(improperUpgrade);
    	dockerHistoricSmells.setNumberOfUnknownPackageVersionSmells(unknownPackageVersion);
    	dockerHistoricSmells.setNumberOfUntaggedImageSmells(untaggedImage);
    	dockerHistoricSmells.setNumberOfImproperSudoSmells(improperSudo);
    	dockerHistoricSmells.setNumberOfImproperCopySmells(improperCopy);
    	dockerHistoricSmells.setNumberOfImproperFromSmells(improperFrom);
    	dockerHistoricSmells.setNumberOfImproperCmdSmells(improperCmd);
    	dockerHistoricSmells.setNumberOfMeaninglessSmells(meaninglessCommands);
    	dockerHistoricSmells.setNumberOfInvalidPortsSmells(invalidPorts);
    	dockerHistoricSmells.setNumberOfImproperShellSmells(improperShell);
    	dockerHistoricSmells.setNumberOfImproperEntrypointSmells(improperEntrypoint);
    	dockerHistoricSmells.setNumberOfDeprecatedInstructionSmells(deprecatedInstruction);
    	
    	return dockerHistoricSmells;
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
}