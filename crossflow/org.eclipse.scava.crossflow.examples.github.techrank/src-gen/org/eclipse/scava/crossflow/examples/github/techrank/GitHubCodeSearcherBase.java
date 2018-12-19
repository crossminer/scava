package org.eclipse.scava.crossflow.examples.github.techrank;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.FailedJob;

public abstract class GitHubCodeSearcherBase extends Task  implements TechnologiesConsumer{
		
	protected TechrankWorkflow workflow;
	
	public void setWorkflow(TechrankWorkflow workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "GitHubCodeSearcher:"+workflow.getName();
	}
	
	protected Repositories repositories;
	
	protected void setRepositories(Repositories repositories) {
		this.repositories = repositories;
	}
	
	private Repositories getRepositories() {
		return repositories;
	}
	
	public void sendToRepositories(Repository repository) {
		getRepositories().send(repository, this.getClass().getName());
	}
	
	
	@Override
	public final void consumeTechnologiesWithNotifications(Technology technology) {
		try {
			workflow.setTaskInProgess(this);
			consumeTechnologies(technology);
		}
		catch (Exception ex) {
			try {
				technology.setFailures(technology.getFailures()+1);
				workflow.getFailedJobsQueue().send(new FailedJob(technology, ex, workflow.getName(), "GitHubCodeSearcher"));
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
	
	public abstract void consumeTechnologies(Technology technology) throws Exception;
	
	
	
}