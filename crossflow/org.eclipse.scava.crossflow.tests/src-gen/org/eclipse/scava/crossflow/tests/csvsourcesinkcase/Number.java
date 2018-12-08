package org.eclipse.scava.crossflow.tests.csvsourcesinkcase;

import java.io.Serializable;
import java.util.UUID;
import org.eclipse.scava.crossflow.runtime.Job;

public class Number extends Job {
	
	public Number() {}
	
	public Number(int n) {
		this.n = n;
	}
	
	protected int n;
	
	public void setN(int n) {
		this.n = n;
	}
	
	public int getN() {
		return n;
	}
	
	
	public Object[] toObjectArray(){
		Object[] ret = new Object[1];
	 	ret[0] = getN();
		return ret;
	}


}