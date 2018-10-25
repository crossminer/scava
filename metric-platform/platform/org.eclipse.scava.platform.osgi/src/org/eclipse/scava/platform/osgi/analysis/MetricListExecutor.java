/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.osgi.analysis;

import java.io.FileWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.IHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricHistoryManager;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.model.MetricExecution;
import org.eclipse.scava.platform.analysis.data.types.AnalysisTaskStatus;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.platform.logging.OssmeterLoggerFactory;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.ProjectError;

public class MetricListExecutor implements Runnable {
	
	protected FileWriter writer;

	final private String projectId;
	final private String taskId;
	protected List<IMetricProvider> metrics;
	protected ProjectDelta delta;
	protected Date date;
	protected OssmeterLogger logger;
	protected Platform platform;
	
	// FIXME: The delta object already references a Project object. Rascal metrics seem to
	public MetricListExecutor(Platform platform,String projectId,String taskId, ProjectDelta delta, Date date) {
		this.projectId = projectId;
		this.taskId = taskId;
		this.delta = delta;
		this.date = date;
		this.logger = (OssmeterLogger) OssmeterLogger.getLogger("MetricListExecutor (" + projectId + ", " + date.toString() + ")");
		this.platform = platform;
	}
	
	public void setMetricList(List<IMetricProvider> metrics) {
		this.metrics = metrics;
	}
	
	private final ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
	
	private long now() {
		return  bean.isCurrentThreadCpuTimeSupported( ) ? bean.getCurrentThreadCpuTime( ) / 1000000: -1L;
	}
	
	@Override
	public void run() {

		Project project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName(projectId);
		AnalysisTask task = platform.getAnalysisRepositoryManager().getRepository().getAnalysisTasks().findOneByAnalysisTaskId(this.taskId);
		

		for (IMetricProvider m : metrics) {
			if(task.getScheduling().getStatus().equals(AnalysisTaskStatus.PENDING_STOP.name()) || task.getScheduling().getStatus().equals(AnalysisTaskStatus.STOP.name())){
				return;
			}
			
			m.setMetricProviderContext(new MetricProviderContext(platform, OssmeterLoggerFactory.getInstance().makeNewLoggerInstance(m.getIdentifier())));
			addDependenciesToMetricProvider(platform, m);
			
		
			
			platform.getAnalysisRepositoryManager().getSchedulingService().startMetricExecution(this.taskId,  m.getIdentifier());
			
			// We need to check that it hasn't already been executed for this date
			MetricExecution mpd = platform.getAnalysisRepositoryManager().getSchedulingService().findMetricExecution(this.projectId,m.getIdentifier());
			try {
				Date lastExec = new Date(mpd.getLastExecutionDate());	
				// Check we haven't already executed the MP for this day.
				if (date.compareTo(lastExec) < 0) {
					logger.warn("Metric provider '" + m.getIdentifier() + "' has been executed for this date already. Ignoring.");
					platform.getAnalysisRepositoryManager().getSchedulingService().endMetricExecution(this.projectId, this.taskId,  m.getIdentifier());
					continue;
				}
			}  catch (NumberFormatException e) {
				e.printStackTrace();
			}
			
			try {
				if (m.appliesTo(project)) {
					logger.info("Start Metric Execution ("+m.getShortIdentifier()+").");
					if (m instanceof ITransientMetricProvider) {
						((ITransientMetricProvider) m).measure(project, delta, ((ITransientMetricProvider) m).adapt(platform.getMetricsRepository(project).getDb()));
					} else if (m instanceof IHistoricalMetricProvider) {
						MetricHistoryManager historyManager = new MetricHistoryManager(platform);
						historyManager.store(project, date, (IHistoricalMetricProvider) m);
					}

					logger.info("Metric Executed ("+m.getShortIdentifier()+").");
				}
			} catch (Exception e) {
				logger.error("Exception thrown during metric provider execution ("+m.getShortIdentifier()+").", e);
				project.getExecutionInformation().setInErrorState(true);
				platform.getProjectRepositoryManager().getProjectRepository().sync();
				
				// Log in DB
				ProjectError error = ProjectError.create(date.toString(), "MetricListExecutor: " + m.getIdentifier(), projectId, project.getName(), e, Configuration.getInstance().getSlaveIdentifier());
				platform.getProjectRepositoryManager().getProjectRepository().getErrors().add(error);
				platform.getProjectRepositoryManager().getProjectRepository().getErrors().sync();
				
				break;
			}finally {
				platform.getAnalysisRepositoryManager().getSchedulingService().endMetricExecution(this.projectId, this.taskId,  m.getIdentifier());
			}		
		}
	}

	/**
	 * Adds references to the dependencies of a metric provider so that they
	 * can use their data for the calculations.
	 * 
	 * FIXME: This seems like an inefficient approach. Look at this later.
	 * @param mp
	 */
	protected void addDependenciesToMetricProvider(Platform platform, IMetricProvider mp) {
		if (mp.getIdentifiersOfUses() == null) return; 
		
		List<IMetricProvider> uses = new ArrayList<IMetricProvider>();
		for (String id : mp.getIdentifiersOfUses()) {
			for (IMetricProvider imp : platform.getMetricProviderManager().getMetricProviders()) {
				if (imp.getIdentifier().equals(id)) {
					uses.add(imp);
					break;
				}
			}
		}
		mp.setUses(uses);
	}

}
