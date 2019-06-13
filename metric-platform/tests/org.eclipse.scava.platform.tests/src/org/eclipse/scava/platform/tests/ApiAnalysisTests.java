package org.eclipse.scava.platform.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.admin.AdminApplication;
import org.eclipse.scava.platform.analysis.data.model.dto.MetricProviderDTO;
import org.eclipse.scava.platform.analysis.data.types.AnalysisExecutionMode;
import org.eclipse.scava.platform.client.api.ApiApplication;
import org.eclipse.scava.platform.osgi.analysis.ProjectAnalyser;
import org.eclipse.scava.repository.model.Project;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Response;
import org.restlet.Server;
import org.restlet.data.Protocol;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class ApiAnalysisTests {
	Mongo mongo;
	Platform platform;
	ApiHelper helper;

	private static String WORKER_ID;

	@Before
	public void setUp() throws UnknownHostException {
		mongo = new Mongo();
		helper = new ApiHelper();
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		platform = Platform.getInstance();
		platform.setMongo(mongo);
		platform.initialize();

		WORKER_ID = Configuration.getInstance().getSlaveIdentifier();
		// Register Worker
		platform.getAnalysisRepositoryManager().getWorkerService().registerWorker(WORKER_ID);

		Component component = new Component();

		Server server = new Server(Protocol.HTTP, 8182);
		component.getServers().add(server);

		final Application app = new ApiApplication();
		component.getDefaultHost().attachDefault(app);
		try {
			component.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testApiChangesJDP() throws InterruptedException {
		String analysisTaskId = "ApiTask";
		String projectId = "javadesignpatterns";
	
		// Set token
		// TODO: Valid githubToken here
		Response res = helper.setProperty("githubToken", "7fed15fc2612eac4d4c06b98e6930fcb5b76b3b0");
		assertEquals(201, res.getStatus().getCode());
	
		// Import project
		res = helper.importProject("https://github.com/iluwatar/java-design-patterns");
		assertEquals(201, res.getStatus().getCode());
		
		// Create task
		List<MetricProviderDTO> providers = helper.getMetricProviders();
		List<String> metricProviders = new ArrayList<String>();
		for (MetricProviderDTO provider : providers) {
			if (provider.getMetricProviderId().contains(".api.")) {
				metricProviders.add(provider.getMetricProviderId());
			}
		}
	
		res = helper.createTask(projectId, analysisTaskId, "API Task", AnalysisExecutionMode.SINGLE_EXECUTION,
				metricProviders, "12/10/2018", "22/10/2018");
		assertEquals(201, res.getStatus().getCode());
	
		// Start task
		res = helper.startTask(analysisTaskId);
		assertEquals(200, res.getStatus().getCode());
	
		// Start execution
		Project project = platform.getProjectRepositoryManager().getProjectRepository().getProjects()
				.findOneByShortName(projectId);
		platform.getAnalysisRepositoryManager().getWorkerService().assignTask(analysisTaskId, WORKER_ID);
		ProjectAnalyser projectAnalyser = new ProjectAnalyser(platform);
		projectAnalyser.executeAnalyse(analysisTaskId, WORKER_ID);
		platform.getAnalysisRepositoryManager().getWorkerService().completeTask(WORKER_ID);
	
		assertFalse(project.getExecutionInformation().getInErrorState());
	}
}
