package org.eclipse.scava.platform.analysis;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.data.model.DataStorage;
import org.eclipse.scava.platform.analysis.data.model.MetricProvider;
import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysisRepository;

public class MetricProviderService {
	
	private ProjectAnalysisRepository repository;

	public MetricProviderService(ProjectAnalysisRepository repository){
		this.repository = repository;
	}
	
	public ProjectAnalysisRepository getRepository() {
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
	
	public List<MetricProvider> getMetricProviders(Platform platform) {
		List<MetricProvider> providers = new ArrayList<MetricProvider>();
		MetricProviderInitialiser initialiser = new MetricProviderInitialiser(platform);
		providers = initialiser.loadMetricProviders();
		return providers;
	}

}
