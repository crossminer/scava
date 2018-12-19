package org.eclipse.scava.crossflow.examples.github.techrank;

import java.io.Serializable;
import java.util.UUID;
import org.eclipse.scava.crossflow.runtime.Job;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class Technology extends Job  {
	
	public Technology() {}
	
	public Technology(String name, String keyword, String extension) {
		this.name = name;
		this.keyword = keyword;
		this.extension = extension;
	}

	public Technology(String name, String keyword, String extension, Job correlation) {
		this.name = name;
		this.keyword = keyword;
		this.extension = extension;
		this.correlationId = correlation.getId();
	}
		
	protected String name;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	protected String keyword;
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	protected String extension;
	
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	public String getExtension() {
		return extension;
	}
	
	
	public Object[] toObjectArray(){
		Object[] ret = new Object[3];
	 	ret[0] = getName();
	 	ret[1] = getKeyword();
	 	ret[2] = getExtension();
		return ret;
	}
	
	public String toString() {
		return "Technology (" + " name=" + name + " keyword=" + keyword + " extension=" + extension + " id=" + id + " correlationId=" + correlationId + " destination=" + destination + ")";
	}
	
}

