package org.eclipse.scava.platform.client.api;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.scava.platform.analysis.data.AnalysisTaskScheduling;
import org.eclipse.scava.platform.analysis.data.IAnalysisRepositoryService;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.types.AnalysisExecutionMode;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class AnalysisCreationTaskResource extends ServerResource {

	@Post
	public Representation createAnalysisTask(Representation entity) throws ParseException, JsonProcessingException, IOException {
		
		getMongoConnection().dropDatabase("ScavaAnalysisScheduling");
		IAnalysisRepositoryService service = new AnalysisTaskScheduling(getMongoConnection());

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
		} else if (taskType.equals(AnalysisExecutionMode.DAILY_EXECUTION.name())) {
			task.setType(AnalysisExecutionMode.DAILY_EXECUTION.name());
		}

		List<String> metricsProviders = new ArrayList<>();
		for (JsonNode metricProvider : (ArrayNode) jsonNode.get("metricProviders")) {
			metricsProviders.add(metricProvider.toString().replace("\"", ""));
		}

		service.createAnalysisTask(projectId, task, metricsProviders);

		StringRepresentation rep = new StringRepresentation(task.getDbObject().toString());
		rep.setMediaType(MediaType.APPLICATION_JSON);
		getResponse().setStatus(Status.SUCCESS_CREATED);

		return rep;

	}
	
	public static Mongo getMongoConnection() throws UnknownHostException {
		List<ServerAddress> mongoHostAddresses = new ArrayList<>();
		mongoHostAddresses.add(new ServerAddress("localhost", 27017));
		return new Mongo(mongoHostAddresses);// ,options);

	}
}
