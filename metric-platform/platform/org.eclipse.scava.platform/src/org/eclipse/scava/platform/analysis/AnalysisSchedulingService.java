package org.eclipse.scava.platform.analysis;

import java.util.Date;

import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.model.MetricExecution;
import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysisResportory;
import org.eclipse.scava.platform.analysis.data.model.Worker;
import org.eclipse.scava.platform.analysis.data.types.AnalysisExecutionMode;
import org.eclipse.scava.platform.analysis.data.types.AnalysisTaskStatus;

public class AnalysisSchedulingService {
	

	private static final long MILISECOND_IN_DAY = 86400000;

	private ProjectAnalysisResportory repository;

	public AnalysisSchedulingService(ProjectAnalysisResportory repository){
		this.repository = repository;
	}
	
	
	public String getOlderPendingAnalysiTask() {
		AnalysisTask older = null;

		for (AnalysisTask task : this.repository.getAnalysisTasks()) {
			if (task.getScheduling().getStatus().equals(AnalysisTaskStatus.PENDING_EXECUTION.name())) {
				if (older == null || older.getScheduling().getExecutionRequestDate().compareTo(task.getScheduling().getExecutionRequestDate()) > 0) {
					older = task;
				}
			}
		}
		if (older != null) {
			return older.getAnalysisTaskId();
		}
		return null;
	}



	public void startMetricExecution(String analysisTaskId, String metricId) {
		AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);
		task.getScheduling().setExecutionRequestDate(new java.util.Date());
		task.getScheduling().setCurrentMetric(metricId);
		
		
		// Update Worker Heartbeat
		Worker worker = this.repository.getWorkers().findOneByWorkerId(task.getScheduling().getWorkerId());
		if(worker != null) {
			worker.setHeartbeat(new Date());
			this.repository.sync();
		}
		
		this.repository.sync();
	}

	public void endMetricExecution(String projectId, String analysisTaskId, String metricId) {
		AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);
		Iterable<MetricExecution> providers = this.repository.getMetricExecutions()
				.find(MetricExecution.PROJECTID.eq(projectId), MetricExecution.METRICPROVIDERID.eq(metricId));

		if (providers.iterator().hasNext()) {
			providers.iterator().next().setLastExecutionDate(task.getScheduling().getCurrentDate());
		}

		// Calculate Progress
		double dailyMetrics = task.getMetricExecutions().size();
		double totalDays = 0;
		double currentDay = 0;
		if (task.getType().equals(AnalysisExecutionMode.DAILY_EXECUTION.name())) {
			totalDays = (new Date().getTime() - task.getStartDate().getTime()) / MILISECOND_IN_DAY;
			currentDay = totalDays - ((new Date().getTime() - task.getScheduling().getCurrentDate().getTime()) / MILISECOND_IN_DAY);
		} else {
			totalDays = (task.getEndDate().getTime() - task.getStartDate().getTime()) / MILISECOND_IN_DAY;
			currentDay = totalDays - ((task.getEndDate().getTime() - task.getScheduling().getCurrentDate().getTime()) / MILISECOND_IN_DAY);
		}
		float currentMetrics = 0;
		for (MetricExecution provider : task.getMetricExecutions()) {
			if (provider.getLastExecutionDate().getTime() == task.getScheduling().getCurrentDate().getTime()) {
				currentMetrics++;
			}
		}

		Double progress = ((dailyMetrics * (currentDay - 1)) + currentMetrics) / (dailyMetrics * totalDays) * 100;
		task.getScheduling().setProgress(progress.intValue());

		this.repository.sync();
	}

	public void newDailyTaskExecution(String analysisTaskId, Date date) {
		AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);
		task.getScheduling().setExecutionRequestDate(new java.util.Date());
		task.getScheduling().setCurrentDate(date);

		// Calculate Progress
		double dailyMetrics = task.getMetricExecutions().size();
		double totalDays = 0;
		double currentDay = 0;
		if (task.getType().equals(AnalysisExecutionMode.DAILY_EXECUTION.name())) {
			totalDays = (new Date().getTime() - task.getStartDate().getTime()) / MILISECOND_IN_DAY;
			currentDay = totalDays - ((new Date().getTime() - task.getScheduling().getCurrentDate().getTime()) / MILISECOND_IN_DAY);
		} else {
			totalDays = (task.getEndDate().getTime() - task.getStartDate().getTime()) / MILISECOND_IN_DAY;
			currentDay = totalDays - ((task.getEndDate().getTime() - task.getScheduling().getCurrentDate().getTime()) / MILISECOND_IN_DAY);
		}
		Double progress = (dailyMetrics * currentDay) / (dailyMetrics * totalDays) * 100;
		task.getScheduling().setProgress(progress.longValue());

		this.repository.sync();
	}

	public MetricExecution findMetricExecution(String projectId, String metricProviderId) {
		Iterable<MetricExecution> providers = this.repository.getMetricExecutions()
				.find(MetricExecution.PROJECTID.eq(projectId), MetricExecution.METRICPROVIDERID.eq(metricProviderId));
		if (providers.iterator().hasNext()) {
			return providers.iterator().next();
		}

		return null;
	}

}
