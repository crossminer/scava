package org.eclipse.scava.platform.client.api;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.AnalysisTaskService;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.model.MetricExecution;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class AnalysisTaskByAnalysisTaskId extends AbstractApiResource {

	@Override
	public Representation doRepresent() {
		try {
			Platform platform = new Platform(Configuration.getInstance().getMongoConnection());
			AnalysisTaskService service = platform.getAnalysisRepositoryManager().getTaskService();
			String analysisTaskId = (String) getRequest().getAttributes().get("analysistaskid");
			
			AnalysisTask analysisTask = service.getTaskByAnalysisTaskId(analysisTaskId);
			ArrayNode taskMetricExecutions = mapper.createArrayNode();

			try {
				List<Object> metricExecutions = new ArrayList<>();
				for (MetricExecution metric : analysisTask.getMetricExecutions()) {
					Map<String, String> newMetric = new HashMap<>();
					newMetric.put("metricProviderId", metric.getDbObject().get("metricProviderId").toString());
					newMetric.put("projectId", metric.getDbObject().get("projectId").toString());
					newMetric.put("lastExecutionDate", metric.getDbObject().get("lastExecutionDate").toString());
					metricExecutions.add(newMetric);
				}
				analysisTask.getDbObject().put("metricExecutions", metricExecutions);
				taskMetricExecutions.add(mapper.readTree(analysisTask.getDbObject().toString()));
				
			} catch (IOException e) {
				e.printStackTrace();
				StringRepresentation rep = new StringRepresentation("");
				rep.setMediaType(MediaType.APPLICATION_JSON);
				getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
				return rep;
			}
			
			StringRepresentation rep = new StringRepresentation(taskMetricExecutions.toString());
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.SUCCESS_OK);
			return rep;
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			StringRepresentation rep = new StringRepresentation("");
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return rep;
		}
		
	}

}
