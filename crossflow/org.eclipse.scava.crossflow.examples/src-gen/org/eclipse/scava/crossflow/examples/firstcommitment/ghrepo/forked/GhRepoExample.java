package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.forked;

import java.util.LinkedList;
import java.util.Collection;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Cache;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;



public class GhRepoExample extends Workflow {
	
	public static void main(String[] args) throws Exception {
		GhRepoExample app = new GhRepoExample();
		new JCommander(app, args);
		app.run();
	}
	
	
	// streams
	protected GhRepos ghRepos;
	protected ResultsPublisher resultsPublisher;
	protected ResultsPublisher2 resultsPublisher2;
	protected EclipseResultPublisher eclipseResultPublisher;
	protected EclipseTaskStatusPublisher eclipseTaskStatusPublisher;
	
	private boolean createBroker = true;
	
	// tasks
	protected GhRepoSource ghRepoSource;
	protected GhRepoCounter ghRepoCounter;
	protected GhRepoCounter2 ghRepoCounter2;
	protected EmptySink emptySink;
	protected EmptySink2 emptySink2;
	
	// excluded tasks from workers
	protected Collection<String> tasksToExclude = new LinkedList<String>();
	
	public void excludeTasks(Collection<String> tasks){
		tasksToExclude = tasks;
	}
	
	public GhRepoExample() {
		this.name = "GhRepoExample";
	}
	
	public void createBroker(boolean createBroker) {
		this.createBroker = createBroker;
	}
	
	public void run() throws Exception {
	
		if (isMaster()) {
			cache = new Cache(this);
			if (createBroker) {
				brokerService = new BrokerService();
				brokerService.setUseJmx(true);
				brokerService.addConnector(getBroker());
				brokerService.start();
			}
		}

		eclipseResultPublisher = new EclipseResultPublisher(this);
		eclipseTaskStatusPublisher = new EclipseTaskStatusPublisher(this);
		
//TODO test of task status until it is integrated to ui
//		eclipseTaskStatusPublisher.addConsumer(new EclipseTaskStatusPublisherConsumer() {
//			@Override
//			public void consumeEclipseTaskStatusPublisher(TaskStatus status) {
//				System.err.println(status.getCaller()+" : "+status.getStatus()+" : "+status.getReason());
//			}
//		});
//
		
		ghRepos = new GhRepos(this);
		resultsPublisher = new ResultsPublisher(this);
		resultsPublisher2 = new ResultsPublisher2(this);
		
		
	
				
		ghRepoSource = new GhRepoSource();
		ghRepoSource.setWorkflow(this);
		
		ghRepoSource.setGhRepos(ghRepos);
		
				
		
		if (!getMode().equals(Mode.MASTER_BARE) && !tasksToExclude.contains("GhRepoCounter")) {
	
				
		ghRepoCounter = new GhRepoCounter();
		ghRepoCounter.setWorkflow(this);
		
			ghRepos.addConsumer(ghRepoCounter, GhRepoCounter.class.getName());			
	
		ghRepoCounter.setResultsPublisher(resultsPublisher);
		}
		else if(isMaster()){
			ghRepos.addConsumer(ghRepoCounter, GhRepoCounter.class.getName());			
		}
		
				
		
		if (!getMode().equals(Mode.MASTER_BARE) && !tasksToExclude.contains("GhRepoCounter2")) {
	
				
		ghRepoCounter2 = new GhRepoCounter2();
		ghRepoCounter2.setWorkflow(this);
		
			ghRepos.addConsumer(ghRepoCounter2, GhRepoCounter2.class.getName());			
	
		ghRepoCounter2.setResultsPublisher2(resultsPublisher2);
		}
		else if(isMaster()){
			ghRepos.addConsumer(ghRepoCounter2, GhRepoCounter2.class.getName());			
		}
		
				
		
	
		if (isMaster()) {
				
		emptySink = new EmptySink();
		emptySink.setWorkflow(this);
		}
		
			resultsPublisher.addConsumer(emptySink, EmptySink.class.getName());			
		if(ghRepoCounter!=null)		
			ghRepoCounter.setEclipseResultPublisher(eclipseResultPublisher);
	
		
				
		
	
		if (isMaster()) {
				
		emptySink2 = new EmptySink2();
		emptySink2.setWorkflow(this);
		}
		
			resultsPublisher2.addConsumer(emptySink2, EmptySink2.class.getName());			
		if(ghRepoCounter2!=null)		
			ghRepoCounter2.setEclipseResultPublisher(eclipseResultPublisher);
	
		
				
		
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
	
	public void setTaskInProgess(Object caller) {
		eclipseTaskStatusPublisher.send(new TaskStatus(TaskStatuses.INPROGRESS, caller.getClass().getName(), ""));
	}

	public void setTaskWaiting(Object caller) {
		eclipseTaskStatusPublisher.send(new TaskStatus(TaskStatuses.WAITING, caller.getClass().getName(), ""));
	}

	public void setTaskBlocked(Object caller, String reason) {
		eclipseTaskStatusPublisher.send(new TaskStatus(TaskStatuses.BLOCKED, caller.getClass().getName(), reason));
	}

	public void setTaskUnblocked(Object caller) {
		eclipseTaskStatusPublisher.send(new TaskStatus(TaskStatuses.INPROGRESS, caller.getClass().getName(), ""));
	}

}