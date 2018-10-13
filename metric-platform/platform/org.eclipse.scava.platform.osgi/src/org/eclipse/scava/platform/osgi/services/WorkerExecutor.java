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
import org.eclipse.scava.platform.analysis.data.model.Worker;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.platform.osgi.analysis.ProjectAnalyser;

public class WorkerExecutor implements Runnable {

	private static String WORKER_ID;
	private Logger logger = OssmeterLogger.getLogger("OssmeterApplication");

	private static final Integer CYCLE = 10000;

	private Platform platform;

	private ProjectAnalyser analyser;
	private Boolean executeTasks;;

	public WorkerExecutor(Platform platform,String workerId) {
		this.platform = platform;
		this.executeTasks = true;
		
		if( workerId == null) {
			WORKER_ID = Configuration.getInstance().getSlaveIdentifier();
		}else {
			WORKER_ID = workerId;
		}
	}

	@Override
	public void run() {
		
		// Register Worker
		platform.getAnalysisRepositoryManager().getWorkerService().registerWorker(WORKER_ID);
		
		while (executeTasks) {
			String analysisTaskId = platform.getAnalysisRepositoryManager().getSchedulingService().getOlderPendingAnalysiTask();
			if (analysisTaskId != null) {
				logger.info("Worker '" + WORKER_ID + "' Executing " + analysisTaskId + " Task");
				platform.getAnalysisRepositoryManager().getWorkerService().assignTask(analysisTaskId,WORKER_ID);
				this.analyser = new ProjectAnalyser(this.platform);		
				this.analyser.executeAnalyse(analysisTaskId,WORKER_ID);	
				platform.getAnalysisRepositoryManager().getWorkerService().completeTask(WORKER_ID);

			} else {				
				Worker worker = platform.getAnalysisRepositoryManager().getRepository().getWorkers().findOneByWorkerId(WORKER_ID);
				worker.setHeartbeat(new Date());
				platform.getAnalysisRepositoryManager().getRepository().sync();	
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
