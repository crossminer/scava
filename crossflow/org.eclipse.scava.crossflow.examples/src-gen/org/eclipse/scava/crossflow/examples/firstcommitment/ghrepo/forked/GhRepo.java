package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.forked;

import java.io.Serializable;
import java.util.UUID;
import org.eclipse.scava.crossflow.runtime.Job;

public class GhRepo extends Job {
	
	public GhRepo() {}
	
	public GhRepo(String repoUrl) {
		this.repoUrl = repoUrl;
	}
	
	protected String repoUrl;
	
	public void setRepoUrl(String repoUrl) {
		this.repoUrl = repoUrl;
	}
	
	public String getRepoUrl() {
		return repoUrl;
	}
	
	
	public Object[] toObjectArray(){
		Object[] ret = new Object[1];
	 	ret[0] = getRepoUrl();
		return ret;
	}


}