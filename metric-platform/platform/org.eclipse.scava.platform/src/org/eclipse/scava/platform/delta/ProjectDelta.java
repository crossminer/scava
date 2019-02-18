/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.delta;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.IBugTrackingSystemManager;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelProjectDelta;
import org.eclipse.scava.platform.delta.communicationchannel.ICommunicationChannelManager;
import org.eclipse.scava.platform.delta.vcs.IVcsManager;
import org.eclipse.scava.platform.delta.vcs.VcsProjectDelta;
import org.eclipse.scava.platform.logging.AnalysisProcessLogger;
import org.eclipse.scava.repository.model.Project;

import com.mongodb.DB;

public class ProjectDelta {
	protected Date date;
	protected Project project;
	protected IVcsManager vcsManager;
	protected ICommunicationChannelManager communicationChannelManager;
	protected IBugTrackingSystemManager bugTrackingSystemManager;
	
	protected final Platform platform;
	protected VcsProjectDelta vcsDelta;
	protected CommunicationChannelProjectDelta communicationChannelDelta;
	protected BugTrackingSystemProjectDelta bugTrackingSystemDelta;

	protected AnalysisProcessLogger loggerAnalysisProcess;
	
	public ProjectDelta(Project project, Date date, Platform platform) {
//			IVcsManager vcsManager, 
//			ICommunicationChannelManager communicationChannelManager, 
//			IBugTrackingSystemManager bugTrackingSystemManager) {
		this.project = project;
		this.date = date;	
		this.vcsManager = platform.getVcsManager();
		this.communicationChannelManager = platform.getCommunicationChannelManager();
		this.bugTrackingSystemManager = platform.getBugTrackingSystemManager();
		this.platform = platform;
		
		this.loggerAnalysisProcess = (AnalysisProcessLogger)AnalysisProcessLogger.getLogger("ProjectDelta ("+project.getName() + "," + date.toString() + ")");
	}
	
	// TODO: Is it more important to execute SOME metrics or execute ALL metrics?
	// I.e. if just one info source throws an exception, can we still execute metrics
	// for the others? I think not. Next time we run the project we'll re-create
	// some deltas unnecessarily.
	public void create() throws Exception{
		loggerAnalysisProcess.info("Creating Delta");
		try {
			long startVcsDelta = System.currentTimeMillis();
			vcsDelta = new VcsProjectDelta(project, date, vcsManager);
			long endVcsDelta = System.currentTimeMillis();
			
			DB db = platform.getMetricsRepository(project).getDb();
			long startCCDelta = System.currentTimeMillis();
			communicationChannelDelta = new CommunicationChannelProjectDelta(db, project, date, communicationChannelManager);
			long endCCDelta = System.currentTimeMillis();
			
			long startBTSDelta = System.currentTimeMillis();
			bugTrackingSystemDelta = new BugTrackingSystemProjectDelta(db, project, date, bugTrackingSystemManager);
			long endBTSDelta = System.currentTimeMillis();
			loggerAnalysisProcess.info("Created Delta");
		} catch (Exception e) {
			loggerAnalysisProcess.error("Delta creation failed.", e);
			throw e;
		}
	}
	
	public Date getDate() {
		return date;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setVcsDelta(VcsProjectDelta vcsDelta) {
		this.vcsDelta = vcsDelta;
	}
	
	public VcsProjectDelta getVcsDelta() {
		return this.vcsDelta;
	}

	public void setCommunicationChannelDelta(CommunicationChannelProjectDelta communicationChannelDelta) {
		this.communicationChannelDelta = communicationChannelDelta;
	}
	
	public CommunicationChannelProjectDelta getCommunicationChannelDelta() {
		return this.communicationChannelDelta;
	}

	public void setBugTrackingSystemDelta(BugTrackingSystemProjectDelta bugTrackingSystemDelta) {
		this.bugTrackingSystemDelta = bugTrackingSystemDelta;
	}
	
	public BugTrackingSystemProjectDelta getBugTrackingSystemDelta() {
		return this.bugTrackingSystemDelta;
	}

}
