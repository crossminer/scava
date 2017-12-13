/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.crossmeter.platform.factoids;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Factoid extends Pongo {
	
	protected List<String> metricDependencies = null;
	
	
	public Factoid() { 
		super();
		dbObject.put("metricDependencies", new BasicDBList());
		METRICID.setOwningType("org.eclipse.crossmeter.platform.factoids.Factoid");
		NAME.setOwningType("org.eclipse.crossmeter.platform.factoids.Factoid");
		FACTOID.setOwningType("org.eclipse.crossmeter.platform.factoids.Factoid");
		STARS.setOwningType("org.eclipse.crossmeter.platform.factoids.Factoid");
		METRICDEPENDENCIES.setOwningType("org.eclipse.crossmeter.platform.factoids.Factoid");
		CATEGORY.setOwningType("org.eclipse.crossmeter.platform.factoids.Factoid");
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