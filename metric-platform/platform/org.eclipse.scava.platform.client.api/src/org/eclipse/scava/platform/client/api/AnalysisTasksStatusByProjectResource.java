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
		
		boolean stat = false;
		for (AnalysisTask analysisTask : tasks) {
			System.out.println(analysisTask.getScheduling().getStatus());
			if (analysisTask.getScheduling().getStatus().equals(AnalysisTaskStatus.COMPLETED.name()) || 
					analysisTask.getScheduling().getStatus().equals(AnalysisTaskStatus.STOP.name())) {
				stat = true;
			} else {
				stat = false;
				break;
			}
		}
		if (stat) {
			globalStatus = "{\"value\":\"up-to-date\"}";
		} else {
			globalStatus = "{\"value\":\"in-progress\"}";
		}
		
		StringRepresentation rep = new StringRepresentation(globalStatus);
		rep.setMediaType(MediaType.APPLICATION_JSON);
		getResponse().setStatus(Status.SUCCESS_OK);
		return rep;
	}

}
