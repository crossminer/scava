package org.eclipse.scava.business.dto;

import java.util.List;

import org.springframework.data.annotation.Id;

public class MetricsForProject {
	@Id
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String project;
	private List<MetricMilestoneSlice> metricMilestoneSlice;
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public List<MetricMilestoneSlice> getMetricMilestoneSlice() {
		return metricMilestoneSlice;
	}
	public void setMetricMilestoneSlice(List<MetricMilestoneSlice> metricMilestoneSlice) {
		this.metricMilestoneSlice = metricMilestoneSlice;
	}
}
