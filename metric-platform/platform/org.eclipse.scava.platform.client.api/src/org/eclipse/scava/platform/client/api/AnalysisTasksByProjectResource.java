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
import com.mongodb.Mongo;

public class AnalysisTasksByProjectResource extends AbstractApiResource {

	@Override
	public Representation doRepresent() {
		Mongo mongo = null;
		try {
			mongo = Configuration.getInstance().getMongoConnection();
			platform = new Platform(mongo);				
			AnalysisTaskService service = platform.getAnalysisRepositoryManager().getTaskService();
			String projectId = (String) getRequest().getAttributes().get("projectid");
			
			List<AnalysisTask> tasks = service.getAnalysisTasksByProject(projectId);
			
			ArrayNode tasksByProject = mapper.createArrayNode();
			
			for (AnalysisTask task : tasks) {
				try {
					task.getDbObject().removeField("_id");
					task.getDbObject().removeField("_type");
					task.getDbObject().removeField("project");
					
					List<Object> metricProviders = new ArrayList<>();
					for (MetricExecution metric : task.getMetricExecutions()) {
						Map<String, String> newMetric = new HashMap<>();
						newMetric.put("projectId", metric.getDbObject().get("projectId").toString());
						newMetric.put("metricProviderId", metric.getDbObject().get("metricProviderId").toString());
						newMetric.put("lastExecutionDate", metric.getDbObject().get("lastExecutionDate").toString());
						metricProviders.add(newMetric);
					}
					task.getDbObject().put("metricExecutions", metricProviders);
					tasksByProject.add(mapper.readTree(task.getDbObject().toString()));
				} catch (IOException e) {
					e.printStackTrace();
					StringRepresentation rep = new StringRepresentation("");
					rep.setMediaType(MediaType.APPLICATION_JSON);
					getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
					return rep;
				}
			}

			StringRepresentation rep = new StringRepresentation(tasksByProject.toString());
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.SUCCESS_OK);
			return rep;
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			StringRepresentation rep = new StringRepresentation("");
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return rep;
		} finally {
			if (mongo != null) mongo.close();
		}
	}

}
