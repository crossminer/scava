package org.eclipse.scava.platform.osgi.services;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.scava.platform.analysis.data.AnalysisSchedulingService;
import org.eclipse.scava.platform.analysis.data.WorkerService;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.model.Worker;
import org.eclipse.scava.platform.analysis.data.types.AnalysisTaskStatus;

import com.mongodb.Mongo;

public class TaskCheckExecutor implements Runnable {

    private static final Integer cycle = 10000;
    private static final Integer heartbet = 1800000;

	private AnalysisSchedulingService schedulingService;
	private WorkerService workerService;
	private Boolean executeTasks;

	public TaskCheckExecutor(Mongo mongo) {
		this.schedulingService = new AnalysisSchedulingService(mongo);
		this.workerService = new WorkerService(mongo);
		this.executeTasks = true;
	}

	@Override
	public void run() {
		while (executeTasks) {			
			Date day = dateToDay(new Date());
						
			// Detect Worker Failure / Replace Task in execution pending list		
			for(Worker  worker : this.workerService.getRepository().getWorkers()) {
				if(worker.getCurrentTask() != null && new Date().getTime() - worker.getHeartbeat().getTime() > heartbet) {			
					worker.getCurrentTask().getScheduling().setStatus(AnalysisTaskStatus.PENDING_EXECUTION.name());
					worker.getCurrentTask().getScheduling().setWorkerId(null);				
					this.workerService.getRepository().getWorkers().remove(worker);				
					this.workerService.getRepository().sync();
				}
			}
			
			// Detect New Daily Execution
			for(AnalysisTask task : this.schedulingService.getRepository().getAnalysisTasks()) {			
				if(task.getScheduling().getStatus().equals(AnalysisTaskStatus.COMPLETED) && task.getScheduling().getCurrentDate().compareTo(day)< 0) {
					task.getScheduling().setStatus(AnalysisTaskStatus.PENDING_EXECUTION.name());	
					this.schedulingService.getRepository().sync();
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
