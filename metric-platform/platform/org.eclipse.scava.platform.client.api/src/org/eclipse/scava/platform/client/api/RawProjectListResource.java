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

import java.util.Iterator;

import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.ProjectRepository;
import org.restlet.representation.Representation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class RawProjectListResource extends AbstractApiResource {

    public Representation doRepresent() {
		// TODO
		boolean paging = getRequest().getAttributes().containsKey("page");
		
		Platform platform = Platform.getInstance();
		ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
		
		Iterator<Project> it = projectRepo.getProjects().iterator();
	
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode projects = mapper.createArrayNode();
		
		while (it.hasNext()) {
			try {
				Project project  = it.next();
				
				ObjectNode p = mapper.createObjectNode();
				p.put("name", project.getName());
				p.put("description", project.getDescription());
				
				projects.add(p);
				
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
				ObjectNode m = mapper.createObjectNode();
				m.put("apicall", "list-all-projects");
				return Util.generateErrorMessageRepresentation(m, e.getMessage());
			}			
		}
		return Util.createJsonRepresentation(projects);
	}

	
}
