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
package org.eclipse.crossmeter.metricprovider.trans.importer.eclipse;


import java.util.List;

import org.eclipse.crossmeter.platform.IMetricProvider;
import org.eclipse.crossmeter.platform.ITransientMetricProvider;
import org.eclipse.crossmeter.platform.MetricProviderContext;
import org.eclipse.crossmeter.platform.Platform;
import org.eclipse.crossmeter.platform.delta.ProjectDelta;
import org.eclipse.crossmeter.platform.logging.OssmeterLogger;
import org.eclipse.crossmeter.repository.model.Project;
import org.eclipse.crossmeter.repository.model.ProjectExecutionInformation;
import org.eclipse.crossmeter.repository.model.eclipse.EclipseProject;
import org.eclipse.crossmeter.repository.model.eclipse.importer.EclipseProjectImporter;
import org.eclipse.crossmeter.repository.model.importer.exception.ProjectUnknownException;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;



public class EclipseImporterProvider implements ITransientMetricProvider{
	public final static String IDENTIFIER = 
			"org.eclipse.crossmeter.metricprovider.trans.eclipseimporter";
	
	protected MetricProviderContext context;
	OssmeterLogger logger;
	public EclipseImporterProvider()
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("GitHub Importer");
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
		return "eclipseimporter";
	}

	@Override
	public String getFriendlyName() {
		// TODO Auto-generated method stub
		return "Eclipse importer";
	}

	@Override
	public String getSummaryInformation() {
		// TODO Auto-generated method stub
		return "This provider imports the prpoject metadata from the Eclipse forge by calling the corresponding importer.";
	}

	@Override
	public boolean appliesTo(Project project) {
		return (project instanceof EclipseProject) ? true : false;
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
		// TODO Auto-generated method stub
		EclipseProject ep = null;
		EclipseProjectImporter epi = new EclipseProjectImporter();
		try 
		{
			Platform platform = Platform.getInstance();
			ep = epi.importProject(project.getShortName(), platform);
			if (ep==null )
			{	
				if(project.getExecutionInformation() == null)
					project.setExecutionInformation(new ProjectExecutionInformation());
				project.getExecutionInformation().setInErrorState(true);
			}
		} catch (ProjectUnknownException e) {
			logger.error("Error launch Eclipse importer: WreongUrl");
			if(project.getExecutionInformation() == null)
				project.setExecutionInformation(new ProjectExecutionInformation());
			project.getExecutionInformation().setInErrorState(true);
		}
	}
}
