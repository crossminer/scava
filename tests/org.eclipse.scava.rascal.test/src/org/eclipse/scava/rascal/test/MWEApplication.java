/*******************************************************************************
 * Copyright (c) 2017 Centrum Wiskunde & Informatica
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.rascal.test;

import java.util.List;

import org.eclipse.scava.metricprovider.rascal.RascalFactoidProvider;
import org.eclipse.scava.metricprovider.rascal.RascalManager;
import org.eclipse.scava.metricprovider.rascal.RascalMetricProvider;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.vcs.PlatformVcsManager;
import org.eclipse.scava.platform.osgi.executors.MetricListExecutor;
import org.eclipse.scava.repository.model.LocalStorage;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.ProjectExecutionInformation;
import org.eclipse.scava.repository.model.VcsRepository;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.imp.pdb.facts.IValue;
import org.rascalmpl.interpreter.Evaluator;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class MWEApplication implements IApplication {
	@Override
	public Object start(IApplicationContext ctx) throws Exception {
		Mongo mongo = new Mongo();
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		Platform platform = new Platform(mongo);
		RascalManager manager = RascalManager.getInstance();
		Evaluator eval = manager.getEvaluator();

		System.out.println("Creating project");
		Project project = ProjectCreationUtil.createGitProject("epsilon", "git://git.eclipse.org/gitroot/epsilon/org.eclipse.epsilon.git");
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();
		
		System.out.print("Extracting metrics providers from deployed plug-ins... ");
		List<IMetricProvider> mProviders = manager.getMetricProviders();
		System.out.println(mProviders.size() + " metrics.");
		
		project.setExecutionInformation(new ProjectExecutionInformation());
		String dataPath = "/Users/dig/scava-tmp";
		LocalStorage localStorage = new LocalStorage();
		localStorage.setPath(dataPath);
		project.getExecutionInformation().setStorage(localStorage);

		VcsRepository repo = project.getVcsRepositories().get(0);
		PlatformVcsManager vcsManager = platform.getVcsManager();
		String firstRev = vcsManager.getFirstRevision(repo);
		//String currentRev = vcsManager.getCurrentRevision(repo);
		//Date firstDate = vcsManager.getDateForRevision(repo, firstRev);
		Date firstDate = new Date("20100101");
		//Date currentDate = new Date("20081012");
		Date currentDate = new Date("20101201");
		System.out.println("range = " + firstDate + " -- " + currentDate);
		Date[] dateRange = Date.range(firstDate, currentDate);
		System.out.println("Done.");
		
		System.out.println("Starting analysis of " + repo.getUrl());
		System.out.println("From " + firstDate + " to " + currentDate);
		Date date = dateRange[0];
		ProjectDelta delta = new ProjectDelta(project, date, platform);
		delta.create();
		
		System.out.println("Creating executor");
		MetricListExecutor executor = new MetricListExecutor(project.getName(), delta, date);
		executor.setMetricList(mProviders);
		try {
			System.out.println("Running...");
			executor.run();
		} catch (Exception e) {
			System.out.println("Nope: " + e.getMessage());
		}
		System.out.println("Done.");
		
		System.out.println("Results:");
		mProviders.forEach((IMetricProvider m) -> {
			try {
				if (m instanceof RascalMetricProvider) {
					RascalMetricProvider rascalMetric = (RascalMetricProvider) m;
					IValue result = rascalMetric.getMetricResult(project, m, manager);
					System.out.println(rascalMetric.getIdentifier() + "[" + rascalMetric.getReturnType() + "] = " + result);
				}
				else if (m instanceof RascalFactoidProvider) {
					RascalFactoidProvider rascalFactoid = (RascalFactoidProvider) m;
					IValue result = rascalFactoid.getMeasuredFactoid(rascalFactoid.adapt(platform.getMetricsRepository(project).getDb()), eval.getValueFactory());
					System.out.println(rascalFactoid.getIdentifier() + " = " + result);
				}
			} catch (Exception e) {
				System.out.println("Something happened: " + e.getMessage());
			}
		});
		
		mongo.close();
		return null;
	}
	
	@Override
	public void stop() {
		// Not much to do there
	}
}
