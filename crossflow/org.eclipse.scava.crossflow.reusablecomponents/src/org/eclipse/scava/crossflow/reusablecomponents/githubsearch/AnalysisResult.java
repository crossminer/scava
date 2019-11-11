package org.eclipse.scava.crossflow.reusablecomponents.githubsearch;

import org.eclipse.scava.crossflow.runtime.Job;

public class AnalysisResult extends Job {
	
	private int fileCount;
	
	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}
	
	public int getFileCount() {
		return fileCount;
	}
	
	private int authorCount;
	
	public void setAuthorCount(int authorCount) {
		this.authorCount = authorCount;
	}
	
	public int getAuthorCount() {
		return authorCount;
	}
	
	private Repository repository;
	
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	
	public Repository getRepository() {
		return repository;
	}
	
	private Technology technology;
	
	public void setTechnology(Technology technology) {
		this.technology = technology;
	}
	
	public Technology getTechnology() {
		return technology;
	}
	
	
	public Object[] toObjectArray() {
		Object[] ret = new Object[4];
	 	ret[0] = getFileCount();
	 	ret[1] = getAuthorCount();
	 	ret[2] = getRepository();
	 	ret[3] = getTechnology();
		return ret;
	}
	
	public String toString() {
		return "AnalysisResult (" + " fileCount=" + fileCount + " authorCount=" + authorCount + " repository=" + repository + " technology=" + technology + " jobId=" + jobId + " correlationId=" + correlationId + " destination=" + destination + ")";
	}
	
}


