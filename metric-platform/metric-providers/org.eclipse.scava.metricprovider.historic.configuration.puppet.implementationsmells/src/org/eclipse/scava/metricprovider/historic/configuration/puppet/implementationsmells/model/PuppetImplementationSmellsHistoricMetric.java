package org.eclipse.scava.metricprovider.historic.configuration.puppet.implementationsmells.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;

public class PuppetImplementationSmellsHistoricMetric extends Pongo {
	
	public static NumericalQueryProducer NUMBEROFMISSINGDEFAULTCASE = new NumericalQueryProducer("numberOfMissingDefaultCaseSmells");
	public static NumericalQueryProducer NUMBEROFINCOSISTENAMINGSmells = new NumericalQueryProducer("numberOfInconsistentNamingSmells");
	public static NumericalQueryProducer NUMBEROFBUPLICATEENTITYMELLS = new NumericalQueryProducer("numberOfDuplicateEntitySmells");
	public static NumericalQueryProducer NUMBEROFMISPLACEDATTRIBUTESSMELLS = new NumericalQueryProducer("numberOfMisplacedAttributeSmells");
	public static NumericalQueryProducer NUMBEROFIMPROPERALIGNMENTSMELLS = new NumericalQueryProducer("numberOfImproperAlignmentSmells");
	public static NumericalQueryProducer NUMBEROFINVALIDPROPERTYSMELLS = new NumericalQueryProducer("numberOfInvalidPropertySmells");
	public static NumericalQueryProducer NUMBEROFIMPROPERQUOTESMELLS = new NumericalQueryProducer("numberOfImproperQuoteSmells");
	public static NumericalQueryProducer NUMBEROFLONGSTATEMENTSSMELLS = new NumericalQueryProducer("numberOfLongStatementsSmells");
	public static NumericalQueryProducer NUMBEROFUNGUARDEDVARIABLESMELLS = new NumericalQueryProducer("numberOfUnguardedVariableSmells");
	public static NumericalQueryProducer NUMBEROFMISSINGDOCSMELLS = new NumericalQueryProducer("numberOfMissingDocSmells");
	public static NumericalQueryProducer NUMBEROFDEPRECATEDSTATEMENTSSMELLS = new NumericalQueryProducer("numberOfDeprecatedStatementsSmells");
	public static NumericalQueryProducer NUMBEROFINCOMPLETETASKSSMELLS = new NumericalQueryProducer("numberOfIncompleteTasksSmells");
	public static NumericalQueryProducer NUMBEROFINCOMPLEXEXPRESSIONSMELLS = new NumericalQueryProducer("numberOfComplexExpressionSmells");
	public static NumericalQueryProducer NUMBEROFMISSINGELSESMELLS = new NumericalQueryProducer("numberOfMissingElseSmells");
	
	public static NumericalQueryProducer CUMULATIVENUMBEROFIMPLEMENTATIONSMELLS = new NumericalQueryProducer("cumulativeNumberOfImplementationSmells");
	
	public PuppetImplementationSmellsHistoricMetric() {
		super();
		NUMBEROFMISSINGDEFAULTCASE.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFINCOSISTENAMINGSmells.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFBUPLICATEENTITYMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFMISPLACEDATTRIBUTESSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFIMPROPERALIGNMENTSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFINVALIDPROPERTYSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFIMPROPERQUOTESMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFLONGSTATEMENTSSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFUNGUARDEDVARIABLESMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFMISSINGDOCSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFDEPRECATEDSTATEMENTSSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFINCOMPLETETASKSSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		NUMBEROFINCOMPLEXEXPRESSIONSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		
		NUMBEROFMISSINGELSESMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
		
		CUMULATIVENUMBEROFIMPLEMENTATIONSMELLS.setOwningType("org.eclipse.scava.metricprovider.historic.configuration.puppet.model.PuppetSmellsHistoricMetric");
	}
	
