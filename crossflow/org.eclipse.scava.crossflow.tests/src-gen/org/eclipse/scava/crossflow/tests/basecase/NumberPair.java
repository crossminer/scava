package org.eclipse.scava.crossflow.tests.basecase;

import java.io.Serializable;
import java.util.UUID;
import org.eclipse.scava.crossflow.runtime.Job;

public class NumberPair extends Job {
	
	public NumberPair() {}
	
	public NumberPair(int a, int b) {
		this.a = a;
		this.b = b;
	}
	
	protected int a;
	
	public void setA(int a) {
		this.a = a;
	}
	
	public int getA() {
		return a;
	}
	
	protected int b;
	
	public void setB(int b) {
		this.b = b;
	}
	
	public int getB() {
		return b;
	}
	
	
	public Object[] toObjectArray(){
		Object[] ret = new Object[2];
	 	ret[0] = getA();
	 	ret[1] = getB();
		return ret;
	}


}