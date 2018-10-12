package org.eclipse.scava.platform;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.scava.platform.analysis.AnalysisSchedulingService;
import org.eclipse.scava.platform.analysis.AnalysisTaskService;
import org.eclipse.scava.platform.analysis.MetricProviderService;
import org.eclipse.scava.platform.analysis.WorkerService;
import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysisResportory;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.ProjectRepository;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class AnalysisRepositoryManager {
	
	private static final String ANALYSIS_SCHEDULING_DATABASE = "scava-analysis";
	private ProjectAnalysisResportory repository;

	private DB db;
	private Mongo mongo;
	
	private AnalysisSchedulingService  schedulingService;
	private AnalysisTaskService taskService;
	private MetricProviderService metricProviderService;
	private WorkerService workerService;
	
	public AnalysisRepositoryManager(Mongo mongo) {
		this.mongo = mongo;
		init();
	}
	
	protected void init() {
		this.db = mongo.getDB(ANALYSIS_SCHEDULING_DATABASE);
		repository = new ProjectAnalysisResportory(db);
		this.schedulingService = new AnalysisSchedulingService(repository);
		this.taskService = new AnalysisTaskService(repository,this.mongo);
		this.metricProviderService = new MetricProviderService(repository);
		this.workerService = new WorkerService(repository);
	}
	
	public ProjectAnalysisResportory getRepository() {
		return repository;
	}
	
	public DB getDb() {
		return db;
	}
	
	public void reset() {
		mongo.dropDatabase(ANALYSIS_SCHEDULING_DATABASE);
		init();
	}

	public AnalysisSchedulingService getSchedulingService() {
		return this.schedulingService;
	}

	public AnalysisTaskService getTaskService() {
		return this.taskService;
	}

	public MetricProviderService getMetricProviderService() {
		return this.metricProviderService;
	}

	public WorkerService getWorkerService() {
		return this.workerService;
	}
	
	
}
