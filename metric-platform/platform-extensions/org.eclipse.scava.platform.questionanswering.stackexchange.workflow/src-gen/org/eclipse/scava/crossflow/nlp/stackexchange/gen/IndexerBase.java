package org.eclipse.scava.crossflow.nlp.stackexchange.gen;

import org.eclipse.scava.crossflow.runtime.FailedJob;
import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class IndexerBase extends Task  implements IndexingQueueConsumer{
		
	protected StackExchangeIndexingWorkFlow workflow;
	
	public void setWorkflow(StackExchangeIndexingWorkFlow workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	
	public String getId(){
		return "Indexer:"+workflow.getName();
	}
	
	
	
	
	
	@Override
	public void consumeIndexingQueueWithNotifications(Post post) {
		
			try {
				workflow.setTaskInProgess(this);

				consumeIndexingQueue(post);

		


			} catch (Exception ex) {
				try {
					post.setFailures(post.getFailures()+1);
					workflow.getFailedJobsQueue().send(new FailedJob(post, ex, workflow.getName(), "Indexer"));
				} catch (Exception e) {
					workflow.reportInternalException(e);
				}
			} finally {
				try {
					workflow.setTaskWaiting(this);
				} catch (Exception e) {
					workflow.reportInternalException(e);
				}
			}
	}
	
	public abstract void consumeIndexingQueue(Post post) throws Exception;
	

	
	
}

