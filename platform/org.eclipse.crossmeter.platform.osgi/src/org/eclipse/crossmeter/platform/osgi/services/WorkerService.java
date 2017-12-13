/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.platform.osgi.services;

import java.util.List;

import org.eclipse.crossmeter.platform.osgi.executors.SchedulerStatus;
import org.eclipse.crossmeter.platform.osgi.executors.SlaveScheduler;

import com.mongodb.Mongo;

public class WorkerService implements IWorkerService {

	protected SlaveScheduler scheduler;
	
	public WorkerService(Mongo mongo) {
		scheduler = new SlaveScheduler(mongo);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.crossmeter.platform.osgi.services.IWorkerService#queueProjects(java.util.List)
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
	 * @see org.eclipse.crossmeter.platform.osgi.services.IWorkerService#getStatus()
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
