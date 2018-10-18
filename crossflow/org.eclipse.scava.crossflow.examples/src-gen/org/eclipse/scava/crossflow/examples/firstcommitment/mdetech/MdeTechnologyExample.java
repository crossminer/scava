package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import java.util.Collection;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Cache;


public class MdeTechnologyExample extends Workflow {
	
	public static void main(String[] args) throws Exception {
		MdeTechnologyExample app = new MdeTechnologyExample();
		new JCommander(app, args);
		app.run();
	}
	
	
	// streams
	protected MdeTechnologies mdeTechnologies;
	protected MdeTechnologyRepoEntries mdeTechnologyRepoEntries;
	protected MdeTechnologyClonedRepoEntries mdeTechnologyClonedRepoEntries;
	protected MdeTechnologyRepoAuthorCountEntries mdeTechnologyRepoAuthorCountEntries;
	protected MdeTechnologyRepoFileCountEntries mdeTechnologyRepoFileCountEntries;
	protected MdeTechnologyRepoOwnerPopularityCountEntries mdeTechnologyRepoOwnerPopularityCountEntries;
	protected EclipseResultPublisher eclipseResultPublisher;
	
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
	protected Collection<String> tasksToExclude;
	
	public void excludeTasks(Collection<String> tasks){
		tasksToExclude = tasks;
	}
	
