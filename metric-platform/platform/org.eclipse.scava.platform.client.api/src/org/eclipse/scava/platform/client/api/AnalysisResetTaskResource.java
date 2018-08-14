package org.eclipse.scava.platform.client.api;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.analysis.data.AnalysisTaskScheduling;
import org.eclipse.scava.platform.analysis.data.IAnalysisRepositoryService;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class AnalysisResetTaskResource extends ServerResource {

	@Post
	public Representation resetAnalysisTask(Representation entity) throws JsonProcessingException, IOException {
		
		IAnalysisRepositoryService service = new AnalysisTaskScheduling(getMongoConnection());

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(entity.getText());
		String analysisTaskId = jsonNode.get("analysisTaskId").toString().replace("\"", "");

		service.resetAnalysisTask(analysisTaskId);

		StringRepresentation rep = new StringRepresentation(analysisTaskId.toString());
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
