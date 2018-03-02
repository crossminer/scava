/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.numberofcommitters.model;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.committers.CommittersMetricProvider;
import org.eclipse.scava.metricprovider.trans.committers.model.Committer;
import org.eclipse.scava.metricprovider.trans.committers.model.ProjectCommitters;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class CommittersOverTime extends AbstractHistoricalMetricProvider {

	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	@Override
	public String getIdentifier() {
		return CommittersOverTime.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "committersovertime";
	}

	@Override
	public String getFriendlyName() {
		return "Number of committers over time";
	}

	@Override
	public String getSummaryInformation() {
		return "The number of committers on the project over time.";
	}

	@Override
	public boolean appliesTo(Project project) {
		return project.getVcsRepositories().size() > 0;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(CommittersMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public Pongo measure(Project project) {
		
		CommittersMetricProvider cmp = (CommittersMetricProvider)uses.get(0);
		ProjectCommitters pc = cmp.adapt(context.getProjectDB(project));
		
		Committers committers = new Committers();
		committers.setTotalNumberOfCommitters((int)pc.getCommitters().size());
		
		Date today = context.getDate().toJavaDate();
		
		System.out.println("Today: " + today + " (" + today.getTime()+")");
		
		Calendar c = Calendar.getInstance();
		c.setTime(today);
		c.add(Calendar.MONTH, -1);
		long oneMonthAgo=c.getTimeInMillis();
//		System.out.println("One month ago: " + c.getTime() + " (" + c.getTimeInMillis()+")");

		c.setTime(today);
		c.add(Calendar.MONTH, -3);
		long threeMonthsAgo=c.getTimeInMillis();
//		System.out.println("Three months ago: " + c.getTime() + " (" + c.getTimeInMillis()+")");

		c.setTime(today);
		c.add(Calendar.MONTH, -6);
		long sixMonthsAgo=c.getTimeInMillis();
//		System.out.println("Six months ago: " + c.getTime() + " (" + c.getTimeInMillis()+")");

		c.setTime(today);
		c.add(Calendar.MONTH, -12);
		long twelveMonthsAgo=c.getTimeInMillis();
//		System.out.println("Twelve months ago: " + c.getTime() + " (" + c.getTimeInMillis()+")");
		
		int numberOfCommittersLast1month = getSizeOfIterable(pc.getCommitters().find(Committer.LASTCOMMITTIME.greaterThan(oneMonthAgo)));
		int numberOfCommittersLast3month = getSizeOfIterable(pc.getCommitters().find(Committer.LASTCOMMITTIME.greaterThan(threeMonthsAgo)));
		int numberOfCommittersLast6month = getSizeOfIterable(pc.getCommitters().find(Committer.LASTCOMMITTIME.greaterThan(sixMonthsAgo)));
		int numberOfCommittersLast12month = getSizeOfIterable(pc.getCommitters().find(Committer.LASTCOMMITTIME.greaterThan(twelveMonthsAgo)));
		
		committers.setNumberOfCommittersLast1month(numberOfCommittersLast1month);
		committers.setNumberOfCommittersLast3months(numberOfCommittersLast3month);
		committers.setNumberOfCommittersLast6months(numberOfCommittersLast6month);
		committers.setNumberOfCommittersLast12months(numberOfCommittersLast12month);
		
		return committers;
	}
	
	protected int getSizeOfIterable(Iterable<?> iterable) {
		Iterator<?> it = iterable.iterator();
		int sum = 0;
		while (it.hasNext()){
			it.next();
			sum++;
		}
		return sum;
	}
}
