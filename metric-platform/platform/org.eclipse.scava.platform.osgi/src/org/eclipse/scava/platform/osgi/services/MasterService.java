/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.osgi.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.SchedulingInformation;

import com.mongodb.Mongo;


public class MasterService implements IMasterService {

	protected List<IWorkerService> workers;
	protected MasterRunner runner;
	protected Mongo mongo;
	protected Platform platform; 
	
	protected Thread master;
	protected OssmeterLogger logger;
	
	public MasterService(List<IWorkerService> workers) {
		this.workers = workers;
		this.logger = (OssmeterLogger) OssmeterLogger.getLogger("Master");
	}
	
	@Override
	public void start() throws Exception {
		logger.info("Master service started.");
		
		mongo = Configuration.getInstance().getMongoConnection(); 
		platform = new Platform(mongo);
	
		// Now start scheduling
		master = new Thread() {
			@Override
			public void run() {
				// FIXME: Oh God, so many while loops.
				while (true) { // TODO: while alive
					Iterator<Project> it = platform.getProjectRepositoryManager().getProjectRepository().getProjects().iterator();
					
					while (it.hasNext()) {
						List<String> projects = new ArrayList<String>();
						
						while (it.hasNext()) {
							Project next = it.next();
							List<String> currentlyExecuting = getCurrentlyExecutingProjects();
							if (next.getExecutionInformation().getMonitor() && !currentlyExecuting.contains(next.getShortName())) {
								projects.add(next.getShortName());
							}
							if (projects.size() >= 1) break;
						}
						
						SchedulingInformation worker = null;
						while (worker == null) {
							worker = nextFreeWorker();
							if (worker == null) {
								try {
									logger.info("No workers available. Sleeping.");
									Thread.sleep(60000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							} else {
								logger.info("Queuing " + projects.size() + " on worker ");

								for (String p : projects)
									worker.getCurrentLoad().add(p);
								platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().sync();
							}
						}
					}
					logger.info("All projects scheduled. Repeating.");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		master.start();
	}
	
	protected List<String> getCurrentlyExecutingProjects() {
		List<String> projects = new ArrayList<>();
		Iterator<SchedulingInformation> it = platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().iterator();
		
		while (it.hasNext()) {
			SchedulingInformation job = it.next();

			// Ensure that we only count slaves who are still running 
			if (System.currentTimeMillis() - job.getHeartbeat() < 70000) {
				
				for (String p : job.getCurrentLoad()) { // Currently can't do addAll as Pongo hasn't implemented toArray
					projects.add(p);
				}
			}
		}
		
		return projects;
	}

	protected SchedulingInformation nextFreeWorker() {
		Iterator<SchedulingInformation> it = platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().iterator();
		
		while (it.hasNext()) {
			SchedulingInformation job = it.next();

			// Ensure that we only use slaves who are still running 
			if (System.currentTimeMillis() - job.getHeartbeat() < 70000) {
				if (job.getCurrentLoad() == null || job.getCurrentLoad().size() == 0) {
					return job;
				}
			}
		}
		return null;
	}
	
	@Override
	public void pause() {
//		master.wait(); // TODO
		
		for (IWorkerService worker : workers) {
			worker.pause();
		}
	}
	
	@Override
	public void resume() {
//		master.notify(); // TODO
		
		for (IWorkerService worker : workers) {
			worker.resume();
		}
	}

	@Override
	public void shutdown() {

		for (IWorkerService worker : workers) {
			worker.shutdown();
		}
		
		mongo.close();
	}

	class MasterRunner implements Runnable {
		public void run() {
			while (true) {
				
			}
		}
	}
}
