/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.platform;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.crossmeter.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.eclipse.crossmeter.platform.delta.vcs.PlatformVcsManager;
import org.eclipse.crossmeter.repository.model.Project;

import com.mongodb.DB;

public class MetricProviderContext {
	
	protected Date date;
	protected Platform platform;
	protected Logger logger;
	
	public MetricProviderContext(Platform platform, Logger logger) {
		this.platform = platform;
		this.logger = logger;
	}
	
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
	
	public PlatformVcsManager getPlatformVcsManager() {
		return platform.getVcsManager();
	}
	
	public PlatformCommunicationChannelManager getPlatformCommunicationChannelManager() {
		return platform.getCommunicationChannelManager();
	}
	
	public PlatformBugTrackingSystemManager getPlatformBugTrackingSystemManager() {
		return platform.getBugTrackingSystemManager();
	}
	
	public DB getProjectDB(Project project) {
		return platform.getMetricsRepository(project).getDb();
	}
	
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	public Logger getLogger() {
		return logger;
	}
	
	public IProgressMonitor getProgressMonitor() {
		return null; // TODO
	}
}
