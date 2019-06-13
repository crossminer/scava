package org.eclipse.scava.crossflow.nlp.stackexchange.gen;

import org.eclipse.scava.crossflow.runtime.FailedJob;
import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class PlainTextBase extends Task  implements PlainTextQueueConsumer{
		
	protected StackExchangeIndexingWorkFlow workflow;
	
	public void setWorkflow(StackExchangeIndexingWorkFlow workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	
	public String getId(){
		return "PlainText:"+workflow.getName();
	}
	
	protected CodeDetectorQueue codeDetectorQueue;
	
	protected void setCodeDetectorQueue(CodeDetectorQueue codeDetectorQueue) {
		this.codeDetectorQueue = codeDetectorQueue;
	}
	
	protected CodeDetectorQueue getCodeDetectorQueue() {
		return codeDetectorQueue;
	}
	
	public void sendToCodeDetectorQueue(Post post) {
		post.setCacheable(this.cacheable);
		getCodeDetectorQueue().send(post, this.getClass().getName());
	}
	
	
	
	
	
	@Override
	public final void consumePlainTextQueueWithNotifications(Post post) {
		
		try {
			workflow.getPlainTexts().getSemaphore().acquire();
		} catch (Exception e) {
			workflow.reportInternalException(e);
		}
				
				
		Runnable consumer = () -> {		
			try {
				workflow.setTaskInProgess(this);

				Post result = consumePlainTextQueue(post);
				if(result != null){
					if(isCacheable())
						result.setCorrelationId(post.getId());				
					result.setTransactional(false);
					sendToCodeDetectorQueue(result);
				}

			} catch (Exception ex) {
				try {
					post.setFailures(post.getFailures()+1);
					workflow.getFailedJobsQueue().send(new FailedJob(post, ex, workflow.getName(), "PlainText"));
				} catch (Exception e) {
					workflow.reportInternalException(e);
				}
			} finally {
				try {
					workflow.getPlainTexts().getSemaphore().release();
					workflow.setTaskWaiting(this);
				} catch (Exception e) {
					workflow.reportInternalException(e);
				}
			}
		
		};

		workflow.getPlainTexts().getExecutor().submit(consumer);
	}
	
	public abstract Post consumePlainTextQueue(Post post) throws Exception;
	

	
	
}

