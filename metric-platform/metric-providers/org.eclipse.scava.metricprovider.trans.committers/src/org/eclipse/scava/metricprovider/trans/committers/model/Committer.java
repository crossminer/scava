/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.committers.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class Committer extends Pongo {
	
	
	
	public Committer() { 
		super();
		NAME.setOwningType("org.eclipse.scava.metricprovider.trans.committers.model.Committer");
		NUMBEROFCOMMITS.setOwningType("org.eclipse.scava.metricprovider.trans.committers.model.Committer");
		LASTCOMMITTIME.setOwningType("org.eclipse.scava.metricprovider.trans.committers.model.Committer");
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
