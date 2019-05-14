/*******************************************************************************
 * Copyright (c) 2018 Softeam
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.osgi.analysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.eclipse.scava.platform.AbstractFactoidMetricProvider;
import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.model.MetricExecution;
import org.eclipse.scava.platform.analysis.data.types.AnalysisExecutionMode;
import org.eclipse.scava.platform.analysis.data.types.AnalysisTaskStatus;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.LocalStorage;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.ProjectError;
import org.eclipse.scava.repository.model.ProjectExecutionInformation;

public class ProjectAnalyser {
	
	private Platform platform;
	private int analysisThreadNumber;
	private Logger logger;
	
	public ProjectAnalyser(Platform platform) {
		this.analysisThreadNumber = Runtime.getRuntime().availableProcessors();		
		this.platform = platform;
	}

	public boolean executeAnalyse(String analysisTaskId, String workerId) {
		long startTime = System.currentTimeMillis();
		
		this.logger = OssmeterLogger.getLogger("ProjectExecutor (" + workerId + ":"+analysisTaskId +")");
		
		AnalysisTask task = platform.getAnalysisRepositoryManager().getRepository().getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);	
		Project project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName(task.getProject().getProjectId());
				
		logger.info("Beginning execution.");
		//Initialize the project local storage
		initialiseProjectLocalStorage(project);
		
		this.logger.info("ProjectAnalyser( Project '" + project.getShortName() +"' Worker '" + workerId + "' AnalysisTask : " + analysisTaskId +")");
	
		// Clear any open flags
		project.getExecutionInformation().setInErrorState(false);
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
		// Split metrics into branches
		List<IMetricProvider> paltformMetricProviders = platform.getMetricProviderManager().getMetricProviders();
		List<IMetricProvider> filtredMetricProvider = filterMetricProvider(paltformMetricProviders,analysisTaskId);
		List<IMetricProvider> factoids = extractFactoidProviders(filtredMetricProvider);
		
		List<List<IMetricProvider>> metricBranches = splitIntoBranches(filtredMetricProvider);

		Date enecutionDate = new Date(task.getScheduling().getCurrentDate());
		Date endDate = null;
		if(task.getType().equals(AnalysisExecutionMode.CONTINUOUS_MONITORING.name())){
			endDate = new Date().addDays(-1);
		}else {
			endDate = new Date(task.getEndDate());
		}		
		
		Date[] dates = Date.range(enecutionDate,endDate);
		this.logger.info("Dates: " + dates.length);
		
		long estimatedDuration = 0;
		
		
		for (Date date : dates) {
			long startTimeDate = System.currentTimeMillis();
			this.logger.info("Project " + project.getShortName() + "Task execution ( " + analysisTaskId + " : Date " + date + " )");
			java.util.Date dailyExecution = new java.util.Date();
			platform.getAnalysisRepositoryManager().getSchedulingService().newDailyTaskExecution(analysisTaskId,date.toJavaDate());
			this.logger.info("Date: " + date + ", project: " + project.getName());
			
			ProjectDelta delta = new ProjectDelta(project, date, platform);
			
			try {
				delta.create();
			} catch (Exception e) {
				project.getExecutionInformation().setInErrorState(true);
				platform.getProjectRepositoryManager().getProjectRepository().sync();
				
				ProjectError error = ProjectError.create(date.toString(), "ProjectAnalyser: Delta creation", project.getShortName(), project.getName(), e, workerId);
				platform.getProjectRepositoryManager().getProjectRepository().getErrors().add(error);
				platform.getProjectRepositoryManager().getProjectRepository().getErrors().sync();
				
				this.logger.error("Project delta creation failed. Aborting.");
				return false;
			}
			
			ExecutorService executorService = Executors.newFixedThreadPool(analysisThreadNumber);

			for (List<IMetricProvider> branch : metricBranches) {
				MetricListExecutor mExe = new MetricListExecutor(this.platform,project.getShortName(),analysisTaskId, delta, date);
				mExe.setWorkerId(workerId);
				mExe.setMetricList(branch);			
				executorService.execute(mExe);
			}
			
			try {
				executorService.shutdown();
				executorService.awaitTermination(24, TimeUnit.HOURS);
			} catch (InterruptedException e) {
				this.logger.error("Exception thrown when shutting down executor service.", e);
			}finally {
				executorService = null;
			}
			
			if (factoids.size() > 0) {
				this.logger.info("Executing factoids.");
				MetricListExecutor mExe = new MetricListExecutor(this.platform,project.getShortName(),analysisTaskId, delta, date);
				mExe.setWorkerId(workerId);
				mExe.setMetricList(factoids);
				mExe.run(); // TODO Blocking (as desired). But should it have its own thread?
			}

			project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName(project.getShortName());
			
			// Update meta-data
			if (project.getExecutionInformation().getInErrorState()) {
				// TODO: what should we do? Is the act of not-updating the lastExecuted flag enough?
				// If it continues to loop, it simply tries tomorrow. We need to stop this happening.
				this.logger.warn("Project in error state. Resuming execution.");
			} else {
				this.logger.info("Updating last executed date."); 
				project.getExecutionInformation().setLastExecuted(date.toString());
				platform.getProjectRepositoryManager().getProjectRepository().sync();
			}		
			
			
			long duration = new java.util.Date().getTime() - dailyExecution.getTime();	
			if(estimatedDuration == 0) {
				estimatedDuration = duration;
			}else {
				estimatedDuration = (estimatedDuration + duration) / 2;
			}
			
			task = platform.getAnalysisRepositoryManager().getRepository().getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);
			task.getScheduling().setLastDailyExecutionDuration(estimatedDuration * (dates.length - Arrays.asList(dates).indexOf(date)));	
			platform.getAnalysisRepositoryManager().getRepository().sync();
			
			
			if(task.getScheduling().getStatus().equals(AnalysisTaskStatus.STOP.name())){
				this.logger.info("Analysis Task Execution  '" + analysisTaskId + "' STOPED at [ "+ date + " ]");
				return false;
			}
			
			if(task.getScheduling().getStatus().equals(AnalysisTaskStatus.PENDING_STOP.name())){
				task.getScheduling().setStatus(AnalysisTaskStatus.STOP.name());
				task.getScheduling().setWorkerId(null);	
				platform.getAnalysisRepositoryManager().getRepository().sync();
				this.logger.info("Analysis Task Execution  '" +analysisTaskId +"' STOPED at [ "+ date + " ]");
				return false;
			}
			this.logger.info("Date " + date + " Task Execution ( " + analysisTaskId + " completed in " + (System.currentTimeMillis() - startTimeDate) + " ms )");
		}
		

		this.logger.info("Project " + project.getShortName() + " Analysis Task Execution complete  '" +analysisTaskId +"' by worker '" + workerId +"' in " + (System.currentTimeMillis() - startTime) + " ms");
		return true;
	}


	private List<IMetricProvider> filterMetricProvider(List<IMetricProvider> metricProviders, String analysisTaskId) {
		long startTime = System.currentTimeMillis();
		this.logger.info("Loading all metricProviders");
		List<IMetricProvider> filtredProviders = new ArrayList<>();
		AnalysisTask task = platform.getAnalysisRepositoryManager().getRepository().getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);		
		
		Set<String> taskProviders = new HashSet<>();
		for(MetricExecution provider : task.getMetricExecutions()) {
			taskProviders.add(provider.getMetricProviderId());
		}
		
		for(IMetricProvider platformProvider : metricProviders) {
			if(taskProviders.contains(platformProvider.getIdentifier())) {
				filtredProviders.add(platformProvider);
			}
		}
		this.logger.info("Loading all metricProviders is done in " + (System.currentTimeMillis() - startTime) + " ms");
		System.out.println(filtredProviders.toString());
		return filtredProviders;
	}

	protected List<IMetricProvider> extractFactoidProviders(List<IMetricProvider> allProviders) {
		this.logger.info("Extracting Factoid metrics of all metricProviders");
		List<IMetricProvider> factoids = new ArrayList<>();
		
		for (IMetricProvider imp : allProviders) {
			if (imp instanceof AbstractFactoidMetricProvider) {
				factoids.add(imp);
			}
		}
		allProviders.removeAll(factoids);
		this.logger.info("Extracting Factoid metricProviders is done.");
		return factoids;
	}
	
	
	/**
	 * Algorithm to split a list of metric providers into dependency branches. Current implementation isn't
	 * wonderful - it was built to work, not to perform - and needs relooking at in the future. 
	 * @param metrics
	 * @return
	 */
	public List<List<IMetricProvider>> splitIntoBranches(List<IMetricProvider> metrics) {
		long startTime = System.currentTimeMillis();
		this.logger.info("Creating metric branches.");
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
		this.logger.info("Creating metric branches is done in " + (System.currentTimeMillis() - startTime) + " ms");
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
		

	
	private List<IMetricProvider> sortMetricProviders(List<IMetricProvider> providers) {
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

	private void initialiseProjectLocalStorage (Project project) {
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
	

	
}
