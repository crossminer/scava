package org.eclipse.scava.crossflow.tests.opinionated;

import java.io.Serializable;
import java.util.UUID;
import org.eclipse.scava.crossflow.runtime.Job;

public class Word extends Job {
	
	public Word() {}
	
	public Word(String w) {
		this.w = w;
	}
	
	protected String w;
	
	public void setW(String w) {
		this.w = w;
	}
	
	public String getW() {
		return w;
	}
	
	
	public Object[] toObjectArray(){
		Object[] ret = new Object[1];
	 	ret[0] = getW();
		return ret;
	}


}