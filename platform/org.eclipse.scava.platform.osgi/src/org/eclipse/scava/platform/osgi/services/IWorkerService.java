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

public interface IWorkerService {

	public String getIdentifier();
	
	public void pause();
	
	public void resume();
	
	public void shutdown();
	
	public abstract boolean queueProjects(List<String> projects);

	public abstract SchedulerStatus getStatus();

}
