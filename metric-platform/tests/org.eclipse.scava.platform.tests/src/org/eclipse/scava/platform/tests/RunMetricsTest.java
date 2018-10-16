package org.eclipse.scava.platform.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.model.Worker;
import org.eclipse.scava.platform.analysis.data.types.AnalysisExecutionMode;
import org.eclipse.scava.platform.osgi.analysis.ProjectAnalyser;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.vcs.git.GitRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RunMetricsTest {
	Mongo mongo;
	Platform platform;
	
	private static String WORKER_ID;
	
	@Rule 
	public TestName name = new TestName();
	
	@Before
	public void setUp() throws UnknownHostException {
		mongo = new Mongo();
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		platform = new Platform(mongo);
		
		WORKER_ID = Configuration.getInstance().getSlaveIdentifier();
		// Register Worker
		platform.getAnalysisRepositoryManager().getWorkerService().registerWorker(WORKER_ID);
	}

	/**
	 * Analyzes the 5 first days of Pongo
	 */
	@Test
	public void testAnalyzePongo() throws ParseException {
		Project project = createGitProject("pongotest", "https://github.com/kolovos/pongo");

		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();
				
		AnalysisTask task = new AnalysisTask();
		task.setLabel("analysis-task");
		task.setAnalysisTaskId("pongotest" + "analysis-task");
		task.setType(AnalysisExecutionMode.SINGLE_EXECUTION.name());
		task.setStartDate(new java.util.Date("2013/01/31"));
		task.setEndDate(new java.util.Date("2013/02/04"));
		task.getScheduling().setCurrentDate(task.getStartDate());
		
		List<String> metricsProviders = new ArrayList<String>();
		metricsProviders.add("org.eclipse.scava.metricprovider.trans.commits.CommitsTransientMetricProvider");

		platform.getAnalysisRepositoryManager().getTaskService().createAnalysisTask("pongotest", task, metricsProviders);

		String analysisTaskId =  platform.getAnalysisRepositoryManager().getRepository().getAnalysisTasks().findOneByAnalysisTaskId("pongotest" + "analysis-task").getAnalysisTaskId();
		if (analysisTaskId != null) {
			platform.getAnalysisRepositoryManager().getWorkerService().assignTask(analysisTaskId,WORKER_ID);
			ProjectAnalyser projectAnalyser = new ProjectAnalyser(platform);
			projectAnalyser.executeAnalyse(analysisTaskId,WORKER_ID);	
			platform.getAnalysisRepositoryManager().getWorkerService().completeTask(WORKER_ID);
		}

		// Just checking that nothing crashes for now
		assertFalse(project.getExecutionInformation().getInErrorState());
		assertEquals(29, mongo.getDB("pongotest").getCollection("Commits.repositories").findOne().get("totalCommits"));
	}

	/**
	 * Analyzes the 5 first days of Vallang
	 */
	@Test
	public void testAnalyzeVallang() throws ParseException {
		Project project = createGitProject("vallangtest", "https://github.com/usethesource/vallang");

		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();
		
		AnalysisTask task = new AnalysisTask();
		task.setLabel("analysis-task");
		task.setAnalysisTaskId("vallangtest" + "analysis-task");
		task.setType(AnalysisExecutionMode.SINGLE_EXECUTION.name());
		task.setStartDate(new java.util.Date("2015/10/16"));
		task.setEndDate(new java.util.Date("2015/10/20"));
		task.getScheduling().setCurrentDate(task.getStartDate());
		
		List<String> metricsProviders = new ArrayList<String>();
		metricsProviders.add("org.eclipse.scava.metricprovider.trans.committers.CommittersMetricProvider");

		platform.getAnalysisRepositoryManager().getTaskService().createAnalysisTask("vallangtest", task, metricsProviders);
		
		String analysisTaskId =  platform.getAnalysisRepositoryManager().getRepository().getAnalysisTasks().findOneByAnalysisTaskId("vallangtest" + "analysis-task").getAnalysisTaskId();
		if (analysisTaskId != null) {
			platform.getAnalysisRepositoryManager().getWorkerService().assignTask(analysisTaskId,WORKER_ID);
			ProjectAnalyser projectAnalyser = new ProjectAnalyser(platform);
			projectAnalyser.executeAnalyse(analysisTaskId,WORKER_ID);	
			platform.getAnalysisRepositoryManager().getWorkerService().completeTask(WORKER_ID);
		}

		// Just checking that nothing crashes for now
		assertFalse(project.getExecutionInformation().getInErrorState());
		assertEquals("Michael Steindorfer", mongo.getDB("vallangtest").getCollection("committers").findOne().get("name"));
	}

	@After
	public void tearDown() {
		if (name.getMethodName().equals("testAnalyzePongo")) {
			Project pongoProject = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByName("pongotest");
			platform.getProjectRepositoryManager().getProjectRepository().getProjects().remove(pongoProject);
			platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();
			AnalysisTask pongoTask = platform.getAnalysisRepositoryManager().getRepository().getAnalysisTasks().findOneByAnalysisTaskId("pongotest" + "analysis-task");
			platform.getAnalysisRepositoryManager().getTaskService().deleteAnalysisTask(pongoTask.getAnalysisTaskId());
			mongo.dropDatabase("pongotest");
		} else if (name.getMethodName().equals("testAnalyzeVallang")) {
			Project vallangProject = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByName("vallangtest");
			platform.getProjectRepositoryManager().getProjectRepository().getProjects().remove(vallangProject);
			platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();
			AnalysisTask vallangTask = platform.getAnalysisRepositoryManager().getRepository().getAnalysisTasks().findOneByAnalysisTaskId("vallangtest" + "analysis-task");
			platform.getAnalysisRepositoryManager().getTaskService().deleteAnalysisTask(vallangTask.getAnalysisTaskId());
			mongo.dropDatabase("vallangtest");
			
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
	}

	private Project createGitProject(String name, String url) {
		Project project = new Project();
		project.setName(name);
		project.setShortName(name);
		project.setDescription(name);

		GitRepository repo = new GitRepository();
		repo.setUrl(url);

		project.getVcsRepositories().add(repo);
		return project;
	}
}
