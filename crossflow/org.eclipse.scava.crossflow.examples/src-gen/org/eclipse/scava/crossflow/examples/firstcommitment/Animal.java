package org.eclipse.scava.crossflow.examples.firstcommitment;

import java.io.Serializable;
import java.util.UUID;
import org.eclipse.scava.crossflow.runtime.Job;

public class Animal extends Job {
	
	protected String name;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}