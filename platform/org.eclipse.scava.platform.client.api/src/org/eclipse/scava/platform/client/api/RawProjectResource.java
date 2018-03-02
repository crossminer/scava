/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.client.api;

import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.ProjectRepository;
import org.restlet.data.Status;
import org.restlet.representation.Representation;

public class RawProjectResource extends AbstractApiResource {

	public Representation doRepresent() {	
		String projectName = (String) getRequest().getAttributes().get("projectid");
		
		ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
		Project p = projectRepo.getProjects().findOneByShortName(projectName);
		
		if (p == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return Util.generateErrorMessageRepresentation(generateRequestJson(mapper, projectName), "No project was found with the requested name.");
		}
		
		try {
			// TODO:
			return Util.createJsonRepresentation(mapper.writeValueAsString(p.getDbObject()));//mapper.writeValueAsString(p);//
		} catch (Exception e) {
			e.printStackTrace();
			return Util.generateErrorMessageRepresentation(generateRequestJson(mapper, projectName), "An error occurred when converting the project to JSON: " + e.getMessage());
		}
	}
}
