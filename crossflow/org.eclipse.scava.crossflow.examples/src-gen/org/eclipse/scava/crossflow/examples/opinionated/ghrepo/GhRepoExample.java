package org.eclipse.scava.crossflow.examples.opinionated.ghrepo;

import com.beust.jcommander.JCommander;
import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Cache;
import com.beust.jcommander.Parameter;

public class GhRepoExample extends Workflow {
	
	public static void main(String[] args) throws Exception {
		GhRepoExample app = new GhRepoExample();
		new JCommander(app, args);
		app.run();
	}
	
	@Parameter(names = { "-favouriteGhRepoUrl"}, description = "favouriteGhRepoUrl workflow parameter")
	protected String favouriteGhRepoUrl;
	
	public void setFavouriteGhRepoUrl(String favouriteGhRepoUrl) {
		this.favouriteGhRepoUrl = favouriteGhRepoUrl;
	}
	
	public String getFavouriteGhRepoUrl() {
		return favouriteGhRepoUrl;
	}
	
	protected GhRepos ghRepos;
	
	public GhRepoExample() {
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
		
		ghRepos = new GhRepos(this);
		
		GhRepoSource ghRepoSource = new GhRepoSource();
		ghRepoSource.setWorkflow(this);
		ghRepoSource.setGhRepos(ghRepos);
		
		GhRepoOccurencesMonitor ghRepoOccurencesMonitor = new GhRepoOccurencesMonitor();
		ghRepoOccurencesMonitor.setWorkflow(this);
		ghRepos.addConsumer(ghRepoOccurencesMonitor);
		
		
		if (isMaster()){
			ghRepoSource.produce();
		}
	}
	
	public GhRepos getGhRepos() {
		return ghRepos;
	}
	
}