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

public interface IWorkerService {

	public String getIdentifier();
	
	public void pause();
	
	public void resume();
	
	public void shutdown();
	
	public abstract boolean queueProjects(List<String> projects);

	public abstract SchedulerStatus getStatus();

}