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

import java.util.Calendar;
import java.util.Date;

import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.model.Worker;
import org.eclipse.scava.platform.analysis.data.types.AnalysisExecutionMode;
import org.eclipse.scava.platform.analysis.data.types.AnalysisTaskStatus;

public class TaskCheckExecutor implements Runnable {

    private static final Integer cycle = 10000;
    private static final Integer heartbet = 600000;

	private Boolean executeTasks;
	private Platform platform;

	public TaskCheckExecutor(Platform platform) {
		this.executeTasks = true;
		this.platform = platform;
	}

	@Override
	public void run() {
		while (executeTasks) {			
			Date day = dateToDay(new Date());
						
			// Detect Worker Failure / Replace Task in execution pending list		
			for(Worker  worker : this.platform.getAnalysisRepositoryManager().getWorkerService().getWorkers()) {
				if(worker.getCurrentTask() != null && new Date().getTime() - worker.getHeartbeat().getTime() > heartbet) {			
					
					AnalysisTask task = this.platform.getAnalysisRepositoryManager().getTaskService().getTaskByAnalysisTaskId(worker.getCurrentTask().getAnalysisTaskId());
					task.getScheduling().setStatus(AnalysisTaskStatus.PENDING_EXECUTION.name());
					task.getScheduling().setWorkerId(null);

					
					this.platform.getAnalysisRepositoryManager().getRepository().getWorkers().remove(worker);				
					this.platform.getAnalysisRepositoryManager().getRepository().sync();
				}
			}
			
			// Detect New Daily Execution
			for(AnalysisTask task : this.platform.getAnalysisRepositoryManager().getRepository().getAnalysisTasks()) {			
				if(task.getType().equals(AnalysisExecutionMode.DAILY_EXECUTION.name()) && task.getScheduling().getStatus().equals(AnalysisTaskStatus.COMPLETED.name()) && task.getScheduling().getCurrentDate().compareTo(day)< 0) {
					task.getScheduling().setStatus(AnalysisTaskStatus.PENDING_EXECUTION.name());	
					this.platform.getAnalysisRepositoryManager().getRepository().sync();
				}
			}
	
			try {
				Thread.sleep(cycle);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
	
	public Date dateToDay(Date date) {
		Calendar cal = Calendar.getInstance(); // locale-specific
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new Date(cal.getTimeInMillis());
	}

	public boolean finish() {
		executeTasks = false;
		return true;
	}

}
