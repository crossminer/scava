package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

import java.io.Serializable;
import java.util.UUID;
import org.eclipse.scava.crossflow.runtime.Job;

public class GhRepo extends Job {
	
	protected String repoUrl;
	
	public void setRepoUrl(String repoUrl) {
		this.repoUrl = repoUrl;
	}
	
	public String getRepoUrl() {
		return repoUrl;
	}
	
}