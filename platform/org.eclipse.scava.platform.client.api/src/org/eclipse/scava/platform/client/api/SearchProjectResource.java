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

import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.ProjectRepository;
import org.restlet.representation.Representation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SearchProjectResource extends AbstractApiResource {
	
	/**
	 * Query: 
	 * 		/search?q=epsilon
	 * TODO: Implement soundex?
	 * @return
	 */
	@Override
	public Representation doRepresent() {
		String query = getQueryValue("q");
		
		if (query == null || "".equals(query)){
			ObjectNode obj = mapper.createObjectNode();
			obj.put("msg", "No search term specified.");
			return Util.createJsonRepresentation(obj);
		}
		// TODO: need to escape the query
		query = "/^" + query + "$/i";
		
		ProjectRepository repo = platform.getProjectRepositoryManager().getProjectRepository();
		Iterator<Project> it = repo.getProjects().findByName(query).iterator();
		
		if (it.hasNext()) {
			ArrayNode arr = mapper.createArrayNode();
			while (it.hasNext()) {
				Project p = it.next();
				ObjectNode obj = mapper.createObjectNode();
				obj.put("id", p.getShortName()); // TODO
				obj.put("name", p.getName());
				obj.put("description", p.getDescription());
				arr.add(obj);
			}
			return Util.createJsonRepresentation(arr);
		} else {
			ObjectNode obj = mapper.createObjectNode();
			obj.put("msg", "No projects matched that query.");
			return Util.createJsonRepresentation(obj);
		}
	}
}
