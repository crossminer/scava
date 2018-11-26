package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

import java.io.Serializable;
import java.util.UUID;
import org.eclipse.scava.crossflow.runtime.Job;

public class Result extends Job {
	
	protected String technology;
	
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	
	public String getTechnology() {
		return technology;
	}
	
	protected Integer repos;
	
	public void setRepos(Integer repos) {
		this.repos = repos;
	}
	
	public Integer getRepos() {
		return repos;
	}
	
	protected Integer files;
	
	public void setFiles(Integer files) {
		this.files = files;
	}
	
	public Integer getFiles() {
		return files;
	}
	
	protected Integer authors;
	
	public void setAuthors(Integer authors) {
		this.authors = authors;
	}
	
	public Integer getAuthors() {
		return authors;
	}
	
	
	public Object[] toObjectArray(){
		Object[] ret = new Object[4];
	 	ret[0] = getTechnology();
	 	ret[1] = getRepos();
	 	ret[2] = getFiles();
	 	ret[3] = getAuthors();
		return ret;
	}


}