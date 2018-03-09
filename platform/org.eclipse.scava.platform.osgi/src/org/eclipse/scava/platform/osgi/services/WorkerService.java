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

import java.util.List;

import org.eclipse.scava.platform.osgi.executors.SchedulerStatus;
import org.eclipse.scava.platform.osgi.executors.SlaveScheduler;

import com.mongodb.Mongo;

public class WorkerService implements IWorkerService {

	protected SlaveScheduler scheduler;
	
	public WorkerService(Mongo mongo) {
		scheduler = new SlaveScheduler(mongo);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.scava.platform.osgi.services.IWorkerService#queueProjects(java.util.List)
	 */
	@Override
	public boolean queueProjects(List<String> projects) {
		if (getStatus().equals(SchedulerStatus.AVAILABLE)) {
			scheduler.queueProjects(projects);
			scheduler.run();
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.scava.platform.osgi.services.IWorkerService#getStatus()
	 */
	@Override
	public SchedulerStatus getStatus() {
		return scheduler.getStatus();
	}

	@Override
	public void pause() {
		scheduler.pause();
	}

	@Override
	public void resume() {
		scheduler.resume();
	}

	@Override
	public void shutdown() {
		boolean clean = scheduler.finish(); // Blocking. Waits for worker to complete.
		if (!clean) {
			throw new RuntimeException("Slave scheduler did not shutdown cleanly.");
		}
	}

	@Override
	public String getIdentifier() {
//		try {
			return "SLAVE - FIXME";//InetAddress.getLocalHost().getHostAddress(); //FIXME
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
//		return "Unknown IP"; //FIXME
	}
}
