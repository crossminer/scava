package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

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



public class MdeTechnologyCsvExample extends Workflow {
	
	public static void main(String[] args) throws Exception {
		MdeTechnologyCsvExample app = new MdeTechnologyCsvExample();
		new JCommander(app, args);
		app.run();
	}
	
	
	// streams
	protected MdeTechnologies mdeTechnologies;
	protected MdeTechnologyRepoEntries mdeTechnologyRepoEntries;
	protected MdeTechnologyClonedRepoEntriesForAuthorCounter mdeTechnologyClonedRepoEntriesForAuthorCounter;
	protected MdeTechnologyClonedRepoEntriesForFileCounter mdeTechnologyClonedRepoEntriesForFileCounter;
	protected MdeTechnologyClonedRepoEntriesForOwnerPopularityCounter mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter;
	protected MdeTechnologyRepoAuthorCountEntries mdeTechnologyRepoAuthorCountEntries;
	protected MdeTechnologyRepoFileCountEntries mdeTechnologyRepoFileCountEntries;
	protected MdeTechnologyRepoOwnerPopularityCountEntries mdeTechnologyRepoOwnerPopularityCountEntries;
	
	private boolean createBroker = true;
	
	// tasks
	protected MdeTechnologyCsvSource mdeTechnologyCsvSource;
	protected MdeTechnologyRepoFetcher mdeTechnologyRepoFetcher;
	protected MdeTechnologyRepoCloner mdeTechnologyRepoCloner;
	protected MdeTechnologyRepoAuthorCounter mdeTechnologyRepoAuthorCounter;
	protected MdeTechnologyRepoFileCounter mdeTechnologyRepoFileCounter;
	protected MdeTechnologyRepoOwnerPopularityCounter mdeTechnologyRepoOwnerPopularityCounter;
	protected MdeTechnologyRepoAuthorCountCsvSink mdeTechnologyRepoAuthorCountCsvSink;
	protected MdeTechnologyRepoFileCountCsvSink mdeTechnologyRepoFileCountCsvSink;
	protected MdeTechnologyRepoOwnerPopularityCountCsvSink mdeTechnologyRepoOwnerPopularityCountCsvSink;
	
