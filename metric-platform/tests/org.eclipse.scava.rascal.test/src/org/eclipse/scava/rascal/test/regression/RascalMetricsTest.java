package org.eclipse.scava.rascal.test.regression;

import static org.junit.Assert.*;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.osgi.executors.ProjectExecutor;
import org.eclipse.scava.rascal.test.ProjectCreationUtil;
import org.eclipse.scava.repository.model.Project;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class RascalMetricsTest {

	@Before
	public void setUp() throws Exception {
		Mongo mongo = new Mongo();
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		Platform platform = new Platform(mongo);

		System.out.println("Creating project");
		Project project = ProjectCreationUtil.createGitProject("pongotest", "https://github.com/kolovos/pongo");
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();				
		
		System.out.println("Executing project " + project.getName());
		ProjectExecutor executor = new ProjectExecutor(platform, project);
		// Compute metrics only for 10 dates
		Date dStart = new Date("20180405");
		Date dEnd = new Date("20180415");
		executor.executeProject(dStart, dEnd);
		
		mongo.close();
		
		System.out.println("Ending project creation.");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		assertTrue(true);
		//fail("Not yet implemented");
	}

}
