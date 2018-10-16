package org.eclipse.scava.crossflow.examples.addition;

import java.io.Serializable;
import java.util.UUID;
import org.eclipse.scava.crossflow.runtime.Job;

public class Number extends Job {
	
	protected int n;
	
	public void setN(int n) {
		this.n = n;
	}
	
	public int getN() {
		return n;
	}
	
}