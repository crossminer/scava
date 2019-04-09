package org.eclipse.scava.platform.analysis.data.model.dto;

import java.util.ArrayList;
import java.util.List;

public class MetricProviderDTO {
	
	private String metricProviderId;
	private String label;
	private String kind;
	private String description;
	private List<MetricProviderDTO> dependOf = new ArrayList<MetricProviderDTO>();
	private List<DataStorageDTO> storages = new ArrayList<DataStorageDTO>();
	
	public MetricProviderDTO() {
	}
	
	public MetricProviderDTO(String metricProviderId, String label, String kind, List<MetricProviderDTO> dependOf) {
		super();
		this.metricProviderId = metricProviderId;
		this.label = label;
		this.kind = kind;
		this.dependOf = dependOf;
	}
	
	public String getMetricProviderId() {
		return metricProviderId;
	}

	public void setMetricProviderId(String metricProviderId) {
		this.metricProviderId = metricProviderId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<MetricProviderDTO> getDependOf() {
		return dependOf;
	}

	public void setDependOf(List<MetricProviderDTO> dependOf) {
		this.dependOf = dependOf;
	}

	public List<DataStorageDTO> getStorages() {
		return storages;
	}

	public void setStorages(List<DataStorageDTO> storages) {
		this.storages = storages;
	}
	
}