/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/

package org.eclipse.scava.metricprovider.trans.indexing.preparation;

import java.util.Collections;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.indexing.preparation.model.ExecutedMetricProviders;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.model.IndexPrepTransMetric;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.scava.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.cc.sympa.SympaMailingList;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.mongodb.DB;

public class IndexPreparationTransMetricProvider implements ITransientMetricProvider<IndexPrepTransMetric> {

	public final static String IDENTIFIER = IndexPreparationTransMetricProvider.class.getCanonicalName();
	
	public final static String SHORT_IDENTIFIER = "index preparation transmetric";

	public final static String FRIENDLY_NAME = "index preparation";

	public final static String DESCRIPTION = "this identifies the the metrics that have been chosen to be executed "
			+ "by the user in preparation for indexing (note: This is required to enable the indexing capabilities "
			+ "of the platform to be dynamic";


	protected MetricProviderContext context;
	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;
	protected PlatformCommunicationChannelManager communicationChannelManager;

	
	@Override
	public String getIdentifier() {

		return IDENTIFIER;
	}

	@Override
	public String getShortIdentifier() {

		return SHORT_IDENTIFIER;
	}

	@Override
	public String getFriendlyName() {

		return FRIENDLY_NAME;
	}

	@Override
	public String getSummaryInformation() {

		return DESCRIPTION;
	}

	@Override
	public boolean appliesTo(Project project) {
		
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
			if (communicationChannel instanceof Discussion) return true;
			if (communicationChannel instanceof EclipseForum) return true;
			if (communicationChannel instanceof SympaMailingList) return true;
		}
		return !project.getBugTrackingSystems().isEmpty();
	
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		// DO NOTHING -- we don't use anything
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		
		return Collections.emptyList();
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();

	}

	@Override
	public IndexPrepTransMetric adapt(DB db) {
		
	return new IndexPrepTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta delta, IndexPrepTransMetric db) {
		System.err.println("Entering Index Preparation");
		clearDB(db);// this ensures that the collection is always clean everytime it is executed
		ExecutedMetricProviders emp = new ExecutedMetricProviders();
		db.getExecutedMetricProviders().add(emp);
		db.sync();
		
		
	}
	
	private void clearDB(IndexPrepTransMetric db) {
		db.getExecutedMetricProviders().getDbCollection().drop();
		db.sync();
	}
	
	
}