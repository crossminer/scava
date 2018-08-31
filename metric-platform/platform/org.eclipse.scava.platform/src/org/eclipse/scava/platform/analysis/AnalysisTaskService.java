package org.eclipse.scava.platform.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.model.DataStorage;
import org.eclipse.scava.platform.analysis.data.model.MetricExecution;
import org.eclipse.scava.platform.analysis.data.model.MetricProvider;
import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysis;
import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysisResportory;
import org.eclipse.scava.platform.analysis.data.model.Worker;
import org.eclipse.scava.platform.analysis.data.types.AnalysisTaskStatus;
import org.eclipse.scava.platform.analysis.data.types.MetricProviderKind;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class AnalysisTaskService {
	private ProjectAnalysisResportory repository;
	private Mongo mongo;

	public AnalysisTaskService(ProjectAnalysisResportory repository, Mongo mongo) {
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

	public AnalysisTask updateAnalysisTask(String oldAnalysisTaskId, AnalysisTask newTask, List<String> metricsProviders) {
		resetAnalysisTask(newTask.getAnalysisTaskId());
		AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(oldAnalysisTaskId);
		task.setAnalysisTaskId(newTask.getAnalysisTaskId());
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

		List<String> metricProviderToDelete = new ArrayList<>();
		for (MetricExecution metricProv : task.getMetricExecutions()) {
			if (metricProv != null && !metricsProviders.contains(metricProv.getMetricProviderId())) {	
				metricProviderToDelete.add(metricProv.getMetricProviderId());			
			}
		}
		
		for(String metricProviderId : metricProviderToDelete) {
			Iterable<MetricExecution> providers = this.repository.getMetricExecutions().find(
					MetricExecution.PROJECTID.eq(task.getProject().getProjectId()),
					MetricExecution.METRICPROVIDERID.eq(metricProviderId));
			if(providers.iterator().hasNext()) {
				MetricExecution toDelete = providers.iterator().next();
				task.getMetricExecutions().remove(toDelete);
				this.repository.getMetricExecutions().remove(toDelete);			
			}
		}
		
		// Update Project Reference
		ProjectAnalysis project = this.repository.getProjects().findOneByProjectId(task.getProject().getProjectId());
		
		//project.getAnalysisTasks().add(task);
		//for(AnalysisTaskStatus taskRef :  ) {
			
		this.repository.sync();

		return task;
	}
	public AnalysisTask deleteAnalysisTask(String analysisTaskId) {
		AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);
		if (task != null) {
			for (MetricExecution metricProvider : task.getMetricExecutions()) {
				this.repository.getMetricExecutions().remove(metricProvider);
			}
			ProjectAnalysis project = this.repository.getProjects().findOneByProjectId(task.getProject().getProjectId());
			project.getAnalysisTasks().remove(task);
			this.repository.getAnalysisTasks().remove(task);
			this.repository.sync();
		}
		return task;
	}

	public List<AnalysisTask> getAnalysisTasksByProject(String projectId) {
		List<AnalysisTask> tasks = new ArrayList<>();
		for (ProjectAnalysis project : this.repository.getProjects().findByProjectId(projectId)) {
			for (AnalysisTask taskRef : project.getAnalysisTasks()) {
				tasks.add(this.repository.getAnalysisTasks().findById(taskRef.getId()).iterator().next());
			}
			return tasks;
		}
		return new ArrayList<>();
	}
	
	public AnalysisTask getTaskByAnalysisTaskId(String analysisTaskId) {
		AnalysisTask analysisTask = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);
		return analysisTask;
	}

	public List<AnalysisTask> getAnalysisTasks() {
		List<AnalysisTask> tasks = new ArrayList<>();
		for (AnalysisTask task : this.repository.getAnalysisTasks()) {
			tasks.add(task);
		}

		Collections.sort(tasks, new Comparator<AnalysisTask>() {
			@Override
			public int compare(AnalysisTask lhs, AnalysisTask rhs) {
				Date data1 = lhs.getScheduling().getExecutionRequestDate();
				Date data2 = rhs.getScheduling().getExecutionRequestDate();
				if(data1 != null && data2 != null) {
					return data1.compareTo(data2);
				}
				return 0;
			}
		});
		return tasks;
	}

	public AnalysisTask promoteTask(String analysisTaskId) {
		AnalysisTask currentTask = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);
		AnalysisTask predecessor = null;
		if (currentTask != null) {
			for (AnalysisTask task : this.repository.getAnalysisTasks()) {
				if (task.getScheduling().getExecutionRequestDate().getTime() <= currentTask.getScheduling()
						.getExecutionRequestDate().getTime()) {
					if (predecessor == null || predecessor.getScheduling().getExecutionRequestDate().getTime() > task
							.getScheduling().getExecutionRequestDate().getTime()) {
						predecessor = task;
					}
				}
			}
		}
		if (predecessor != null) {
			System.out.println(predecessor.getAnalysisTaskId());
			currentTask.getScheduling().setExecutionRequestDate(
					new Date(predecessor.getScheduling().getExecutionRequestDate().getTime() - 1000));
			this.repository.sync();
		}

		return currentTask;
	}

	public AnalysisTask demoteTask(String analysisTaskId) {
		AnalysisTask currentTask = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);
		AnalysisTask successor = null;
		if (currentTask != null) {
			for (AnalysisTask task : this.repository.getAnalysisTasks()) {
				System.out.println(task.getAnalysisTaskId());
				if (task.getScheduling().getExecutionRequestDate().getTime() >= currentTask.getScheduling()
						.getExecutionRequestDate().getTime()) {
					if (successor == null || successor.getScheduling().getExecutionRequestDate().getTime() < task
							.getScheduling().getExecutionRequestDate().getTime()) {
						successor = task;
					}
				}
			}
		}
		if (successor != null) {
			currentTask.getScheduling().setExecutionRequestDate(
					new Date(successor.getScheduling().getExecutionRequestDate().getTime() + 1000));
			this.repository.sync();
		}
		return currentTask;
	}

	public AnalysisTask executeTaskOnWorker(String analysisTaskId, String workerId) {
		Worker worker = this.repository.getWorkers().findOneByWorkerId(workerId);
		AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);
		if (worker != null && task != null && worker.getCurrentTask() != null) {

			worker.getCurrentTask().getScheduling().setStatus(AnalysisTaskStatus.PENDING_STOP.name());
			task.getScheduling().setExecutionRequestDate(
					new Date(worker.getCurrentTask().getScheduling().getExecutionRequestDate().getTime() + 1));

			this.repository.sync();
		}
		return task;
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

	public AnalysisTask stopAnalysisTask(String analysisTaskId) {
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
