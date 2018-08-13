package org.eclipse.scava.platform.osgi.services;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.scava.platform.analysis.data.AnalysisTaskScheduling;
import org.eclipse.scava.platform.analysis.data.IAnalysisSchedulingService;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.types.AnalysisTaskStatus;

import com.mongodb.Mongo;

public class TaskCheckExecutor implements Runnable {

    private static final Integer cycle = 10000;
    private static final Integer heartbet = 1800000;

	private IAnalysisSchedulingService taskRepository;
	private Boolean executeTasks;

	public TaskCheckExecutor(Mongo mongo) {
		this.taskRepository = new AnalysisTaskScheduling(mongo);
		this.executeTasks = true;
	}

	@Override
	public void run() {
		while (executeTasks) {			
			Date day = dateToDay(new Date());
			boolean sync = false;
			for(AnalysisTask task : this.taskRepository.getRepository().getAnalysisTasks()) {		
				// Detect Worker Failure / Replace Task in execution pending list
				if((task.getScheduling().getStatus().equals(AnalysisTaskStatus.EXECUTION) || task.getScheduling().getStatus().equals(AnalysisTaskStatus.PENDING_STOP))  && new Date().getTime() - task.getScheduling().getHeartbeat().getTime() > heartbet) {
					task.getScheduling().setStatus(AnalysisTaskStatus.PENDING_EXECUTION.name());
					task.getScheduling().setWorkerId(null);
					sync = true;
				}
				
				// Detect New Daily Execution
				if(task.getScheduling().getStatus().equals(AnalysisTaskStatus.COMPLETED) && task.getScheduling().getCurrentDate().compareTo(day)< 0) {
					task.getScheduling().setStatus(AnalysisTaskStatus.PENDING_EXECUTION.name());	
					sync = true;
				}
			}
			
			if(sync) {
				this.taskRepository.getRepository().sync();
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
