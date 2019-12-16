/*******************************************************************************
 * Copyright (c) 2018 Softeam
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.osgi.services;

import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.model.Worker;
import org.eclipse.scava.platform.analysis.data.types.AnalysisTaskStatus;
import org.eclipse.scava.platform.logging.OssmeterLogger;

public class WorkerExecutor implements Runnable {

	private static String WORKER_ID;
	private Logger loggerOssmeter = OssmeterLogger.getLogger("WorkerExecutor");

	private static final Integer CYCLE = 5000;

	private Platform platform;
	private Boolean executeTasks;

	public WorkerExecutor(Platform platform, String workerId) {
		this.platform = platform;
		this.executeTasks = true;

		if (workerId == null) {
			WORKER_ID = Configuration.getInstance().getSlaveIdentifier();
		} else {
			WORKER_ID = workerId;
		}
	}

	@Override
	public void run() {
		// Register Worker
		platform.getAnalysisRepositoryManager().getWorkerService().registerWorker(WORKER_ID);

		Thread workerThread = null;
		String runningTask = null;
		while (executeTasks) {
			Worker worker = platform.getAnalysisRepositoryManager().getRepository().getWorkers().findOneByWorkerId(WORKER_ID);
			worker.setHeartbeat(new Date());
			platform.getAnalysisRepositoryManager().getRepository().sync();
			if(workerThread == null) {
				String analysisTaskId = platform.getAnalysisRepositoryManager().getSchedulingService().getOlderPendingAnalysiTask();
				if (analysisTaskId != null) {
					if(workerThread == null) {
						workerThread = new Thread(new TaskExecutor(WORKER_ID,analysisTaskId, platform));
						workerThread.start();
						runningTask = analysisTaskId;
					}
				} else {			
					loggerOssmeter.info("Worker '" + WORKER_ID + "' Waiting new Tasks");		
				}
			}else {
				if(workerThread.isAlive()) {
					AnalysisTask task = platform.getAnalysisRepositoryManager().getRepository().getAnalysisTasks().findOneByAnalysisTaskId(runningTask);

					if (task.getScheduling().getStatus().equals((AnalysisTaskStatus.PENDING_STOP.name()))) {
						workerThread.stop();
						workerThread = null;
						task.getScheduling().setStatus(AnalysisTaskStatus.STOP.name());
						task.getScheduling().setWorkerId(null);	
						platform.getAnalysisRepositoryManager().getRepository().sync();
						platform.getAnalysisRepositoryManager().getWorkerService().completeTask(WORKER_ID);
					}
					
				}else {
					workerThread = null;
				}
			}

			try {
				Thread.sleep(CYCLE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

}
