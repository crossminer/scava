package org.eclipse.scava.business.dto;

import java.util.Date;
import java.util.List;

public class MetricBoundary {
	private Date beginDate;
	private Date endDate;
	private List<MetricDescriptor> metricValues;
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public List<MetricDescriptor> getMetricValues() {
		return metricValues;
	}
	public void setMetricValues(List<MetricDescriptor> metricValues) {
		this.metricValues = metricValues;
	}

}
