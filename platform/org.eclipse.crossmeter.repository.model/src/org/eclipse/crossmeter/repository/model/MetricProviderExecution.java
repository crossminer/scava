package org.eclipse.crossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class MetricProviderExecution extends Pongo {
	
	protected MetricProvider metricProvider = null;
	
	
	public MetricProviderExecution() { 
		super();
		dbObject.put("metricProvider", new BasicDBObject());
		METRICPROVIDERID.setOwningType("org.eclipse.crossmeter.repository.model.MetricProviderExecution");
		TYPE.setOwningType("org.eclipse.crossmeter.repository.model.MetricProviderExecution");
		LASTEXECUTED.setOwningType("org.eclipse.crossmeter.repository.model.MetricProviderExecution");
	}
	
	public static StringQueryProducer METRICPROVIDERID = new StringQueryProducer("metricProviderId"); 
	public static StringQueryProducer TYPE = new StringQueryProducer("type"); 
	public static StringQueryProducer LASTEXECUTED = new StringQueryProducer("lastExecuted"); 
	
	
	public String getMetricProviderId() {
		return parseString(dbObject.get("metricProviderId")+"", "");
	}
	
	public MetricProviderExecution setMetricProviderId(String metricProviderId) {
		dbObject.put("metricProviderId", metricProviderId);
		notifyChanged();
		return this;
	}
	public MetricProviderType getType() {
		MetricProviderType type = null;
		try {
			type = MetricProviderType.valueOf(dbObject.get("type")+"");
		}
		catch (Exception ex) {}
		return type;
	}
	
	public MetricProviderExecution setType(MetricProviderType type) {
		dbObject.put("type", type.toString());
		notifyChanged();
		return this;
	}
	public String getLastExecuted() {
		return parseString(dbObject.get("lastExecuted")+"", "-1");
	}
	
	public MetricProviderExecution setLastExecuted(String lastExecuted) {
		dbObject.put("lastExecuted", lastExecuted);
		notifyChanged();
		return this;
	}
	
	
	
	public MetricProviderExecution setMetricProvider(MetricProvider metricProvider) {
		if (this.metricProvider != metricProvider) {
			if (metricProvider == null) {
				dbObject.put("metricProvider", new BasicDBObject());
			}
			else {
				createReference("metricProvider", metricProvider);
			}
			this.metricProvider = metricProvider;
			notifyChanged();
		}
		return this;
	}
	
	public MetricProvider getMetricProvider() {
		if (metricProvider == null) {
			metricProvider = (MetricProvider) resolveReference("metricProvider");
		}
		return metricProvider;
	}
	
}