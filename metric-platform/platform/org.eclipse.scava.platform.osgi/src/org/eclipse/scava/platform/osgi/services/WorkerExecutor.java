package org.eclipse.scava.platform.osgi.services;

import org.apache.log4j.Logger;
import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.data.AnalysisTaskScheduling;
import org.eclipse.scava.platform.analysis.data.IAnalysisSchedulingService;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.platform.osgi.analysis.ProjectAnalyser;

import com.mongodb.Mongo;

public class WorkerExecutor implements Runnable {

	private static final String WORKER_ID = Configuration.getInstance().getSlaveIdentifier();
	private Logger logger = OssmeterLogger.getLogger("OssmeterApplication");

	private static final Integer CYCLE = 10000;

	private Platform platform;
	private IAnalysisSchedulingService repository;

	private ProjectAnalyser analyser;
	private Boolean executeTasks;

	public WorkerExecutor(Mongo mongo) {
		this.platform = new Platform(mongo);
		this.repository = new AnalysisTaskScheduling(mongo);
		this.executeTasks = true;
	}

	@Override
	public void run() {
		while (executeTasks) {
			String analysisTaskId = repository.getOlderPendingAnalysiTask();
			if (analysisTaskId != null) {
				logger.info("Worker '" + WORKER_ID + "' Executing " + analysisTaskId + " Task");				
				this.analyser = new ProjectAnalyser(this.platform, this.repository);
				this.analyser.executeAnalyse(analysisTaskId,WORKER_ID);	
			} else {
				logger.info("Worker '" + WORKER_ID + "' Waiting New Tasks");
			}

			try {
				Thread.sleep(CYCLE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
