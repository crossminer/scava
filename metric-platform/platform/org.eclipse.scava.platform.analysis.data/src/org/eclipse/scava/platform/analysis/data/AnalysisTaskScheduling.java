package org.eclipse.scava.platform.analysis.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysis;
import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysisResportory;
import org.eclipse.scava.platform.analysis.data.model.ProjectMetricProvider;
import org.eclipse.scava.platform.analysis.data.types.AnalysisTaskStatus;

import com.mongodb.Mongo;

public class AnalysisTaskScheduling implements IAnalysisRepositoryService,IAnalysisSchedulingService {

	private static final String ANALYSIS_TASK_DB = "ScavaAnalysisScheduling";
	private static final long MILISECOND_IN_DAY = 86400000;

	private Mongo mongo;
	private ProjectAnalysisResportory repository;

	public AnalysisTaskScheduling(Mongo mongo) {
		this.repository = new ProjectAnalysisResportory(mongo.getDB(ANALYSIS_TASK_DB));
		this.mongo = mongo;
	}
	
	
	@Override
	public ProjectAnalysisResportory getRepository() {
		return this.repository;
	}

	@Override
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
		
		for(String metricProviderId : metricsProviders) {
			Iterable<ProjectMetricProvider> providers =this.repository.getMetricProviders().find(ProjectMetricProvider.PROJECTID.eq(projectId), ProjectMetricProvider.METRICPROVIDERID.eq(metricProviderId));		
			ProjectMetricProvider provider = null;
			if(providers.iterator().hasNext()) {		
				provider = providers.iterator().next();
				
			}else {
				 provider = new ProjectMetricProvider();
				 provider.setProjectId(projectId);
				 provider.setMetricProviderId(metricProviderId);
				 provider.setLastExecutionDate(new Date(0));
				 this.repository.getMetricProviders().add(provider);
			}
			task.getMetrics().add(provider);
		}
		
		this.repository.sync();
		
