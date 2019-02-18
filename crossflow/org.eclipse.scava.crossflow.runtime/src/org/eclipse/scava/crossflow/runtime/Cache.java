package org.eclipse.scava.crossflow.runtime;

import java.util.List;

public interface Cache {
	
	public List<Job> getCachedOutputs(Job input);
	
	public boolean hasCachedOutputs(Job input);
	
	public void cache(Job output);
	
	public void setWorkflow(Workflow workflow);
	
	public void cacheTransactionally(Job output);

}
