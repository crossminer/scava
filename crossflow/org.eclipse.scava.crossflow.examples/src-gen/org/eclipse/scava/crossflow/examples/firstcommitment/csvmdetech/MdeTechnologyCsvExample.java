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
	
	// excluded tasks from workers
	protected Collection<String> tasksToExclude = new LinkedList<String>();
	
	public void excludeTasks(Collection<String> tasks){
		tasksToExclude = tasks;
	}
	
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
					
		
	
				
					mdeTechnologyCsvSource = new MdeTechnologyCsvSource();
					mdeTechnologyCsvSource.setWorkflow(MdeTechnologyCsvExample.this);
		
					mdeTechnologyCsvSource.setMdeTechnologies(mdeTechnologies);
		
				
		
					if (!getMode().equals(Mode.MASTER_BARE) && !tasksToExclude.contains("MdeTechnologyRepoFetcher")) {
	
				
					mdeTechnologyRepoFetcher = new MdeTechnologyRepoFetcher();
					mdeTechnologyRepoFetcher.setWorkflow(MdeTechnologyCsvExample.this);
		
						mdeTechnologies.addConsumer(mdeTechnologyRepoFetcher, MdeTechnologyRepoFetcher.class.getName());			
	
					mdeTechnologyRepoFetcher.setMdeTechnologyRepoEntries(mdeTechnologyRepoEntries);
					}
					else if(isMaster()){
						mdeTechnologies.addConsumer(mdeTechnologyRepoFetcher, MdeTechnologyRepoFetcher.class.getName());			
					}
		
				
		
					if (!getMode().equals(Mode.MASTER_BARE) && !tasksToExclude.contains("MdeTechnologyRepoCloner")) {
	
				
					mdeTechnologyRepoCloner = new MdeTechnologyRepoCloner();
					mdeTechnologyRepoCloner.setWorkflow(MdeTechnologyCsvExample.this);
		
						mdeTechnologyRepoEntries.addConsumer(mdeTechnologyRepoCloner, MdeTechnologyRepoCloner.class.getName());			
	
					mdeTechnologyRepoCloner.setMdeTechnologyClonedRepoEntriesForAuthorCounter(mdeTechnologyClonedRepoEntriesForAuthorCounter);
					mdeTechnologyRepoCloner.setMdeTechnologyClonedRepoEntriesForFileCounter(mdeTechnologyClonedRepoEntriesForFileCounter);
					mdeTechnologyRepoCloner.setMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter(mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter);
					}
					else if(isMaster()){
						mdeTechnologyRepoEntries.addConsumer(mdeTechnologyRepoCloner, MdeTechnologyRepoCloner.class.getName());			
					}
		
				
		
					if (!getMode().equals(Mode.MASTER_BARE) && !tasksToExclude.contains("MdeTechnologyRepoAuthorCounter")) {
	
				
					mdeTechnologyRepoAuthorCounter = new MdeTechnologyRepoAuthorCounter();
					mdeTechnologyRepoAuthorCounter.setWorkflow(MdeTechnologyCsvExample.this);
		
						mdeTechnologyClonedRepoEntriesForAuthorCounter.addConsumer(mdeTechnologyRepoAuthorCounter, MdeTechnologyRepoAuthorCounter.class.getName());			
	
					mdeTechnologyRepoAuthorCounter.setMdeTechnologyRepoAuthorCountEntries(mdeTechnologyRepoAuthorCountEntries);
					}
					else if(isMaster()){
						mdeTechnologyClonedRepoEntriesForAuthorCounter.addConsumer(mdeTechnologyRepoAuthorCounter, MdeTechnologyRepoAuthorCounter.class.getName());			
					}
		
				
		
					if (!getMode().equals(Mode.MASTER_BARE) && !tasksToExclude.contains("MdeTechnologyRepoFileCounter")) {
	
				
					mdeTechnologyRepoFileCounter = new MdeTechnologyRepoFileCounter();
					mdeTechnologyRepoFileCounter.setWorkflow(MdeTechnologyCsvExample.this);
		
						mdeTechnologyClonedRepoEntriesForFileCounter.addConsumer(mdeTechnologyRepoFileCounter, MdeTechnologyRepoFileCounter.class.getName());			
	
					mdeTechnologyRepoFileCounter.setMdeTechnologyRepoFileCountEntries(mdeTechnologyRepoFileCountEntries);
					}
					else if(isMaster()){
						mdeTechnologyClonedRepoEntriesForFileCounter.addConsumer(mdeTechnologyRepoFileCounter, MdeTechnologyRepoFileCounter.class.getName());			
					}
		
				
		
					if (!getMode().equals(Mode.MASTER_BARE) && !tasksToExclude.contains("MdeTechnologyRepoOwnerPopularityCounter")) {
	
				
					mdeTechnologyRepoOwnerPopularityCounter = new MdeTechnologyRepoOwnerPopularityCounter();
					mdeTechnologyRepoOwnerPopularityCounter.setWorkflow(MdeTechnologyCsvExample.this);
		
						mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter.addConsumer(mdeTechnologyRepoOwnerPopularityCounter, MdeTechnologyRepoOwnerPopularityCounter.class.getName());			
	
					mdeTechnologyRepoOwnerPopularityCounter.setMdeTechnologyRepoOwnerPopularityCountEntries(mdeTechnologyRepoOwnerPopularityCountEntries);
					}
					else if(isMaster()){
						mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter.addConsumer(mdeTechnologyRepoOwnerPopularityCounter, MdeTechnologyRepoOwnerPopularityCounter.class.getName());			
					}
		
				
		
	
					if (isMaster()) {
				
					mdeTechnologyRepoAuthorCountCsvSink = new MdeTechnologyRepoAuthorCountCsvSink();
					mdeTechnologyRepoAuthorCountCsvSink.setWorkflow(MdeTechnologyCsvExample.this);
					}
		
						mdeTechnologyRepoAuthorCountEntries.addConsumer(mdeTechnologyRepoAuthorCountCsvSink, MdeTechnologyRepoAuthorCountCsvSink.class.getName());			
					if(mdeTechnologyRepoAuthorCounter!=null)		
						mdeTechnologyRepoAuthorCounter.setResultsBroadcaster(resultsBroadcaster);
	
		
				
		
	
					if (isMaster()) {
				
					mdeTechnologyRepoFileCountCsvSink = new MdeTechnologyRepoFileCountCsvSink();
					mdeTechnologyRepoFileCountCsvSink.setWorkflow(MdeTechnologyCsvExample.this);
					}
		
						mdeTechnologyRepoFileCountEntries.addConsumer(mdeTechnologyRepoFileCountCsvSink, MdeTechnologyRepoFileCountCsvSink.class.getName());			
					if(mdeTechnologyRepoFileCounter!=null)		
						mdeTechnologyRepoFileCounter.setResultsBroadcaster(resultsBroadcaster);
	
		
				
		
	
					if (isMaster()) {
				
					mdeTechnologyRepoOwnerPopularityCountCsvSink = new MdeTechnologyRepoOwnerPopularityCountCsvSink();
					mdeTechnologyRepoOwnerPopularityCountCsvSink.setWorkflow(MdeTechnologyCsvExample.this);
					}
		
						mdeTechnologyRepoOwnerPopularityCountEntries.addConsumer(mdeTechnologyRepoOwnerPopularityCountCsvSink, MdeTechnologyRepoOwnerPopularityCountCsvSink.class.getName());			
					if(mdeTechnologyRepoOwnerPopularityCounter!=null)		
						mdeTechnologyRepoOwnerPopularityCounter.setResultsBroadcaster(resultsBroadcaster);
	
		
				
		
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