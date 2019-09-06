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

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.AnalysisTaskService;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.types.AnalysisExecutionMode;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class AnalysisCreationTaskResource extends ServerResource {

	@Post
	public Representation createAnalysisTask(Representation entity) {
		Platform platform = Platform.getInstance();
		AnalysisTaskService service = platform.getAnalysisRepositoryManager().getTaskService();
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(entity.getText());
			String analysisTaskId = jsonNode.get("analysisTaskId").toString().replace("\"", "");
			String taskLabel = jsonNode.get("label").toString().replace("\"", "");
			String taskType = jsonNode.get("type").toString().replace("\"", "");

			Date taskStartDate = new SimpleDateFormat("dd/MM/yyyy")
					.parse(jsonNode.get("startDate").toString().replace("\"", ""));

			String projectId = jsonNode.get("projectId").toString().replace("\"", "");

			AnalysisTask task = new AnalysisTask();
			task.setAnalysisTaskId(analysisTaskId);
			task.setLabel(taskLabel);
			task.setStartDate(taskStartDate);

			if (taskType.equals(AnalysisExecutionMode.SINGLE_EXECUTION.name())) {
				task.setType(AnalysisExecutionMode.SINGLE_EXECUTION.name());
				Date taskEndDate = new SimpleDateFormat("dd/MM/yyyy")
						.parse(jsonNode.get("endDate").toString().replace("\"", ""));
				task.setEndDate(taskEndDate);
			} else if (taskType.equals(AnalysisExecutionMode.CONTINUOUS_MONITORING.name())) {
				task.setType(AnalysisExecutionMode.CONTINUOUS_MONITORING.name());
			}

			List<String> metricsProviders = new ArrayList<>();
			for (JsonNode metricProvider : (ArrayNode) jsonNode.get("metricProviders")) {
				metricsProviders.add(metricProvider.toString().replace("\"", ""));
			}
			
			service.validateMetricProviders(metricsProviders);

			AnalysisTask addedTask = service.createAnalysisTask(projectId, task, metricsProviders);

			StringRepresentation rep = new StringRepresentation(addedTask.getDbObject().toString());
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.SUCCESS_CREATED);
			return rep;
			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			StringRepresentation rep = new StringRepresentation("");
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return rep;
		}
	}

}