		return task;
	}
	
	@Override
	public AnalysisTask updateAnalysisTask(AnalysisTask newTask, List<String> metricsProviders) {
		resetAnalysisTask(newTask.getAnalysisTaskId());
		AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(newTask.getAnalysisTaskId());	
		task.setLabel(newTask.getLabel());
		task.setType(newTask.getType());
		task.setStartDate(newTask.getStartDate());
		task.setEndDate(newTask.getEndDate());
			
		// Add new ProjectMetricProvider
		for(String metricProviderId : metricsProviders) {
			Iterable<ProjectMetricProvider> providers = this.repository.getMetricProviders().find(ProjectMetricProvider.PROJECTID.eq(task.getProject().getProjectId()), ProjectMetricProvider.METRICPROVIDERID.eq(metricProviderId));		
			ProjectMetricProvider provider = null;
			if(providers.iterator().hasNext()) {		
				provider = providers.iterator().next();				
			}else {
				 provider = new ProjectMetricProvider();
				 provider.setProjectId(task.getProject().getProjectId());
				 provider.setMetricProviderId(metricProviderId);
				 provider.setLastExecutionDate(new Date(0));
				 this.repository.getMetricProviders().add(provider);
			}
			task.getMetrics().add(provider);
		}
	

		// Remove deleted ProjectMetricProvider
		for(ProjectMetricProvider metricProv :  new ArrayList<>(task.getMetrics())) {
			if(!metricsProviders.contains(metricProv.getMetricProviderId())) {
				task.getMetrics().remove(metricsProviders);
				this.repository.getMetricProviders().remove(metricProv);
			}
		}
	   
		this.repository.sync();
		
		return task;
	}
	
	@Override
	public AnalysisTask deleteAnalysisTask(String analysisTaskId) {
		AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);	
		if(task != null) {		
			for(ProjectMetricProvider metricProvider : task.getMetrics()) {
				this.repository.getMetricProviders().remove(metricProvider);
			}	
			this.repository.getAnalysisTasks().remove(task);
			this.repository.sync();
		}
		return task;
	}
	

	@Override
	public List<AnalysisTask> getAnalysisTasksByProject(String projectId) {
		for(ProjectAnalysis project : this.repository.getProjects().findByProjectId(projectId)) {
			return project.getAnalysisTasks();
		}
		return new ArrayList<>();
	}


	@Override
	public List<AnalysisTask> getAnalysisTasks() {
		List<AnalysisTask> tasks = new ArrayList<>();
		for (AnalysisTask task : this.repository.getAnalysisTasks()) {
			tasks.add(task);
		}	
		return tasks;
	}


	@Override
	public String getOlderPendingAnalysiTask() {
		AnalysisTask older = null;

		for (AnalysisTask task : this.repository.getAnalysisTasks()) {
			if (task.getScheduling().getStatus().equals(AnalysisTaskStatus.PENDING_EXECUTION.name())) {
				if (older == null
						|| older.getScheduling().getHeartbeat().compareTo(task.getScheduling().getHeartbeat()) > 0) {
					older = task;
				}
			}
		}
		if (older != null) {
			return older.getAnalysisTaskId();
		}
		return null;
	}

	@Override
	public AnalysisTask startAnalysisTask(String analysisTaskId) {
		AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);

		if (task != null) {
			task.getScheduling().setStatus(AnalysisTaskStatus.PENDING_EXECUTION.name());
			if (task.getScheduling().getCurrentDate() == null) {
				task.getScheduling().setCurrentDate(task.getStartDate());
			}
			this.repository.sync();
		}
		return task;
	}

	@Override
	public AnalysisTask stoptAnalysisTask(String analysisTaskId) {
		AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);

		if (task != null &&  ! task.getScheduling().getStatus().equals((AnalysisTaskStatus.STOP.name()))) {
			task.getScheduling().setStatus(AnalysisTaskStatus.PENDING_STOP.name());
			this.repository.sync();
		}
		return task;
	}

	@Override
	public AnalysisTask resetAnalysisTask(String analysisTaskId) {
		AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);

		if (task != null) {
			task.getScheduling().setStatus(AnalysisTaskStatus.STOP.name());
			task.getScheduling().setCurrentDate(task.getStartDate());
			task.getScheduling().setProgress(0);
			task.getScheduling().setCurrentMetric(null);
			task.getScheduling().setWorkerId(null);
			this.repository.sync();
		}
		return task;
	}


	@Override
	public void startMetricExecution(String analysisTaskId, String metricId) {
		AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);
		
		task.getScheduling().setHeartbeat(new java.util.Date());
		task.getScheduling().setCurrentMetric(metricId);
		
		this.repository.sync();
	}
	
	@Override
	public void endMetricExecution(String projectId,String analysisTaskId, String metricId) {
		AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);		
		Iterable<ProjectMetricProvider> providers =this.repository.getMetricProviders().find(ProjectMetricProvider.PROJECTID.eq(projectId), ProjectMetricProvider.METRICPROVIDERID.eq(metricId));		
	
		if(providers.iterator().hasNext()) {		
			providers.iterator().next().setLastExecutionDate(task.getScheduling().getCurrentDate());
		}
		
		// Calculate Progress
		double dailyMetrics = task.getMetrics().size();
		double totalDays = (task.getEndDate().getTime() - task.getStartDate().getTime()) / MILISECOND_IN_DAY;
		double currentDay = totalDays - (task.getEndDate().getTime() - task.getScheduling().getCurrentDate().getTime())/ MILISECOND_IN_DAY;
		float currentMetrics = 0;	
		for(ProjectMetricProvider provider : task.getMetrics()) {
			if(provider.getLastExecutionDate().getTime() == task.getScheduling().getCurrentDate().getTime()) {
				currentMetrics++;
			}
		}

		Double progress = ((dailyMetrics * (currentDay - 1)) + currentMetrics) / (dailyMetrics * totalDays) * 100;
		task.getScheduling().setProgress(progress.intValue());
		
		this.repository.sync();
	}
	

	@Override
	public void newDailyTaskExecution(String analysisTaskId, Date date) {
		AnalysisTask task = this.repository.getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);
		task.getScheduling().setHeartbeat(new java.util.Date());
		task.getScheduling().setCurrentDate(date);
		
		// Calculate Progress
		double dailyMetrics = task.getMetrics().size();
		double totalDays = (task.getEndDate().getTime() - task.getStartDate().getTime()) / MILISECOND_IN_DAY;
		double currentDay = totalDays - (task.getEndDate().getTime() - task.getScheduling().getCurrentDate().getTime())/ MILISECOND_IN_DAY;
		Double progress = (dailyMetrics * currentDay)/ (dailyMetrics * totalDays) * 100;
		task.getScheduling().setProgress(progress.intValue());
		
		this.repository.getAnalysisTasks().sync();	
	}

	@Override
	public ProjectMetricProvider findMetricProviderScheduling(String projectId, String metricProviderId) {
		Iterable<ProjectMetricProvider> providers = this.repository.getMetricProviders().find(ProjectMetricProvider.PROJECTID.eq(projectId), ProjectMetricProvider.METRICPROVIDERID.eq(metricProviderId));		
		if(providers.iterator().hasNext()) {		
			return providers.iterator().next();	
		}
		
		return null;
	}






}
