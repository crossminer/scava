package org.eclipse.scava.platform.analysis;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.analysis.data.model.DataStorage;
import org.eclipse.scava.platform.analysis.data.model.MetricProvider;
import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysisResportory;

public class MetricProviderService {
	
	private ProjectAnalysisResportory repository;

	public MetricProviderService(ProjectAnalysisResportory repository){
		this.repository = repository;
	}
	
	public ProjectAnalysisResportory getRepository() {
		return this.repository;
	}
	
	public MetricProvider registreMetricProvider(String metricProviderId, String label, String kind, String description,List<String> dataStograges) {
		MetricProvider provider = new MetricProvider();
		provider.setMetricProviderId(metricProviderId);
		provider.setLabel(label);
		provider.setKind(kind);
		provider.setDescription(description);
		this.repository.getMetricProviders().add(provider);

		for (String storage : dataStograges) {
			DataStorage dCollection = new DataStorage();
			dCollection.setStorage(storage);
			provider.getStorages().add(dCollection);
		}
		this.repository.sync();
		return provider;
	}

	
	public void addMetricProviderDependency(MetricProvider provider, List<MetricProvider> dependsOn) {
		for (MetricProvider dependency : dependsOn) {
			provider.getDependOf().add(dependency);
		}
		this.repository.sync();
	}
	
	
	public List<MetricProvider> getMetricProviders() {
		List<MetricProvider> providers = new ArrayList<>();
		for (MetricProvider provider : this.repository.getMetricProviders()) {
			providers.add(provider);
		}
		return providers;
	}

}
