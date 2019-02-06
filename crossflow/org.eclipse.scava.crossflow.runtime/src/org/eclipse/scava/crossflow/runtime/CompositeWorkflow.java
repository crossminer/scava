package org.eclipse.scava.crossflow.runtime;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeWorkflow {

	// default parallel instances on each node equal to the number of virtual cores
	// on the machine
	protected int parallelization = Runtime.getRuntime().availableProcessors();

	protected List<Workflow> elements = new ArrayList<Workflow>();

	public abstract void run(int delay) throws Exception;

	public abstract boolean hasTerminated();

}