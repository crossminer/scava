package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

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
	
	
	// streams
	protected GhRepos ghRepos;
	protected ResultsPublisher resultsPublisher;
	
	// tasks
	protected GhRepoSource ghRepoSource;
	protected GhRepoCounter ghRepoCounter;
	
	
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
		resultsPublisher = new ResultsPublisher(this);
		
		ghRepoSource = new GhRepoSource();
		ghRepoSource.setWorkflow(this);
		ghRepoSource.setGhRepos(ghRepos);
		
		ghRepoCounter = new GhRepoCounter();
		ghRepoCounter.setWorkflow(this);
		ghRepos.addConsumer(ghRepoCounter);
		ghRepoCounter.setResultsPublisher(resultsPublisher);
		
		
		if (isMaster()){
			ghRepoSource.produce();
		}
	}
	
	public GhRepos getGhRepos() {
		return ghRepos;
	}
	public ResultsPublisher getResultsPublisher() {
		return resultsPublisher;
	}
	
	public GhRepoSource getGhRepoSource() {
		return ghRepoSource;
	}
	public GhRepoCounter getGhRepoCounter() {
		return ghRepoCounter;
	}
	
}