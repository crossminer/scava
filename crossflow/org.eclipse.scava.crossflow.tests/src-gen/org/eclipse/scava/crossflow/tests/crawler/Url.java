package org.eclipse.scava.crossflow.tests.crawler;

import java.io.Serializable;
import java.util.UUID;
import org.eclipse.scava.crossflow.runtime.Job;

public class Url extends Job {
	
	public Url() {}
	
	public Url(String location) {
		this.location = location;
	}
	
	protected String location;
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getLocation() {
		return location;
	}
	
	
	public Object[] toObjectArray(){
		Object[] ret = new Object[1];
	 	ret[0] = getLocation();
		return ret;
	}


}