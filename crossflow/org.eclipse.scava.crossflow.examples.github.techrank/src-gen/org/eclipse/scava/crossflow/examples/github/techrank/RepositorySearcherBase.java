package org.eclipse.scava.crossflow.examples.github.techrank;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.FailedJob;

public abstract class RepositorySearcherBase extends Task  implements RepositorySearchesConsumer{
		
	protected TechrankWorkflow workflow;
	
	public void setWorkflow(TechrankWorkflow workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "RepositorySearcher:"+workflow.getName();
	}
	
	protected RepositorySearchResults repositorySearchResults;
	
	protected void setRepositorySearchResults(RepositorySearchResults repositorySearchResults) {
		this.repositorySearchResults = repositorySearchResults;
	}
	
	private RepositorySearchResults getRepositorySearchResults() {
		return repositorySearchResults;
	}
	
	public void sendToRepositorySearchResults(RepositorySearchResult repositorySearchResult) {
		getRepositorySearchResults().send(repositorySearchResult, this.getClass().getName());
	}
	
	
	@Override
	public final void consumeRepositorySearchesWithNotifications(RepositorySearch repositorySearch) {
		try {
			workflow.setTaskInProgess(this);
			consumeRepositorySearches(repositorySearch);
		}
		catch (Exception ex) {
			try {
				repositorySearch.setFailures(repositorySearch.getFailures()+1);
				workflow.getFailedJobsQueue().send(new FailedJob(repositorySearch, ex, workflow.getName(), "RepositorySearcher"));
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
	
	public abstract void consumeRepositorySearches(RepositorySearch repositorySearch) throws Exception;
	
	
	
}