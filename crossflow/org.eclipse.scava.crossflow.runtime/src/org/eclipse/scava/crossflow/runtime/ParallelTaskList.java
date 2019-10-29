package org.eclipse.scava.crossflow.runtime;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class ParallelTaskList<E> extends LinkedList<E> {
	
	private static final long serialVersionUID = -7187844983786218545L;
	
	private Semaphore semaphore;
	private ListeningExecutorService executor;

	public void init(Workflow<?> w) {
		semaphore = new Semaphore(w.getParallelization());
		// TODO: Guava 29.0-JRE will allow executors without this decorator to be used for timeouts
		executor = MoreExecutors.listeningDecorator(w.newExecutor());
	}

	public Semaphore getSemaphore() {
		return semaphore;
	}

	public ListeningExecutorService getExecutor() {
		return executor;
	}

}
