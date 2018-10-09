/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform;

import static java.nio.file.Paths.get;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.scava.platform.delta.bugtrackingsystem.ExtensionPointBugTrackingSystemManager;
import org.eclipse.scava.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.scava.platform.delta.communicationchannel.ExtensionPointCommunicationChannelManager;
import org.eclipse.scava.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.eclipse.scava.platform.delta.vcs.ExtensionPointVcsManager;
import org.eclipse.scava.platform.delta.vcs.PlatformVcsManager;
import org.eclipse.scava.repository.model.Project;

import com.mongodb.Mongo;

public class Platform {
	
	/*
	 * We need a static reference so that we can get hold of the platform from the
	 * client API.
	 */
	protected static Platform INSTANCE;
	
	public static Platform getInstance() {
		return INSTANCE;
	}

	protected ProjectRepositoryManager projectRepositoryManager = null;
	protected AnalysisRepositoryManager analysisRepositoryManager = null;
	protected IMetricProviderManager metricProviderManager = null;
	protected PlatformVcsManager vcsManager = null;
	protected PlatformCommunicationChannelManager communicationChannelManager = null;
	protected PlatformBugTrackingSystemManager bugTrackingSystemManager = null;
	protected Mongo mongo;
	
	// FIXME: Take from config
	protected final Path localStorageHomeDirectory = get(System.getProperty("user.home"), "scava");

	public Platform(Mongo mongo) {
		INSTANCE = this;
		this.mongo = mongo;
		projectRepositoryManager = new ProjectRepositoryManager(mongo);
		analysisRepositoryManager = new AnalysisRepositoryManager(mongo);
		metricProviderManager = new ExtensionPointMetricProviderManager();
		vcsManager = new ExtensionPointVcsManager(this);
		communicationChannelManager = new ExtensionPointCommunicationChannelManager(this);
		bugTrackingSystemManager = new ExtensionPointBugTrackingSystemManager(this);
		initialisePlatformLocalStorage();
	}
	
	public Path getLocalStorageHomeDirectory() {
		return localStorageHomeDirectory;
	}
	
	public MetricsRepository getMetricsRepository(Project project) {
		return new MetricsRepository(project, mongo);
	}
	
	public ProjectRepositoryManager getProjectRepositoryManager() {
		return this.projectRepositoryManager;
	}
	
	
	public AnalysisRepositoryManager getAnalysisRepositoryManager() {
		return this.analysisRepositoryManager;
	}
	
	public IMetricProviderManager getMetricProviderManager() {
		return metricProviderManager;
	}
	
	public void setMetricProviderManager(IMetricProviderManager metricProviderManager) {
		this.metricProviderManager = metricProviderManager;
	}
	
	public PlatformVcsManager getVcsManager() {
		return vcsManager;
	}
	
	public void setPlatformVcsManager(PlatformVcsManager vcsManager) {
		this.vcsManager = vcsManager;
	}
	
	public PlatformCommunicationChannelManager getCommunicationChannelManager() {
		return communicationChannelManager;
	}
	
	public void setPlatformCommunicationChannelManager(PlatformCommunicationChannelManager communicationChannelManager) {
		this.communicationChannelManager = communicationChannelManager;
	}
	
	public PlatformBugTrackingSystemManager getBugTrackingSystemManager() {
		return bugTrackingSystemManager;
	}
	
	public void setPlatformBugTrackingSystemManager(PlatformBugTrackingSystemManager bugTrackingSystemManager) {
		this.bugTrackingSystemManager = bugTrackingSystemManager;
	}
	
	private void initialisePlatformLocalStorage() {
		try{	
			if (Files.notExists(localStorageHomeDirectory)) {
					Files.createDirectory(localStorageHomeDirectory);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
