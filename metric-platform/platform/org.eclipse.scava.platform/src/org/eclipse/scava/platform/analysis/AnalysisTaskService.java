package org.eclipse.scava.platform.analysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.model.DataStorage;
import org.eclipse.scava.platform.analysis.data.model.MetricExecution;
import org.eclipse.scava.platform.analysis.data.model.MetricProvider;
import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysis;
import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysisResportory;
import org.eclipse.scava.platform.analysis.data.types.AnalysisTaskStatus;
import org.eclipse.scava.platform.analysis.data.types.MetricProviderKind;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class AnalysisTaskService {
	private ProjectAnalysisResportory repository;
	private Mongo mongo;
	
	public AnalysisTaskService(ProjectAnalysisResportory repository,Mongo mongo) {
		this.repository = repository;
		this.mongo = mongo;
	}
	
	public AnalysisTask createAnalysisTask(String projectId, AnalysisTask task, List<String> metricsProviders) {
		ProjectAnalysis project = this.repository.getProjects().findOneByProjectId(projectId);

		task.getScheduling().setStatus(AnalysisTaskStatus.STOP.name());
		this.repository.getAnalysisTasks().add(task);

		if (project == null) {
			project = new ProjectAnalysis();
			project.setProjectId(projectId);
			this.repository.getProjects().add(project);
		}
		task.setProject(project);
		project.getAnalysisTasks().add(task);

		for (String metricProviderId : metricsProviders) {
			Iterable<MetricExecution> providers = this.repository.getMetricExecutions().find(
					MetricExecution.PROJECTID.eq(projectId), MetricExecution.METRICPROVIDERID.eq(metricProviderId));
			MetricExecution provider = null;
			if (providers.iterator().hasNext()) {
				provider = providers.iterator().next();

			} else {
				provider = new MetricExecution();
				provider.setProjectId(projectId);
				provider.setMetricProviderId(metricProviderId);
				provider.setLastExecutionDate(new Date(0));
				this.repository.getMetricExecutions().add(provider);
			}
			task.getMetricExecutions().add(provider);
		}

		this.repository.sync();

		return task;
	}

	
	public AnalysisTask updateAnalysisTask(AnalysisTask newTask, List<String> metricsProviders) {
		resetAnalysisTask(newTask.getAnalysisTaskId());
		AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(newTask.getAnalysisTaskId());
		task.setLabel(newTask.getLabel());
		task.setType(newTask.getType());
		task.setStartDate(newTask.getStartDate());
		task.setEndDate(newTask.getEndDate());

		// Add new MetricExecution
		for (String metricProviderId : metricsProviders) {
			Iterable<MetricExecution> providers = this.repository.getMetricExecutions().find(
					MetricExecution.PROJECTID.eq(task.getProject().getProjectId()),
					MetricExecution.METRICPROVIDERID.eq(metricProviderId));
			MetricExecution provider = null;
			if (providers.iterator().hasNext()) {
				provider = providers.iterator().next();
			} else {
				provider = new MetricExecution();
				provider.setProjectId(task.getProject().getProjectId());
				provider.setMetricProviderId(metricProviderId);
				provider.setLastExecutionDate(new Date(0));
				this.repository.getMetricExecutions().add(provider);
			}
			task.getMetricExecutions().add(provider);
		}

		// Remove deleted MetricExecution
		for (MetricExecution metricProv : new ArrayList<>(task.getMetricExecutions())) {
			if (!metricsProviders.contains(metricProv.getMetricProviderId())) {
				task.getMetricExecutions().remove(metricsProviders);
				this.repository.getMetricExecutions().remove(metricProv);
			}
		}

		this.repository.sync();

		return task;
	}

	
	public AnalysisTask deleteAnalysisTask(String analysisTaskId) {
		AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);
		if (task != null) {
			for (MetricExecution metricProvider : task.getMetricExecutions()) {
				this.repository.getMetricExecutions().remove(metricProvider);
			}
			this.repository.getAnalysisTasks().remove(task);
			this.repository.sync();
		}
		return task;
	}

	
	public List<AnalysisTask> getAnalysisTasksByProject(String projectId) {
		for (ProjectAnalysis project : this.repository.getProjects().findByProjectId(projectId)) {
			return project.getAnalysisTasks();
		}
		return new ArrayList<>();
	}

	
	public List<AnalysisTask> getAnalysisTasks() {
		List<AnalysisTask> tasks = new ArrayList<>();
		for (AnalysisTask task : this.repository.getAnalysisTasks()) {
			tasks.add(task);
		}
		return tasks;
	}
	
	
	public AnalysisTask startAnalysisTask(String analysisTaskId) {
		AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);

		if (task != null) {
			task.getScheduling().setStatus(AnalysisTaskStatus.PENDING_EXECUTION.name());
			task.getScheduling().setExecutionRequestDate(new Date());
			if (task.getScheduling().getCurrentDate() == null) {
				task.getScheduling().setCurrentDate(task.getStartDate());
			}
			this.repository.sync();
		}
		return task;
	}

	public AnalysisTask stoptAnalysisTask(String analysisTaskId) {
		AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);

		if (task != null && !task.getScheduling().getStatus().equals((AnalysisTaskStatus.STOP.name()))) {
			task.getScheduling().setStatus(AnalysisTaskStatus.PENDING_STOP.name());
			this.repository.sync();
		}
		return task;
	}

	public AnalysisTask resetAnalysisTask(String analysisTaskId) {
		AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);

		if (task != null) {
			// Clean Project DB Repository for historic metrics
			cleanMetricDatabase(task);

			task.getScheduling().setStatus(AnalysisTaskStatus.STOP.name());
			task.getScheduling().setCurrentDate(task.getStartDate());
			task.getScheduling().setProgress(0);
			task.getScheduling().setCurrentMetric(null);
			task.getScheduling().setWorkerId(null);
			this.repository.sync();
		}

		return task;
	}

	private void cleanMetricDatabase(AnalysisTask task) {
		DB projectDb = mongo.getDB(task.getProject().getProjectId());
		for (MetricExecution executionData : task.getMetricExecutions()) {
			Iterable<MetricProvider> providers = this.repository.getMetricProviders()
					.findByMetricProviderId(executionData.getMetricProviderId());
			if (providers.iterator().hasNext()) {
				MetricProvider provider = providers.iterator().next();
				if (MetricProviderKind.HISTORIC.name().equals(provider.getKind())) {
					for (DataStorage storage : provider.getStorages()) {
						projectDb.getCollection(storage.getStorage()).drop();
					}
				}
			}
		}
	}

	
}
