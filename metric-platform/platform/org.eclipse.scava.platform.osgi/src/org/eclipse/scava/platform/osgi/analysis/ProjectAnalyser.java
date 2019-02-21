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
	private List<IMetricProvider> platformMetricProviders;

	
	public ProjectAnalyser(Platform platform) {
		this.analysisThreadNumber = Runtime.getRuntime().availableProcessors();		
		this.platform = platform;
		platformMetricProviders = platform.getMetricProviderManager().getMetricProviders();
	}

	public boolean executeAnalyse(String analysisTaskId, String workerId) {
		this.logger = OssmeterLogger.getLogger("ProjectExecutor (" + workerId + ":"+analysisTaskId +")");	

		AnalysisTask task = platform.getAnalysisRepositoryManager().getRepository().getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);	
		Project project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName(task.getProject().getProjectId());

		logger.info("Beginning execution.");
		initialiseProjectLocalStorage(project);
		
		// Clear any open flags
		project.getExecutionInformation().setInErrorState(false);
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
		// Split metrics into branches
		//List<IMetricProvider> paltformMetricProviders = platform.getMetricProviderManager().getMetricProviders();
		List<IMetricProvider> filtredMetricProvider = filterMetricProvider(platformMetricProviders,analysisTaskId);
		List<IMetricProvider> factoids = extractFactoidProviders(filtredMetricProvider);
		
		logger.info("Creating metric branches.");
		List<List<IMetricProvider>> metricBranches = splitIntoBranches(filtredMetricProvider, factoids, task);
		//Method for updating the AnalysisTask if there were found new metrics dependencies
		int counter=0;
		for(List<IMetricProvider> branch : metricBranches)
		{
			counter+=branch.size();
		}
		if(counter>filtredMetricProvider.size())
			task = platform.getAnalysisRepositoryManager().getRepository().getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);
		logger.info("Created metric branches.");
		

		Date enecutionDate = new Date(task.getScheduling().getCurrentDate());
		Date endDate = null;
		if(task.getType().equals(AnalysisExecutionMode.DAILY_EXECUTION.name())){
			endDate = new Date().addDays(-1);
		}else {
			endDate = new Date(task.getEndDate());
		}		
		
		Date[] dates = Date.range(enecutionDate,endDate);
		logger.info("Dates: " + dates.length);
		
		long estimatedDuration = 0; 
		for (Date date : dates) {
			java.util.Date dailyExecution = new java.util.Date();
			
			platform.getAnalysisRepositoryManager().getSchedulingService().newDailyTaskExecution(analysisTaskId,date.toJavaDate());
	
			ExecutorService executorService = Executors.newFixedThreadPool(analysisThreadNumber);
			logger.info("Date: " + date + ", project: " + project.getName());
			
			ProjectDelta delta = new ProjectDelta(project, date, platform);
			
			try {
				delta.create();
			} catch (Exception e) {
				project.getExecutionInformation().setInErrorState(true);
				platform.getProjectRepositoryManager().getProjectRepository().sync();
				
				ProjectError error = ProjectError.create(date.toString(), "ProjectExecutor: Delta creation", project.getShortName(), project.getName(), e, Configuration.getInstance().getSlaveIdentifier());
				platform.getProjectRepositoryManager().getProjectRepository().getErrors().add(error);
				platform.getProjectRepositoryManager().getProjectRepository().getErrors().sync();
				
				logger.error("Project delta creation failed. Aborting.");
				return false;
			}
			
			for (List<IMetricProvider> branch : metricBranches) {
				MetricListExecutor mExe = new MetricListExecutor(this.platform,project.getShortName(),analysisTaskId, delta, date);
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
			// depend on other 0669...
			// TODO: Should check if in error state before and after factoids
			if (factoids.size() > 0) {
				logger.info("Executing factoids.");
				MetricListExecutor mExe = new MetricListExecutor(this.platform,project.getShortName(),analysisTaskId, delta, date);
				mExe.setMetricList(factoids);
				mExe.run(); // TODO Blocking (as desired). But should it have its own thread?
			}

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
				logger.info("Analysis Task Execution  '" +analysisTaskId +"'STOPED at [ "+ date + " ]");
				return false;
			}
			
			if(task.getScheduling().getStatus().equals(AnalysisTaskStatus.PENDING_STOP.name())){
				task.getScheduling().setStatus(AnalysisTaskStatus.STOP.name());
				task.getScheduling().setWorkerId(null);	
				platform.getAnalysisRepositoryManager().getRepository().sync();
				logger.info("Analysis Task Execution  '" +analysisTaskId +"'STOPED at [ "+ date + " ]");
				return false;
			}
		}
		
		logger.info("Analysis Task Execution complete  '" +analysisTaskId +"' by worker '" + workerId +"'");
		return true;
	}


	private List<IMetricProvider> filterMetricProvider(List<IMetricProvider> metricProviders, String analysisTaskId) {
		List<IMetricProvider> filtredProviders = new ArrayList<>();
		AnalysisTask task = platform.getAnalysisRepositoryManager().getRepository().getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);		
		
		Set<String> taskProviders = new HashSet<>();
		for(MetricExecution provider : task.getMetricExecutions()) {
			taskProviders.add(provider.getMetricProviderId());
		}

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(IMetricProvider platformProvider : metricProviders) {
			if(taskProviders.contains(platformProvider.getIdentifier())) {
				filtredProviders.add(platformProvider);
			}
		}
		
		return filtredProviders;
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
//	public List<List<IMetricProvider>> splitIntoBranches(List<IMetricProvider> metrics) {
//		List<Set<IMetricProvider>> branches = new ArrayList<Set<IMetricProvider>>();
//		
//		for (IMetricProvider m : metrics) {
//			Set<IMetricProvider> mBranch = new HashSet<>();
//			
//			for (Set<IMetricProvider> branch : branches) {
//				if (branch.contains(m)) {
//					mBranch = branch;
//					break;
//				}
//			}
//			if (!mBranch.contains(m)) mBranch.add(m);
//			if (!branches.contains(mBranch)) branches.add(mBranch);
//
//			if (m.getIdentifiersOfUses() != null) {
//				for (String id : m.getIdentifiersOfUses()) {
//					IMetricProvider use = lookupMetricProviderById(metrics, id);
//					if (use == null) continue;
//					boolean foundUse = false;
//					for (Set<IMetricProvider> branch : branches) {
//						if (branch.contains(use)) {
//							branch.addAll(mBranch);
//							branches.remove(mBranch);
//							mBranch = branch;
//							foundUse = true;
//							break;
//						}
//					}
//					if (!foundUse) {
//						mBranch.add(use);
//					}
//				}
//			}
//			if (!branches.contains(mBranch)) branches.add(mBranch);
//		}
//		
//		List<List<IMetricProvider>> sortedBranches = new ArrayList<List<IMetricProvider>>();
//		for (Set<IMetricProvider> b : branches) {
//			sortedBranches.add(sortMetricProviders(new ArrayList<IMetricProvider>(b)));
//		}
//		
//		return sortedBranches;
//	}
	
	private List<IMetricProvider> getIdentifiersOfUse(List<IMetricProvider> metrics) {
		List<IMetricProvider> identifiersOfUse = new ArrayList<IMetricProvider>(0);
		
		for(IMetricProvider metric : metrics)
		{
			if (metric.getIdentifiersOfUses() != null)
			{
				for (String id : metric.getIdentifiersOfUses())
				{
					IMetricProvider use = lookupMetricProviderById(platformMetricProviders, id);
					if (use == null)
						continue;
					if(!identifiersOfUse.contains(use))
						identifiersOfUse.add(use);
				}
			}
		}
		return identifiersOfUse;
	}
	
	private List<IMetricProvider> getRecursiveIdentifiersOfUse(IMetricProvider metric) {
		List<IMetricProvider> finalMetrics = new ArrayList<IMetricProvider>();
		List<IMetricProvider> intermediateMetrics = new ArrayList<IMetricProvider>();
		intermediateMetrics.add(metric);
		boolean finished=false;
		
		while(!finished)
		{
			intermediateMetrics=getIdentifiersOfUse(intermediateMetrics);
			if(intermediateMetrics.isEmpty())
				finished=true;
			else
			{
				for(IMetricProvider m : intermediateMetrics)
				{
					if(!finalMetrics.contains(m))
						finalMetrics.add(m);
				}
			}
		}
		return finalMetrics;
	}
	
	/**
	 * For the moment the factoids are necessary to update the AnalysisTask, however, we need to find a way to include the dependencies of the factoids
	 * @param metrics
	 * @param factoids
	 * @param task
	 * @return
	 */
	public List<List<IMetricProvider>> splitIntoBranches(List<IMetricProvider> metrics, List<IMetricProvider> factoids, AnalysisTask task) {
		List<Set<IMetricProvider>> branches = new ArrayList<Set<IMetricProvider>>();
		List<String> toAddInTask = new ArrayList<String>(0);
		boolean foundUse;
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
				
				for (IMetricProvider use : getRecursiveIdentifiersOfUse(m))
				{
					MetricExecution mpd = platform.getAnalysisRepositoryManager().getSchedulingService().findMetricExecution(task.getProject().getId(),m.getIdentifier());
					if(mpd==null)
						toAddInTask.add(use.getIdentifier());
					foundUse = false;
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
		
		if(!toAddInTask.isEmpty())
		{
			for (IMetricProvider m : metrics) {
				toAddInTask.add(m.getIdentifier());
			}
			for (IMetricProvider m : factoids) {
				toAddInTask.add(m.getIdentifier());
			}
			platform.getAnalysisRepositoryManager().getTaskService().updateAnalysisTask(task.getAnalysisTaskId(), task, toAddInTask);
			platform.getAnalysisRepositoryManager().getTaskService().startAnalysisTask(task.getAnalysisTaskId());
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
