/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.importer.sourceforge;
import java.util.List;

import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.ProjectExecutionInformation;
import org.eclipse.scava.repository.model.importer.exception.WrongUrlException;
import org.eclipse.scava.repository.model.sourceforge.SourceForgeProject;
import org.eclipse.scava.repository.model.sourceforge.importer.SourceforgeProjectImporter;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;
public class SourceForgeImporterProvider  implements ITransientMetricProvider {

	public final static String IDENTIFIER = 
			"org.eclipse.scava.metricprovider.trans.sourceforgeimporter";
	
	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;

	private OssmeterLogger logger;
	
	public SourceForgeImporterProvider()
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.importer.sourceforge");
	}
	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return IDENTIFIER;
	}

	@Override
	public String getShortIdentifier() {
		// TODO Auto-generated method stub
		return "sourceforgeimporter";
	}

	@Override
	public String getFriendlyName() {
		return "SourceForge importer";
	}

	@Override
	public String getSummaryInformation() {
		return "This provider enable to update a projects calling a importProject from sourceforge importer";
	}

	@Override
	public boolean appliesTo(Project project) {
		return (project instanceof SourceForgeProject) ? true : false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;

	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return null;
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;

	}

	@Override
	public PongoDB adapt(DB db) {
		return null;
	}

	@Override
	public void measure(Project project, ProjectDelta delta, PongoDB db) {
		SourceForgeProject ep = null;
		SourceforgeProjectImporter epi = new SourceforgeProjectImporter();
		Platform platform = Platform.getInstance();
		try {
			ep = epi.importProject(project.getShortName(), platform);
			if(ep == null)
			{
				if(project.getExecutionInformation() == null)
					project.setExecutionInformation(new ProjectExecutionInformation());
				project.getExecutionInformation().setInErrorState(true);
			}
		} catch (WrongUrlException e) {
			logger.error("Error launch sourceforge importer: Wrong Url or ProjectId");
			if(project.getExecutionInformation() == null)
				project.setExecutionInformation(new ProjectExecutionInformation());
			project.getExecutionInformation().setInErrorState(true);
		}
	}

}
