package org.eclipse.scava.platform.client.api;

import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.AnalysisTaskService;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.ServerResource;

public class AnalysisDeleteTaskResource extends ServerResource {

	@Delete
	public Representation deleteAnalysisTask() {
		Platform platform = Platform.getInstance();
		AnalysisTaskService service = platform.getAnalysisRepositoryManager().getTaskService();
		
		String analysisTaskId = getQueryValue("analysisTaskId");

		AnalysisTask task = service.deleteAnalysisTask(analysisTaskId);

		StringRepresentation rep = new StringRepresentation(task.getDbObject().toString());
		rep.setMediaType(MediaType.APPLICATION_JSON);
		getResponse().setStatus(Status.SUCCESS_OK);
		return rep;
	}

}
