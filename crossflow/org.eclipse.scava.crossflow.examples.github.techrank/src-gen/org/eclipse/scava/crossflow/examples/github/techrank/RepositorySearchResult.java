package org.eclipse.scava.crossflow.examples.github.techrank;

import java.io.Serializable;
import java.util.UUID;
import org.eclipse.scava.crossflow.runtime.Job;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class RepositorySearchResult extends Job  {
	
	public RepositorySearchResult() {}
	
	public RepositorySearchResult(String technology, int results) {
		this.technology = technology;
		this.results = results;
	}

	public RepositorySearchResult(String technology, int results, Job correlation) {
		this.technology = technology;
		this.results = results;
		this.correlationId = correlation.getId();
	}
		
	protected String technology;
	
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	
	public String getTechnology() {
		return technology;
	}
	
	protected int results;
	
	public void setResults(int results) {
		this.results = results;
	}
	
	public int getResults() {
		return results;
	}
	
	
	public Object[] toObjectArray(){
		Object[] ret = new Object[2];
	 	ret[0] = getTechnology();
	 	ret[1] = getResults();
		return ret;
	}
	
	public String toString() {
		return "RepositorySearchResult (" + " technology=" + technology + " results=" + results + " id=" + id + " correlationId=" + correlationId + " destination=" + destination + ")";
	}
	
}

