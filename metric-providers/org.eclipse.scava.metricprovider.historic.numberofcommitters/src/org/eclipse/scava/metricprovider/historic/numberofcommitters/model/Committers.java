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

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class Committers extends Pongo {
	
	
	
	public Committers() { 
		super();
		TOTALNUMBEROFCOMMITTERS.setOwningType("org.eclipse.scava.metricprovider.historic.numberofcommitters.model.Committers");
		NUMBEROFCOMMITTERSLAST1MONTH.setOwningType("org.eclipse.scava.metricprovider.historic.numberofcommitters.model.Committers");
		NUMBEROFCOMMITTERSLAST3MONTHS.setOwningType("org.eclipse.scava.metricprovider.historic.numberofcommitters.model.Committers");
		NUMBEROFCOMMITTERSLAST6MONTHS.setOwningType("org.eclipse.scava.metricprovider.historic.numberofcommitters.model.Committers");
		NUMBEROFCOMMITTERSLAST12MONTHS.setOwningType("org.eclipse.scava.metricprovider.historic.numberofcommitters.model.Committers");
	}
	
	public static NumericalQueryProducer TOTALNUMBEROFCOMMITTERS = new NumericalQueryProducer("totalNumberOfCommitters");
	public static NumericalQueryProducer NUMBEROFCOMMITTERSLAST1MONTH = new NumericalQueryProducer("numberOfCommittersLast1month");
	public static NumericalQueryProducer NUMBEROFCOMMITTERSLAST3MONTHS = new NumericalQueryProducer("numberOfCommittersLast3months");
	public static NumericalQueryProducer NUMBEROFCOMMITTERSLAST6MONTHS = new NumericalQueryProducer("numberOfCommittersLast6months");
	public static NumericalQueryProducer NUMBEROFCOMMITTERSLAST12MONTHS = new NumericalQueryProducer("numberOfCommittersLast12months");
	
	
	public int getTotalNumberOfCommitters() {
		return parseInteger(dbObject.get("totalNumberOfCommitters")+"", 0);
	}
	
	public Committers setTotalNumberOfCommitters(int totalNumberOfCommitters) {
		dbObject.put("totalNumberOfCommitters", totalNumberOfCommitters);
		notifyChanged();
		return this;
	}
	public int getNumberOfCommittersLast1month() {
		return parseInteger(dbObject.get("numberOfCommittersLast1month")+"", 0);
	}
	
	public Committers setNumberOfCommittersLast1month(int numberOfCommittersLast1month) {
		dbObject.put("numberOfCommittersLast1month", numberOfCommittersLast1month);
		notifyChanged();
		return this;
	}
	public int getNumberOfCommittersLast3months() {
		return parseInteger(dbObject.get("numberOfCommittersLast3months")+"", 0);
	}
	
	public Committers setNumberOfCommittersLast3months(int numberOfCommittersLast3months) {
		dbObject.put("numberOfCommittersLast3months", numberOfCommittersLast3months);
		notifyChanged();
		return this;
	}
	public int getNumberOfCommittersLast6months() {
		return parseInteger(dbObject.get("numberOfCommittersLast6months")+"", 0);
	}
	
	public Committers setNumberOfCommittersLast6months(int numberOfCommittersLast6months) {
		dbObject.put("numberOfCommittersLast6months", numberOfCommittersLast6months);
		notifyChanged();
		return this;
	}
	public int getNumberOfCommittersLast12months() {
		return parseInteger(dbObject.get("numberOfCommittersLast12months")+"", 0);
	}
	
	public Committers setNumberOfCommittersLast12months(int numberOfCommittersLast12months) {
		dbObject.put("numberOfCommittersLast12months", numberOfCommittersLast12months);
		notifyChanged();
		return this;
	}
	
	
	
	
}
