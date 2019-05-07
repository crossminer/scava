package org.eclipse.scava.metricprovider.trans.indexing.preparation.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ExecutedMetricProviders extends Pongo {
	
	protected List<String> metricIdentifiers = null;
	
	
	public ExecutedMetricProviders() { 
		super();
		dbObject.put("metricIdentifiers", new BasicDBList());
		METRICIDENTIFIERS.setOwningType("org.eclipse.scava.metricprovider.trans.indexing.preperation.model.ExecutedMetricProviders");
	}
	
	public static ArrayQueryProducer METRICIDENTIFIERS = new ArrayQueryProducer("metricIdentifiers");
	
	
	
	public List<String> getMetricIdentifiers() {
		if (metricIdentifiers == null) {
			metricIdentifiers = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("metricIdentifiers"));
		}
		return metricIdentifiers;
	}
	
	
	
}