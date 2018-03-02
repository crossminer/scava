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

import org.eclipse.scava.repository.model.ProjectRepository;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Put;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ProjectResource extends AbstractApiResource {

	public Representation doRepresent() {	
		String projectId = (String) getRequest().getAttributes().get("projectid");
		
		ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
		
		// FIXME: This exclusion list needs to be somewhere... 
		BasicDBObject ex = new BasicDBObject("executionInformation", 0);
		ex.put("storage", 0);
		ex.put("metricProviderData", 0);
		ex.put("_superTypes", 0);
		ex.put("_id", 0);
		
		// FIXME: Temporary solution to DBRefs not being expanded
		// How can we auto-expand and DBRefs when serialising?
		ex.put("licenses", 0);
		ex.put("persons", 0);
		ex.put("companies", 0);
		
		BasicDBObject query = new BasicDBObject("shortName", projectId);
		
		DBObject p = projectRepo.getProjects().getDbCollection().findOne(query, ex);

		if (p == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return Util.generateErrorMessageRepresentation(generateRequestJson(mapper, projectId), "No project was found with the requested name.");
		}
		
		try {
			StringRepresentation resp = new StringRepresentation(p.toString());
			resp.setMediaType(MediaType.APPLICATION_JSON);
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
			getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
			return Util.generateErrorMessageRepresentation(generateRequestJson(mapper, projectId), "An error occurred when converting the project to JSON: " + e.getMessage());
		}
	}
	
	/**
	 * This is an update to the existing project (identified by projectId field)
	 * @param entity
	 */
	@Put("json")
	public void updateProject(Representation entity) {
		
	}
}
