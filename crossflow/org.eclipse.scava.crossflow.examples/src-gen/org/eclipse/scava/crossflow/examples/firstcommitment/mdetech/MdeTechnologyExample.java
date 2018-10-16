package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import com.beust.jcommander.JCommander;
import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Cache;
import com.beust.jcommander.Parameter;

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
	
	// tasks
	protected MdeTechnologySource mdeTechnologySource;
	protected MdeTechnologyRepoFetcher mdeTechnologyRepoFetcher;
	protected MdeTechnologyRepoCloner mdeTechnologyRepoCloner;
	protected MdeTechnologyRepoAuthorCounter mdeTechnologyRepoAuthorCounter;
	protected MdeTechnologyRepoFileCounter mdeTechnologyRepoFileCounter;
	protected MdeTechnologyRepoOwnerFollowerCounter mdeTechnologyRepoOwnerFollowerCounter;
	
	
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
		
		mdeTechnologies = new MdeTechnologies(this);
		mdeTechnologyRepoEntries = new MdeTechnologyRepoEntries(this);
		mdeTechnologyClonedRepoEntries = new MdeTechnologyClonedRepoEntries(this);
		
		mdeTechnologySource = new MdeTechnologySource();
		mdeTechnologySource.setWorkflow(this);
		mdeTechnologySource.setMdeTechnologies(mdeTechnologies);
		
		mdeTechnologyRepoFetcher = new MdeTechnologyRepoFetcher();
		mdeTechnologyRepoFetcher.setWorkflow(this);
		mdeTechnologies.addConsumer(mdeTechnologyRepoFetcher);
		mdeTechnologyRepoFetcher.setMdeTechnologyRepoEntries(mdeTechnologyRepoEntries);
		
		mdeTechnologyRepoCloner = new MdeTechnologyRepoCloner();
		mdeTechnologyRepoCloner.setWorkflow(this);
		mdeTechnologyRepoEntries.addConsumer(mdeTechnologyRepoCloner);
		mdeTechnologyRepoCloner.setMdeTechnologyClonedRepoEntries(mdeTechnologyClonedRepoEntries);
		
		mdeTechnologyRepoAuthorCounter = new MdeTechnologyRepoAuthorCounter();
		mdeTechnologyRepoAuthorCounter.setWorkflow(this);
		mdeTechnologyRepoEntries.addConsumer(mdeTechnologyRepoAuthorCounter);
		
		mdeTechnologyRepoFileCounter = new MdeTechnologyRepoFileCounter();
		mdeTechnologyRepoFileCounter.setWorkflow(this);
		mdeTechnologyRepoEntries.addConsumer(mdeTechnologyRepoFileCounter);
		
		mdeTechnologyRepoOwnerFollowerCounter = new MdeTechnologyRepoOwnerFollowerCounter();
		mdeTechnologyRepoOwnerFollowerCounter.setWorkflow(this);
		mdeTechnologyRepoEntries.addConsumer(mdeTechnologyRepoOwnerFollowerCounter);
		
		
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
	public MdeTechnologyRepoOwnerFollowerCounter getMdeTechnologyRepoOwnerFollowerCounter() {
		return mdeTechnologyRepoOwnerFollowerCounter;
	}
	
}