package org.eclipse.scava.crossflow.nlp.stackexchange.gen;

import org.eclipse.scava.crossflow.runtime.FailedJob;
import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class CodeDetectorBase extends Task  implements CodeDetectorQueueConsumer{
		
	protected StackExchangeIndexingWorkFlow workflow;
	
	public void setWorkflow(StackExchangeIndexingWorkFlow workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	
	public String getId(){
		return "CodeDetector:"+workflow.getName();
	}
	
	protected IndexingQueue indexingQueue;
	
	protected void setIndexingQueue(IndexingQueue indexingQueue) {
		this.indexingQueue = indexingQueue;
	}
	
	protected IndexingQueue getIndexingQueue() {
		return indexingQueue;
	}
	
	public void sendToIndexingQueue(Post post) {
		post.setCacheable(this.cacheable);
		getIndexingQueue().send(post, this.getClass().getName());
	}
	
	
	
	
	
	@Override
	public final void consumeCodeDetectorQueueWithNotifications(Post post) {
		
		try {
			workflow.getCodeDetectors().getSemaphore().acquire();
		} catch (Exception e) {
			workflow.reportInternalException(e);
		}
				
				
		Runnable consumer = () -> {		
			try {
				workflow.setTaskInProgess(this);

				Post result = consumeCodeDetectorQueue(post);
				if(result != null){
					if(isCacheable())
						result.setCorrelationId(post.getId());				
					result.setTransactional(false);
					sendToIndexingQueue(result);
				}

			} catch (Exception ex) {
				try {
					post.setFailures(post.getFailures()+1);
					workflow.getFailedJobsQueue().send(new FailedJob(post, ex, workflow.getName(), "CodeDetector"));
				} catch (Exception e) {
					workflow.reportInternalException(e);
				}
			} finally {
				try {
					workflow.getCodeDetectors().getSemaphore().release();
					workflow.setTaskWaiting(this);
				} catch (Exception e) {
					workflow.reportInternalException(e);
				}
			}
		
		};

		workflow.getCodeDetectors().getExecutor().submit(consumer);
	}
	
	public abstract Post consumeCodeDetectorQueue(Post post) throws Exception;
	

	
	
}

