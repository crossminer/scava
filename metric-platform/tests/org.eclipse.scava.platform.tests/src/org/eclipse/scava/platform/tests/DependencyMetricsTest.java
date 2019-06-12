package org.eclipse.scava.platform.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.types.AnalysisExecutionMode;
import org.eclipse.scava.platform.osgi.analysis.ProjectAnalyser;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.vcs.git.GitRepository;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class DependencyMetricsTest {
	Mongo mongo;
	Platform platform;

	private static String WORKER_ID;

	@Before
	public void setUp() throws UnknownHostException {
		mongo = new Mongo();
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		platform = Platform.getInstance();
		platform.setMongo(mongo);
		platform.initialize();
		
		WORKER_ID = Configuration.getInstance().getSlaveIdentifier();
		// Register Worker
		platform.getAnalysisRepositoryManager().getWorkerService().registerWorker(WORKER_ID);
	}

	@Test
	public void testMavenMetrics() {
		Project project = createGitProject("maventest", "https://github.com/tdegueul/scava-test-maven-repo");

		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();

		AnalysisTask task = new AnalysisTask();
		task.setLabel("analysis-task");
		task.setAnalysisTaskId("maventest" + "analysis-task");
		task.setType(AnalysisExecutionMode.SINGLE_EXECUTION.name());
		task.setStartDate(new java.util.Date("2019/02/11"));
		task.setEndDate(new java.util.Date("2019/02/14"));
		task.getScheduling().setCurrentDate(task.getStartDate());

		List<String> metricsProviders = new ArrayList<String>();
		metricsProviders.add("trans.rascal.dependency.maven.allMavenDependencies");
		metricsProviders.add("trans.rascal.dependency.maven.ratioOptionalMavenDependencies");
		metricsProviders.add("trans.rascal.dependency.maven.numberUniqueMavenDependencies");
		metricsProviders.add("trans.rascal.dependency.maven.allOptionalMavenDependencies");
		metricsProviders.add("trans.rascal.dependency.maven.numberMavenDependencies");
		metricsProviders.add("trans.rascal.dependency.maven.allMavenDependencies");

		platform.getAnalysisRepositoryManager().getTaskService().createAnalysisTask("maventest", task, metricsProviders);

		String analysisTaskId =  platform.getAnalysisRepositoryManager().getRepository().getAnalysisTasks().findOneByAnalysisTaskId("maventest" + "analysis-task").getAnalysisTaskId();
		if (analysisTaskId != null) {
			platform.getAnalysisRepositoryManager().getWorkerService().assignTask(analysisTaskId,WORKER_ID);
			ProjectAnalyser projectAnalyser = new ProjectAnalyser(platform);
			projectAnalyser.executeAnalyse(analysisTaskId,WORKER_ID);	
			platform.getAnalysisRepositoryManager().getWorkerService().completeTask(WORKER_ID);
		}

		assertFalse(project.getExecutionInformation().getInErrorState());

		System.out.println(mongo.getDB("maventest").getCollection("trans.rascal.dependency.maven.allMavenDependencies").findOne().get("value"));
		System.out.println(mongo.getDB("maventest").getCollection("trans.rascal.dependency.maven.ratioOptionalMavenDependencies").findOne().get("value"));
		System.out.println(mongo.getDB("maventest").getCollection("trans.rascal.dependency.maven.numberUniqueMavenDependencies").findOne().get("value"));
		System.out.println(mongo.getDB("maventest").getCollection("trans.rascal.dependency.maven.allOptionalMavenDependencies").getCount());
		System.out.println(mongo.getDB("maventest").getCollection("trans.rascal.dependency.maven.numberMavenDependencies").findOne().get("value"));

		assertEquals("bundle://maven/com.google.guava/guava/27.0.1-jre", mongo.getDB("maventest").getCollection("trans.rascal.dependency.maven.allMavenDependencies").findOne().get("value"));
		assertEquals(0.0, mongo.getDB("maventest").getCollection("trans.rascal.dependency.maven.ratioOptionalMavenDependencies").findOne().get("value"));
		assertEquals(1L, mongo.getDB("maventest").getCollection("trans.rascal.dependency.maven.numberUniqueMavenDependencies").findOne().get("value"));
		assertEquals(0, mongo.getDB("maventest").getCollection("trans.rascal.dependency.maven.allOptionalMavenDependencies").getCount());
		assertEquals(1L, mongo.getDB("maventest").getCollection("trans.rascal.dependency.maven.numberMavenDependencies").findOne().get("value"));
	}
	
	@Test
	public void testOSGiMetrics() {
		Project project = createGitProject("osgitest", "https://github.com/tdegueul/scava-test-osgi-repo");

		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();

		AnalysisTask task = new AnalysisTask();
		task.setLabel("analysis-task");
		task.setAnalysisTaskId("osgitest" + "analysis-task");
		task.setType(AnalysisExecutionMode.SINGLE_EXECUTION.name());
		task.setStartDate(new java.util.Date("2019/02/11"));
		task.setEndDate(new java.util.Date("2019/02/14"));
		task.getScheduling().setCurrentDate(task.getStartDate());

		List<String> metricsProviders = new ArrayList<String>();
		metricsProviders.add("trans.rascal.dependency.osgi.allOSGiPackageDependencies");
		metricsProviders.add("trans.rascal.dependency.osgi.unversionedOSGiRequiredBundles");
		metricsProviders.add("trans.rascal.dependency.osgi.unusedOSGiImportedPackages");
		metricsProviders.add("trans.rascal.dependency.osgi.numberOSGiSplitImportedPackages");
		metricsProviders.add("trans.rascal.dependency.osgi.ratioUnusedOSGiImportedPackages");
		metricsProviders.add("trans.rascal.dependency.osgi.allOSGiBundleDependencies");
		metricsProviders.add("trans.rascal.dependency.osgi.unversionedOSGiExportedPackages");
		metricsProviders.add("trans.rascal.dependency.osgi.numberOSGiSplitExportedPackages");
		metricsProviders.add("trans.rascal.dependency.osgi.allOSGiDynamicImportedPackages");
		metricsProviders.add("trans.rascal.dependency.osgi.numberOSGiBundleDependencies");
		metricsProviders.add("trans.rascal.dependency.osgi.ratioUnversionedOSGiImportedPackages");
		metricsProviders.add("trans.rascal.dependency.osgi.unversionedOSGiImportedPackages");
		metricsProviders.add("trans.rascal.dependency.osgi.numberOSGiPackageDependencies");
		metricsProviders.add("trans.rascal.dependency.osgi.ratioUnversionedOSGiRequiredBundles");
		metricsProviders.add("trans.rascal.dependency.osgi.usedOSGiUnimportedPackages");
		metricsProviders.add("trans.rascal.dependency.osgi.ratioUnversionedOSGiExportedPackages");
		metricsProviders.add("trans.rascal.dependency.osgi.ratioUsedOSGiImportedPackages");

		platform.getAnalysisRepositoryManager().getTaskService().createAnalysisTask("osgitest", task, metricsProviders);

		String analysisTaskId =  platform.getAnalysisRepositoryManager().getRepository().getAnalysisTasks().findOneByAnalysisTaskId("osgitest" + "analysis-task").getAnalysisTaskId();
		if (analysisTaskId != null) {
			platform.getAnalysisRepositoryManager().getWorkerService().assignTask(analysisTaskId,WORKER_ID);
			ProjectAnalyser projectAnalyser = new ProjectAnalyser(platform);
			projectAnalyser.executeAnalyse(analysisTaskId,WORKER_ID);	
			platform.getAnalysisRepositoryManager().getWorkerService().completeTask(WORKER_ID);
		}

		assertFalse(project.getExecutionInformation().getInErrorState());
		assertEquals(0, mongo.getDB("osgitest").getCollection("trans.rascal.dependency.osgi.allOSGiPackageDependencies").getCount());
		assertEquals(0, mongo.getDB("osgitest").getCollection("trans.rascal.dependency.osgi.unversionedOSGiRequiredBundles").getCount());
		assertEquals(0, mongo.getDB("osgitest").getCollection("trans.rascal.dependency.osgi.unusedOSGiImportedPackages").getCount());
		assertEquals(0L, mongo.getDB("osgitest").getCollection("trans.rascal.dependency.osgi.numberOSGiSplitImportedPackages").findOne().get("value"));
		assertEquals(0.0, mongo.getDB("osgitest").getCollection("trans.rascal.dependency.osgi.ratioUnusedOSGiImportedPackages").findOne().get("value"));
		// FIXME
		assertEquals("bundle://eclipse/com.google.guava/%5B16.0.0-(-1", mongo.getDB("osgitest").getCollection("trans.rascal.dependency.osgi.allOSGiBundleDependencies").findOne().get("value"));
		assertEquals(0, mongo.getDB("osgitest").getCollection("trans.rascal.dependency.osgi.unversionedOSGiExportedPackages").getCount());
		assertEquals(0L, mongo.getDB("osgitest").getCollection("trans.rascal.dependency.osgi.numberOSGiSplitExportedPackages").findOne().get("value"));
		assertEquals(0, mongo.getDB("osgitest").getCollection("trans.rascal.dependency.osgi.allOSGiDynamicImportedPackages").getCount());
		assertEquals(1L, mongo.getDB("osgitest").getCollection("trans.rascal.dependency.osgi.numberOSGiBundleDependencies").findOne().get("value"));
		assertEquals(0.0, mongo.getDB("osgitest").getCollection("trans.rascal.dependency.osgi.ratioUnversionedOSGiImportedPackages").findOne().get("value"));
		assertEquals(0L, mongo.getDB("osgitest").getCollection("trans.rascal.dependency.osgi.numberOSGiPackageDependencies").findOne().get("value"));
		assertEquals(0.0, mongo.getDB("osgitest").getCollection("trans.rascal.dependency.osgi.ratioUnversionedOSGiRequiredBundles").findOne().get("value"));
		assertEquals("java+package:///com", mongo.getDB("osgitest").getCollection("trans.rascal.dependency.osgi.usedOSGiUnimportedPackages").findOne().get("value"));
		assertEquals(0.0, mongo.getDB("osgitest").getCollection("trans.rascal.dependency.osgi.ratioUnversionedOSGiExportedPackages").findOne().get("value"));
		assertEquals(0.0, mongo.getDB("osgitest").getCollection("trans.rascal.dependency.osgi.ratioUsedOSGiImportedPackages").findOne().get("value"));
	}

	@After
	public void tearDown() {
		// Don't retain state in-between runs
		mongo.dropDatabase("maventest");
		mongo.dropDatabase("osgitest");
		mongo.dropDatabase("scava");
		mongo.dropDatabase("scava-analysis");
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
