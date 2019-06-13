package org.eclipse.scava.crossflow.nlp.stackexchange.gen;

import org.eclipse.scava.crossflow.runtime.FailedJob;
import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class StackExchangeReaderBase extends Task {
		
	protected StackExchangeIndexingWorkFlow workflow;
	
	public void setWorkflow(StackExchangeIndexingWorkFlow workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	
	public String getId(){
		return "StackExchangeReader:"+workflow.getName();
	}
	
	protected PlainTextQueue plainTextQueue;
	
	protected void setPlainTextQueue(PlainTextQueue plainTextQueue) {
		this.plainTextQueue = plainTextQueue;
	}
	
	protected PlainTextQueue getPlainTextQueue() {
		return plainTextQueue;
	}
	
	public void sendToPlainTextQueue(Post post) {
		post.setCacheable(this.cacheable);
		post.setTransactional(false);
		getPlainTextQueue().send(post, this.getClass().getName());
	}
	
	
	
	public abstract void produce() throws Exception;
	
}

