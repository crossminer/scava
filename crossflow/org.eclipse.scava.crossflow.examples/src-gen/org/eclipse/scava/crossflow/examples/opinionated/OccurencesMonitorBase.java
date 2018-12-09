package org.eclipse.scava.crossflow.examples.opinionated;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class OccurencesMonitorBase extends Task  implements WordsConsumer{
		
	protected OpinionatedExample workflow;
	
	public void setWorkflow(OpinionatedExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "OccurencesMonitor:"+workflow.getName();
	}
	
	
	
	@Override
	public void consumeWordsActual(Word word) {

		workflow.setTaskInProgess(this);
		
		consumeWords(word);
		
		workflow.setTaskWaiting(this);
		
	}
	
	
	
	
}