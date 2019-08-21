/*******************************************************************************
 * Copyright (c) 2018 Softeam
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.client.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.eclipse.scava.repository.model.ProjectError;
import org.eclipse.scava.repository.model.ProjectRepository;
import org.restlet.representation.Representation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class AnalysisStackTracesResource extends AbstractApiResource {
	
	public Representation doRepresent() {
		
		/**
		 * Fetch the analysis stacktraces
		 */
		
		ArrayNode results = mapper.createArrayNode();
		ProjectRepository projectRepository = platform.getProjectRepositoryManager().getProjectRepository();
		for (ProjectError projectError : projectRepository.getErrors()) {
			ObjectNode error = mapper.createObjectNode();
			error.put("projectId", projectError.getDbObject().get("projectId").toString());
			error.put("projectName", projectError.getDbObject().get("projectName").toString());
			error.put("clazz", projectError.getDbObject().get("clazz").toString());
			error.put("stackTrace", projectError.getDbObject().get("stackTrace").toString().replaceAll("\n", "").replaceAll("\t", ""));
			error.put("workerIdentifier", projectError.getDbObject().get("workerIdentifier").toString());
			try {
				SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat parser1 = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy",Locale.UK);
				String executionDateError = projectError.getDbObject().get("date").toString();
				error.put("executionErrorDate", formater.format(parser1.parse(executionDateError)));
				
				SimpleDateFormat parser2 = new SimpleDateFormat("yyyyMMdd");
				String analysisDateError = projectError.getDbObject().get("dateForError").toString();
				error.put("analysisRangeErrorDate", formater.format(parser2.parse(analysisDateError)));
			} catch (ParseException e) {
				e.printStackTrace();
			}			
			results.add(error);
		}
		
		return Util.createJsonRepresentation(results);
	}
}
