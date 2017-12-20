/*******************************************************************************
 * Copyright (c) 2017 aabherve
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.platform.osgi.executors;

import java.io.FileWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.crossmeter.platform.Configuration;
import org.eclipse.crossmeter.platform.Date;
import org.eclipse.crossmeter.platform.IHistoricalMetricProvider;
import org.eclipse.crossmeter.platform.IMetricProvider;
import org.eclipse.crossmeter.platform.ITransientMetricProvider;
import org.eclipse.crossmeter.platform.MetricHistoryManager;
import org.eclipse.crossmeter.platform.MetricProviderContext;
import org.eclipse.crossmeter.platform.Platform;
import org.eclipse.crossmeter.platform.delta.ProjectDelta;
import org.eclipse.crossmeter.platform.logging.OssmeterLogger;
import org.eclipse.crossmeter.platform.logging.OssmeterLoggerFactory;
import org.eclipse.crossmeter.repository.model.MetricAnalysis;
import org.eclipse.crossmeter.repository.model.MetricProviderExecution;
import org.eclipse.crossmeter.repository.model.MetricProviderType;
import org.eclipse.crossmeter.repository.model.Project;
import org.eclipse.crossmeter.repository.model.ProjectError;

import com.mongodb.Mongo;

public class MetricListExecutor implements Runnable {
	
	protected FileWriter writer;

	final protected String projectId;
	protected List<IMetricProvider> metrics;
	protected ProjectDelta delta;
	protected Date date;
	protected OssmeterLogger logger;
	
	// FIXME: The delta object already references a Project object. Rascal metrics seem to
	// use this for some reason. Is it an issue???????
	public MetricListExecutor(String projectId, ProjectDelta delta, Date date) {
		this.projectId = projectId;
		this.delta = delta;
		this.date = date;
		this.logger = (OssmeterLogger) OssmeterLogger.getLogger("MetricListExecutor (" + projectId + ", " + date.toString() + ")");
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
		Mongo mongo;
		try {
			mongo = Configuration.getInstance().getMongoConnection();
		} catch (UnknownHostException e2) {
			e2.printStackTrace(); // FIXME appropriately log
			return;
		}
		Platform platform = new Platform(mongo);

		Project project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName(projectId);
		
		int transientMetric =0;
		int historicMetric =0;

		for (IMetricProvider m : metrics) {
			
			m.setMetricProviderContext(new MetricProviderContext(platform, OssmeterLoggerFactory.getInstance().makeNewLoggerInstance(m.getIdentifier())));
			addDependenciesToMetricProvider(platform, m);
			
			// We need to check that it hasn't already been excuted for this date
			// e.g. in cases where a different MP 
			
			
			MetricProviderType type = MetricProviderType.TRANSIENT;
			if (m instanceof IHistoricalMetricProvider) {
				type = MetricProviderType.HISTORIC;
				historicMetric++;
			}
			else {
				transientMetric++;
			}
			
			MetricProviderExecution mpd = getProjectModelMetricProvider(project, m);
			if (mpd == null) {
				mpd = new MetricProviderExecution();
				mpd.setMetricProviderId(m.getIdentifier());
				mpd.setType(type);
				project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName(project.getShortName());
				project.getExecutionInformation().getMetricProviderData().add(mpd);
				platform.getProjectRepositoryManager().getProjectRepository().sync();
			}
			
			try {
				Date lastExec = new Date(mpd.getLastExecuted());
				
				// Check we haven't already executed the MP for this day.
				if (date.compareTo(lastExec) < 0) {
					logger.warn("Metric provider '" + m.getIdentifier() + "' has been executed for this date already. Ignoring.");
					continue;
				}
			} catch (ParseException e) {
				// we can ignore this
			} catch (NumberFormatException e) {
				// We can ignore this
			}
			
			// Performance analysis
			MetricAnalysis mAnal = new MetricAnalysis();
			mAnal.setMetricId(m.getIdentifier());
			mAnal.setProjectId(project.getShortName()); // FIXME
			mAnal.setAnalysisDate(date.toJavaDate());
			mAnal.setExecutionDate(new java.util.Date());
			platform.getProjectRepositoryManager().getProjectRepository().getMetricAnalysis().add(mAnal);
			long start = now(); // TODO: Could edit the generated code to encapsulate this.

			// Now execute
			try {
				if (m.appliesTo(project)) {
					if (m instanceof ITransientMetricProvider) {
						((ITransientMetricProvider) m).measure(project, delta, ((ITransientMetricProvider) m).adapt(platform.getMetricsRepository(project).getDb()));
					} else if (m instanceof IHistoricalMetricProvider) {
						MetricHistoryManager historyManager = new MetricHistoryManager(platform);
						historyManager.store(project, date, (IHistoricalMetricProvider) m);
					}
					
					// Update the meta data -- need to requery the database due to Pongo caching in different threads(!)
					project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName(project.getShortName());
					mpd = getProjectModelMetricProvider(project, m);
				
					if (mpd == null) {	//FIXME
						mpd = new MetricProviderExecution();
						mpd.setMetricProviderId(m.getIdentifier());
						mpd.setType(type);
						project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName(project.getShortName());
						project.getExecutionInformation().getMetricProviderData().add(mpd);
					}
					platform.getProjectRepositoryManager().getProjectRepository().sync();
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
			}
			
			mAnal.setMillisTaken(now() - start);
			platform.getProjectRepositoryManager().getProjectRepository().getMetricAnalysis().sync();
		}
		mongo.close();
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
	
	/**
	 * Ensures that the project DB has the up-to-date information regarding
	 * the date of last execution.
	 * @param project
	 * @param provider
	 * @param date
	 * @param type
	 */
	protected void updateMetricProviderMetaData(Platform platform, Project project, IMetricProvider provider, Date date, MetricProviderType type) {
		// Update project MP meta-data
		MetricProviderExecution mp = getProjectModelMetricProvider(project, provider);
		if (mp == null) {
			mp = new MetricProviderExecution();
			project.getExecutionInformation().getMetricProviderData().add(mp);
			mp.setMetricProviderId(provider.getShortIdentifier());
			mp.setType(type);
		}	
		mp.setLastExecuted(date.toString()); 
		platform.getProjectRepositoryManager().getProjectRepository().sync();
	}
	
	/**
	 * 
	 * @param project
	 * @param iProvider
	 * @return A MetricProvider (part of the Project DB) that matches the given IMetricProvider.
	 */
	protected MetricProviderExecution getProjectModelMetricProvider(Project project, IMetricProvider iProvider) {
		Iterator<MetricProviderExecution> it = project.getExecutionInformation().getMetricProviderData().iterator();
		MetricProviderExecution mp = null;
		while (it.hasNext()) {
			mp = it.next();
			if (mp == null) continue; //FIXME: intermittent bug adds nulls, but should have been fixed by synchonized block
			if (mp.getMetricProviderId().equals(iProvider.getIdentifier())) {
				return mp;
			}
		}

		return null;
	}
}
