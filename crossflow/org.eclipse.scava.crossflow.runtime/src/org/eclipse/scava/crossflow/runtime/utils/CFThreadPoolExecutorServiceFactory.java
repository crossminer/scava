package org.eclipse.scava.crossflow.runtime.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CFThreadPoolExecutorServiceFactory {

	private static Collection<ThreadPoolExecutor> pools = new HashSet<ThreadPoolExecutor>();
	//
	private static int maxThreads = Runtime.getRuntime().availableProcessors();
	//

	public static ThreadPoolExecutor getExecutor() {
		return getExecutor(maxThreads);
	}

	public static ThreadPoolExecutor getExecutor(int threads) {
		ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
		pools.add(pool);
		return pool;
	}

	public static Collection<ThreadPoolExecutor> getPools() {
		return pools;
	}

}
