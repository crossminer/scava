package org.eclipse.scava.metricprovider.historic.configuration.docker.smells.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;

public class DockerSmellsHistoricMetric extends Pongo {
	
	public static NumericalQueryProducer NUMBEROFIMPROPERUPGRADESMELLS = new NumericalQueryProducer("numberOfImproperUpgradeSmells");
	public static NumericalQueryProducer NUMBEROFUNKOWNPACKAGEVERSIONSMELLS = new NumericalQueryProducer("numberOfUnknownPackageVersionSmells");
	public static NumericalQueryProducer NUMBEROFUNTAGGEDIMAGESMELLS = new NumericalQueryProducer("numberOfUntaggedImageSmells");
	public static NumericalQueryProducer NUMBEROFIMPROPERSUDOSMELLS = new NumericalQueryProducer("numberOfImproperSudoSmells");
	public static NumericalQueryProducer NUMBEROFIMPROPERCOPYSMELLS = new NumericalQueryProducer("numberOfImproperCopySmells");
	public static NumericalQueryProducer NUMBEROFIMPROPERFROMSMELLS = new NumericalQueryProducer("numberOfImproperFromSmells");
	public static NumericalQueryProducer NUMBEROFIMPROPERCMDSMELLS = new NumericalQueryProducer("numberOfImproperCmdSmells");
	public static NumericalQueryProducer NUMBEROFMEANINGLESSSMELLS = new NumericalQueryProducer("numberOfMeaninglessSmells");
	public static NumericalQueryProducer NUMBEROFINVALIDPORTSSMELLS = new NumericalQueryProducer("numberOfInvalidPortsSmells");
	public static NumericalQueryProducer NUMBEROFIMPROPERSHELLSMELLS = new NumericalQueryProducer("numberOfImproperShellSmells");
	public static NumericalQueryProducer NUMBEROFIMPROPERENTRYPOINTSMELLS = new NumericalQueryProducer("numberOfImproperEntrypointSmells");
	public static NumericalQueryProducer NUMBEROFDEPRECATEDIINSTRUCTIONSMELLS = new NumericalQueryProducer("numberOfDeprecatedInstructionSmells");
	public static NumericalQueryProducer CUMULATIVENUMBEROFDOCKERSMELLS = new NumericalQueryProducer("cumulativeNumberOfDockerSmells");
	
	public DockerSmellsHistoricMetric() {
		super();
		NUMBEROFIMPROPERUPGRADESMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.docker.model.DockerSmellsHistoricMetric");
		NUMBEROFUNKOWNPACKAGEVERSIONSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.docker.model.DockerSmellsHistoricMetric");
		NUMBEROFUNTAGGEDIMAGESMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.docker.model.DockerSmellsHistoricMetric");
		NUMBEROFIMPROPERSUDOSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.docker.model.DockerSmellsHistoricMetric");
		NUMBEROFIMPROPERCOPYSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.docker.model.DockerSmellsHistoricMetric");
		NUMBEROFIMPROPERFROMSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.docker.model.DockerSmellsHistoricMetric");
		NUMBEROFIMPROPERCMDSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.docker.model.DockerSmellsHistoricMetric");
		NUMBEROFMEANINGLESSSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.docker.model.DockerSmellsHistoricMetric");
		NUMBEROFINVALIDPORTSSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.docker.model.DockerSmellsHistoricMetric");
		NUMBEROFIMPROPERSHELLSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.docker.model.DockerSmellsHistoricMetric");
		NUMBEROFIMPROPERENTRYPOINTSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.docker.model.DockerSmellsHistoricMetric");
		NUMBEROFDEPRECATEDIINSTRUCTIONSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.docker.model.DockerSmellsHistoricMetric");
		CUMULATIVENUMBEROFDOCKERSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.docker.model.DockerSmellsHistoricMetric");
	}
	
	public int getNumberOfImproperUpgradeSmells() {
		return parseInteger(dbObject.get("numberOfImproperUpgradeSmells")+"", 0);
	}
	
