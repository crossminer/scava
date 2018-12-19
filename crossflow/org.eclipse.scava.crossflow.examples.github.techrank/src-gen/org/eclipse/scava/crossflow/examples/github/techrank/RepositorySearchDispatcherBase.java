package org.eclipse.scava.crossflow.examples.github.techrank;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.FailedJob;

public abstract class RepositorySearchDispatcherBase extends Task  implements RepositoriesConsumer{
		
	protected TechrankWorkflow workflow;
	
	public void setWorkflow(TechrankWorkflow workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "RepositorySearchDispatcher:"+workflow.getName();
	}
	
	protected RepositorySearches repositorySearches;
	
	protected void setRepositorySearches(RepositorySearches repositorySearches) {
		this.repositorySearches = repositorySearches;
	}
	
	private RepositorySearches getRepositorySearches() {
		return repositorySearches;
	}
	
	public void sendToRepositorySearches(RepositorySearch repositorySearch) {
		getRepositorySearches().send(repositorySearch, this.getClass().getName());
	}
	
	
	@Override
	public final void consumeRepositoriesWithNotifications(Repository repository) {
		try {
			workflow.setTaskInProgess(this);
			consumeRepositories(repository);
		}
		catch (Exception ex) {
			try {
				repository.setFailures(repository.getFailures()+1);
				workflow.getFailedJobsQueue().send(new FailedJob(repository, ex, workflow.getName(), "RepositorySearchDispatcher"));
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
	
	public abstract void consumeRepositories(Repository repository) throws Exception;
	
	
	
}