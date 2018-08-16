package org.eclipse.scava.platform.osgi.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.platform.AbstractFactoidMetricProvider;
import org.eclipse.scava.platform.IHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.data.MetricProviderService;
import org.eclipse.scava.platform.analysis.data.model.MetricProvider;
import org.eclipse.scava.platform.analysis.data.types.MetricProviderKind;

import com.mongodb.Mongo;

public class MetricProviderInitialiser {

	private MetricProviderService taskRepository;
	private Platform platform;

	public MetricProviderInitialiser(Mongo mongo) {
		this.taskRepository = new MetricProviderService(mongo);
		this.platform = new Platform(mongo);
	}

	public void initialiseMetricProviderRepository() {
		Map<String, MetricProvider> metricsProviders = new HashMap<>();

		// Clean Repository
		for (MetricProvider oldMp : this.taskRepository.getRepository().getMetricProviders()) {
			this.taskRepository.getRepository().getMetricProviders().remove(oldMp);
		}
		this.taskRepository.getRepository().sync();

		List<IMetricProvider> platformProvider = this.platform.getMetricProviderManager().getMetricProviders();

		// Create metric providers
		for (IMetricProvider provider : platformProvider) {
			MetricProvider providerData = null;
			if (provider instanceof AbstractFactoidMetricProvider) {
				providerData = this.taskRepository.registreMetricProvider(provider.getIdentifier(),
						provider.getFriendlyName(),MetricProviderKind.FACTOID.name() ,provider.getSummaryInformation(), new ArrayList<String>());
			}else if (provider instanceof IHistoricalMetricProvider) {
				List<String> collections = new ArrayList<>();
				collections.add(((IHistoricalMetricProvider) provider).getCollectionName());
				providerData = this.taskRepository.registreMetricProvider(provider.getIdentifier(),
						provider.getFriendlyName(),MetricProviderKind.HISTORIC.name(), provider.getSummaryInformation(), collections);
			} else {
				providerData = this.taskRepository.registreMetricProvider(provider.getIdentifier(),
						provider.getFriendlyName(),MetricProviderKind.TRANSIENT.name(), provider.getSummaryInformation(), new ArrayList<String>());
			}

			metricsProviders.put(provider.getIdentifier(), providerData);
		}

		// Resolve Dependencys
		for (IMetricProvider provider : platformProvider) {
			List<MetricProvider> dependencys = new ArrayList<>();
			if (provider.getIdentifiersOfUses() != null) {
				for (String dependencyId : provider.getIdentifiersOfUses()) {
					MetricProvider metricDependency = metricsProviders.get(dependencyId);
					if (metricDependency != null) {
						dependencys.add(metricDependency);
					}
				}
			}

			taskRepository.addMetricProviderDependency(metricsProviders.get(provider.getIdentifier()), dependencys);
		}
	}

}
