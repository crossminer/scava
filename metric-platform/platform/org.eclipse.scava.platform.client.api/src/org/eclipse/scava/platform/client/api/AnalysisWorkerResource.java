/*******************************************************************************
 * Copyright (c) 2018 Softeam
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.client.api;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

public class AnalysisWorkerResource extends AbstractApiResource {

	@Override
	public Representation doRepresent() {
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
						newMetric.put("hasVisualisation", metric.getDbObject().get("hasVisualisation").toString());
						metricExecutions.add(newMetric);
					}
					currentTask.put("metricExecutions", metricExecutions);
					
					List<Object> schedulingInformation = new ArrayList<>();
					SchedulingInformation scheduling = task.getScheduling();
					Map<String, String> scheduleInfo = new HashMap<>();
					scheduleInfo.put("status", scheduling.getDbObject().get("status").toString());
					
					String cdate = scheduling.getDbObject().get("currentDate").toString();
					SimpleDateFormat parser = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy",Locale.UK);
					SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
					
					try {
						scheduleInfo.put("currentDate",formater.format(parser.parse(cdate)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if(scheduling.getDbObject().get("executionRequestDate") != null) {
						scheduleInfo.put("executionRequestDate", scheduling.getDbObject().get("executionRequestDate").toString());
					}
					if(scheduling.getDbObject().get("progress") != null) {
						scheduleInfo.put("progress", scheduling.getDbObject().get("progress").toString());
					}
					if(scheduling.getDbObject().get("currentMetric") != null) {
						scheduleInfo.put("currentMetric", scheduling.getDbObject().get("currentMetric").toString());
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
	}

}
