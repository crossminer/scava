package org.eclipse.scava.platform.client.api;

import java.net.UnknownHostException;

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.AnalysisTaskService;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.ServerResource;

import com.mongodb.Mongo;

public class AnalysisTaskPushOnWorkerResource extends ServerResource {

	@Delete
	public Representation deleteAnalysisTask(Representation entity) {
		Mongo mongo = null;
		Platform platform = null;
		try {
			mongo = Configuration.getInstance().getMongoConnection();
			platform = new Platform(mongo);				
			AnalysisTaskService service = platform.getAnalysisRepositoryManager().getTaskService();
			
			String analysisTaskId = (String) getRequest().getAttributes().get("analysisTaskId");
			String workerId = (String) getRequest().getAttributes().get("workerId");
			AnalysisTask task =  service.executeTaskOnWorker(analysisTaskId, workerId);

			StringRepresentation rep = new StringRepresentation(task.getDbObject().toString());
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
			platform = null;
		}

	}

}
