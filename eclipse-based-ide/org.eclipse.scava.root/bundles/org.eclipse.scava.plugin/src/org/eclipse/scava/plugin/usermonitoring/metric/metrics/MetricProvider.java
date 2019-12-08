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
*    Zsolt János Szamosvölgyi
*    Endre Tamás Váradi
*    Gergõ Balogh
**********************************************************************/
package org.eclipse.scava.plugin.usermonitoring.metric.metrics;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.preferences.Preferences;
import org.eclipse.scava.plugin.preferences.UserActivityDisablements;
import org.eclipse.scava.plugin.usermonitoring.ScavaProject;
import org.eclipse.scava.plugin.usermonitoring.event.events.IEvent;
import org.eclipse.scava.plugin.usermonitoring.event.events.scheduledUploadEvent.ScheduledUploadEvent;
import org.eclipse.scava.plugin.usermonitoring.gremlin.GremlinUtils;
import org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics.EclipseSearchUsageBasicMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics.FileAccesRateBasicMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics.GuiUsageRateBasicMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics.IBasicMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics.ModificationRateBasicMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics.ScavaLibraryUsageBasicMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics.ScavaSearchSuccesBasicMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics.ScavaSearchUsageBasicMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics.TestingRateBasicMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics.WorkingTimeBasicMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.metric.IMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.metric.MetricDefinition;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.milestones.IBasicMilestone;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.milestones.TimeintervalGraphtraversal;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.milestones.basicMilestones.EclipseCloseMilestone;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.milestones.basicMilestones.HourMilestone;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.milestones.basicMilestones.LaunchMilestone;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.milestones.basicMilestones.SaveMilestone;

import com.google.gson.Gson;

import io.swagger.client.model.MetricBoundary;
import io.swagger.client.model.MetricDescriptor;
import io.swagger.client.model.MetricMilestoneSlice;
import io.swagger.client.model.MetricsForProject;

public class MetricProvider {

	private final GremlinUtils gremlinUtils;
	private static final String GENERAL = "general";
	private String disablementsJson = Activator.getDefault().getPreferenceStore().getString(Preferences.USERMONITORING_DISABLEMENTS);

	public MetricProvider(GremlinUtils gremlinUtils) {
		this.gremlinUtils = gremlinUtils;
	}

