/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.osgi.executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.model.Worker;
import org.eclipse.scava.platform.analysis.data.types.AnalysisExecutionMode;
import org.eclipse.scava.platform.osgi.ErrorThrowingTransientMetricProvider;
import org.eclipse.scava.platform.osgi.ManualRegistrationMetricProviderManager;
import org.eclipse.scava.platform.osgi.analysis.ProjectAnalyser;
import org.eclipse.scava.repository.model.Project;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class MetricExecutorTest {
	
	private static Mongo mongo;
	private static Platform platform;
	private static String WORKER_ID;
	private static String PROJECT_NAME = "debug-project";
	private static String TASK_LABEL = "analysis-task";
	
	@BeforeClass
	public static void setup() throws Exception {
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		
		mongo = new Mongo();
		platform = new Platform(mongo);
		
		Project project = new Project();
		project.setName(PROJECT_NAME);
		project.setShortName(PROJECT_NAME);
		String startDate = new Date().addDays(-2).toString();
		project.getExecutionInformation().setLastExecuted(startDate);
		
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();
				
		AnalysisTask task = new AnalysisTask();
		task.setLabel(TASK_LABEL);
		TASK_LABEL = task.getLabel();
		task.setAnalysisTaskId(PROJECT_NAME + TASK_LABEL);
		task.setType(AnalysisExecutionMode.SINGLE_EXECUTION.name());
		task.setStartDate(new java.util.Date("2010/01/01"));
		task.setEndDate(new java.util.Date("2010/12/01"));
		task.getScheduling().setCurrentDate(task.getStartDate());
		
		List<String> metricsProviders = new ArrayList<String>();
		metricsProviders.add("org.eclipse.scava.metricprovider.trans.commits.CommitsTransientMetricProvider");

		platform.getAnalysisRepositoryManager().getTaskService().createAnalysisTask(PROJECT_NAME, task, metricsProviders);

		// Register Worker
		WORKER_ID = Configuration.getInstance().getSlaveIdentifier();
		platform.getAnalysisRepositoryManager().getWorkerService().registerWorker(WORKER_ID);
	}
	
	@AfterClass
	public static void closedown() {
		Project project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByName(PROJECT_NAME);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().remove(project);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();
		
		AnalysisTask task = platform.getAnalysisRepositoryManager().getRepository().getAnalysisTasks().findOneByAnalysisTaskId(PROJECT_NAME + TASK_LABEL);
		platform.getAnalysisRepositoryManager().getTaskService().deleteAnalysisTask(task.getAnalysisTaskId());
		
		List<Worker> workers = platform.getAnalysisRepositoryManager().getWorkerService().getWorkers();
		for (Worker worker : workers) {
			if (worker.getWorkerId().equals(WORKER_ID)) {
				platform.getAnalysisRepositoryManager().getRepository().getWorkers().remove(worker);
				platform.getAnalysisRepositoryManager().getRepository().getWorkers().sync();
				break;
			}
		}
		
		mongo.close();
	}
	
	@Test
	public void testCurrentDate() throws Exception {
		ManualRegistrationMetricProviderManager metricProviderManager = new ManualRegistrationMetricProviderManager();
		metricProviderManager.addMetricProvider(new ErrorThrowingTransientMetricProvider());
		platform.setMetricProviderManager(metricProviderManager);
		
		assertEquals(1,platform.getMetricProviderManager().getMetricProviders().size());
		
		Project project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByName(PROJECT_NAME);
		String analysisTaskId =  platform.getAnalysisRepositoryManager().getRepository().getAnalysisTasks().findOneByAnalysisTaskId(PROJECT_NAME + TASK_LABEL).getAnalysisTaskId();
		if (analysisTaskId != null) {
			platform.getAnalysisRepositoryManager().getWorkerService().assignTask(analysisTaskId,WORKER_ID);
			ProjectAnalyser projectAnalyser = new ProjectAnalyser(platform);
			projectAnalyser.executeAnalyse(analysisTaskId,WORKER_ID);
			platform.getAnalysisRepositoryManager().getWorkerService().completeTask(WORKER_ID);
		}
		
		assertTrue(!project.getExecutionInformation().getInErrorState());
		
		AnalysisTask task = platform.getAnalysisRepositoryManager().getRepository().getAnalysisTasks().findOneByAnalysisTaskId(PROJECT_NAME + TASK_LABEL);
		assertEquals(task.getEndDate(), task.getScheduling().getCurrentDate());
	}
	
	
}
