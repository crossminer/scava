package org.eclipse.scava.platform.analysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysisResportory;
import org.eclipse.scava.platform.analysis.data.model.Worker;
import org.eclipse.scava.platform.analysis.data.model.WorkerCollection;
import org.eclipse.scava.platform.analysis.data.types.AnalysisTaskStatus;

public class WorkerService {
	
	private ProjectAnalysisResportory repository;

	public WorkerService(ProjectAnalysisResportory repository){
		this.repository = repository;
	}
	
	public synchronized ProjectAnalysisResportory getRepository() {	
		return this.repository;
	}
	
	public synchronized void registerWorker(String workerId) {
		Worker worker = this.getRepository().getWorkers().findOneByWorkerId(workerId);
		if(worker == null) {
			worker = new Worker();
			worker.setWorkerId(workerId);
			this.repository.getWorkers().add(worker);
			this.repository.sync();
		}
	}
	
	public synchronized void assignTask(String taskId,String workerId) {
		Worker worker = this.getRepository().getWorkers().findOneByWorkerId(workerId);
		AnalysisTask task = this.getRepository().getAnalysisTasks().findOneByAnalysisTaskId(taskId);
		
		if(worker != null && task != null) {
			task.getScheduling().setStatus(AnalysisTaskStatus.EXECUTION.name());
			task.getScheduling().setWorkerId(workerId);	
			worker.setCurrentTask(task);
			worker.setHeartbeat(new Date());
			this.repository.sync();
		}	
	}

	public synchronized void completeTask(String workerId) {
		Worker worker = this.getRepository().getWorkers().findOneByWorkerId(workerId);
		if(worker != null) {
			AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(worker.getCurrentTask().getAnalysisTaskId());
			
			if(!task.getScheduling().getStatus().equals(AnalysisTaskStatus.STOP.name())) {
				task.getScheduling().setStatus(AnalysisTaskStatus.COMPLETED.name());
				task.getScheduling().setProgress(100);
				task.getScheduling().setWorkerId(null);	
			}

			this.getRepository().getWorkers().remove(worker);
			worker = new Worker();
			worker.setWorkerId(workerId);
			this.repository.getWorkers().add(worker);
			this.repository.sync();
		}
		
	}
	
	
	public synchronized List<Worker> getWorkers(){
		List<Worker> workers = new ArrayList<>();
		
		for(Worker worker : this.getRepository().getWorkers()) {
			workers.add(worker);
		}
		return workers;
	}
	
	

}
