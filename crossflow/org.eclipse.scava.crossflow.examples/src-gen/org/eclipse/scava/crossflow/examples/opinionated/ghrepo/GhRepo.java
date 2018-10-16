package org.eclipse.scava.crossflow.examples.opinionated.ghrepo;

import org.eclipse.scava.crossflow.runtime.Job;

public class GhRepo extends Job {
	
	private static final long serialVersionUID = -3357517408175142017L;
	
	protected String repoUrl;
	
	public void setRepoUrl(String repoUrl) {
		this.repoUrl = repoUrl;
	}
	
	public String getRepoUrl() {
		return repoUrl;
	}
	
}