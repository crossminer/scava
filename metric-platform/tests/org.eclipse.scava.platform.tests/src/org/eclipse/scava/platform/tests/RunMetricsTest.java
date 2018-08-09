package org.eclipse.scava.platform.tests;

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

import static org.junit.Assert.*;

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
