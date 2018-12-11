package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.forked;

import java.util.LinkedList;
import java.util.Collection;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Cache;
import org.eclipse.scava.crossflow.runtime.DirectoryCache;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.Moded;
import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;

public class GhRepoExample extends Workflow {
	
	public static GhRepoExample run(String[] args) throws Exception {
		Moded moded = new Moded();
		new JCommander(moded, args);
		GhRepoExample app = new GhRepoExample(moded.getMode());
		new JCommander(app, args);
		app.run();
		return app;
	}
	
	public static void main(String[] args) throws Exception {
		run(args);
	}
	
	
	// streams
	protected GhRepos ghRepos;
	protected ResultsPublisher resultsPublisher;
	protected ResultsPublisher2 resultsPublisher2;
	
	private boolean createBroker = true;
	
	// tasks
	protected GhRepoSource ghRepoSource;
	protected GhRepoCounter ghRepoCounter;
	protected GhRepoCounter2 ghRepoCounter2;
	protected EmptySink emptySink;
	protected EmptySink2 emptySink2;
	
	public GhRepoExample() {
		this(Mode.MASTER);
	}
	
	public GhRepoExample(Mode mode) {
		super();
		this.name = "GhRepoExample";
		this.mode = mode;
		if (isMaster()) {
		ghRepoSource = new GhRepoSource();
		ghRepoSource.setWorkflow(this);
		emptySink = new EmptySink();
		emptySink.setWorkflow(this);
		emptySink2 = new EmptySink2();
		emptySink2.setWorkflow(this);
		}
		
		if (isWorker()) {
			if (!tasksToExclude.contains("GhRepoCounter")) {
				ghRepoCounter = new GhRepoCounter();
				ghRepoCounter.setWorkflow(this);
			}
			if (!tasksToExclude.contains("GhRepoCounter2")) {
				ghRepoCounter2 = new GhRepoCounter2();
				ghRepoCounter2.setWorkflow(this);
			}
		}
	}
	
	public void createBroker(boolean createBroker) {
		this.createBroker = createBroker;
	}
	
	/**
	 * Run with initial delay in ms before starting execution (after creating broker
	 * if master)
	 * 
	 * @param delay
	 */
	@Override
	public void run(int delay) throws Exception {
	
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					
					if (isMaster()) {
						if (createBroker) {
							brokerService = new BrokerService();
							brokerService.setUseJmx(true);
							brokerService.addConnector(getBroker());
							brokerService.start();
						}
					}

					connect();

					Thread.sleep(delay);
					
					ghRepos = new GhRepos(GhRepoExample.this);
					activeQueues.add(ghRepos);
					resultsPublisher = new ResultsPublisher(GhRepoExample.this);
					activeQueues.add(resultsPublisher);
					resultsPublisher2 = new ResultsPublisher2(GhRepoExample.this);
					activeQueues.add(resultsPublisher2);
					
					if (isMaster()) {
							ghRepoSource.setResultsBroadcaster(resultsBroadcaster);
							ghRepoSource.setGhRepos(ghRepos);
							emptySink.setResultsBroadcaster(resultsBroadcaster);
							resultsPublisher.addConsumer(emptySink, "EmptySink");			
							emptySink2.setResultsBroadcaster(resultsBroadcaster);
							resultsPublisher2.addConsumer(emptySink2, "EmptySink2");			
					}
					
					if (isWorker()) {
						if (!tasksToExclude.contains("GhRepoCounter")) {
								ghRepoCounter.setResultsBroadcaster(resultsBroadcaster);
								ghRepos.addConsumer(ghRepoCounter, "GhRepoCounter");			
								ghRepoCounter.setResultsPublisher(resultsPublisher);
						}
						if (!tasksToExclude.contains("GhRepoCounter2")) {
								ghRepoCounter2.setResultsBroadcaster(resultsBroadcaster);
								ghRepos.addConsumer(ghRepoCounter2, "GhRepoCounter2");			
								ghRepoCounter2.setResultsPublisher2(resultsPublisher2);
						}
					}
					
					
					if (isMaster()){
						ghRepoSource.produce();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		}).start();
	
	}				
	
	public GhRepos getGhRepos() {
		return ghRepos;
	}
	public ResultsPublisher getResultsPublisher() {
		return resultsPublisher;
	}
	public ResultsPublisher2 getResultsPublisher2() {
		return resultsPublisher2;
	}
	
	public GhRepoSource getGhRepoSource() {
		return ghRepoSource;
	}
	public GhRepoCounter getGhRepoCounter() {
		return ghRepoCounter;
	}
	public GhRepoCounter2 getGhRepoCounter2() {
		return ghRepoCounter2;
	}
	public EmptySink getEmptySink() {
		return emptySink;
	}
	public EmptySink2 getEmptySink2() {
		return emptySink2;
	}

}

