package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class MdeTechnologyRepoOwnerPopularityCountPrinterBase extends Task  implements MdeTechnologyRepoOwnerPopularityCountEntriesConsumer{
		
	protected MdeTechnologyExample workflow;
	
	public void setWorkflow(MdeTechnologyExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "MdeTechnologyRepoOwnerPopularityCountPrinter:"+workflow.getName();
	}
	
	
	
	@Override
	public void consumeMdeTechnologyRepoOwnerPopularityCountEntriesActual(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) {

		workflow.setTaskInProgess(this);
		
		consumeMdeTechnologyRepoOwnerPopularityCountEntries(extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);
		
		workflow.setTaskWaiting(this);
		
	}
	
	
	
	
}