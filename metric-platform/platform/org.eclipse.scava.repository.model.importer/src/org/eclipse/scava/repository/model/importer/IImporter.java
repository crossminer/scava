/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.importer;

import java.io.IOException;
import java.net.MalformedURLException;

import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.importer.exception.ProjectUnknownException;
import org.eclipse.scava.repository.model.importer.exception.RepoInfoNotFound;
import org.eclipse.scava.repository.model.importer.exception.WrongUrlException;

public interface IImporter {
	public Project importProject(String projectId, Platform platform)
			throws ProjectUnknownException, WrongUrlException, RepoInfoNotFound;

	public void importAll(Platform platform) throws RepoInfoNotFound;

	public Project importProjectByUrl(String url, Platform platform)
			throws WrongUrlException, ProjectUnknownException, RepoInfoNotFound;

	public void importProjects(Platform platform, int numberfOfProjects) throws WrongUrlException, RepoInfoNotFound;

	public boolean isProjectInDB(String projectId, Platform platform)
			throws WrongUrlException, ProjectUnknownException,
			MalformedURLException, IOException;
	
	public boolean isProjectInDBByUrl(String projectId, Platform platform)
			throws WrongUrlException, ProjectUnknownException,
			MalformedURLException, IOException;
}
