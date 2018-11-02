package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

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
	protected EclipseResultPublisher eclipseResultPublisher;
	protected EclipseTaskStatusPublisher eclipseTaskStatusPublisher;

	private boolean createBroker = true;

	// tasks
	protected GhRepoSource ghRepoSource;
	protected GhRepoCounter ghRepoCounter;
	protected EmptySink emptySink;

	// excluded tasks from workers
	protected Collection<String> tasksToExclude = new LinkedList<String>();

	public void excludeTasks(Collection<String> tasks) {
		tasksToExclude = tasks;
	}

	public GhRepoExample() {
		this.name = "GhRepoExample";
	}

	public void createBroker(boolean createBroker) {
		this.createBroker = createBroker;
	}

	private boolean enableCache() {
		return false;
	}

	public void run() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {

					if (isMaster()) {
						if (enableCache())
							cache = new Cache(GhRepoExample.this);
						if (createBroker) {
							brokerService = new BrokerService();
							brokerService.setUseJmx(true);
							brokerService.addConnector(getBroker());
							brokerService.start();
						}
					}

					eclipseResultPublisher = new EclipseResultPublisher(GhRepoExample.this);
					eclipseTaskStatusPublisher = new EclipseTaskStatusPublisher(GhRepoExample.this);

					// TODO test of task status until it is integrated to ui
					// eclipseTaskStatusPublisher.addConsumer(new
					// EclipseTaskStatusPublisherConsumer() {
					// @Override
					// public void consumeEclipseTaskStatusPublisher(TaskStatus
					// status) {
					// System.err.println(status.getCaller()+" :
					// "+status.getStatus()+" :
					// "+status.getReason());
					// }
					// });
					//

					ghRepos = new GhRepos(GhRepoExample.this);
					resultsPublisher = new ResultsPublisher(GhRepoExample.this);

					if (isMaster()) {

						ghRepoSource = new GhRepoSource();
						ghRepoSource.setWorkflow(GhRepoExample.this);

						ghRepoSource.setGhRepos(ghRepos);
					}

					if (!getMode().equals(Mode.MASTER_BARE) && !tasksToExclude.contains("GhRepoCounter")) {

						ghRepoCounter = new GhRepoCounter();
						ghRepoCounter.setWorkflow(GhRepoExample.this);

						ghRepos.addConsumer(ghRepoCounter, GhRepoCounter.class.getName());

						ghRepoCounter.setResultsPublisher(resultsPublisher);
					} else if (isMaster()) {
						ghRepos.addConsumer(ghRepoCounter, GhRepoCounter.class.getName());
					}

					if (isMaster()) {

						emptySink = new EmptySink();
						emptySink.setWorkflow(GhRepoExample.this);
					}

					resultsPublisher.addConsumer(emptySink, EmptySink.class.getName());
					if (ghRepoCounter != null)
						ghRepoCounter.setEclipseResultPublisher(eclipseResultPublisher);

					if (isMaster()) {
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

	private boolean terminated = false;

	public void terminate() {
		terminated = true;
	}

	public boolean isTerminated() {
		return terminated;
	}

}