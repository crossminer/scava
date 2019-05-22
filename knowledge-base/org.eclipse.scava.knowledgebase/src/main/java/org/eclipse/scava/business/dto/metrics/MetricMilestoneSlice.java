package org.eclipse.scava.business.dto.metrics;

import java.util.List;

public class MetricMilestoneSlice {

	private String bounder;
	private List<MetricBoundary> boundary;
	public String getBounder() {
		return bounder;
	}
	public void setBounder(String bounder) {
		this.bounder = bounder;
	}
	public List<MetricBoundary> getBoundary() {
		return boundary;
	}
	public void setBoundary(List<MetricBoundary> boundary) {
		this.boundary = boundary;
	}

}
