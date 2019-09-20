package org.eclipse.scava.platform.client.api;

import java.util.List;

import org.eclipse.scava.platform.analysis.AnalysisTaskService;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.types.AnalysisTaskStatus;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

public class AnalysisTasksStatusByProjectResource extends AbstractApiResource{
	
	@Override
	public Representation doRepresent() {
		String globalStatus;
		AnalysisTaskService service = platform.getAnalysisRepositoryManager().getTaskService();
		String projectId = (String) getRequest().getAttributes().get("projectid");
		
		List<AnalysisTask> tasks = service.getAnalysisTasksByProject(projectId);
		
		String stat = "none";
		for (AnalysisTask analysisTask : tasks) {
			if (analysisTask.getScheduling().getStatus().equals(AnalysisTaskStatus.COMPLETED.name()) || analysisTask.getScheduling().getStatus().equals(AnalysisTaskStatus.STOP.name())) {
				stat = "up-to-date";
			} else if (analysisTask.getScheduling().getStatus().equals(AnalysisTaskStatus.ERROR.name())) {
				stat = "error";
				break;
			}
			else {
				stat = "in-progress";
				break;
			}
		}
		if (stat.equals("up-to-date")) {
			globalStatus = "{\"value\":\"up-to-date\"}";
		} else if (stat.equals("in-progress")){
			globalStatus = "{\"value\":\"in-progress\"}";
		} else if (stat.equals("error")){
			globalStatus = "{\"value\":\"error\"}";
		} else {
			globalStatus = "{\"value\":\"none\"}";
		}
		
		StringRepresentation rep = new StringRepresentation(globalStatus);
		rep.setMediaType(MediaType.APPLICATION_JSON);
		getResponse().setStatus(Status.SUCCESS_OK);
		return rep;
	}

}
