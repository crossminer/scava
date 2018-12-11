package org.eclipse.scava.crossflow.tests.opinionated;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class OccurencesMonitorBase extends Task  implements WordsConsumer{
		
	protected OpinionatedWorkflow workflow;
	
	public void setWorkflow(OpinionatedWorkflow workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "OccurencesMonitor:"+workflow.getName();
	}
	
	
	@Override
	public final void consumeWordsWithNotifications(Word word) {
		workflow.setTaskInProgess(this);
		consumeWords(word);
		workflow.setTaskWaiting(this);
	}
	
	public abstract void consumeWords(Word word);
	
	
	
}