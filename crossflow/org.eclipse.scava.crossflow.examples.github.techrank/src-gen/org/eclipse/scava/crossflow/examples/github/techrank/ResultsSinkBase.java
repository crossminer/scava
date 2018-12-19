package org.eclipse.scava.crossflow.examples.github.techrank;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.FailedJob;

public abstract class ResultsSinkBase extends Task  implements RepositorySearchResultsConsumer{
		
	protected TechrankWorkflow workflow;
	
	public void setWorkflow(TechrankWorkflow workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "ResultsSink:"+workflow.getName();
	}
	
	
	@Override
	public final void consumeRepositorySearchResultsWithNotifications(RepositorySearchResult repositorySearchResult) {
		try {
			workflow.setTaskInProgess(this);
			consumeRepositorySearchResults(repositorySearchResult);
		}
		catch (Exception ex) {
			try {
				repositorySearchResult.setFailures(repositorySearchResult.getFailures()+1);
				workflow.getFailedJobsQueue().send(new FailedJob(repositorySearchResult, ex, workflow.getName(), "ResultsSink"));
			} catch (Exception e) {
				workflow.reportInternalException(e);
			}
		}
		finally {
			try {
				workflow.setTaskWaiting(this);
			} catch (Exception e) {
				workflow.reportInternalException(e);
			}
		}
	}
	
	public abstract void consumeRepositorySearchResults(RepositorySearchResult repositorySearchResult) throws Exception;
	
	
	
}