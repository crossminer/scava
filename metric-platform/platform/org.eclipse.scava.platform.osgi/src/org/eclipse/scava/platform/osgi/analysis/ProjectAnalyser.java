package org.eclipse.scava.platform.osgi.analysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import org.eclipse.scava.platform.analysis.data.AnalysisSchedulingService;
import org.eclipse.scava.platform.analysis.data.WorkerService;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.model.MetricExecution;
import org.eclipse.scava.platform.analysis.data.types.AnalysisTaskStatus;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.LocalStorage;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.ProjectError;
import org.eclipse.scava.repository.model.ProjectExecutionInformation;

public class ProjectAnalyser {
	
	private Platform platform;
	private AnalysisSchedulingService schedulingService;;
	private WorkerService workerService;
	private int analysisThreadNumber;
	private Logger logger;

	
	public ProjectAnalyser(Platform platform, AnalysisSchedulingService schedulingService,WorkerService workerService ) {
		this.analysisThreadNumber = Runtime.getRuntime().availableProcessors();		
		this.platform = platform;
		this.schedulingService = schedulingService;
		this.workerService = workerService;
	}

	public void executeAnalyse(String analysisTaskId, String workerId) {
		this.logger = OssmeterLogger.getLogger("ProjectExecutor (" + workerId + ":"+analysisTaskId +")");	

		AnalysisTask task = this.schedulingService.getRepository().getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);	
		Project project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName(task.getProject().getProjectId());

		logger.info("Beginning execution.");
		initialiseProjectLocalStorage(project);
		
		// Clear any open flags
		project.getExecutionInformation().setInErrorState(false);
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
		// Split metrics into branches
		List<IMetricProvider> paltformMetricProviders = platform.getMetricProviderManager().getMetricProviders();
		List<IMetricProvider> filtredMetricProvider = filterMetricProvider(paltformMetricProviders,analysisTaskId);
		List<IMetricProvider> factoids = extractFactoidProviders(filtredMetricProvider);
		
		logger.info("Creating metric branches.");
		List<List<IMetricProvider>> metricBranches = splitIntoBranches(filtredMetricProvider);
		logger.info("Created metric branches.");
		

		Date enecutionDate = new Date(task.getScheduling().getCurrentDate());
		
		Date[] dates = Date.range(enecutionDate, new Date().addDays(-1));
		logger.info("Dates: " + dates.length);
		
		 
		for (Date date : dates) {	
			java.util.Date dailyExecution = new java.util.Date();
			
			this.schedulingService.newDailyTaskExecution(analysisTaskId,date.toJavaDate());

			task = this.schedulingService.getRepository().getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);
			if(task.getScheduling().getStatus().equals(AnalysisTaskStatus.STOP.name())){
				logger.info("Analysis Task Execution  '" +analysisTaskId +"'STOPED at [ "+ date + " ]");
				return;
			}
			
			if(task.getScheduling().getStatus().equals(AnalysisTaskStatus.PENDING_STOP.name())){
				task.getScheduling().setStatus(AnalysisTaskStatus.STOP.name());
				task.getScheduling().setWorkerId(null);	
				this.schedulingService.getRepository().sync();
				logger.info("Analysis Task Execution  '" +analysisTaskId +"'STOPED at [ "+ date + " ]");
				return;
			}
			
//			List<List<IMetricProvider>> dailyBranches = filterMetricByExecutionDate(metricBranches,date,project.getShortName());
//			if(dailyBranches.isEmpty()) {
//				logger.info("All Metric Already Executed at this Date: " + date + ", project: " + project.getName());
//				
//				continue;
//			}

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
				return;
			}
			
			for (List<IMetricProvider> branch : metricBranches) {
				MetricListExecutor mExe = new MetricListExecutor(project.getShortName(),analysisTaskId, delta, date);
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
				MetricListExecutor mExe = new MetricListExecutor(project.getShortName(),analysisTaskId, delta, date);
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
			
			task.getScheduling().setLastDailyExecutionDuration(new java.util.Date().getTime() - dailyExecution.getTime());
			this.schedulingService.getRepository().sync();
		}
		
		logger.info("Analysis Task Execution complete  '" +analysisTaskId +"' by worker '" + workerId +"'");
	}

//	private List<List<IMetricProvider>> filterMetricByExecutionDate(List<List<IMetricProvider>> metricBranches,Date date,String projectId) {
//		List<List<IMetricProvider>> filtredBranchs = new ArrayList<>();
//		
//		
//		for(List<IMetricProvider> branch : metricBranches) {
//			List<IMetricProvider> filtredBranch = new ArrayList<>();		
//			for(IMetricProvider metric : branch) {
//				if(!alreadyExecuted(metric,date,projectId)) {
//					filtredBranch.add(metric);
//				}
//			}
//			if(!filtredBranch.isEmpty()) {
//				filtredBranchs.add(filtredBranch);
//			}
//			
//		}		
//		return filtredBranchs;
//	}
//
//	private boolean alreadyExecuted(IMetricProvider metric, Date date,String projectId) {
//		ProjectMetricProvider mpd = schedulingService.findMetricProviderScheduling(projectId,metric.getIdentifier());
//		try {
//			Date lastExec = new Date(mpd.getLastExecutionDate());	
//			// Check we haven't already executed the MP for this day.
//			if (date.compareTo(lastExec) < 0) {
//				return true;
//			}
//		}  catch (NumberFormatException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}

	private List<IMetricProvider> filterMetricProvider(List<IMetricProvider> metricProviders, String analysisTaskId) {
		List<IMetricProvider> filtredProviders = new ArrayList<>();
		AnalysisTask task = this.schedulingService.getRepository().getAnalysisTasks().findOneByAnalysisTaskId(analysisTaskId);		
		
		Set<String> taskProviders = new HashSet<>();
		for(MetricExecution provider : task.getMetricExecutions()) {
			taskProviders.add(provider.getMetricProviderId());
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
