package org.eclipse.scava.metricprovider.historic.configuration.puppet.dependencies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;

public class PuppetDependenciesHistoricMetric extends Pongo {
	
	public static NumericalQueryProducer NUMBEROFPUPPETDEPENDENCIES = new NumericalQueryProducer("numberOfPuppetDependencies");
	
	public PuppetDependenciesHistoricMetric() {
		super();
		NUMBEROFPUPPETDEPENDENCIES.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.dependencies.model.PuppetDependenciesHistoricMetric");
	}
	
	public int getNumberOfPuppetDependencies() {
		return parseInteger(dbObject.get("numberOfPuppetDependencies")+"", 0);
	}
	
	public PuppetDependenciesHistoricMetric setNumberOfPuppetDependencies(int numberOfPuppetDependencies) {
		dbObject.put("numberOfPuppetDependencies", numberOfPuppetDependencies);
		notifyChanged();
		return this;
	}
}