	public List<MetricsForProject> calculateMetrics(GraphTraversal<Vertex, Vertex> graphTraversal, Date currentTime, IEvent event) throws CoreException {

		MetricRemainHandler remainHandler = getRemainsMetric();
		UserActivityDisablements disablements = new Gson().fromJson(disablementsJson, UserActivityDisablements.class);

		List<MetricsForProject> metricValues = new ArrayList<>();
		List<MetricsForProject> remainMetricValues = new ArrayList<>();// TODO

		List<ScavaProject> projectToMetricCalculation = ProjectUtils.getProjectsForMetricCalculation();
		ScavaProject general;

		projectToMetricCalculation.add(general = new ScavaProject("NonSpecified", null));

		for (ScavaProject scavaProject : projectToMetricCalculation) {

			String projectId = scavaProject.getProjectId();

			List<IBasicMilestone> milestones = new ArrayList<>();

			milestones.add(new HourMilestone(gremlinUtils, currentTime));
			milestones.add(new EclipseCloseMilestone(graphTraversal, remainHandler, projectId, currentTime));
			if (scavaProject != general) {
				milestones.add(new SaveMilestone(graphTraversal, remainHandler, projectId, currentTime));
				milestones.add(new LaunchMilestone(graphTraversal, remainHandler, projectId, currentTime));

			}

			List<MetricMilestoneSlice> metricMilestoneSlices = new ArrayList<>();
			List<MetricMilestoneSlice> remainMetricMilestoneSlices = new ArrayList<>();// TODO

			for (IBasicMilestone basicMilestone : milestones) {

				List<MetricBoundary> metricBoundaries = new ArrayList<>();
				List<MetricBoundary> remainMetricBoundaries = new ArrayList<>();// TODO
				List<TimeintervalGraphtraversal<Vertex, Vertex>> eventsBetweenMilestone = new ArrayList<>();

				eventsBetweenMilestone.addAll(basicMilestone.getSubWindows());

				for (TimeintervalGraphtraversal<Vertex, Vertex> timeintervalGraphtraversal : eventsBetweenMilestone) {

					List<IMetric> metrics = new ArrayList<>();
					String[] files = scavaProject.getFiles().toArray(new String[scavaProject.getFiles().size()]);

					if (scavaProject != general) {
						metrics.add(new MetricDefinition(new ModificationRateBasicMetric(scavaProject.getProjectId()), projectId));
						metrics.add(new MetricDefinition(new TestingRateBasicMetric(projectId), projectId));
						metrics.add(new MetricDefinition(new WorkingTimeBasicMetric(projectId, files), projectId));
					}

					if (!basicMilestone.isProjectSpecific() && scavaProject == general) {
						metrics.add(new MetricDefinition(new GuiUsageRateBasicMetric(), GENERAL));
						metrics.add(new MetricDefinition(new FileAccesRateBasicMetric(), GENERAL));
						metrics.add(new MetricDefinition(new ScavaLibraryUsageBasicMetric(), GENERAL));
						metrics.add(new MetricDefinition(new ScavaSearchSuccesBasicMetric(), GENERAL));
						metrics.add(new MetricDefinition(new ScavaSearchUsageBasicMetric(), GENERAL));
						metrics.add(new MetricDefinition(new EclipseSearchUsageBasicMetric(), GENERAL));
					}

					List<MetricDescriptor> metricDescriptors = new ArrayList<>();

					metrics = metrics.stream().filter(m -> {
						Class<? extends IBasicMetric> class1 = m.getBasicMetric().getClass();
						boolean disabled = disablements.isDisabled(class1);
						return !disabled;

					}).collect(Collectors.toList());

					for (IMetric basicMetrics : metrics) {
						MetricDescriptor descriptor = new MetricDescriptor();
						descriptor.setItemValuePairs(basicMetrics.getItemValuePairs(timeintervalGraphtraversal));
						descriptor.setMetricName(basicMetrics.getID());

						metricDescriptors.add(descriptor);
					}

					if (timeintervalGraphtraversal.isRemainder() != true) {

						MetricBoundary boundary = new MetricBoundary();
						boundary.setBeginDate(timeintervalGraphtraversal.getBeginDate().toInstant().atOffset(ZoneOffset.UTC));
						boundary.setEndDate(timeintervalGraphtraversal.getEndDate().toInstant().atOffset(ZoneOffset.UTC));
						boundary.setMetricValues(metricDescriptors);

						metricBoundaries.add(boundary);
					} else {

						MetricBoundary boundary = new MetricBoundary();

						if (timeintervalGraphtraversal.getBeginDate() != null) {
							boundary.setBeginDate(timeintervalGraphtraversal.getBeginDate().toInstant().atOffset(ZoneOffset.UTC));
						}
						boundary.setBeginDate(null);
						boundary.setEndDate(timeintervalGraphtraversal.getEndDate().toInstant().atOffset(ZoneOffset.UTC));
						boundary.setMetricValues(metricDescriptors);

						remainMetricBoundaries.add(boundary);
					}
				}

				if (!remainMetricBoundaries.isEmpty()) {

					MetricMilestoneSlice metricMilestoneSlice = new MetricMilestoneSlice();
					metricMilestoneSlice.setBounder(basicMilestone.getDescription());
					metricMilestoneSlice.setBoundary(remainMetricBoundaries);

					remainMetricMilestoneSlices.add(metricMilestoneSlice);
				}

				MetricMilestoneSlice metricMilestoneSlice = new MetricMilestoneSlice();
				metricMilestoneSlice.setBounder(basicMilestone.getDescription());
				metricMilestoneSlice.setBoundary(metricBoundaries);

				metricMilestoneSlices.add(metricMilestoneSlice);

			}
			if (!remainMetricMilestoneSlices.isEmpty()) {

				MetricsForProject metricsForProject = new MetricsForProject();
				metricsForProject.setProjectId(projectId);
				metricsForProject.setMetricMilestoneSlice(remainMetricMilestoneSlices);

				remainMetricValues.add(metricsForProject);
			}

			MetricsForProject metricsForProject = new MetricsForProject();
			metricsForProject.setProjectId(projectId);
			metricsForProject.setMetricMilestoneSlice(metricMilestoneSlices);

			metricValues.add(metricsForProject);
		}

//		metricValues.forEach(v -> {
//			System.out.println(v.getProject());
//			v.getMetricMilestoneSlice().forEach(k -> {
//				System.out.println("\t" + k.getBounder());
//				k.getBoundary().forEach(l -> {
//					System.out.println("\t\t" + l.getBeginDate());
//					System.out.println("\t\t" + l.getEndDate());
//					l.getMetricValues().forEach(z -> {
//						System.out.println("\t\t\t" + z.getMetricName());
//						z.getItemValuePairs().forEach((a, b) -> {
//							System.out.println("\t\t\t\t" + a);
//							System.out.println("\t\t\t\t" + b);
//						});
//					});
//				});
//			});
//		});

		System.out.println("AZ: "+new Gson().toJson(metricValues)+" IGEN");
		//System.out.println(new Gson().toJson(remainMetricValues));

		updateUpdateEventInfo(currentTime, event, remainMetricValues);

		return metricValues;

	}

	private void updateUpdateEventInfo(Date currentTime, IEvent event, List<MetricsForProject> remainMetricValues) {
		((ScheduledUploadEvent) event).setTimestamp(currentTime.getTime());
		((ScheduledUploadEvent) event).setRemainder(new Gson().toJson(remainMetricValues));
	}

	private MetricRemainHandler getRemainsMetric() {
		String json = "[]";
		VertexProperty<String> property = gremlinUtils.getLastUploadEvent().property("Remains");
		if (property.isPresent()) {
			json = property.value();
		}
		MetricRemainHandler remainHandler = new MetricRemainHandler(json);
		return remainHandler;
	}

}