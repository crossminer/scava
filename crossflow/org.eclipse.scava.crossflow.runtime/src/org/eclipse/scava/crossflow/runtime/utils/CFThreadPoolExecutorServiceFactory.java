package org.eclipse.scava.crossflow.runtime.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CFThreadPoolExecutorServiceFactory {

	//
	private static int maxThreads = Runtime.getRuntime().availableProcessors();
	//

	public static ThreadPoolExecutor getExecutor() {
		return getExecutor(maxThreads);
	}

	public static ThreadPoolExecutor getExecutor(int threads) {
		return (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
	}

}
