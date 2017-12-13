/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.crossmeter.platform;

import static java.nio.file.Paths.get;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.ExtensionPointBugTrackingSystemManager;
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.crossmeter.platform.delta.communicationchannel.ExtensionPointCommunicationChannelManager;
import org.eclipse.crossmeter.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.eclipse.crossmeter.platform.delta.vcs.ExtensionPointVcsManager;
import org.eclipse.crossmeter.platform.delta.vcs.PlatformVcsManager;
import org.eclipse.crossmeter.repository.model.Project;

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
	protected IMetricProviderManager metricProviderManager = null;
	protected PlatformVcsManager vcsManager = null;
	protected PlatformCommunicationChannelManager communicationChannelManager = null;
	protected PlatformBugTrackingSystemManager bugTrackingSystemManager = null;
	protected Mongo mongo;
	
	// FIXME: Take from config
	protected final Path localStorageHomeDirectory = get(System.getProperty("user.home"), "crossmeter");

	public Platform(Mongo mongo) {
		INSTANCE = this;
		this.mongo = mongo;
		projectRepositoryManager = new ProjectRepositoryManager(mongo);
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
		return projectRepositoryManager;
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
