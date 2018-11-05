package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

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



public class MdeTechnologyExample extends Workflow {
	
	public static void main(String[] args) throws Exception {
		MdeTechnologyExample app = new MdeTechnologyExample();
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
	protected MdeTechnologySource mdeTechnologySource;
	protected MdeTechnologyRepoFetcher mdeTechnologyRepoFetcher;
	protected MdeTechnologyRepoCloner mdeTechnologyRepoCloner;
	protected MdeTechnologyRepoAuthorCounter mdeTechnologyRepoAuthorCounter;
	protected MdeTechnologyRepoFileCounter mdeTechnologyRepoFileCounter;
	protected MdeTechnologyRepoOwnerPopularityCounter mdeTechnologyRepoOwnerPopularityCounter;
	protected MdeTechnologyRepoAuthorCountPrinter mdeTechnologyRepoAuthorCountPrinter;
	protected MdeTechnologyRepoFileCountPrinter mdeTechnologyRepoFileCountPrinter;
	protected MdeTechnologyRepoOwnerPopularityCountPrinter mdeTechnologyRepoOwnerPopularityCountPrinter;
	
	// excluded tasks from workers
	protected Collection<String> tasksToExclude = new LinkedList<String>();
	
	public void excludeTasks(Collection<String> tasks){
		tasksToExclude = tasks;
	}
	
	public MdeTechnologyExample() {
		super();
		this.name = "MdeTechnologyExample";
	}
	
	public void createBroker(boolean createBroker) {
		this.createBroker = createBroker;
	}
	
	/**
	 * Run with initial delay i ms before starting execution (after creating broker
	 * if master)
	 * 
	 * @param i
	 */
	@Override
	public void run(int i) throws Exception {
	
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
	
					if (isMaster()) {
					if(isCacheEnabled())
						cache = new Cache(MdeTechnologyExample.this);
						if (createBroker) {
							brokerService = new BrokerService();
							brokerService.setUseJmx(true);
							brokerService.addConnector(getBroker());
							brokerService.start();
						}
					}

					connect();

					Thread.sleep(i);
					
//TODO test of task status until it is integrated to ui
//		taskStatusPublisher.addConsumer(new TaskStatusPublisherConsumer() {
//			@Override
//			public void consumeTaskStatusPublisher(TaskStatus status) {
//				System.err.println(status.getCaller()+" : "+status.getStatus()+" : "+status.getReason());
//			}
//		});
//
					
					mdeTechnologies = new MdeTechnologies(MdeTechnologyExample.this);
					activeQueues.add(mdeTechnologies);
					mdeTechnologyRepoEntries = new MdeTechnologyRepoEntries(MdeTechnologyExample.this);
					activeQueues.add(mdeTechnologyRepoEntries);
					mdeTechnologyClonedRepoEntriesForAuthorCounter = new MdeTechnologyClonedRepoEntriesForAuthorCounter(MdeTechnologyExample.this);
					activeQueues.add(mdeTechnologyClonedRepoEntriesForAuthorCounter);
					mdeTechnologyClonedRepoEntriesForFileCounter = new MdeTechnologyClonedRepoEntriesForFileCounter(MdeTechnologyExample.this);
					activeQueues.add(mdeTechnologyClonedRepoEntriesForFileCounter);
					mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter = new MdeTechnologyClonedRepoEntriesForOwnerPopularityCounter(MdeTechnologyExample.this);
					activeQueues.add(mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter);
					mdeTechnologyRepoAuthorCountEntries = new MdeTechnologyRepoAuthorCountEntries(MdeTechnologyExample.this);
					activeQueues.add(mdeTechnologyRepoAuthorCountEntries);
					mdeTechnologyRepoFileCountEntries = new MdeTechnologyRepoFileCountEntries(MdeTechnologyExample.this);
					activeQueues.add(mdeTechnologyRepoFileCountEntries);
					mdeTechnologyRepoOwnerPopularityCountEntries = new MdeTechnologyRepoOwnerPopularityCountEntries(MdeTechnologyExample.this);
					activeQueues.add(mdeTechnologyRepoOwnerPopularityCountEntries);
					
		
	
				
					mdeTechnologySource = new MdeTechnologySource();
					mdeTechnologySource.setWorkflow(MdeTechnologyExample.this);
		
					mdeTechnologySource.setMdeTechnologies(mdeTechnologies);
		
				
		
					if (!getMode().equals(Mode.MASTER_BARE) && !tasksToExclude.contains("MdeTechnologyRepoFetcher")) {
	
				
					mdeTechnologyRepoFetcher = new MdeTechnologyRepoFetcher();
					mdeTechnologyRepoFetcher.setWorkflow(MdeTechnologyExample.this);
		
						mdeTechnologies.addConsumer(mdeTechnologyRepoFetcher, MdeTechnologyRepoFetcher.class.getName());			
	
					mdeTechnologyRepoFetcher.setMdeTechnologyRepoEntries(mdeTechnologyRepoEntries);
					}
					else if(isMaster()){
						mdeTechnologies.addConsumer(mdeTechnologyRepoFetcher, MdeTechnologyRepoFetcher.class.getName());			
					}
		
				
		
					if (!getMode().equals(Mode.MASTER_BARE) && !tasksToExclude.contains("MdeTechnologyRepoCloner")) {
	
				
					mdeTechnologyRepoCloner = new MdeTechnologyRepoCloner();
					mdeTechnologyRepoCloner.setWorkflow(MdeTechnologyExample.this);
		
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
					mdeTechnologyRepoAuthorCounter.setWorkflow(MdeTechnologyExample.this);
		
						mdeTechnologyClonedRepoEntriesForAuthorCounter.addConsumer(mdeTechnologyRepoAuthorCounter, MdeTechnologyRepoAuthorCounter.class.getName());			
	
					mdeTechnologyRepoAuthorCounter.setMdeTechnologyRepoAuthorCountEntries(mdeTechnologyRepoAuthorCountEntries);
					}
					else if(isMaster()){
						mdeTechnologyClonedRepoEntriesForAuthorCounter.addConsumer(mdeTechnologyRepoAuthorCounter, MdeTechnologyRepoAuthorCounter.class.getName());			
					}
		
				
		
					if (!getMode().equals(Mode.MASTER_BARE) && !tasksToExclude.contains("MdeTechnologyRepoFileCounter")) {
	
				
					mdeTechnologyRepoFileCounter = new MdeTechnologyRepoFileCounter();
					mdeTechnologyRepoFileCounter.setWorkflow(MdeTechnologyExample.this);
		
						mdeTechnologyClonedRepoEntriesForFileCounter.addConsumer(mdeTechnologyRepoFileCounter, MdeTechnologyRepoFileCounter.class.getName());			
	
					mdeTechnologyRepoFileCounter.setMdeTechnologyRepoFileCountEntries(mdeTechnologyRepoFileCountEntries);
					}
					else if(isMaster()){
						mdeTechnologyClonedRepoEntriesForFileCounter.addConsumer(mdeTechnologyRepoFileCounter, MdeTechnologyRepoFileCounter.class.getName());			
					}
		
				
		
					if (!getMode().equals(Mode.MASTER_BARE) && !tasksToExclude.contains("MdeTechnologyRepoOwnerPopularityCounter")) {
	
				
					mdeTechnologyRepoOwnerPopularityCounter = new MdeTechnologyRepoOwnerPopularityCounter();
					mdeTechnologyRepoOwnerPopularityCounter.setWorkflow(MdeTechnologyExample.this);
		
						mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter.addConsumer(mdeTechnologyRepoOwnerPopularityCounter, MdeTechnologyRepoOwnerPopularityCounter.class.getName());			
	
					mdeTechnologyRepoOwnerPopularityCounter.setMdeTechnologyRepoOwnerPopularityCountEntries(mdeTechnologyRepoOwnerPopularityCountEntries);
					}
					else if(isMaster()){
						mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter.addConsumer(mdeTechnologyRepoOwnerPopularityCounter, MdeTechnologyRepoOwnerPopularityCounter.class.getName());			
					}
		
				
		
	
					if (isMaster()) {
				
					mdeTechnologyRepoAuthorCountPrinter = new MdeTechnologyRepoAuthorCountPrinter();
					mdeTechnologyRepoAuthorCountPrinter.setWorkflow(MdeTechnologyExample.this);
					}
		
						mdeTechnologyRepoAuthorCountEntries.addConsumer(mdeTechnologyRepoAuthorCountPrinter, MdeTechnologyRepoAuthorCountPrinter.class.getName());			
					if(mdeTechnologyRepoAuthorCounter!=null)		
						mdeTechnologyRepoAuthorCounter.setResultsBroadcaster(resultsBroadcaster);
	
		
				
		
	
					if (isMaster()) {
				
					mdeTechnologyRepoFileCountPrinter = new MdeTechnologyRepoFileCountPrinter();
					mdeTechnologyRepoFileCountPrinter.setWorkflow(MdeTechnologyExample.this);
					}
		
						mdeTechnologyRepoFileCountEntries.addConsumer(mdeTechnologyRepoFileCountPrinter, MdeTechnologyRepoFileCountPrinter.class.getName());			
					if(mdeTechnologyRepoFileCounter!=null)		
						mdeTechnologyRepoFileCounter.setResultsBroadcaster(resultsBroadcaster);
	
		
				
		
	
					if (isMaster()) {
				
					mdeTechnologyRepoOwnerPopularityCountPrinter = new MdeTechnologyRepoOwnerPopularityCountPrinter();
					mdeTechnologyRepoOwnerPopularityCountPrinter.setWorkflow(MdeTechnologyExample.this);
					}
		
						mdeTechnologyRepoOwnerPopularityCountEntries.addConsumer(mdeTechnologyRepoOwnerPopularityCountPrinter, MdeTechnologyRepoOwnerPopularityCountPrinter.class.getName());			
					if(mdeTechnologyRepoOwnerPopularityCounter!=null)		
						mdeTechnologyRepoOwnerPopularityCounter.setResultsBroadcaster(resultsBroadcaster);
	
		
				
		
					if (isMaster()){
						mdeTechnologySource.produce();
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
	
	public MdeTechnologySource getMdeTechnologySource() {
		return mdeTechnologySource;
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
	public MdeTechnologyRepoAuthorCountPrinter getMdeTechnologyRepoAuthorCountPrinter() {
		return mdeTechnologyRepoAuthorCountPrinter;
	}
	public MdeTechnologyRepoFileCountPrinter getMdeTechnologyRepoFileCountPrinter() {
		return mdeTechnologyRepoFileCountPrinter;
	}
	public MdeTechnologyRepoOwnerPopularityCountPrinter getMdeTechnologyRepoOwnerPopularityCountPrinter() {
		return mdeTechnologyRepoOwnerPopularityCountPrinter;
	}
	
	public void setTaskInProgess(Task caller) {
		taskStatusPublisher.send(new TaskStatus(TaskStatuses.INPROGRESS, caller.getId(), ""));
	}

	public void setTaskWaiting(Task caller) {
		taskStatusPublisher.send(new TaskStatus(TaskStatuses.WAITING, caller.getId(), ""));
	}

	public void setTaskBlocked(Task caller, String reason) {
		taskStatusPublisher.send(new TaskStatus(TaskStatuses.BLOCKED, caller.getId(), reason));
	}

	public void setTaskUnblocked(Task caller) {
		taskStatusPublisher.send(new TaskStatus(TaskStatuses.INPROGRESS, caller.getId(), ""));
	}

}