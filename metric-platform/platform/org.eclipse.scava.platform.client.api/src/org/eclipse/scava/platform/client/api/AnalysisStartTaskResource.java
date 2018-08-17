package org.eclipse.scava.platform.client.api;

import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.scava.platform.analysis.data.AnalysisTaskService;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AnalysisStartTaskResource extends ServerResource {
	
	@Post
	public Representation startAnalysisTask(Representation entity) {
		
		try {
			AnalysisTaskService service = new AnalysisTaskService(SingletonMongoConnection.getInstance());
			ObjectMapper mapper = new ObjectMapper();
			try {
				JsonNode jsonNode = mapper.readTree(entity.getText());
				String analysisTaskId = jsonNode.get("analysisTaskId").toString().replace("\"", "");
				AnalysisTask task = service.startAnalysisTask(analysisTaskId);
				StringRepresentation rep = new StringRepresentation(task.getDbObject().toString());
				rep.setMediaType(MediaType.APPLICATION_JSON);
				getResponse().setStatus(Status.SUCCESS_OK);
				return rep;
				
			} catch (IOException e) {
				e.printStackTrace();
				StringRepresentation rep = new StringRepresentation("");
				rep.setMediaType(MediaType.APPLICATION_JSON);
				getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
				return rep;
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			StringRepresentation rep = new StringRepresentation("");
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return rep;
		}
		
	}
}
