package org.eclipse.scava.crossflow.examples.addition;

import java.io.Serializable;
import java.util.UUID;
import org.eclipse.scava.crossflow.runtime.Job;

public class NumberPair extends Job {
	
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
	
}