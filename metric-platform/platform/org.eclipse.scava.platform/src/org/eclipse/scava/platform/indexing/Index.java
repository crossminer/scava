package org.eclipse.scava.platform.indexing;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Index extends Pongo {
	
	protected List<String> metricDependencies = null;
	
	
	public Index() { 
		super();
		dbObject.put("metricDependencies", new BasicDBList());
		METRICID.setOwningType("org.eclipse.scava.platform.indexing.Index");
		INDEXNAME.setOwningType("org.eclipse.scava.platform.indexing.Index");
		METRICDEPENDENCIES.setOwningType("org.eclipse.scava.platform.indexing.Index");
	}
	
	public static StringQueryProducer METRICID = new StringQueryProducer("metricId"); 
	public static StringQueryProducer INDEXNAME = new StringQueryProducer("indexName"); 
	public static ArrayQueryProducer METRICDEPENDENCIES = new ArrayQueryProducer("metricDependencies");
	
	
	public String getMetricId() {
		return parseString(dbObject.get("metricId")+"", "");
	}
	
	public Index setMetricId(String metricId) {
		dbObject.put("metricId", metricId);
		notifyChanged();
		return this;
	}
	public String getIndexName() {
		return parseString(dbObject.get("indexName")+"", "");
	}
	
	public Index setIndexName(String indexName) {
		dbObject.put("indexName", indexName);
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