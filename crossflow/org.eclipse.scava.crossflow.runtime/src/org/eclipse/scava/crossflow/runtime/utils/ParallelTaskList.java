package org.eclipse.scava.crossflow.runtime.utils;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

import org.eclipse.scava.crossflow.runtime.Workflow;

public class ParallelTaskList<E> extends LinkedList<E> {
	
	private static final long serialVersionUID = -7187844983786218545L;
	
	private Semaphore semaphore;
	private ExecutorService executor;

	public void init(Workflow w) {
		semaphore = new Semaphore(w.getParallelization());
		executor = w.newExecutor();
	}

	public Semaphore getSemaphore() {
		return semaphore;
	}

	public ExecutorService getExecutor() {
		return executor;
	}

}
