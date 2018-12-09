package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class MdeTechnologyRepoFileCountPrinterBase extends Task  implements MdeTechnologyRepoFileCountEntriesConsumer{
		
	protected MdeTechnologyExample workflow;
	
	public void setWorkflow(MdeTechnologyExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "MdeTechnologyRepoFileCountPrinter:"+workflow.getName();
	}
	
	
	
	@Override
	public void consumeMdeTechnologyRepoFileCountEntriesActual(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) {

		workflow.setTaskInProgess(this);
		
		consumeMdeTechnologyRepoFileCountEntries(extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);
		
		workflow.setTaskWaiting(this);
		
	}
	
	
	
	
}