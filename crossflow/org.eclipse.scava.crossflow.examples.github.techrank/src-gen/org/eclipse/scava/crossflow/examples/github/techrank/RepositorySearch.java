package org.eclipse.scava.crossflow.examples.github.techrank;

import java.io.Serializable;
import java.util.UUID;
import org.eclipse.scava.crossflow.runtime.Job;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class RepositorySearch extends Job  {
	
	public RepositorySearch() {}
	
	public RepositorySearch(String repository, List<Technology> technologies) {
		this.repository = repository;
		this.technologies = technologies;
	}

	public RepositorySearch(String repository, List<Technology> technologies, Job correlation) {
		this.repository = repository;
		this.technologies = technologies;
		this.correlationId = correlation.getId();
	}
		
	protected String repository;
	
	public void setRepository(String repository) {
		this.repository = repository;
	}
	
	public String getRepository() {
		return repository;
	}
	
	protected List<Technology> technologies= new ArrayList<>();
	
	public void setTechnologies(List<Technology> technologies) {
		this.technologies = technologies;
	}
	
	public List<Technology> getTechnologies() {
		return technologies;
	}
	
	
	public Object[] toObjectArray(){
		Object[] ret = new Object[2];
	 	ret[0] = getRepository();
	 	ret[1] = getTechnologies();
		return ret;
	}
	
	public String toString() {
		return "RepositorySearch (" + " repository=" + repository + " technologies=" + technologies + " id=" + id + " correlationId=" + correlationId + " destination=" + destination + ")";
	}
	
}

