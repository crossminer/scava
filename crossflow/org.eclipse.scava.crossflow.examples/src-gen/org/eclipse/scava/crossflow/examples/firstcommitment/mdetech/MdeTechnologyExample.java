package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

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
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;



public class MdeTechnologyExample extends Workflow {
	
	public static MdeTechnologyExample run(String[] args) throws Exception {
		Moded moded = new Moded();
		new JCommander(moded, args);
		MdeTechnologyExample app = new MdeTechnologyExample(moded.getMode());
		new JCommander(app, args);
		app.run();
		return app;
	}
	
	public static void main(String[] args) throws Exception {
		run(args);
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
	
	public MdeTechnologyExample() {
		this(Mode.MASTER);
	}
	
	public MdeTechnologyExample(Mode mode) {
		super();
		this.name = "MdeTechnologyExample";
		this.mode = mode;
		if (isMaster()) {
		mdeTechnologySource = new MdeTechnologySource();
		mdeTechnologySource.setWorkflow(this);
		mdeTechnologyRepoAuthorCountPrinter = new MdeTechnologyRepoAuthorCountPrinter();
		mdeTechnologyRepoAuthorCountPrinter.setWorkflow(this);
		mdeTechnologyRepoFileCountPrinter = new MdeTechnologyRepoFileCountPrinter();
		mdeTechnologyRepoFileCountPrinter.setWorkflow(this);
		mdeTechnologyRepoOwnerPopularityCountPrinter = new MdeTechnologyRepoOwnerPopularityCountPrinter();
		mdeTechnologyRepoOwnerPopularityCountPrinter.setWorkflow(this);
		}
		
		if (isWorker()) {
			if (!tasksToExclude.contains("MdeTechnologyRepoFetcher")) {
				mdeTechnologyRepoFetcher = new MdeTechnologyRepoFetcher();
				mdeTechnologyRepoFetcher.setWorkflow(this);
			}
			if (!tasksToExclude.contains("MdeTechnologyRepoCloner")) {
				mdeTechnologyRepoCloner = new MdeTechnologyRepoCloner();
				mdeTechnologyRepoCloner.setWorkflow(this);
			}
			if (!tasksToExclude.contains("MdeTechnologyRepoAuthorCounter")) {
				mdeTechnologyRepoAuthorCounter = new MdeTechnologyRepoAuthorCounter();
				mdeTechnologyRepoAuthorCounter.setWorkflow(this);
			}
			if (!tasksToExclude.contains("MdeTechnologyRepoFileCounter")) {
				mdeTechnologyRepoFileCounter = new MdeTechnologyRepoFileCounter();
				mdeTechnologyRepoFileCounter.setWorkflow(this);
			}
			if (!tasksToExclude.contains("MdeTechnologyRepoOwnerPopularityCounter")) {
				mdeTechnologyRepoOwnerPopularityCounter = new MdeTechnologyRepoOwnerPopularityCounter();
				mdeTechnologyRepoOwnerPopularityCounter.setWorkflow(this);
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
					
					if (isMaster()) {
							mdeTechnologySource.setResultsBroadcaster(resultsBroadcaster);
							mdeTechnologySource.setMdeTechnologies(mdeTechnologies);
							mdeTechnologyRepoAuthorCountPrinter.setResultsBroadcaster(resultsBroadcaster);
							mdeTechnologyRepoAuthorCountEntries.addConsumer(mdeTechnologyRepoAuthorCountPrinter, "MdeTechnologyRepoAuthorCountPrinter");			
							mdeTechnologyRepoFileCountPrinter.setResultsBroadcaster(resultsBroadcaster);
							mdeTechnologyRepoFileCountEntries.addConsumer(mdeTechnologyRepoFileCountPrinter, "MdeTechnologyRepoFileCountPrinter");			
							mdeTechnologyRepoOwnerPopularityCountPrinter.setResultsBroadcaster(resultsBroadcaster);
							mdeTechnologyRepoOwnerPopularityCountEntries.addConsumer(mdeTechnologyRepoOwnerPopularityCountPrinter, "MdeTechnologyRepoOwnerPopularityCountPrinter");			
					}
					
					if (isWorker()) {
						if (!tasksToExclude.contains("MdeTechnologyRepoFetcher")) {
								mdeTechnologyRepoFetcher.setResultsBroadcaster(resultsBroadcaster);
								mdeTechnologies.addConsumer(mdeTechnologyRepoFetcher, "MdeTechnologyRepoFetcher");			
								mdeTechnologyRepoFetcher.setMdeTechnologyRepoEntries(mdeTechnologyRepoEntries);
						}
						if (!tasksToExclude.contains("MdeTechnologyRepoCloner")) {
								mdeTechnologyRepoCloner.setResultsBroadcaster(resultsBroadcaster);
								mdeTechnologyRepoEntries.addConsumer(mdeTechnologyRepoCloner, "MdeTechnologyRepoCloner");			
								mdeTechnologyRepoCloner.setMdeTechnologyClonedRepoEntriesForAuthorCounter(mdeTechnologyClonedRepoEntriesForAuthorCounter);
								mdeTechnologyRepoCloner.setMdeTechnologyClonedRepoEntriesForFileCounter(mdeTechnologyClonedRepoEntriesForFileCounter);
								mdeTechnologyRepoCloner.setMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter(mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter);
						}
						if (!tasksToExclude.contains("MdeTechnologyRepoAuthorCounter")) {
								mdeTechnologyRepoAuthorCounter.setResultsBroadcaster(resultsBroadcaster);
								mdeTechnologyClonedRepoEntriesForAuthorCounter.addConsumer(mdeTechnologyRepoAuthorCounter, "MdeTechnologyRepoAuthorCounter");			
								mdeTechnologyRepoAuthorCounter.setMdeTechnologyRepoAuthorCountEntries(mdeTechnologyRepoAuthorCountEntries);
						}
						if (!tasksToExclude.contains("MdeTechnologyRepoFileCounter")) {
								mdeTechnologyRepoFileCounter.setResultsBroadcaster(resultsBroadcaster);
								mdeTechnologyClonedRepoEntriesForFileCounter.addConsumer(mdeTechnologyRepoFileCounter, "MdeTechnologyRepoFileCounter");			
								mdeTechnologyRepoFileCounter.setMdeTechnologyRepoFileCountEntries(mdeTechnologyRepoFileCountEntries);
						}
						if (!tasksToExclude.contains("MdeTechnologyRepoOwnerPopularityCounter")) {
								mdeTechnologyRepoOwnerPopularityCounter.setResultsBroadcaster(resultsBroadcaster);
								mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter.addConsumer(mdeTechnologyRepoOwnerPopularityCounter, "MdeTechnologyRepoOwnerPopularityCounter");			
								mdeTechnologyRepoOwnerPopularityCounter.setMdeTechnologyRepoOwnerPopularityCountEntries(mdeTechnologyRepoOwnerPopularityCountEntries);
						}
					}
					
					
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

}