	public MdeTechnologyCsvExample() {
		super();
		this.name = "MdeTechnologyCsvExample";
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
						cache = new Cache(MdeTechnologyCsvExample.this);
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
					
					mdeTechnologies = new MdeTechnologies(MdeTechnologyCsvExample.this);
					activeQueues.add(mdeTechnologies);
					mdeTechnologyRepoEntries = new MdeTechnologyRepoEntries(MdeTechnologyCsvExample.this);
					activeQueues.add(mdeTechnologyRepoEntries);
					mdeTechnologyClonedRepoEntriesForAuthorCounter = new MdeTechnologyClonedRepoEntriesForAuthorCounter(MdeTechnologyCsvExample.this);
					activeQueues.add(mdeTechnologyClonedRepoEntriesForAuthorCounter);
					mdeTechnologyClonedRepoEntriesForFileCounter = new MdeTechnologyClonedRepoEntriesForFileCounter(MdeTechnologyCsvExample.this);
					activeQueues.add(mdeTechnologyClonedRepoEntriesForFileCounter);
					mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter = new MdeTechnologyClonedRepoEntriesForOwnerPopularityCounter(MdeTechnologyCsvExample.this);
					activeQueues.add(mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter);
					mdeTechnologyRepoAuthorCountEntries = new MdeTechnologyRepoAuthorCountEntries(MdeTechnologyCsvExample.this);
					activeQueues.add(mdeTechnologyRepoAuthorCountEntries);
					mdeTechnologyRepoFileCountEntries = new MdeTechnologyRepoFileCountEntries(MdeTechnologyCsvExample.this);
					activeQueues.add(mdeTechnologyRepoFileCountEntries);
					mdeTechnologyRepoOwnerPopularityCountEntries = new MdeTechnologyRepoOwnerPopularityCountEntries(MdeTechnologyCsvExample.this);
					activeQueues.add(mdeTechnologyRepoOwnerPopularityCountEntries);
					
					if (isMaster()) {
					}
					
					if (!isMaster() || (isMaster() && !getMode().equals(Mode.MASTER_BARE))) {
						if (!tasksToExclude.contains("MdeTechnologyCsvSource")) {
							mdeTechnologyCsvSource = new MdeTechnologyCsvSource();
							mdeTechnologyCsvSource.setWorkflow(MdeTechnologyCsvExample.this);
							mdeTechnologyCsvSource.setResultsBroadcaster(resultsBroadcaster);
							mdeTechnologyCsvSource.setMdeTechnologies(mdeTechnologies);
						}
						if (!tasksToExclude.contains("MdeTechnologyRepoFetcher")) {
							mdeTechnologyRepoFetcher = new MdeTechnologyRepoFetcher();
							mdeTechnologyRepoFetcher.setWorkflow(MdeTechnologyCsvExample.this);
							mdeTechnologyRepoFetcher.setResultsBroadcaster(resultsBroadcaster);
							mdeTechnologies.addConsumer(mdeTechnologyRepoFetcher, "MdeTechnologyRepoFetcher");			
							mdeTechnologyRepoFetcher.setMdeTechnologyRepoEntries(mdeTechnologyRepoEntries);
						}
						if (!tasksToExclude.contains("MdeTechnologyRepoCloner")) {
							mdeTechnologyRepoCloner = new MdeTechnologyRepoCloner();
							mdeTechnologyRepoCloner.setWorkflow(MdeTechnologyCsvExample.this);
							mdeTechnologyRepoCloner.setResultsBroadcaster(resultsBroadcaster);
							mdeTechnologyRepoEntries.addConsumer(mdeTechnologyRepoCloner, "MdeTechnologyRepoCloner");			
							mdeTechnologyRepoCloner.setMdeTechnologyClonedRepoEntriesForAuthorCounter(mdeTechnologyClonedRepoEntriesForAuthorCounter);
							mdeTechnologyRepoCloner.setMdeTechnologyClonedRepoEntriesForFileCounter(mdeTechnologyClonedRepoEntriesForFileCounter);
							mdeTechnologyRepoCloner.setMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter(mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter);
						}
						if (!tasksToExclude.contains("MdeTechnologyRepoAuthorCounter")) {
							mdeTechnologyRepoAuthorCounter = new MdeTechnologyRepoAuthorCounter();
							mdeTechnologyRepoAuthorCounter.setWorkflow(MdeTechnologyCsvExample.this);
							mdeTechnologyRepoAuthorCounter.setResultsBroadcaster(resultsBroadcaster);
							mdeTechnologyClonedRepoEntriesForAuthorCounter.addConsumer(mdeTechnologyRepoAuthorCounter, "MdeTechnologyRepoAuthorCounter");			
							mdeTechnologyRepoAuthorCounter.setMdeTechnologyRepoAuthorCountEntries(mdeTechnologyRepoAuthorCountEntries);
						}
						if (!tasksToExclude.contains("MdeTechnologyRepoFileCounter")) {
							mdeTechnologyRepoFileCounter = new MdeTechnologyRepoFileCounter();
							mdeTechnologyRepoFileCounter.setWorkflow(MdeTechnologyCsvExample.this);
							mdeTechnologyRepoFileCounter.setResultsBroadcaster(resultsBroadcaster);
							mdeTechnologyClonedRepoEntriesForFileCounter.addConsumer(mdeTechnologyRepoFileCounter, "MdeTechnologyRepoFileCounter");			
							mdeTechnologyRepoFileCounter.setMdeTechnologyRepoFileCountEntries(mdeTechnologyRepoFileCountEntries);
						}
						if (!tasksToExclude.contains("MdeTechnologyRepoOwnerPopularityCounter")) {
							mdeTechnologyRepoOwnerPopularityCounter = new MdeTechnologyRepoOwnerPopularityCounter();
							mdeTechnologyRepoOwnerPopularityCounter.setWorkflow(MdeTechnologyCsvExample.this);
							mdeTechnologyRepoOwnerPopularityCounter.setResultsBroadcaster(resultsBroadcaster);
							mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter.addConsumer(mdeTechnologyRepoOwnerPopularityCounter, "MdeTechnologyRepoOwnerPopularityCounter");			
							mdeTechnologyRepoOwnerPopularityCounter.setMdeTechnologyRepoOwnerPopularityCountEntries(mdeTechnologyRepoOwnerPopularityCountEntries);
						}
						if (!tasksToExclude.contains("MdeTechnologyRepoAuthorCountCsvSink")) {
							mdeTechnologyRepoAuthorCountCsvSink = new MdeTechnologyRepoAuthorCountCsvSink();
							mdeTechnologyRepoAuthorCountCsvSink.setWorkflow(MdeTechnologyCsvExample.this);
							mdeTechnologyRepoAuthorCountCsvSink.setResultsBroadcaster(resultsBroadcaster);
							mdeTechnologyRepoAuthorCountEntries.addConsumer(mdeTechnologyRepoAuthorCountCsvSink, "MdeTechnologyRepoAuthorCountCsvSink");			
						}
						if (!tasksToExclude.contains("MdeTechnologyRepoFileCountCsvSink")) {
							mdeTechnologyRepoFileCountCsvSink = new MdeTechnologyRepoFileCountCsvSink();
							mdeTechnologyRepoFileCountCsvSink.setWorkflow(MdeTechnologyCsvExample.this);
							mdeTechnologyRepoFileCountCsvSink.setResultsBroadcaster(resultsBroadcaster);
							mdeTechnologyRepoFileCountEntries.addConsumer(mdeTechnologyRepoFileCountCsvSink, "MdeTechnologyRepoFileCountCsvSink");			
						}
						if (!tasksToExclude.contains("MdeTechnologyRepoOwnerPopularityCountCsvSink")) {
							mdeTechnologyRepoOwnerPopularityCountCsvSink = new MdeTechnologyRepoOwnerPopularityCountCsvSink();
							mdeTechnologyRepoOwnerPopularityCountCsvSink.setWorkflow(MdeTechnologyCsvExample.this);
							mdeTechnologyRepoOwnerPopularityCountCsvSink.setResultsBroadcaster(resultsBroadcaster);
							mdeTechnologyRepoOwnerPopularityCountEntries.addConsumer(mdeTechnologyRepoOwnerPopularityCountCsvSink, "MdeTechnologyRepoOwnerPopularityCountCsvSink");			
						}
	
					}
					
					
					if (isMaster()){
						mdeTechnologyCsvSource.produce();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		}).start();
	
	}				
	
	public MdeTechnologies getMdeTechnologies() {
		return mdeTechnologies;
	}
	public MdeTechnologyRepoEntries getMdeTechnologyRepoEntries() {
		return mdeTechnologyRepoEntries;
	}
	public MdeTechnologyClonedRepoEntriesForAuthorCounter getMdeTechnologyClonedRepoEntriesForAuthorCounter() {
		return mdeTechnologyClonedRepoEntriesForAuthorCounter;
	}
	public MdeTechnologyClonedRepoEntriesForFileCounter getMdeTechnologyClonedRepoEntriesForFileCounter() {
		return mdeTechnologyClonedRepoEntriesForFileCounter;
	}
	public MdeTechnologyClonedRepoEntriesForOwnerPopularityCounter getMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter() {
		return mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter;
	}
	public MdeTechnologyRepoAuthorCountEntries getMdeTechnologyRepoAuthorCountEntries() {
		return mdeTechnologyRepoAuthorCountEntries;
	}
	public MdeTechnologyRepoFileCountEntries getMdeTechnologyRepoFileCountEntries() {
		return mdeTechnologyRepoFileCountEntries;
	}
	public MdeTechnologyRepoOwnerPopularityCountEntries getMdeTechnologyRepoOwnerPopularityCountEntries() {
		return mdeTechnologyRepoOwnerPopularityCountEntries;
	}
	
	public MdeTechnologyCsvSource getMdeTechnologyCsvSource() {
		return mdeTechnologyCsvSource;
	}
	public MdeTechnologyRepoFetcher getMdeTechnologyRepoFetcher() {
		return mdeTechnologyRepoFetcher;
	}
	public MdeTechnologyRepoCloner getMdeTechnologyRepoCloner() {
		return mdeTechnologyRepoCloner;
	}
	public MdeTechnologyRepoAuthorCounter getMdeTechnologyRepoAuthorCounter() {
		return mdeTechnologyRepoAuthorCounter;
	}
	public MdeTechnologyRepoFileCounter getMdeTechnologyRepoFileCounter() {
		return mdeTechnologyRepoFileCounter;
	}
	public MdeTechnologyRepoOwnerPopularityCounter getMdeTechnologyRepoOwnerPopularityCounter() {
		return mdeTechnologyRepoOwnerPopularityCounter;
	}
	public MdeTechnologyRepoAuthorCountCsvSink getMdeTechnologyRepoAuthorCountCsvSink() {
		return mdeTechnologyRepoAuthorCountCsvSink;
	}
	public MdeTechnologyRepoFileCountCsvSink getMdeTechnologyRepoFileCountCsvSink() {
		return mdeTechnologyRepoFileCountCsvSink;
	}
	public MdeTechnologyRepoOwnerPopularityCountCsvSink getMdeTechnologyRepoOwnerPopularityCountCsvSink() {
		return mdeTechnologyRepoOwnerPopularityCountCsvSink;
	}

}

