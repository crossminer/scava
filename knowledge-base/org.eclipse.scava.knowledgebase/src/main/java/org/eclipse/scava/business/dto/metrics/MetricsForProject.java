package org.eclipse.scava.business.dto.metrics;

import java.util.List;

import org.springframework.data.annotation.Id;

public class MetricsForProject {
	@Id
	private String id;
	private String userId;
	private String projectId;
	private List<MetricMilestoneSlice> metricMilestoneSlice;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public List<MetricMilestoneSlice> getMetricMilestoneSlice() {
		return metricMilestoneSlice;
	}
	public void setMetricMilestoneSlice(List<MetricMilestoneSlice> metricMilestoneSlice) {
		this.metricMilestoneSlice = metricMilestoneSlice;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
