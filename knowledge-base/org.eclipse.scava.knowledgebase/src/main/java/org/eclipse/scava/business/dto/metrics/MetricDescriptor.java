package org.eclipse.scava.business.dto.metrics;

import java.util.Map;

public class MetricDescriptor {
	private String metricName;
	private Map<String, Double> itemValuePairs;
	public String getMetricName() {
		return metricName;
	}
	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}
	public Map<String, Double> getItemValuePairs() {
		return itemValuePairs;
	}
	public void setItemValuePairs(Map<String, Double> itemValuePairs) {
		this.itemValuePairs = itemValuePairs;
	}

	

}