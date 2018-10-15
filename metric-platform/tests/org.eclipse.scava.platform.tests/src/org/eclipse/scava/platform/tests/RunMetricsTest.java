package org.eclipse.scava.platform.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.net.UnknownHostException;
import java.text.ParseException;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.osgi.executors.ProjectExecutor;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.vcs.git.GitRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class RunMetricsTest {
	Mongo mongo;
	Platform platform;

	@Before
	public void setUp() throws UnknownHostException {
		mongo = new Mongo();
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		platform = new Platform(mongo);
	}

	/**
	 * Analyzes the 4 first days of Pongo
	 */
	@Test
	public void testAnalyzePongo() throws ParseException {
		Project project = createGitProject("pongotest", "https://github.com/kolovos/pongo");

		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();

		ProjectExecutor executor = new ProjectExecutor(platform, project);
		Date dStart = executor.getLastExecutedDate();
		Date dEnd = (new Date(dStart.toString())).addDays(5);
		executor.executeProject(dStart, dEnd);

		// Just checking that nothing crashes for now
		assertFalse(project.getExecutionInformation().getInErrorState());
		assertEquals(38, mongo.getDB("pongotest").getCollection("trans.rascal.LOC.genericLOC").count());
	}

	/**
	 * Analyzes the 4 first days of Vallang
	 */
	@Test
	public void testAnalyzeVallang() throws ParseException {
		Project project = createGitProject("vallangtest", "https://github.com/usethesource/vallang");

		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();

		ProjectExecutor executor = new ProjectExecutor(platform, project);
		Date dStart = executor.getLastExecutedDate();
		Date dEnd = (new Date(dStart.toString())).addDays(5);
		executor.executeProject(dStart, dEnd);

		// Just checking that nothing crashes for now
		assertFalse(project.getExecutionInformation().getInErrorState());
		assertEquals("Jurgen J. Vinju", mongo.getDB("vallangtest").getCollection("committers").findOne().get("name"));
	}

	@After
	public void tearDown() {
		mongo.dropDatabase("pongotest");
		mongo.dropDatabase("vallangtest");
		mongo.close();
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

//package org.eclipse.scava.platform.tests;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//
//import java.net.UnknownHostException;
//import java.text.ParseException;
//
//import org.eclipse.scava.platform.Configuration;
//import org.eclipse.scava.platform.Platform;
//import org.eclipse.scava.platform.analysis.data.model.Worker;
//import org.eclipse.scava.platform.osgi.analysis.ProjectAnalyser;
//import org.eclipse.scava.repository.model.Project;
//import org.eclipse.scava.repository.model.vcs.git.GitRepository;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.googlecode.pongo.runtime.PongoFactory;
//import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
//import com.mongodb.Mongo;
//
//public class RunMetricsTest {
//	Mongo mongo;
//	Platform platform;
//	
//	private static String WORKER_ID;
//
//	@Before
//	public void setUp() throws UnknownHostException {
//		mongo = new Mongo();
//		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
//		platform = new Platform(mongo);
//		
//		WORKER_ID = Configuration.getInstance().getSlaveIdentifier();
//		// Register Worker
//		platform.getAnalysisRepositoryManager().getWorkerService().registerWorker(WORKER_ID);
//	}
//
//	/**
//	 * Analyzes the 4 first days of Pongo
//	 */
//	@Test
//	public void testAnalyzePongo() throws ParseException {
//		Project project = createGitProject("pongotest", "https://github.com/kolovos/pongo");
//
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();
//
////		ProjectExecutor executor = new ProjectExecutor(platform, project);
////		Date dStart = executor.getLastExecutedDate();
////		Date dEnd = (new Date(dStart.toString())).addDays(5);
////		executor.executeProject(dStart, dEnd);
//		
//
//		String analysisTaskId = platform.getAnalysisRepositoryManager().getSchedulingService().getOlderPendingAnalysiTask();
//		if (analysisTaskId != null) {
//			platform.getAnalysisRepositoryManager().getWorkerService().assignTask(analysisTaskId,WORKER_ID);
//			ProjectAnalyser projectAnalyser = new ProjectAnalyser(platform);
//			projectAnalyser.executeAnalyse(analysisTaskId,WORKER_ID);	
//			platform.getAnalysisRepositoryManager().getWorkerService().completeTask(WORKER_ID);
//
//		} else {				
//			Worker worker = platform.getAnalysisRepositoryManager().getRepository().getWorkers().findOneByWorkerId(WORKER_ID);
//			worker.setHeartbeat(new java.util.Date());
//			platform.getAnalysisRepositoryManager().getRepository().sync();	
//		}
//
//		// Just checking that nothing crashes for now
//		assertFalse(project.getExecutionInformation().getInErrorState());
//		assertEquals(38, mongo.getDB("pongotest").getCollection("trans.rascal.LOC.genericLOC").count());
//	}
//
//	/**
//	 * Analyzes the 4 first days of Vallang
//	 */
//	@Test
//	public void testAnalyzeVallang() throws ParseException {
//		Project project = createGitProject("vallangtest", "https://github.com/usethesource/vallang");
//
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();
//
////		ProjectExecutor executor = new ProjectExecutor(platform, project);
////		Date dStart = executor.getLastExecutedDate();
////		Date dEnd = (new Date(dStart.toString())).addDays(5);
////		executor.executeProject(dStart, dEnd);
//		
//		String analysisTaskId = platform.getAnalysisRepositoryManager().getSchedulingService().getOlderPendingAnalysiTask();
//		if (analysisTaskId != null) {
//			platform.getAnalysisRepositoryManager().getWorkerService().assignTask(analysisTaskId,WORKER_ID);
//			ProjectAnalyser projectAnalyser = new ProjectAnalyser(platform);
//			projectAnalyser.executeAnalyse(analysisTaskId,WORKER_ID);	
//			platform.getAnalysisRepositoryManager().getWorkerService().completeTask(WORKER_ID);
//
//		} else {				
//			Worker worker = platform.getAnalysisRepositoryManager().getRepository().getWorkers().findOneByWorkerId(WORKER_ID);
//			worker.setHeartbeat(new java.util.Date());
//			platform.getAnalysisRepositoryManager().getRepository().sync();	
//		}
//
//		// Just checking that nothing crashes for now
//		assertFalse(project.getExecutionInformation().getInErrorState());
//		assertEquals("Jurgen J. Vinju", mongo.getDB("vallangtest").getCollection("committers").findOne().get("name"));
//	}
//
//	@After
//	public void tearDown() {
//		mongo.dropDatabase("pongotest");
//		mongo.dropDatabase("vallangtest");
//		mongo.close();
//	}
//
//	private Project createGitProject(String name, String url) {
//		Project project = new Project();
//		project.setName(name);
//		project.setShortName(name);
//		project.setDescription(name);
//
//		GitRepository repo = new GitRepository();
//		repo.setUrl(url);
//
//		project.getVcsRepositories().add(repo);
//		return project;
//	}
//}
