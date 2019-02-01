/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.gitlab.importer;

import java.io.IOException;
import java.net.MalformedURLException;

import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.gitlab.GitLabRepository;
import org.eclipse.scava.repository.model.importer.IImporter;
import org.eclipse.scava.repository.model.importer.dto.Credentials;
import org.eclipse.scava.repository.model.importer.exception.ProjectUnknownException;
import org.eclipse.scava.repository.model.importer.exception.RepoInfoNotFound;
import org.eclipse.scava.repository.model.importer.exception.WrongUrlException;

public class GitLabImporter implements IImporter {

	protected OssmeterLogger logger;

	public GitLabImporter() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("importer.gitLab");
	}

	@Override
	public Project importProject(String projectId, Platform platform)
			throws ProjectUnknownException, WrongUrlException, RepoInfoNotFound {
		throw new UnsupportedOperationException();
	}

	@Override
	public void importAll(Platform platform) throws RepoInfoNotFound {
		throw new UnsupportedOperationException();
	}

	@Override
	public GitLabRepository importProjectByUrl(String url, Platform platform)
			throws WrongUrlException, ProjectUnknownException, RepoInfoNotFound {
				
		
		return null;
	}

	@Override
	public void importProjects(Platform platform, int numberfOfProjects) throws WrongUrlException, RepoInfoNotFound {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isProjectInDB(String projectId, Platform platform)
			throws WrongUrlException, ProjectUnknownException, MalformedURLException, IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isProjectInDBByUrl(String projectId, Platform platform)
			throws WrongUrlException, ProjectUnknownException, MalformedURLException, IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCredentials(Credentials credentials) {
		throw new UnsupportedOperationException();
	}
}
