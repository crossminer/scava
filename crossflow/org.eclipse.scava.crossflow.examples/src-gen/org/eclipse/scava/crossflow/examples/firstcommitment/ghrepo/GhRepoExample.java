package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

import java.util.Collection;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Cache;


public class GhRepoExample extends Workflow {
	
	public static void main(String[] args) throws Exception {
		GhRepoExample app = new GhRepoExample();
		new JCommander(app, args);
		app.run();
	}
	
	
	// streams
	protected GhRepos ghRepos;
	protected ResultsPublisher resultsPublisher;
	protected EclipseResultPublisher eclipseResultPublisher;
	
	// tasks
	protected GhRepoSource ghRepoSource;
	protected GhRepoCounter ghRepoCounter;
	protected EmptySink emptySink;
	
	// excluded tasks from workers
	protected Collection<String> tasksToExclude;
	
	public void excludeTasks(Collection<String> tasks){
		tasksToExclude = tasks;
	}
	
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

		eclipseResultPublisher = new EclipseResultPublisher(this);
		
		ghRepos = new GhRepos(this);
		resultsPublisher = new ResultsPublisher(this);
		
		if(isMaster() || !tasksToExclude.contains("GhRepoSource")) {
		ghRepoSource = new GhRepoSource();
		ghRepoSource.setWorkflow(this);
		ghRepoSource.setGhRepos(ghRepos);
		}
	
		if (isMaster()) {
		if(isMaster() || !tasksToExclude.contains("GhRepoCounter")) {
		ghRepoCounter = new GhRepoCounter();
		ghRepoCounter.setWorkflow(this);
		
			ghRepos.addConsumer(ghRepoCounter);
			
	
		ghRepoCounter.setResultsPublisher(resultsPublisher);
		}
		}
	
		if(isMaster() || !tasksToExclude.contains("EmptySink")) {
		emptySink = new EmptySink();
		emptySink.setWorkflow(this);
		if (isMaster()) 		
			resultsPublisher.addConsumer(emptySink);
			
		if(ghRepoCounter!=null)		
			ghRepoCounter.setEclipseResultPublisher(eclipseResultPublisher);
	
		}
	
		
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
	public EmptySink getEmptySink() {
		return emptySink;
	}
	
}