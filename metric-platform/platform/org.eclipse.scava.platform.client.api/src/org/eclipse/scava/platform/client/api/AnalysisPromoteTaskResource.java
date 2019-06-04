package org.eclipse.scava.platform.client.api;

import org.eclipse.scava.platform.analysis.AnalysisTaskService;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

public class AnalysisPromoteTaskResource extends AbstractApiResource {

	@Override
	public Representation doRepresent() {
		AnalysisTaskService service = platform.getAnalysisRepositoryManager().getTaskService();
		
		String analysisTaskId = getQueryValue("analysisTaskId");

		AnalysisTask task = service.promoteTask(analysisTaskId);

		StringRepresentation rep = new StringRepresentation(task.getDbObject().toString());
		rep.setMediaType(MediaType.APPLICATION_JSON);
		getResponse().setStatus(Status.SUCCESS_OK);
		return rep;
	}

}
