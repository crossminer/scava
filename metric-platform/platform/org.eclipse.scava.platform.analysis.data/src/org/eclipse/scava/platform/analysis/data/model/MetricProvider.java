/*******************************************************************************
 * Copyright (c) 2018 Softeam
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.analysis.data.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class MetricProvider extends Pongo {
	
	protected List<MetricProvider> dependOf = null;
	protected List<DataStorage> storages = null;
	
	
	public MetricProvider() { 
		super();
		dbObject.put("dependOf", new BasicDBList());
		dbObject.put("storages", new BasicDBList());
		METRICPROVIDERID.setOwningType("org.eclipse.scava.platform.analysis.data.model.MetricProvider");
		LABEL.setOwningType("org.eclipse.scava.platform.analysis.data.model.MetricProvider");
		DESCRIPTION.setOwningType("org.eclipse.scava.platform.analysis.data.model.MetricProvider");
		KIND.setOwningType("org.eclipse.scava.platform.analysis.data.model.MetricProvider");
	}
	
	public static StringQueryProducer METRICPROVIDERID = new StringQueryProducer("metricProviderId"); 
	public static StringQueryProducer LABEL = new StringQueryProducer("label"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static StringQueryProducer KIND = new StringQueryProducer("kind"); 
	
	
	public String getMetricProviderId() {
		return parseString(dbObject.get("metricProviderId")+"", "");
	}
	
	public MetricProvider setMetricProviderId(String metricProviderId) {
		dbObject.put("metricProviderId", metricProviderId);
		notifyChanged();
		return this;
	}
	public String getLabel() {
		return parseString(dbObject.get("label")+"", "");
	}
	
	public MetricProvider setLabel(String label) {
		dbObject.put("label", label);
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public MetricProvider setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public String getKind() {
		return parseString(dbObject.get("kind")+"", "");
	}
	
	public MetricProvider setKind(String kind) {
		dbObject.put("kind", kind);
		notifyChanged();
		return this;
	}
	
	
	public List<MetricProvider> getDependOf() {
		if (dependOf == null) {
			dependOf = new PongoList<MetricProvider>(this, "dependOf", false);
		}
		return dependOf;
	}
	public List<DataStorage> getStorages() {
		if (storages == null) {
			storages = new PongoList<DataStorage>(this, "storages", true);
		}
		return storages;
	}
	
	
}