/*******************************************************************************
 * Copyright (c) 2018 Softeam
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.osgi.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.platform.AbstractFactoidMetricProvider;
import org.eclipse.scava.platform.IHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.data.model.MetricProvider;
import org.eclipse.scava.platform.analysis.data.types.MetricProviderKind;

public class MetricProviderInitialiser {

	private Platform platform;

	public MetricProviderInitialiser(Platform platform) {
		this.platform = platform;
	}

	public void initialiseMetricProviderRepository() {
		Map<String, MetricProvider> metricsProviders = new HashMap<>();

		// Clean Repository
		for (MetricProvider oldMp : this.platform.getAnalysisRepositoryManager().getRepository().getMetricProviders()) {
			this.platform.getAnalysisRepositoryManager().getRepository().getMetricProviders().remove(oldMp);
		}
		this.platform.getAnalysisRepositoryManager().getRepository().sync();

		List<IMetricProvider> platformProvider = this.platform.getMetricProviderManager().getMetricProviders();

		// Create metric providers
		for (IMetricProvider provider : platformProvider) {
			MetricProvider providerData = null;
			if (provider instanceof AbstractFactoidMetricProvider) {
				providerData = this.platform.getAnalysisRepositoryManager().getMetricProviderService().registreMetricProvider(provider.getIdentifier(),
						provider.getFriendlyName(),MetricProviderKind.FACTOID.name() ,provider.getSummaryInformation(), new ArrayList<String>());
			}else if (provider instanceof IHistoricalMetricProvider) {
				List<String> collections = new ArrayList<>();
				collections.add(((IHistoricalMetricProvider) provider).getCollectionName());
				providerData = this.platform.getAnalysisRepositoryManager().getMetricProviderService().registreMetricProvider(provider.getIdentifier(),
						provider.getFriendlyName(),MetricProviderKind.HISTORIC.name(), provider.getSummaryInformation(), collections);
			} else {
				providerData = this.platform.getAnalysisRepositoryManager().getMetricProviderService().registreMetricProvider(provider.getIdentifier(),
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

			this.platform.getAnalysisRepositoryManager().getMetricProviderService().addMetricProviderDependency(metricsProviders.get(provider.getIdentifier()), dependencys);
		}
	}

}
