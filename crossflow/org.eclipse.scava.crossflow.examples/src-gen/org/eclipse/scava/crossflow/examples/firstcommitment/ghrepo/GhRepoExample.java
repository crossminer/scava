package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

import java.util.LinkedList;
import java.util.Collection;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Cache;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;



public class GhRepoExample extends Workflow {
	
	public static void main(String[] args) throws Exception {
		GhRepoExample app = new GhRepoExample();
		new JCommander(app, args);
		app.run();
	}
	
	
	// streams
	protected GhRepos ghRepos;
	protected ResultsPublisher resultsPublisher;
	
	private boolean createBroker = true;
	
	// tasks
	protected GhRepoSource ghRepoSource;
	protected GhRepoCounter ghRepoCounter;
	protected EmptySink emptySink;
	
	public GhRepoExample() {
		super();
		this.name = "GhRepoExample";
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
					if(isCacheEnabled())
						cache = new Cache(GhRepoExample.this);
						if (createBroker) {
							brokerService = new BrokerService();
							brokerService.setUseJmx(true);
							brokerService.addConnector(getBroker());
							brokerService.start();
						}
					}

					connect();

					Thread.sleep(delay);
					
//TODO test of task status until it is integrated to ui
//		taskStatusPublisher.addConsumer(new TaskStatusPublisherConsumer() {
//			@Override
//			public void consumeTaskStatusPublisher(TaskStatus status) {
//				System.err.println(status.getCaller()+" : "+status.getStatus()+" : "+status.getReason());
//			}
//		});
//
					
					ghRepos = new GhRepos(GhRepoExample.this);
					activeQueues.add(ghRepos);
					resultsPublisher = new ResultsPublisher(GhRepoExample.this);
					activeQueues.add(resultsPublisher);
					
					if (isMaster()) {
						ghRepoSource = new GhRepoSource();
						ghRepoSource.setWorkflow(GhRepoExample.this);
						ghRepoSource.setResultsBroadcaster(resultsBroadcaster);
						ghRepoSource.setGhRepos(ghRepos);
						emptySink = new EmptySink();
						emptySink.setWorkflow(GhRepoExample.this);
						emptySink.setResultsBroadcaster(resultsBroadcaster);
						resultsPublisher.addConsumer(emptySink, "EmptySink");			
					}
					
					if (!isMaster() || (isMaster() && !getMode().equals(Mode.MASTER_BARE))) {
						if (!tasksToExclude.contains("GhRepoCounter")) {
							ghRepoCounter = new GhRepoCounter();
							ghRepoCounter.setWorkflow(GhRepoExample.this);
							ghRepoCounter.setResultsBroadcaster(resultsBroadcaster);
							ghRepos.addConsumer(ghRepoCounter, "GhRepoCounter");			
							ghRepoCounter.setResultsPublisher(resultsPublisher);
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

