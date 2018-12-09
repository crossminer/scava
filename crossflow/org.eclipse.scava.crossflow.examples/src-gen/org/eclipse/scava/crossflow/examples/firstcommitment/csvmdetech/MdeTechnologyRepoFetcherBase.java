package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class MdeTechnologyRepoFetcherBase extends Task  implements MdeTechnologiesConsumer{
		
	protected MdeTechnologyCsvExample workflow;
	
	public void setWorkflow(MdeTechnologyCsvExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "MdeTechnologyRepoFetcher:"+workflow.getName();
	}
	
	protected MdeTechnologyRepoEntries mdeTechnologyRepoEntries;
	
	protected void setMdeTechnologyRepoEntries(MdeTechnologyRepoEntries mdeTechnologyRepoEntries) {
		this.mdeTechnologyRepoEntries = mdeTechnologyRepoEntries;
	}
	
	private MdeTechnologyRepoEntries getMdeTechnologyRepoEntries() {
		return mdeTechnologyRepoEntries;
	}
	
	public void sendToMdeTechnologyRepoEntries(ExtensionKeywordStargazersTuple extensionKeywordStargazersTuple) {
		getMdeTechnologyRepoEntries().send(extensionKeywordStargazersTuple, this.getClass().getName());
	}
	
	
	
	@Override
	public void consumeMdeTechnologiesActual(ExtensionKeywordTuple extensionKeywordTuple) {

		workflow.setTaskInProgess(this);
		
		consumeMdeTechnologies(extensionKeywordTuple);
		
		workflow.setTaskWaiting(this);
		
	}
	
	
	
	
}