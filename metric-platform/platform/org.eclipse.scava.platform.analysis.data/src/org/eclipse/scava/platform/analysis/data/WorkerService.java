package org.eclipse.scava.platform.analysis.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysisResportory;
import org.eclipse.scava.platform.analysis.data.model.Worker;
import org.eclipse.scava.platform.analysis.data.types.AnalysisTaskStatus;

import com.mongodb.BasicDBObject;
import com.mongodb.Mongo;

public class WorkerService {
	
	private static final String ANALYSIS_TASK_DB = "scava-scheduling";

	private ProjectAnalysisResportory repository;

	public WorkerService(Mongo mongo) {
		this.repository = new ProjectAnalysisResportory(mongo.getDB(ANALYSIS_TASK_DB));
	}
	
	public ProjectAnalysisResportory getRepository() {
		
		return this.repository;
	}
	
	public void registerWorker(String workerId) {
		Worker worker = this.getRepository().getWorkers().findOneByWorkerId(workerId);
		if(worker == null) {
			worker = new Worker();
			worker.setWorkerId(workerId);
			this.repository.getWorkers().add(worker);
			this.repository.sync();
		}
	}
	
	public void assignTask(String taskId,String workerId) {
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

	public void completeTask(String workerId) {
		Worker worker = this.getRepository().getWorkers().findOneByWorkerId(workerId);
		if(worker != null) {
			AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(worker.getCurrentTask().getAnalysisTaskId());
			
			task.getScheduling().setStatus(AnalysisTaskStatus.COMPLETED.name());
			task.getScheduling().setWorkerId(null);	

			this.getRepository().getWorkers().remove(worker);
			worker = new Worker();
			worker.setWorkerId(workerId);
			this.repository.getWorkers().add(worker);
			this.repository.sync();
		}
		
	}
	
	
	public List<Worker> getWorkers(){
		List<Worker> workers = new ArrayList<>();
		for(Worker worker : this.getRepository().getWorkers()) {
			workers.add(worker);
		}
		return workers;
	}
	
	

}
