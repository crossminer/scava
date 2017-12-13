/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.trans.committers.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class Committer extends Pongo {
	
	
	
	public Committer() { 
		super();
		NAME.setOwningType("org.eclipse.crossmeter.metricprovider.trans.committers.model.Committer");
		NUMBEROFCOMMITS.setOwningType("org.eclipse.crossmeter.metricprovider.trans.committers.model.Committer");
		LASTCOMMITTIME.setOwningType("org.eclipse.crossmeter.metricprovider.trans.committers.model.Committer");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static NumericalQueryProducer NUMBEROFCOMMITS = new NumericalQueryProducer("numberOfCommits");
	public static NumericalQueryProducer LASTCOMMITTIME = new NumericalQueryProducer("lastCommitTime");
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public Committer setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public int getNumberOfCommits() {
		return parseInteger(dbObject.get("numberOfCommits")+"", 0);
	}
	
	public Committer setNumberOfCommits(int numberOfCommits) {
		dbObject.put("numberOfCommits", numberOfCommits);
		notifyChanged();
		return this;
	}
	public long getLastCommitTime() {
		return parseLong(dbObject.get("lastCommitTime")+"", 0);
	}
	
	public Committer setLastCommitTime(long lastCommitTime) {
		dbObject.put("lastCommitTime", lastCommitTime);
		notifyChanged();
		return this;
	}
	
	
	
	
}