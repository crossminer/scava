package org.eclipse.scava.metricprovider.historic.configuration.puppet.implementationsmells.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;

public class PuppetImplementationSmellsHistoricMetric extends Pongo {
	
	public static NumericalQueryProducer NUMBEROFMULTIFACETEDSMELLS = new NumericalQueryProducer("numberOfMultifacetedSmells");
	public static NumericalQueryProducer NUMBEROFUNNECESSARYSMELLS = new NumericalQueryProducer("numberOfUnnecessarySmells");
	public static NumericalQueryProducer NUMBEROFIMPERATIVESMELLS = new NumericalQueryProducer("numberOfImperativeSmells");
	public static NumericalQueryProducer NUMBEROFMISSABSMELLS = new NumericalQueryProducer("numberOfMissAbSmells");
	public static NumericalQueryProducer NUMBEROFINSUFFICIENTSMELLS = new NumericalQueryProducer("numberOfInsufficientSmells");
	public static NumericalQueryProducer NUMBEROFUNSTRUCTUREDSMELLS = new NumericalQueryProducer("numberOfUnstructuredSmells");
	public static NumericalQueryProducer NUMBEROFTIGHTSMELLS = new NumericalQueryProducer("numberOfTightSmells");
	public static NumericalQueryProducer NUMBEROFBROKENSMELLS = new NumericalQueryProducer("numberOfBrokenSmells");
	public static NumericalQueryProducer NUMBEROFMISSINGDEPSMELLS = new NumericalQueryProducer("numberOfMissingDepSmells");
	public static NumericalQueryProducer NUMBEROFHAIRBALLSMELLS = new NumericalQueryProducer("numberOfHairballSmells");
	public static NumericalQueryProducer NUMBEROFDEFICIENTSMELLS = new NumericalQueryProducer("numberOfDeficientSmells");
	public static NumericalQueryProducer NUMBEROFWEAKENSMELLS = new NumericalQueryProducer("numberOfWeakenSmells");
	public static NumericalQueryProducer CUMULATIVENUMBEROFDESIGNSMELLS = new NumericalQueryProducer("cumulativeNumberOfDesignSmells");
	
	public PuppetImplementationSmellsHistoricMetric() {
		super();
		NUMBEROFMULTIFACETEDSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFUNNECESSARYSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFIMPERATIVESMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFMISSABSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFINSUFFICIENTSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFUNSTRUCTUREDSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFTIGHTSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFBROKENSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFMISSINGDEPSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFHAIRBALLSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFDEFICIENTSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFWEAKENSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		CUMULATIVENUMBEROFDESIGNSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
	}
	
	public int getNumberOfMultifacetedSmells() {
		return parseInteger(dbObject.get("numberOfMultifacetedSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfMultifacetedSmells(int numberOfMultifacetedSmells) {
		dbObject.put("numberOfMultifacetedSmells", numberOfMultifacetedSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfUnnecessarySmells() {
		return parseInteger(dbObject.get("numberOfUnnecessarySmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfUnnecessarySmells(int numberOfUnnecessarySmells) {
		dbObject.put("numberOfUnnecessarySmells", numberOfUnnecessarySmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfImperativeSmells() {
		return parseInteger(dbObject.get("numberOfImperativeSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfImperativeSmells(int numberOfImperativeSmells) {
		dbObject.put("numberOfImperativeSmells", numberOfImperativeSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfMissAbSmells() {
		return parseInteger(dbObject.get("numberOfMissAbSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfMissAbSmells(int numberOfMissAbSmells) {
		dbObject.put("numberOfMissAbSmells", numberOfMissAbSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfInsufficientSmells() {
		return parseInteger(dbObject.get("numberOfInsufficientSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfInsufficientSmells(int numberOfInsufficientSmells) {
		dbObject.put("numberOfInsufficientSmells", numberOfInsufficientSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfUnstructuredSmells() {
		return parseInteger(dbObject.get("numberOfUnstructuredSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfUnstructuredSmells(int numberOfUnstructuredSmells) {
		dbObject.put("numberOfUnstructuredSmells", numberOfUnstructuredSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfTightSmells() {
		return parseInteger(dbObject.get("numberOfTightSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfTightSmells(int numberOfTightSmells) {
		dbObject.put("numberOfTightSmells", numberOfTightSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfBrokenSmells() {
		return parseInteger(dbObject.get("numberOfBrokenSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfBrokenSmells(int numberOfBrokenSmells) {
		dbObject.put("numberOfBrokenSmells", numberOfBrokenSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfMissingDepSmells() {
		return parseInteger(dbObject.get("numberOfMissingDepSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfMissingDepSmells(int numberOfMissingDepSmells) {
		dbObject.put("numberOfMissingDepSmells", numberOfMissingDepSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfHairballSmells() {
		return parseInteger(dbObject.get("numberOfHairballSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfHairballSmells(int numberOfHairballSmells) {
		dbObject.put("numberOfHairballSmells", numberOfHairballSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfDeficientSmells() {
		return parseInteger(dbObject.get("numberOfDeficientSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfDeficientSmells(int numberOfDeficientSmells) {
		dbObject.put("numberOfDeficientSmells", numberOfDeficientSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfWeakenSmells() {
		return parseInteger(dbObject.get("numberOfWeakenSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfWeakenSmells(int numberOfWeakenSmells) {
		dbObject.put("numberOfWeakenSmells", numberOfWeakenSmells);
		notifyChanged();
		return this;
	}
	
	public int getCumulativeNumberOfDesignUsers() {
		return parseInteger(dbObject.get("cumulativeNumberOfDesignSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setCumulativeNumberOfDesignUsers(int cumulativeNumberOfDesignUsers) {
		dbObject.put("cumulativeNumberOfDesignSmells", cumulativeNumberOfDesignUsers);
		notifyChanged();
		return this;
	}

}
