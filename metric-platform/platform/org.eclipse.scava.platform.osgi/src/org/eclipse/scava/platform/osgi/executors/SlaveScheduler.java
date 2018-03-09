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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.SchedulingInformation;

import com.mongodb.Mongo;

public class SlaveScheduler {

	protected final int heartbeatTick = 10000;
	
	protected boolean alive = true;
	protected Object master;
	
	protected SchedulerStatus status;
	protected List<String> queue;
	
	protected Thread worker;
	protected Thread heartbeat;
	final protected Mongo mongo;
	protected Platform platform;
	
	private volatile boolean running = true;
	
	public SlaveScheduler(Mongo mongo) {
		status = SchedulerStatus.AVAILABLE;
		queue = new ArrayList<String>();
		this.mongo = mongo;
		
		// FIXME: This should be passed configuration information
		// specifying local storage location etc.
		this.platform = new Platform(mongo);
	}
	
	public boolean queueProjects(List<String> projects) {
		if (status.equals(SchedulerStatus.BUSY)) {
			return false;
		}
		synchronized (queue) {
			queue.addAll(projects);
			queue.notify();
		}
		return true;
	}

	public void run() {
		final String identifier = Configuration.getInstance().getSlaveIdentifier();
		
		heartbeat = new Thread() {
			@Override
			public void run() {
				while (alive) {
					SchedulingInformation job = platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().findOneByWorkerIdentifier(identifier);
					if (job == null) {
						job = new SchedulingInformation();
						job.setWorkerIdentifier(identifier);
						platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().add(job);
					}
					// TODO: One issue with this is that the machines clock's may differ..
					job.setHeartbeat(System.currentTimeMillis());
					platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().sync();
					
					try {
						Thread.sleep(heartbeatTick);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		heartbeat.start();
		
		worker = new Thread() {
			@Override
			public void run() {
				
				while (alive) {
					SchedulingInformation job = platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().findOneByWorkerIdentifier(identifier);
					
					if(job != null) {
						List<String> projects = job.getCurrentLoad();
						
						System.out.println("Slave '" + identifier + "' executing " + projects.size() + " projects.");
						
						for (String projectName : projects) {
							Project project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName(projectName); //FIXME This should be just NAME
							
							if (project == null) {
								System.err.println("DB lookup for project named '" + projectName + "' failed. Skipping.");
								continue;
							}
							
							ProjectExecutor exe = new ProjectExecutor(platform, project);
							exe.run();
						}
						
						// Update that database to accept new jobs
						job = platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().findOneByWorkerIdentifier(identifier);
						job.getCurrentLoad().clear();
						job.setWorkerIdentifier(job.getWorkerIdentifier()); // FIXME: We have to force dirtying
						platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().sync();
					}
					try {
						// Give the master time to schedule some new projects
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		worker.start();
	}
	
	public void pause() {
		try {
			worker.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void resume() {
		worker.notify();
	}
	
	public boolean finish() {
		alive = false;
		try {
			worker.join();
			heartbeat.join();
			return true;
		} catch (InterruptedException e) {
			return false;
		}
	}

	public SchedulerStatus getStatus() {
		return status;
	}
}
