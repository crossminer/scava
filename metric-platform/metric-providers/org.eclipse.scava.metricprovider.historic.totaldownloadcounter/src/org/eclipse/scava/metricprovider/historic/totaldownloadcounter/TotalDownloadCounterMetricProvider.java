/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.totaldownloadcounter;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.downloadcounter.model.Download;
import org.eclipse.scava.metricprovider.downloadcounter.model.DownloadCounter;
import org.eclipse.scava.metricprovider.downloadcounter.sourceforge.SourceForgeDownloadCounterMetricProvider;
import org.eclipse.scava.metricprovider.historic.totaldownloadcounter.model.TotalDownloadCounter;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.sourceforge.SourceForgeProject;

import com.googlecode.pongo.runtime.Pongo;

public class TotalDownloadCounterMetricProvider extends AbstractHistoricalMetricProvider{

	protected MetricProviderContext context;
	protected SourceForgeDownloadCounterMetricProvider downloadCounterMetricProvider;
	
	@Override
	public String getIdentifier() {
		return TotalDownloadCounterMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
		return project instanceof SourceForgeProject;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		// TODO Auto-generated method stub
		this.downloadCounterMetricProvider = (SourceForgeDownloadCounterMetricProvider)uses.get(0);
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		// TODO Auto-generated method stub
		return Arrays.asList("DownloadCounterMetricProvider");
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}
	
	@Override
	public Pongo measure(Project project) {
		
		DownloadCounter downloadCounter =  new DownloadCounter(context.getProjectDB(project));
		int totalCounter = 0;
		
		for (Download download : downloadCounter.getDownloads()) {
			totalCounter = totalCounter + download.getCounter();
		}
		
		TotalDownloadCounter totalDownloadCounter = new TotalDownloadCounter();
		totalDownloadCounter.setDownloads(totalCounter);
		
		return totalDownloadCounter;
	}

	@Override
	public String getShortIdentifier() {
		return "tdc";
	}

	@Override
	public String getFriendlyName() {
		return "Total downloads";
	}

	@Override
	public String getSummaryInformation() {
		return "Lorum";
	}

}
