/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*    Zsolt Janos Szamosvolgyi
*    Endre Tamas Varadi
*    Gergo Balogh
**********************************************************************/
package org.eclipse.scava.plugin.usermonitoring;

import java.util.Collections;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;
import org.eclipse.scava.plugin.preferences.Preferences;
import org.eclipse.scava.plugin.preferences.UserActivityDisablements;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;
import org.eclipse.scava.plugin.usermonitoring.event.EventManager;
import org.eclipse.scava.plugin.usermonitoring.event.events.IEvent;
import org.eclipse.scava.plugin.usermonitoring.gremlin.GremlinAdapter;
import org.eclipse.scava.plugin.usermonitoring.gremlin.GremlinUtils;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.MetricManager;
import org.eclipse.swt.widgets.Display;

import com.google.common.eventbus.EventBus;
import com.google.gson.Gson;

import io.swagger.client.ApiException;
import io.swagger.client.model.MetricsForProject;

public class UserMonitor {

	private final EventManager eventManager;
	private final MetricManager metricManager;
	private static GremlinAdapter gremlinAdapter;
	private static final int MAXIMUM_ATTEMPT_COUNT = 3;

	private UserActivityDisablements disablements;

	public UserMonitor(EventBus eventBus) {
		connectDisablements();

		gremlinAdapter = new GremlinAdapter();
		GremlinUtils gremlinUtils = gremlinAdapter.getGremlinUtils();

		this.eventManager = new EventManager(gremlinUtils, eventBus, this);
		this.metricManager = new MetricManager(gremlinUtils, eventBus);
		ProjectStructureHandler.exploreProjects();

	}

	public IEvent startMetricCalculation(IEvent event) {
		List<MetricsForProject> calculatedMetrics = metricManager.startMetricCalculation(event);
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		String token = preferenceStore.getString(Preferences.USERMONITORING_USERTOKEN);

		Collections.reverse(calculatedMetrics);

		for (MetricsForProject metricsForProject : calculatedMetrics) {

			metricsForProject.setId(token);

			try {
				boolean succes = false;

				for (int attempt = 0; attempt < MAXIMUM_ATTEMPT_COUNT; attempt++) {
					succes = uploadMetricInformation(metricsForProject);
					if (succes) {
						break;
					}
				}

				if (succes) {
					System.out.println("Metrics for " + metricsForProject.getProjectId() + " are successfully uploaded.");
				} else {
					System.err.println("Metrics for " + metricsForProject.getProjectId() + " are failed to upload.");
				}
			} catch (ApiException e) {
				e.printStackTrace();
				ErrorHandler.handle(Display.getDefault().getActiveShell(), e, ErrorType.METRIC_UPLOAD_ERROR);
			}

		}
		return event;
	}

	private boolean uploadMetricInformation(MetricsForProject metrics) throws ApiException {

		KnowledgeBaseAccess access = new KnowledgeBaseAccess();
		access.getArtifactRestControllerApi().storeIDEMetricsUsingPOST(metrics);

		return true;
	}

	public UserActivityDisablements getDisablements() {
		return disablements;
	}

	public EventManager getEventManager() {
		return this.eventManager;
	}

	public GremlinAdapter getGremlinAdapter() {
		return gremlinAdapter;
	}

	private void connectDisablements() {
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(e -> {
			if (e.getProperty().equals(Preferences.USERMONITORING_DISABLEMENTS)) {
				updateDisablements(e.getNewValue());
			}
		});
		Object json = Activator.getDefault().getPreferenceStore().getString(Preferences.USERMONITORING_DISABLEMENTS);
		updateDisablements(json);
	}

	private void updateDisablements(Object data) {
		String json = (String) data;
		disablements = new Gson().fromJson(json, UserActivityDisablements.class);
	}

}
