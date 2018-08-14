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
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class AnalysisTasksByProjectResource extends ServerResource {

	@Get
	public Representation getAnalysisTasksByProject(Representation entity) throws JsonProcessingException, IOException {
		
		IAnalysisRepositoryService service = new AnalysisTaskScheduling(getMongoConnection());
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(entity.getText());
		String projectId = jsonNode.get("projectId").toString().replace("\"", "");

		service.getAnalysisTasksByProject(projectId);

		StringRepresentation rep = new StringRepresentation(projectId.toString());
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
