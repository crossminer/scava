package org.eclipse.scava.platform.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysisResportory;
import org.eclipse.scava.platform.analysis.data.model.dto.MetricProviderDTO;
import org.eclipse.scava.platform.visualisation.MetricVisualisation;
import org.eclipse.scava.platform.visualisation.MetricVisualisationExtensionPointManager;

public class MetricProviderService {
	
	private ProjectAnalysisResportory repository;

	public MetricProviderService(ProjectAnalysisResportory repository){
		this.repository = repository;
	}
	
	public ProjectAnalysisResportory getRepository() {
		return this.repository;
	}
	
	public MetricProviderDTO registreMetricProvider(String metricProviderId, String label, String kind, String description,List<String> dataStograges) {
		MetricProviderDTO provider = new MetricProviderDTO();
		provider.setMetricProviderId(metricProviderId);
		provider.setLabel(label);
		provider.setKind(kind);
		provider.setDescription(description);

//		for (String storage : dataStograges) {
//			DataStorage dCollection = new DataStorage();
//			dCollection.setStorage(storage);
//			provider.getStorages().add(dCollection);
//		}
		return provider;
	}	
	
	public List<MetricProviderDTO> getMetricProviders(Platform platform) {
		List<MetricProviderDTO> providers = new ArrayList<MetricProviderDTO>();
		MetricProviderInitialiser initialiser = new MetricProviderInitialiser(platform);
		providers = initialiser.loadMetricProviders();
		return providers;
	}

}
