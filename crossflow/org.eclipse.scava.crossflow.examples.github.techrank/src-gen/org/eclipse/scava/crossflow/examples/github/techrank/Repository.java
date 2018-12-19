package org.eclipse.scava.crossflow.examples.github.techrank;

import java.io.Serializable;
import java.util.UUID;
import org.eclipse.scava.crossflow.runtime.Job;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class Repository extends Job  {
	
	public Repository() {}
	
	public Repository(String path) {
		this.path = path;
	}

	public Repository(String path, Job correlation) {
		this.path = path;
		this.correlationId = correlation.getId();
	}
		
	protected String path;
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
	
	
	public Object[] toObjectArray(){
		Object[] ret = new Object[1];
	 	ret[0] = getPath();
		return ret;
	}
	
	public String toString() {
		return "Repository (" + " path=" + path + " id=" + id + " correlationId=" + correlationId + " destination=" + destination + ")";
	}
	
}

