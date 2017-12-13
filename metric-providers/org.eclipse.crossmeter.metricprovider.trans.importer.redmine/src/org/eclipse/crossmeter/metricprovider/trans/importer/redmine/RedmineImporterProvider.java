/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 	Davide Di Ruscio- Implementation,
 *    Juri Di Rocco - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.trans.importer.redmine;

import java.util.List;

import org.eclipse.crossmeter.platform.IMetricProvider;
import org.eclipse.crossmeter.platform.ITransientMetricProvider;
import org.eclipse.crossmeter.platform.MetricProviderContext;
import org.eclipse.crossmeter.platform.Platform;
import org.eclipse.crossmeter.platform.delta.ProjectDelta;
import org.eclipse.crossmeter.platform.logging.OssmeterLogger;
import org.eclipse.crossmeter.repository.model.Project;
import org.eclipse.crossmeter.repository.model.ProjectExecutionInformation;
import org.eclipse.crossmeter.repository.model.importer.exception.RepoInfoNotFound;
import org.eclipse.crossmeter.repository.model.redmine.RedmineProject;
import org.eclipse.crossmeter.repository.model.redmine.importer.RedmineImporter;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class RedmineImporterProvider implements ITransientMetricProvider {
	public final static String IDENTIFIER = 
			"org.eclipse.crossmeter.metricprovider.trans.redmineimporter";
	
	protected MetricProviderContext context;
	OssmeterLogger logger;
	public RedmineImporterProvider()
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("Redmine Importer");
	}
	/**
	 * List of MPs that are used by this MP. These are MPs who have specified that 
	 * they 'provide' data for this MP.
	 */
	protected List<IMetricProvider> uses;
	
	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return IDENTIFIER;
	}

	@Override
	public String getShortIdentifier() {
		// TODO Auto-generated method stub
		return "redmineimporter";
	}

	@Override
	public String getFriendlyName() {
		// TODO Auto-generated method stub
		return "Redmine importer";
	}

	@Override
	public String getSummaryInformation() {
		// TODO Auto-generated method stub
		return "This provider enable to update a projects calling a importProject from redmine importer";
	}

	@Override
	public boolean appliesTo(Project project) {
		return (project instanceof RedmineProject) ? true : false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;

	}

	@Override
	public List<String> getIdentifiersOfUses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public PongoDB adapt(DB db) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void measure(Project project, ProjectDelta delta, PongoDB db) {
		RedmineProject ep = null;
			RedmineImporter epi = new RedmineImporter();
			
			Platform platform = Platform.getInstance();
			try {
				ep = epi.importProject(project.getShortName(), platform);
				if(ep == null)
				{
					if(project.getExecutionInformation() == null)
						project.setExecutionInformation(new ProjectExecutionInformation());
					project.getExecutionInformation().setInErrorState(true);
				}
			} catch (RepoInfoNotFound e) {			
				if(project.getExecutionInformation() == null)
					project.setExecutionInformation(new ProjectExecutionInformation());
				project.getExecutionInformation().setInErrorState(true);
			}
			//, "http://mancoosi.di.univaq.it/redmine/","juri","juri", "369fb37d8ca43f186505f588a14809a294aea732");
			
			
		
		
	}
}
