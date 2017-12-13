/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Yannis Korkontzelos - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.factoid.newsgroups.threadlength;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.eclipse.crossmeter.metricprovider.historic.newsgroups.threads.ThreadsHistoricMetricProvider;
import org.eclipse.crossmeter.metricprovider.historic.newsgroups.threads.model.NewsgroupsThreadsHistoricMetric;
import org.eclipse.crossmeter.platform.AbstractFactoidMetricProvider;
import org.eclipse.crossmeter.platform.Date;
import org.eclipse.crossmeter.platform.IMetricProvider;
import org.eclipse.crossmeter.platform.delta.ProjectDelta;
import org.eclipse.crossmeter.platform.factoids.Factoid;
import org.eclipse.crossmeter.platform.factoids.StarRating;
import org.eclipse.crossmeter.repository.model.CommunicationChannel;
import org.eclipse.crossmeter.repository.model.Project;
import org.eclipse.crossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class NewsgroupsChannelThreadLengthFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "NewsgroupChannelThreadLength";
	}

	@Override
	public String getFriendlyName() {
		return "Newsgroup Channel Thread Length";
		// This method will NOT be removed in a later version.
	}

	@Override
	public String getSummaryInformation() {
		return "summaryblah"; // This method will NOT be removed in a later version.
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
		}
		return false;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(ThreadsHistoricMetricProvider.IDENTIFIER);
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
//		factoid.setCategory(FactoidCategory.BUGS);
		factoid.setName(getFriendlyName());

		ThreadsHistoricMetricProvider threadsProvider = null;
		
		for (IMetricProvider m : this.uses) {
			if (m instanceof ThreadsHistoricMetricProvider) {
				threadsProvider = (ThreadsHistoricMetricProvider) m;
				continue;
			}
		}

		Date end = new Date();
		Date start = (new Date()).addDays(-30);
//		Date start=null, end=null;
//		try {
//			start = new Date("20040801");
//			end = new Date("20050801");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		List<Pongo> threadsList = threadsProvider.getHistoricalMeasurements(context, project, start, end);
		
		float averageComments = 0;

		if ( threadsList.size() > 0 ) {
			NewsgroupsThreadsHistoricMetric bugsPongo = 
					(NewsgroupsThreadsHistoricMetric) threadsList.get(threadsList.size() - 1);
			averageComments = bugsPongo.getAverageArticlesPerThread();
		}

		if ( (averageComments > 0 ) && (averageComments < 5 ) ) {
			factoid.setStars(StarRating.FOUR);
		} else if ( (averageComments > 0 ) && ( averageComments < 10 ) ) {
			factoid.setStars(StarRating.THREE);
		} else if ( (averageComments > 0 ) && ( averageComments < 20 ) ) {
			factoid.setStars(StarRating.TWO);
		} else {
			factoid.setStars(StarRating.ONE);
		}

		StringBuffer stringBuffer = new StringBuffer();
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		
		stringBuffer.append("Discussions tend to be ");
		if ( (averageComments > 0 ) && (averageComments < 5 ) ) {
			stringBuffer.append("very short");
		} else if ( (averageComments > 0 ) && ( averageComments < 10 ) ) {
			stringBuffer.append("short");
		} else if ( (averageComments > 0 ) && ( averageComments < 20 ) ) {
			stringBuffer.append("quite long");
		} else {
			stringBuffer.append("long");
		}
		stringBuffer.append(", containing approximately ");
		stringBuffer.append( decimalFormat.format(averageComments));
		stringBuffer.append(" articles.\n");

		factoid.setFactoid(stringBuffer.toString());

	}

}