	public int getNumberOfMissingDefaultCaseSmells() {
		return parseInteger(dbObject.get("numberOfMissingDefaultCaseSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfMissingDefaultCaseSmells(int numberOfMissingDefaultCaseSmells) {
		dbObject.put("numberOfMissingDefaultCaseSmells", numberOfMissingDefaultCaseSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOInconsistentNamingSmells() {
		return parseInteger(dbObject.get("numberOfInconsistentNamingSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfInconsistentNamingSmells(int numberOfInconsistentNamingSmells) {
		dbObject.put("numberOfInconsistentNamingSmells", numberOfInconsistentNamingSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfDuplicateEntitySmells() {
		return parseInteger(dbObject.get("numberOfDuplicateEntitySmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfDuplicateEntitySmells(int numberOfDuplicateEntitySmells) {
		dbObject.put("numberOfDuplicateEntitySmells", numberOfDuplicateEntitySmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfMisplacedAttributeSmells() {
		return parseInteger(dbObject.get("numberOfMisplacedAttributeSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfMisplacedAttributeSmells(int numberOfMisplacedAttributeSmells) {
		dbObject.put("numberOfMisplacedAttributeSmells", numberOfMisplacedAttributeSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfImproperAlignment() {
		return parseInteger(dbObject.get("numberOfImproperAlignment")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfImproperAlignment(int numberOfImproperAlignment) {
		dbObject.put("numberOfImproperAlignment", numberOfImproperAlignment);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfInvalidPropertySmells() {
		return parseInteger(dbObject.get("numberOfInvalidPropertySmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfInvalidPropertySmells(int numberOfInvalidPropertySmells) {
		dbObject.put("numberOfInvalidPropertySmells", numberOfInvalidPropertySmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfImproperQuoteSmells() {
		return parseInteger(dbObject.get("numberOfImproperQuoteSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfImproperQuoteSmells(int numberOfImproperQuoteSmells) {
		dbObject.put("numberOfImproperQuoteSmells", numberOfImproperQuoteSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfLongStatementsSmells() {
		return parseInteger(dbObject.get("numberOfLongStatementsSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfLongStatementsSmells(int numberOfLongStatementsSmells) {
		dbObject.put("numberOfLongStatementsSmells", numberOfLongStatementsSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfUnguardedVariableSmells() {
		return parseInteger(dbObject.get("numberOfUnguardedVariableSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfUnguardedVariableSmells(int numberOfUnguardedVariableSmells) {
		dbObject.put("numberOfUnguardedVariableSmells", numberOfUnguardedVariableSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfMissingDocSmells() {
		return parseInteger(dbObject.get("numberOfMissingDocSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfMissingDocSmells(int numberOfMissingDocSmells) {
		dbObject.put("numberOfMissingDocSmells", numberOfMissingDocSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfDeprecatedStatementsSmells() {
		return parseInteger(dbObject.get("numberOfDeprecatedStatementsSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfDeprecatedStatementsSmells(int numberOfDeprecatedStatementsSmells) {
		dbObject.put("numberOfDeprecatedStatementsSmells", numberOfDeprecatedStatementsSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfIncompleteTasksSmells() {
		return parseInteger(dbObject.get("numberOfIncompleteTasksSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfIncompleteTasksSmells(int numberOfIncompleteTasksSmells) {
		dbObject.put("numberOfIncompleteTasksSmells", numberOfIncompleteTasksSmells);
		notifyChanged();
		return this;
	}
	
	public int getNumberOfComplexExpressionSmells() {
		return parseInteger(dbObject.get("numberOfComplexExpressionSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfComplexExpressionSmells(int numberOfComplexExpressionSmells) {
		dbObject.put("numberOfComplexExpressionSmells", numberOfComplexExpressionSmells);
		notifyChanged();
		return this;
	}
	
	
	public int getNumberOfMissingElseSmells() {
		return parseInteger(dbObject.get("numberOfMissingElseSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setNumberOfMissingElseSmells(int numberOfMissingElseSmells) {
		dbObject.put("numberOfMissingElseSmells", numberOfMissingElseSmells);
		notifyChanged();
		return this;
	}
	
	
	public int getCumulativeNumberOfImplementationUsers() {
		return parseInteger(dbObject.get("cumulativeNumberOfImplementationSmells")+"", 0);
	}
	
	public PuppetImplementationSmellsHistoricMetric setCumulativeNumberOfImplementationUsers(int cumulativeNumberOfImplementationUsers) {
		dbObject.put("cumulativeNumberOfImplementationSmells", cumulativeNumberOfImplementationUsers);
		notifyChanged();
		return this;
	}

}
