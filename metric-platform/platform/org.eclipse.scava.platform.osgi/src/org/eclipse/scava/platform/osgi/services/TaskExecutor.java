package org.eclipse.scava.platform.osgi.services;

import org.apache.log4j.Logger;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.platform.osgi.analysis.ProjectAnalyser;

public class TaskExecutor implements Runnable {

	private Logger loggerOssmeter = OssmeterLogger.getLogger("WorkerExecutor");
	private String WORKER_ID;
	private String analysisTaskId;
	private Platform platform;

	TaskExecutor(String workerId, String analysisTaskId, Platform platform) {
		this.WORKER_ID = workerId;
		this.analysisTaskId = analysisTaskId;
		this.platform = platform;
	}

	@Override
	public void run() {
		loggerOssmeter.info("Worker '" + WORKER_ID + "' Executing " + analysisTaskId + " Task");
		platform.getAnalysisRepositoryManager().getWorkerService().assignTask(analysisTaskId, WORKER_ID);
		ProjectAnalyser analyser = new ProjectAnalyser(this.platform);
		boolean analysisStatus = analyser.executeAnalyse(analysisTaskId, WORKER_ID);
		if (analysisStatus) {
			platform.getAnalysisRepositoryManager().getWorkerService().completeTask(WORKER_ID);
		} else {
			platform.getAnalysisRepositoryManager().getWorkerService().interruptFailedTask(WORKER_ID);
		}
	}

}
