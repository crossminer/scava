/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.factoids;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Factoid extends Pongo {
	
	protected List<String> metricDependencies = null;
	
	
	public Factoid() { 
		super();
		dbObject.put("metricDependencies", new BasicDBList());
		METRICID.setOwningType("org.eclipse.scava.platform.factoids.Factoid");
		NAME.setOwningType("org.eclipse.scava.platform.factoids.Factoid");
		FACTOID.setOwningType("org.eclipse.scava.platform.factoids.Factoid");
		STARS.setOwningType("org.eclipse.scava.platform.factoids.Factoid");
		METRICDEPENDENCIES.setOwningType("org.eclipse.scava.platform.factoids.Factoid");
		CATEGORY.setOwningType("org.eclipse.scava.platform.factoids.Factoid");
	}
	
	public static StringQueryProducer METRICID = new StringQueryProducer("metricId"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer FACTOID = new StringQueryProducer("factoid"); 
	public static StringQueryProducer STARS = new StringQueryProducer("stars"); 
	public static StringQueryProducer CATEGORY = new StringQueryProducer("category"); 
	public static ArrayQueryProducer METRICDEPENDENCIES = new ArrayQueryProducer("metricDependencies");
	
	
	public String getMetricId() {
		return parseString(dbObject.get("metricId")+"", "");
	}
	
	public Factoid setMetricId(String metricId) {
		dbObject.put("metricId", metricId);
		notifyChanged();
		return this;
	}
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public Factoid setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public String getFactoid() {
		return parseString(dbObject.get("factoid")+"", "");
	}
	
	public Factoid setFactoid(String factoid) {
		dbObject.put("factoid", factoid);
		notifyChanged();
		return this;
	}
	public StarRating getStars() {
		StarRating stars = null;
		try {
			stars = StarRating.valueOf(dbObject.get("stars")+"");
		}
		catch (Exception ex) {}
		return stars;
	}
	
	public Factoid setStars(StarRating stars) {
		dbObject.put("stars", stars.toString());
		notifyChanged();
		return this;
	}
	public FactoidCategory getCategory() {
		FactoidCategory category = null;
		try {
			category = FactoidCategory.valueOf(dbObject.get("category")+"");
		}
		catch (Exception ex) {}
		return category;
	}
	
	public Factoid setCategory(FactoidCategory category) {
		dbObject.put("category", category.toString());
		notifyChanged();
		return this;
	}
	
	public List<String> getMetricDependencies() {
		if (metricDependencies == null) {
			metricDependencies = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("metricDependencies"));
		}
		return metricDependencies;
	}
	
	
	
}
