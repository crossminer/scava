package org.eclipse.scava.platform.client.api;

import java.net.UnknownHostException;
import java.util.List;

import javax.annotation.PreDestroy;

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.AnalysisTaskService;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.types.AnalysisTaskStatus;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

import com.mongodb.Mongo;

public class AnalysisTasksStatusByProjectResource extends AbstractApiResource{
	
	@Override
	public Representation doRepresent() {
		Mongo mongo = null;
		String globalStatus;
		try {
			mongo = Configuration.getInstance().getMongoConnection();
			platform = new Platform(mongo);				
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
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			StringRepresentation rep = new StringRepresentation("");
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return rep;
		} finally {
			if (mongo != null) mongo.close();
		}
		
		StringRepresentation rep = new StringRepresentation(globalStatus);
		rep.setMediaType(MediaType.APPLICATION_JSON);
		getResponse().setStatus(Status.SUCCESS_OK);
		return rep;
	}

}
