package org.eclipse.scava.platform.osgi.services;

import org.apache.log4j.Logger;
import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.data.AnalysisSchedulingService;
import org.eclipse.scava.platform.analysis.data.WorkerService;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.platform.osgi.analysis.ProjectAnalyser;

import com.mongodb.Mongo;

public class WorkerExecutor implements Runnable {

	private static final String WORKER_ID = Configuration.getInstance().getSlaveIdentifier();
	private Logger logger = OssmeterLogger.getLogger("OssmeterApplication");

	private static final Integer CYCLE = 10000;

	private Platform platform;
	private AnalysisSchedulingService schedulingService;
	private WorkerService workerService;

	private ProjectAnalyser analyser;
	private Boolean executeTasks;

	public WorkerExecutor(Mongo mongo) {
		this.platform = new Platform(mongo);
		this.schedulingService = new AnalysisSchedulingService(mongo);
		this.workerService = new WorkerService(mongo);
		this.executeTasks = true;
	}

	@Override
	public void run() {
		
		// Register Worker
		this.workerService.registerWorker(WORKER_ID);
		
		while (executeTasks) {
			String analysisTaskId = schedulingService.getOlderPendingAnalysiTask();
			if (analysisTaskId != null) {
				logger.info("Worker '" + WORKER_ID + "' Executing " + analysisTaskId + " Task");
				this.workerService.assignTask(analysisTaskId,WORKER_ID);
				this.analyser = new ProjectAnalyser(this.platform, this.schedulingService,this.workerService);		
				this.analyser.executeAnalyse(analysisTaskId,WORKER_ID);	
				this.workerService.completeTask(WORKER_ID);
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