	public MdeTechnologyExample() {
		this.name = "MdeTechnologyExample";
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
		
		mdeTechnologies = new MdeTechnologies(this);
		mdeTechnologyRepoEntries = new MdeTechnologyRepoEntries(this);
		mdeTechnologyClonedRepoEntries = new MdeTechnologyClonedRepoEntries(this);
		mdeTechnologyRepoAuthorCountEntries = new MdeTechnologyRepoAuthorCountEntries(this);
		mdeTechnologyRepoFileCountEntries = new MdeTechnologyRepoFileCountEntries(this);
		mdeTechnologyRepoOwnerPopularityCountEntries = new MdeTechnologyRepoOwnerPopularityCountEntries(this);
		
		if(isMaster() || ( tasksToExclude!=null && !tasksToExclude.contains("MdeTechnologySource") )) {
		mdeTechnologySource = new MdeTechnologySource();
		mdeTechnologySource.setWorkflow(this);
		mdeTechnologySource.setMdeTechnologies(mdeTechnologies);
		}
	
		if(isMaster() || ( tasksToExclude!=null && !tasksToExclude.contains("MdeTechnologyRepoFetcher") )) {
		mdeTechnologyRepoFetcher = new MdeTechnologyRepoFetcher();
		mdeTechnologyRepoFetcher.setWorkflow(this);
		
			mdeTechnologies.addConsumer(mdeTechnologyRepoFetcher);
			
	
		mdeTechnologyRepoFetcher.setMdeTechnologyRepoEntries(mdeTechnologyRepoEntries);
		}
	
		if(isMaster() || ( tasksToExclude!=null && !tasksToExclude.contains("MdeTechnologyRepoCloner") )) {
		mdeTechnologyRepoCloner = new MdeTechnologyRepoCloner();
		mdeTechnologyRepoCloner.setWorkflow(this);
		
			mdeTechnologyRepoEntries.addConsumer(mdeTechnologyRepoCloner);
			
	
		mdeTechnologyRepoCloner.setMdeTechnologyClonedRepoEntries(mdeTechnologyClonedRepoEntries);
		}
	
		if(isMaster() || ( tasksToExclude!=null && !tasksToExclude.contains("MdeTechnologyRepoAuthorCounter") )) {
		mdeTechnologyRepoAuthorCounter = new MdeTechnologyRepoAuthorCounter();
		mdeTechnologyRepoAuthorCounter.setWorkflow(this);
		
			mdeTechnologyClonedRepoEntries.addConsumer(mdeTechnologyRepoAuthorCounter);
			
	
		mdeTechnologyRepoAuthorCounter.setMdeTechnologyRepoAuthorCountEntries(mdeTechnologyRepoAuthorCountEntries);
		}
	
		if(isMaster() || ( tasksToExclude!=null && !tasksToExclude.contains("MdeTechnologyRepoFileCounter") )) {
		mdeTechnologyRepoFileCounter = new MdeTechnologyRepoFileCounter();
		mdeTechnologyRepoFileCounter.setWorkflow(this);
		
			mdeTechnologyClonedRepoEntries.addConsumer(mdeTechnologyRepoFileCounter);
			
	
		mdeTechnologyRepoFileCounter.setMdeTechnologyRepoFileCountEntries(mdeTechnologyRepoFileCountEntries);
		}
	
		if(isMaster() || ( tasksToExclude!=null && !tasksToExclude.contains("MdeTechnologyRepoOwnerPopularityCounter") )) {
		mdeTechnologyRepoOwnerPopularityCounter = new MdeTechnologyRepoOwnerPopularityCounter();
		mdeTechnologyRepoOwnerPopularityCounter.setWorkflow(this);
		
			mdeTechnologyClonedRepoEntries.addConsumer(mdeTechnologyRepoOwnerPopularityCounter);
			
	
		mdeTechnologyRepoOwnerPopularityCounter.setMdeTechnologyRepoOwnerPopularityCountEntries(mdeTechnologyRepoOwnerPopularityCountEntries);
		}
	
		if(isMaster() || ( tasksToExclude!=null && !tasksToExclude.contains("MdeTechnologyRepoAuthorCountPrinter") )) {
		mdeTechnologyRepoAuthorCountPrinter = new MdeTechnologyRepoAuthorCountPrinter();
		mdeTechnologyRepoAuthorCountPrinter.setWorkflow(this);
		if (isMaster()) 		
			mdeTechnologyRepoAuthorCountEntries.addConsumer(mdeTechnologyRepoAuthorCountPrinter);
			
		if(mdeTechnologyRepoAuthorCounter!=null)		
			mdeTechnologyRepoAuthorCounter.setEclipseResultPublisher(eclipseResultPublisher);
	
		}
	
		if(isMaster() || ( tasksToExclude!=null && !tasksToExclude.contains("MdeTechnologyRepoFileCountPrinter") )) {
		mdeTechnologyRepoFileCountPrinter = new MdeTechnologyRepoFileCountPrinter();
		mdeTechnologyRepoFileCountPrinter.setWorkflow(this);
		if (isMaster()) 		
			mdeTechnologyRepoFileCountEntries.addConsumer(mdeTechnologyRepoFileCountPrinter);
			
		if(mdeTechnologyRepoFileCounter!=null)		
			mdeTechnologyRepoFileCounter.setEclipseResultPublisher(eclipseResultPublisher);
	
		}
	
		if(isMaster() || ( tasksToExclude!=null && !tasksToExclude.contains("MdeTechnologyRepoOwnerPopularityCountPrinter") )) {
		mdeTechnologyRepoOwnerPopularityCountPrinter = new MdeTechnologyRepoOwnerPopularityCountPrinter();
		mdeTechnologyRepoOwnerPopularityCountPrinter.setWorkflow(this);
		if (isMaster()) 		
			mdeTechnologyRepoOwnerPopularityCountEntries.addConsumer(mdeTechnologyRepoOwnerPopularityCountPrinter);
			
		if(mdeTechnologyRepoOwnerPopularityCounter!=null)		
			mdeTechnologyRepoOwnerPopularityCounter.setEclipseResultPublisher(eclipseResultPublisher);
	
		}
	
		
		if (isMaster()){
			mdeTechnologySource.produce();
		}
	}
	
	public MdeTechnologies getMdeTechnologies() {
		return mdeTechnologies;
	}
	public MdeTechnologyRepoEntries getMdeTechnologyRepoEntries() {
		return mdeTechnologyRepoEntries;
	}
	public MdeTechnologyClonedRepoEntries getMdeTechnologyClonedRepoEntries() {
		return mdeTechnologyClonedRepoEntries;
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