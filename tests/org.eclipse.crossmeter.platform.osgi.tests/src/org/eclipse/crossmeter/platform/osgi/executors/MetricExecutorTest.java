/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.platform.osgi.executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.crossmeter.platform.Date;
import org.eclipse.crossmeter.platform.Platform;
import org.eclipse.crossmeter.platform.osgi.ErrorThrowingTransientMetricProvider;
import org.eclipse.crossmeter.platform.osgi.ManualRegistrationMetricProviderManager;
import org.eclipse.crossmeter.platform.osgi.executors.ProjectExecutor;
import org.eclipse.crossmeter.repository.model.Project;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class MetricExecutorTest {
	
	protected static Platform platform;
	protected static Mongo mongo;
	
	@BeforeClass
	public static void setup() throws Exception {
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		
		mongo = new Mongo();
		platform = new Platform(mongo);
		
		Project project = new Project();
		project.setName("debug-project");
		String startDate = new Date().addDays(-2).toString();
		project.getExecutionInformation().setLastExecuted(startDate);
		
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();
	}
	
	@AfterClass
	public static void closedown() {
		
		Project project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByName("debug-project");
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().remove(project);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();
		
		mongo.close();
	}
	
	@Test
	public void testLastExecutedDate() throws Exception {
		
		ManualRegistrationMetricProviderManager metricProviderManager = new ManualRegistrationMetricProviderManager();
		metricProviderManager.addMetricProvider(new ErrorThrowingTransientMetricProvider());
		platform.setMetricProviderManager(metricProviderManager);
		
		assertEquals(1,platform.getMetricProviderManager().getMetricProviders().size());
		
		Project project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByName("debug-project");
		String startDate = project.getExecutionInformation().getLastExecuted();

		ProjectExecutor pe = new ProjectExecutor(platform, project);
		pe.run();
		
		assertTrue(project.getExecutionInformation().getInErrorState());
		assertEquals(startDate, project.getExecutionInformation().getLastExecuted());
		
		// 
	}
	
	
}
