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
package org.eclipse.scava.plugin.usermonitoring.metric.metrics.metric;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics.IBasicMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.milestones.TimeintervalGraphTraversalWithRemains;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.milestones.TimeintervalGraphtraversal;

import io.swagger.client.model.MetricBoundary;
import io.swagger.client.model.MetricDescriptor;
import io.swagger.client.model.MetricMilestoneSlice;

public class MetricDefinition implements IMetric {

	private final IBasicMetric basicMetric;
	private final String metricName;
	private String project;

	public MetricDefinition(IBasicMetric basicMetric, String project) {
		this.basicMetric = basicMetric;
		this.metricName = basicMetric.getName();
		this.project = project;
	}

	private Map<String, Double> addRemains(Map<String, Double> current, Map<String, Double> remains) {

		remains.forEach((key, value) -> {
			current.merge(key, value, Double::sum);
		});

		return current;
	}

	private Map<String, Double> getRemains(MetricMilestoneSlice remainMetricMilestoneSlices) {

		Map<String, Double> remains = new HashMap<>();
		List<MetricBoundary> boundary = remainMetricMilestoneSlices.getBoundary();
		for (MetricBoundary metricBoundary : boundary) {
			List<MetricDescriptor> metricValues = metricBoundary.getMetricValues();
			for (MetricDescriptor metricDescriptor : metricValues) {
				if (metricDescriptor.getMetricName().equals(this.getID())) {
					Map<String, Double> itemValuePairs = metricDescriptor.getItemValuePairs();
					remains.putAll(itemValuePairs);
				}

			}
		}

		return remains;
	}

	@Override
	public String getID() {
		return metricName;
	}

	@Override
	public Map<String, Double> getItemValuePairs(TimeintervalGraphtraversal<Vertex, Vertex> timeintervalGraphTraversal) {

		Map<String, Double> process = basicMetric.process(timeintervalGraphTraversal.getGraphtraversal());

		if (timeintervalGraphTraversal instanceof TimeintervalGraphTraversalWithRemains) {
			MetricMilestoneSlice remainMetricMilestoneSlices = ((TimeintervalGraphTraversalWithRemains<Vertex, Vertex>) timeintervalGraphTraversal).getRemainMetricMilestoneSlices();
			Map<String, Double> remains = getRemains(remainMetricMilestoneSlices);

			addRemains(process, remains);
		}

		return process;
	}

	@Override
	public String getDescription() {
		return basicMetric.getDiscription();
	}

	@Override
	public IBasicMetric getBasicMetric() {
		return basicMetric;
	}

	@Override
	public String getProjectId() {
		return project;
	}

}
