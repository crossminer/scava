package org.eclipse.scava.metricprovider.historic.configuration.docker.dependencies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;

public class DockerDependenciesHistoricMetric extends Pongo {
	
	public static NumericalQueryProducer NUMBEROFDOCKERDEPENDENCIES = new NumericalQueryProducer("numberOfDockerDependencies");
	public static NumericalQueryProducer NUMBEROFDOCKERPACKAGEDEPENDENCIES = new NumericalQueryProducer("numberOfDockerPackageDependencies");
	public static NumericalQueryProducer NUMBEROFDOCKERIMAGEDEPENDENCIES = new NumericalQueryProducer("numberOfDockerImageDependencies");
	
	public DockerDependenciesHistoricMetric() {
		super();
		NUMBEROFDOCKERDEPENDENCIES.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.docker.dependencies.model.DockerDependenciesHistoricMetric");
		NUMBEROFDOCKERPACKAGEDEPENDENCIES.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.docker.dependencies.model.DockerDependenciesHistoricMetric");
		NUMBEROFDOCKERIMAGEDEPENDENCIES.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.docker.dependencies.model.DockerDependenciesHistoricMetric");
	}
	
	public int getNumberOfDockerDependencies() {
		return parseInteger(dbObject.get("numberOfDockerDependencies")+"", 0);
	}
	
	public DockerDependenciesHistoricMetric setNumberOfDockerDependencies(int numberOfDockerDependencies) {
		dbObject.put("numberOfDockerDependencies", numberOfDockerDependencies);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfDockerPackageDependencies() {
		return parseInteger(dbObject.get("numberOfDockerPackageDependencies")+"", 0);
	}
	
	public DockerDependenciesHistoricMetric setNumberOfDockerPackageDependencies(int numberOfDockerPackageDependencies) {
		dbObject.put("numberOfDockerPackageDependencies", numberOfDockerPackageDependencies);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfDockerImageDependencies() {
		return parseInteger(dbObject.get("numberOfDockerImageDependencies")+"", 0);
	}
	
	public DockerDependenciesHistoricMetric setNumberOfDockerImageDependencies(int numberOfDockerImageDependencies) {
		dbObject.put("numberOfDockerImageDependencies", numberOfDockerImageDependencies);
		notifyChanged();
		return this;
	}
}