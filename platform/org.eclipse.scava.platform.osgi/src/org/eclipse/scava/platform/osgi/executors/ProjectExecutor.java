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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.eclipse.scava.platform.AbstractFactoidMetricProvider;
import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.delta.NoManagerFoundException;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.LocalStorage;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.ProjectError;
import org.eclipse.scava.repository.model.ProjectExecutionInformation;
import org.eclipse.scava.repository.model.VcsRepository;

public class ProjectExecutor implements Runnable {
	
	protected Project project;
	protected int numberOfCores;
	protected Platform platform;
	protected OssmeterLogger logger;
	
	public ProjectExecutor(Platform platform, Project project) {
		this.numberOfCores = Runtime.getRuntime().availableProcessors();
		this.platform = platform;
		this.project = project;
		this.logger = (OssmeterLogger)OssmeterLogger.getLogger("ProjectExecutor (" + project.getName() +")");
		
	}
	
	protected void initialiseProjectLocalStorage (Project project) {
		if (project.getExecutionInformation() == null) {
			project.setExecutionInformation(new ProjectExecutionInformation());
		}
		
		try{	
			Path projectLocalStoragePath = Paths.get(platform.getLocalStorageHomeDirectory().toString(), project.getShortName());		
			if (Files.notExists(projectLocalStoragePath)) {
				Files.createDirectory(projectLocalStoragePath);
			}
			LocalStorage projectLocalStorage = new LocalStorage();
			projectLocalStorage.setPath(projectLocalStoragePath.toString());
			project.getExecutionInformation().setStorage(projectLocalStorage);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		if (project == null) {
			logger.error("No project scheduled. Exiting.");
			return;
		}
		logger.info("Beginning execution.");
		
		initialiseProjectLocalStorage(project);
		
		// Clear any open flags
		project.getExecutionInformation().setInErrorState(false);
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
		// Split metrics into branches
		List<IMetricProvider> metricProviders = platform.getMetricProviderManager().getMetricProviders();
		List<IMetricProvider> factoids = extractFactoidProviders(metricProviders);
		
		logger.info("Creating metric branches.");
		// FIXME: Need to check that no metrics depend on factoids!
		List<List<IMetricProvider>> metricBranches = splitIntoBranches(metricProviders);
		logger.info("Created metric branches.");
		
		// Find the date to start from 
		Date lastExecuted = getLastExecutedDate();
		
		logger.info("Last executed: " + lastExecuted);
		
		if (lastExecuted == null) {
			// TODO: Perhaps flag the project as being in a fatal error state? This will potentially keep occurring.
			logger.error("Parse error of project's lastExecuted date. Returned null.");
			return;
		}
		Date today = new Date();
		
		if (lastExecuted.compareTo(today) >= 0) {
			logger.info("Project up to date. Skipping metric execution.");
			return;
		}
		
		Date[] dates = Date.range(lastExecuted.addDays(1), today.addDays(-1));
		logger.info("Dates: " + dates.length);
		
		for (Date date : dates) {
			// TODO: An alternative would be to have a single thread pool for the node. I briefly tried this
			// and it didn't work. Reverted to this implement (temporarily at least).
			ExecutorService executorService = Executors.newFixedThreadPool(numberOfCores);
			logger.info("Date: " + date + ", project: " + project.getName());
			
			ProjectDelta delta = new ProjectDelta(project, date, platform);
			
			try {
				delta.create();
			} catch (Exception e) {
				project.getExecutionInformation().setInErrorState(true);
				platform.getProjectRepositoryManager().getProjectRepository().sync();
				
				// Log in DB
				ProjectError error = ProjectError.create(date.toString(), "ProjectExecutor: Delta creation", project.getShortName(), project.getName(), e, Configuration.getInstance().getSlaveIdentifier());
				platform.getProjectRepositoryManager().getProjectRepository().getErrors().add(error);
				platform.getProjectRepositoryManager().getProjectRepository().getErrors().sync();
				
				logger.error("Project delta creation failed. Aborting.");
				return;
			}
			
			for (List<IMetricProvider> branch : metricBranches) {
				MetricListExecutor mExe = new MetricListExecutor(project.getShortName(), delta, date);
				mExe.setMetricList(branch);
				
				executorService.execute(mExe);
			}
			
			try {
				executorService.shutdown();
				executorService.awaitTermination(24, TimeUnit.HOURS);
			} catch (InterruptedException e) {
				logger.error("Exception thrown when shutting down executor service.", e);
			}
			
			// Now fun the factoids: 
			// FIXME: Should factoids only run on the last date..? It depends on whether factoid results can 
			// depend on other factoids...
			// TODO: Should check if in error state before and after factoids
			if (factoids.size() > 0) {
				logger.info("Executing factoids.");
				MetricListExecutor mExe = new MetricListExecutor(project.getShortName(), delta, date);
				mExe.setMetricList(factoids);
				mExe.run(); // TODO Blocking (as desired). But should it have its own thread?
			}

			// FIXME: We need to re-query the DB as we're holding onto an old instance of the project object
			// Need to find a way around this - updating to the newer version of the Mongo Java client may help as it
			// provides a new, threadsafe class called MongoClient
			project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName(project.getShortName());
			
			// Update meta-data
			if (project.getExecutionInformation().getInErrorState()) {
				// TODO: what should we do? Is the act of not-updating the lastExecuted flag enough?
				// If it continues to loop, it simply tries tomorrow. We need to stop this happening.
				logger.warn("Project in error state. Stopping execution.");
				break;
			} else {
				logger.info("Updating last executed date."); 
				project.getExecutionInformation().setLastExecuted(date.toString());
				platform.getProjectRepositoryManager().getProjectRepository().sync();
			}
			
		}
		
		if (!project.getExecutionInformation().getInErrorState() && !project.getAnalysed()) {
			project.setAnalysed(true);
		}
		
		logger.info("Project execution complete. In error state: " + project.getExecutionInformation().getInErrorState());
	}

	protected List<IMetricProvider> extractFactoidProviders(List<IMetricProvider> allProviders) {
		List<IMetricProvider> factoids = new ArrayList<>();
		
		for (IMetricProvider imp : allProviders) {
			if (imp instanceof AbstractFactoidMetricProvider) {
				factoids.add(imp);
			}
		}
		
		allProviders.removeAll(factoids);
		
		return factoids;
	}
	
	
	/**
	 * Algorithm to split a list of metric providers into dependency branches. Current implementation isn't
	 * wonderful - it was built to work, not to perform - and needs relooking at in the future. 
	 * @param metrics
	 * @return
	 */
	protected List<List<IMetricProvider>> splitIntoBranches(List<IMetricProvider> metrics) {
		List<Set<IMetricProvider>> branches = new ArrayList<Set<IMetricProvider>>();
		
		for (IMetricProvider m : metrics) {
			Set<IMetricProvider> mBranch = new HashSet<>();
			
			for (Set<IMetricProvider> branch : branches) {
				if (branch.contains(m)) {
					mBranch = branch;
					break;
				}
			}
			if (!mBranch.contains(m)) mBranch.add(m);
			if (!branches.contains(mBranch)) branches.add(mBranch);

			if (m.getIdentifiersOfUses() != null) {
				for (String id : m.getIdentifiersOfUses()) {
					IMetricProvider use = lookupMetricProviderById(metrics, id);
					if (use == null) continue;
					boolean foundUse = false;
					for (Set<IMetricProvider> branch : branches) {
						if (branch.contains(use)) {
							branch.addAll(mBranch);
							branches.remove(mBranch);
							mBranch = branch;
							foundUse = true;
							break;
						}
					}
					if (!foundUse) {
						mBranch.add(use);
					}
				}
			}
			if (!branches.contains(mBranch)) branches.add(mBranch);
		}
		
		List<List<IMetricProvider>> sortedBranches = new ArrayList<List<IMetricProvider>>();
		for (Set<IMetricProvider> b : branches) {
			sortedBranches.add(sortMetricProviders(new ArrayList<IMetricProvider>(b)));
		}
		
		return sortedBranches;
	}
	
	protected IMetricProvider lookupMetricProviderById(List<IMetricProvider> metrics, String id){
		for (IMetricProvider mp : metrics) {
			if (mp.getIdentifier().equals(id)) {
				return mp;
			}
		}
		return null;
	}
		
	protected Date getLastExecutedDate() {
		Date lastExec;
		String lastExecuted = project.getExecutionInformation().getLastExecuted();
		if(lastExecuted.equals("null") || lastExecuted.equals("")) {
			lastExec = new Date();
			
			for (VcsRepository repo : project.getVcsRepositories()) {
				// This needs to be the day BEFORE the first day! (Hence the addDays(-1)) 
				try {
					Date d = platform.getVcsManager().getDateForRevision(repo, platform.getVcsManager().getFirstRevision(repo)).addDays(-1);
					if (d == null) continue;
					if (lastExec.compareTo(d) > 0) {
						lastExec = d;
					} 
				} catch (NoManagerFoundException e) {
					System.err.println(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (CommunicationChannel communicationChannel : project.getCommunicationChannels()) {
				try {
					Date d = platform.getCommunicationChannelManager().getFirstDate(platform.getMetricsRepository(project).getDb(), communicationChannel);
					if (d == null) continue;
					d = d.addDays(-1);
					if (lastExec.compareTo(d) > 0) {
						lastExec = d;
					}
				} catch (NoManagerFoundException e) {
					System.err.println(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (BugTrackingSystem bugTrackingSystem : project.getBugTrackingSystems()) {
				try {
					Date d = platform.getBugTrackingSystemManager().getFirstDate(platform.getMetricsRepository(project).getDb(), bugTrackingSystem).addDays(-1);
					if (d == null) continue;
					if (lastExec.compareTo(d) > 0) {
						lastExec = d;
					}
				} 
				catch (NoManagerFoundException e) {
					System.err.println(e.getMessage());					
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				lastExec = new Date(lastExecuted);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		return lastExec;
	}
	
	public List<IMetricProvider> sortMetricProviders(List<IMetricProvider> providers) {
		List<IMetricProvider> sorted = new ArrayList<IMetricProvider>();
		List<IMetricProvider> marked = new ArrayList<IMetricProvider>();
		List<IMetricProvider> temporarilyMarked = new ArrayList<IMetricProvider>();
		List<IMetricProvider> unmarked = new ArrayList<IMetricProvider>();
		unmarked.addAll(providers);
		
		while (unmarked.size()>0) {
			IMetricProvider mp = unmarked.get(0);
			visitDependencies(marked, temporarilyMarked, unmarked, mp, providers, sorted);
		}
		return sorted;
	}
	
	protected void visitDependencies(List<IMetricProvider> marked, List<IMetricProvider> temporarilyMarked, List<IMetricProvider> unmarked, IMetricProvider mp, List<IMetricProvider> providers, List<IMetricProvider> sorted) {
		if (temporarilyMarked.contains(mp)) {
			throw new RuntimeException("Temporarily marked error.");
		}
		if (!marked.contains(mp)) {
			temporarilyMarked.add(mp);
			List<String> dependencies = mp.getIdentifiersOfUses();
			if (dependencies != null) 
				for (String dependencyIdentifier : dependencies) {
					for (IMetricProvider p : providers) {
						if (p.getIdentifier().equals(dependencyIdentifier)){
							visitDependencies(marked, temporarilyMarked, unmarked, p, providers, sorted);
							break;
						}
					}
				}
			marked.add(mp);
			temporarilyMarked.remove(mp);
			unmarked.remove(mp);
			sorted.add(mp);
		}
	}
}
