package org.eclipse.scava.platform.client.api;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.WorkerService;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.model.MetricExecution;
import org.eclipse.scava.platform.analysis.data.model.SchedulingInformation;
import org.eclipse.scava.platform.analysis.data.model.Worker;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mongodb.Mongo;

public class AnalysisWorkerResource extends AbstractApiResource {

	@Override
	public Representation doRepresent() {
		Mongo mongo = null;
		try {
			mongo = Configuration.getInstance().getMongoConnection();
			platform = new Platform(mongo);				
			WorkerService service = platform.getAnalysisRepositoryManager().getWorkerService();
			List<Worker> workers = service.getWorkers();

			ArrayNode listWorkers = mapper.createArrayNode();
	
			for (Worker worker : workers) {
				try {
					AnalysisTask task = worker.getCurrentTask();				
					if (task != null) {
						task = service.getRepository().getAnalysisTasks().findOneByAnalysisTaskId(task.getAnalysisTaskId());
						Map<String, Object> currentTask = new HashMap<>();
						currentTask.put("analysisTaskId", task.getDbObject().get("analysisTaskId").toString());
						currentTask.put("label", task.getDbObject().get("label").toString());
						currentTask.put("type", task.getDbObject().get("type").toString());
						List<Object> metricExecutions = new ArrayList<>();
						for (MetricExecution metric : task.getMetricExecutions()) {
							Map<String, String> newMetric = new HashMap<>();
							newMetric.put("projectId", metric.getDbObject().get("projectId").toString());
							newMetric.put("metricProviderId", metric.getDbObject().get("metricProviderId").toString());
							newMetric.put("lastExecutionDate", metric.getDbObject().get("lastExecutionDate").toString());
							metricExecutions.add(newMetric);
						}
						currentTask.put("metricExecutions", metricExecutions);
						
						List<Object> schedulingInformation = new ArrayList<>();
						SchedulingInformation scheduling = task.getScheduling();
						Map<String, String> scheduleInfo = new HashMap<>();
						scheduleInfo.put("status", scheduling.getDbObject().get("status").toString());
						scheduleInfo.put("currentDate", scheduling.getDbObject().get("currentDate").toString());
						if(scheduling.getDbObject().get("executionRequestDate") != null) {
							scheduleInfo.put("executionRequestDate", scheduling.getDbObject().get("executionRequestDate").toString());
						}
						if(scheduling.getDbObject().get("progress") != null) {
							scheduleInfo.put("progress", scheduling.getDbObject().get("progress").toString());
						}
						if(scheduling.getDbObject().get("lastDailyExecutionDuration") != null) {
							scheduleInfo.put("lastDailyExecutionDuration", scheduling.getDbObject().get("lastDailyExecutionDuration").toString());
						}
						
						schedulingInformation.add(scheduleInfo);
						currentTask.put("scheduling", schedulingInformation);
						
						currentTask.put("startDate", task.getDbObject().get("startDate").toString());
						if(task.getDbObject().get("endDate") != null) {
							currentTask.put("endDate", task.getDbObject().get("endDate").toString());
						}			
						worker.getDbObject().put("currentTask", currentTask);
					}else {
						worker.getDbObject().put("currentTask", null);
					}
					
					listWorkers.add(mapper.readTree(worker.getDbObject().toString()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			StringRepresentation rep = new StringRepresentation(listWorkers.toString());
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
		}
	}

}
