package org.eclipse.scava.crossflow.examples.opinionated.ghmde;

import com.beust.jcommander.JCommander;
import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.examples.opinionated.ghmde.GhMdeTechSource;
import org.eclipse.scava.crossflow.examples.opinionated.ghmde.GhMdeTechOccurencesMonitor;
import org.eclipse.scava.crossflow.runtime.Cache;
import com.beust.jcommander.Parameter;

public class GhMdeTechExample extends Workflow {
	
	public static void main(String[] args) throws Exception {
		GhMdeTechExample app = new GhMdeTechExample();
		new JCommander(app, args);
		app.run();
	}
	
	@Parameter(names = { "-favouriteGhRepoUrl"}, description = "favouriteGhRepoUrl workflow parameter")
	protected String favouriteGhMdeTech;
	
	public void setFavouriteGhMdeTech(String favouriteGhMdeTech) {
		this.favouriteGhMdeTech = favouriteGhMdeTech;
	}
	
	public String getFavouriteGhMdeTech() {
		return favouriteGhMdeTech;
	}
	
	protected GhMdeTechs ghMdeTechs;
	
	public GhMdeTechExample() {
		this.name = "GhRepoExample";
	}
	
	public void run() throws Exception {
	
		if (isMaster()) {
			cache = new Cache(this);
			BrokerService broker = new BrokerService();
			broker.setUseJmx(true);
			broker.addConnector(getBroker());
			broker.start();
		}
		
		ghMdeTechs = new GhMdeTechs(this);
		
		GhMdeTechSource ghMdeTechSource = new GhMdeTechSource();
		ghMdeTechSource.setWorkflow(this);
		ghMdeTechSource.setWords(ghMdeTechs);
		
		GhMdeTechOccurencesMonitor ghMdeTechOccurencesMonitor = new GhMdeTechOccurencesMonitor();
		ghMdeTechOccurencesMonitor.setWorkflow(this);
		ghMdeTechs.addConsumer(ghMdeTechOccurencesMonitor);
		
		
		if (isMaster()){
			ghMdeTechSource.produce();
		}
	}
	
	public GhMdeTechs getGhMdeTechs() {
		return ghMdeTechs;
	}
	
}