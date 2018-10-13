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

import java.net.UnknownHostException;
import java.util.List;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.Platform;
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

public class DependencyOrderingTest {

	private static Mongo mongo;
	private static Platform platform;
	private static String PROJECT_NAME = "Ponte";
	private static ProjectAnalyser projectAnalyser;
	
	@BeforeClass
	public static void setUp() throws UnknownHostException {
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());

		mongo = new Mongo();
		platform = new Platform(mongo);

		Project project = new Project();
		project.setName(PROJECT_NAME);
		String startDate = new Date().addDays(-2).toString();
		project.getExecutionInformation().setLastExecuted(startDate);
	
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();
		
		project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByName(PROJECT_NAME);
		projectAnalyser = new ProjectAnalyser(platform);
	}
	
	@AfterClass
	public static void tearDown() {
		Project project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByName(PROJECT_NAME);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().remove(project);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();
		
		mongo.close();
	}
	
	@Test
	public void testMetricProvidersInAllBranches() throws Exception{
		
		long a = System.currentTimeMillis();
//		List<IMetricProvider> tMetrics = pe.getOrderedTransientMetricProviders(platform.getMetricProviderManager().getMetricProviders());
//		long timeTransOrder = System.currentTimeMillis() - a;
//		
//		a = System.currentTimeMillis();
//		List<IMetricProvider> hMetrics = pe.getOrderedHistoricMetricProviders(platform.getMetricProviderManager().getMetricProviders());
//		long timeHistOrder = System.currentTimeMillis() - a;
//		
//		a = System.currentTimeMillis();
//		List<List<IMetricProvider>> tbran = pe.splitIntoBranches(tMetrics);
//		Thread.sleep(1000);
//		long timeTransBranch = System.currentTimeMillis() - a;
//		
//		System.out.println("number of trans: " + tMetrics.size());
//		System.out.println("number of trans branches: " + tbran.size());
//		printBranches(tbran);
//		
//
//		System.out.println();
//		
//		a = System.currentTimeMillis();
//		List<List<IMetricProvider>> hbran = pe.splitIntoBranches(hMetrics);
//		long timeHistBranch = System.currentTimeMillis() - a;
//		
//		System.out.println("number of hists: " + hMetrics.size());
//		System.out.println("number of hist branches: " + hbran.size());
//		printBranches(hbran);
//
//		System.out.println();
//		
//		a = System.currentTimeMillis();
//		List<IMetricProvider> allMetrics = pe.sortMetricProviders(platform.getMetricProviderManager().getMetricProviders());
//		long timeSortAll = System.currentTimeMillis() - a;
//		
//		System.out.println("Sorted metrics: ");
//		for (IMetricProvider i : allMetrics){
//			System.out.println(i.getIdentifier());
//		}
//		
//		
//		a = System.currentTimeMillis();
//		List<List<IMetricProvider>> allbran = pe.splitIntoBranchesDFS(allMetrics);
//		Thread.sleep(1000);
//		long timeAllBranch = System.currentTimeMillis() - a;
//		
//		
//		System.out.println("number of all metrics: " + allMetrics.size());
//		System.out.println("number of all branches: " + allbran.size());
//		printBranches(allbran);
//		
//		int i = 0;
//		for (List<IMetricProvider> bran : allbran) {
//			i += bran.size();
//		}
//		
//		System.out.println("numer of metrics in all branches: " + i);
//		
////		System.out.println("Time to order trans: " + timeTransOrder);
////		System.out.println("Time to order hists: " + timeHistOrder);
////		System.out.println("Time to split trans: " + timeTransBranch);
////		System.out.println("Time to split hists: " + timeHistBranch);
//		System.out.println("Time to order all: " + timeSortAll);
//		System.out.println("Time to split all: " + timeAllBranch);
		
		ManualRegistrationMetricProviderManager metricProviderManager = new ManualRegistrationMetricProviderManager();
		metricProviderManager.addMetricProvider(new ErrorThrowingTransientMetricProvider());
		platform.setMetricProviderManager(metricProviderManager);
		
		List<List<IMetricProvider>> allbran = projectAnalyser.splitIntoBranches(platform.getMetricProviderManager().getMetricProviders());
		for (List<IMetricProvider> bran : allbran) {
			for (IMetricProvider m : bran) {
				System.out.print(m.getIdentifier() + " -> ");
			}
			System.out.println();
		}
		
		int i = 0;
		for (List<IMetricProvider> bran : allbran) {
			i += bran.size();
		}

		System.out.println("number of metrics in all branches: " + i);
		assertEquals(i, allbran.size());
		
	}
	
	protected void printBranches(List<List<IMetricProvider>> branches) {
		for (List<IMetricProvider> branch : branches) {
			for (IMetricProvider m : branch) {
				System.out.print(m.getIdentifier() + " -> ");
			}
			System.out.println();
		}
	}
	
}
