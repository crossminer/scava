package org.eclipse.scava.platform.analysis;

import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.model.MetricExecution;
import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysisRepository;
import org.eclipse.scava.platform.analysis.data.model.Worker;
import org.eclipse.scava.platform.analysis.data.types.AnalysisExecutionMode;
import org.eclipse.scava.platform.analysis.data.types.AnalysisTaskStatus;
import org.eclipse.scava.platform.logging.OssmeterLogger;

public class AnalysisSchedulingService {
	
	private static final long MILISECOND_IN_DAY = 86400000;
	protected Logger loggerOssmeter;
	private ProjectAnalysisRepository repository;
	
	public AnalysisSchedulingService(ProjectAnalysisRepository repository) {
		this.repository = repository;
		this.loggerOssmeter = (OssmeterLogger) OssmeterLogger.getLogger("AnalysisSchedulingService");
	}
	
	
	public String getOlderPendingAnalysiTask() {
		//loggerAnalysisProcess.info("Getting older pending AnalysisTask ");
		AnalysisTask older = null;

		for (AnalysisTask task : this.repository.getAnalysisTasks()) {
			if (task.getScheduling().getStatus().equals(AnalysisTaskStatus.PENDING_EXECUTION.name())) {
				if (older == null || older.getScheduling().getExecutionRequestDate().compareTo(task.getScheduling().getExecutionRequestDate()) > 0) {
					older = task;
				}
			}
		}
		if (older != null) {
			//loggerAnalysisProcess.info("Getting older pending AnalysisTask '" + older.getAnalysisTaskId() + "' done.");
			return older.getAnalysisTaskId();
		}
		return null;
	}



	public void startMetricExecution(String analysisTaskId, String metricId) {
		loggerOssmeter.info("Starting MetricExecution '" + metricId + "'");
		AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);
		task.getScheduling().setExecutionRequestDate(new java.util.Date());
		task.getScheduling().setCurrentMetric(metricId);
		
		// Update Worker Heartbeat
		Worker worker = this.repository.getWorkers().findOneByWorkerId(task.getScheduling().getWorkerId());
		if(worker != null) {
			worker.setHeartbeat(new Date());
			this.repository.sync();
			loggerOssmeter.info("Update the worker '" + worker.getWorkerId() + "' heartBeat " + worker.getHeartbeat() + "'");
		}
		this.repository.sync();
		loggerOssmeter.info("Starting MetricExecution '" + metricId + "' is done.");
	}

	public void endMetricExecution(String projectId, String analysisTaskId, String metricId) {
		loggerOssmeter.info("Ending MetricExecution '" + metricId + "'");
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
		if (task.getType().equals(AnalysisExecutionMode.CONTINUOUS_MONITORING.name())) {
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
		loggerOssmeter.info("Ending MetricExecution '" + metricId + "' is done.");
	}

	public void newDailyTaskExecution(String analysisTaskId, Date date) {
		loggerOssmeter.info("Starting new daily execution AnalysisTask '" + analysisTaskId + "'");
		AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);
		task.getScheduling().setExecutionRequestDate(new java.util.Date());
		task.getScheduling().setCurrentDate(date);

		// Calculate Progress
		double dailyMetrics = task.getMetricExecutions().size();
		double totalDays = 0;
		double currentDay = 0;
		if (task.getType().equals(AnalysisExecutionMode.CONTINUOUS_MONITORING.name())) {
			totalDays = (new Date().getTime() - task.getStartDate().getTime()) / MILISECOND_IN_DAY;
			currentDay = totalDays - ((new Date().getTime() - task.getScheduling().getCurrentDate().getTime()) / MILISECOND_IN_DAY);
		} else {
			totalDays = (task.getEndDate().getTime() - task.getStartDate().getTime()) / MILISECOND_IN_DAY;
			currentDay = totalDays - ((task.getEndDate().getTime() - task.getScheduling().getCurrentDate().getTime()) / MILISECOND_IN_DAY);
		}
		Double progress = (dailyMetrics * currentDay) / (dailyMetrics * totalDays) * 100;
		task.getScheduling().setProgress(progress.longValue());

		this.repository.sync();
		loggerOssmeter.info("Starting new daily execution AnalysisTask '" + analysisTaskId + "' is done.");
	}

	public MetricExecution findMetricExecution(String projectId, String metricProviderId) {
		loggerOssmeter.info("Starting find out MetricExecution '" + metricProviderId +"' on Project '" + projectId + "'");
		Iterable<MetricExecution> providers = this.repository.getMetricExecutions()
				.find(MetricExecution.PROJECTID.eq(projectId), MetricExecution.METRICPROVIDERID.eq(metricProviderId));
		if (providers.iterator().hasNext()) {
			return providers.iterator().next();
		}
		loggerOssmeter.info("Find MetricExecution '" + metricProviderId +"' on Project '" + projectId + "' is done.");
		return null;
	}

}