	public DockerSmellsHistoricMetric setNumberOfImproperUpgradeSmells(int numberOfImproperUpgradeSmells) {
		dbObject.put("numberOfImproperUpgradeSmells", numberOfImproperUpgradeSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfUnknownPackageVersionSmells() {
		return parseInteger(dbObject.get("numberOfUnknownPackageVersionSmells")+"", 0);
	}
	
	public DockerSmellsHistoricMetric setNumberOfUnknownPackageVersionSmells(int numberOfUnknownPackageVersionSmells) {
		dbObject.put("numberOfUnknownPackageVersionSmells", numberOfUnknownPackageVersionSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfUntaggedImageSmells() {
		return parseInteger(dbObject.get("numberOfUntaggedImageSmells")+"", 0);
	}
	
	public DockerSmellsHistoricMetric setNumberOfUntaggedImageSmells(int numberOfUntaggedImageSmells) {
		dbObject.put("numberOfUntaggedImageSmells", numberOfUntaggedImageSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfImproperSudoSmells() {
		return parseInteger(dbObject.get("numberOfImproperSudoSmells")+"", 0);
	}
	
	public DockerSmellsHistoricMetric setNumberOfImproperSudoSmells(int numberOfImproperSudoSmells) {
		dbObject.put("numberOfImproperSudoSmells", numberOfImproperSudoSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfImproperCopySmells() {
		return parseInteger(dbObject.get("numberOfImproperCopySmells")+"", 0);
	}
	
	public DockerSmellsHistoricMetric setNumberOfImproperCopySmells(int numberOfImproperCopySmells) {
		dbObject.put("numberOfImproperCopySmells", numberOfImproperCopySmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfImproperFromSmells() {
		return parseInteger(dbObject.get("numberOfImproperFromSmells")+"", 0);
	}
	
	public DockerSmellsHistoricMetric setNumberOfImproperFromSmells(int numberOfImproperFromSmells) {
		dbObject.put("numberOfImproperFromSmells", numberOfImproperFromSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfImproperCmdSmells() {
		return parseInteger(dbObject.get("numberOfImproperCmdSmells")+"", 0);
	}
	
	public DockerSmellsHistoricMetric setNumberOfImproperCmdSmells(int numberOfImproperCmdSmells) {
		dbObject.put("numberOfImproperCmdSmells", numberOfImproperCmdSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfMeaninglessSmells() {
		return parseInteger(dbObject.get("numberOfMeaninglessSmells")+"", 0);
	}
	
	public DockerSmellsHistoricMetric setNumberOfMeaninglessSmells(int numberOfMeaninglessSmells) {
		dbObject.put("numberOfMeaninglessSmells", numberOfMeaninglessSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfInvalidPortsSmells() {
		return parseInteger(dbObject.get("numberOfInvalidPortsSmells")+"", 0);
	}
	
	public DockerSmellsHistoricMetric setNumberOfInvalidPortsSmells(int numberOfInvalidPortsSmells) {
		dbObject.put("numberOfInvalidPortsSmells", numberOfInvalidPortsSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfImproperShellSmells() {
		return parseInteger(dbObject.get("numberOfImproperShellSmells")+"", 0);
	}
	
	public DockerSmellsHistoricMetric setNumberOfImproperShellSmells(int numberOfImproperShellSmells) {
		dbObject.put("numberOfImproperShellSmells", numberOfImproperShellSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfImproperEntrypointSmells() {
		return parseInteger(dbObject.get("numberOfImproperEntrypointSmells")+"", 0);
	}
	
	public DockerSmellsHistoricMetric setNumberOfImproperEntrypointSmells(int numberOfImproperEntrypointSmells) {
		dbObject.put("numberOfImproperEntrypointSmells", numberOfImproperEntrypointSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfDeprecatedInstructionSmells() {
		return parseInteger(dbObject.get("numberOfDeprecatedInstructionSmells")+"", 0);
	}
	
	public DockerSmellsHistoricMetric setNumberOfDeprecatedInstructionSmells(int numberOfDeprecatedInstructionSmells) {
		dbObject.put("numberOfDeprecatedInstructionSmells", numberOfDeprecatedInstructionSmells);
		notifyChanged();
		return this;
	}
	
	public int getCumulativeNumberOfDockerSmells() {
		return parseInteger(dbObject.get("cumulativeNumberOfDockerSmells")+"", 0);
	}
	
	public DockerSmellsHistoricMetric setCumulativeNumberOfDockerSmells(int cumulativeNumberOfDockerUsers) {
		dbObject.put("cumulativeNumberOfDockerSmells", cumulativeNumberOfDockerUsers);
		notifyChanged();
		return this;
	}
}