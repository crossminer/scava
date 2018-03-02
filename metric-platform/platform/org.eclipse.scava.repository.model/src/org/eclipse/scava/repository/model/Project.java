/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Project extends NamedElement {
	
	protected List<VcsRepository> vcsRepositories = null;
	protected List<CommunicationChannel> communicationChannels = null;
	protected List<BugTrackingSystem> bugTrackingSystems = null;
	protected List<Person> persons = null;
	protected List<License> licenses = null;
	protected List<MetricProvider> metricProviderData = null;
	protected List<Company> companies = null;
	protected Project parent = null;
	protected ProjectExecutionInformation executionInformation = null;
	
	
	public Project() { 
		super();
		dbObject.put("parent", new BasicDBObject());
		dbObject.put("executionInformation", new ProjectExecutionInformation().getDbObject());
		dbObject.put("vcsRepositories", new BasicDBList());
		dbObject.put("communicationChannels", new BasicDBList());
		dbObject.put("bugTrackingSystems", new BasicDBList());
		dbObject.put("persons", new BasicDBList());
		dbObject.put("licenses", new BasicDBList());
		dbObject.put("metricProviderData", new BasicDBList());
		dbObject.put("companies", new BasicDBList());
		super.setSuperTypes("org.eclipse.scava.repository.model.NamedElement");
		NAME.setOwningType("org.eclipse.scava.repository.model.Project");
		SHORTNAME.setOwningType("org.eclipse.scava.repository.model.Project");
		DESCRIPTION.setOwningType("org.eclipse.scava.repository.model.Project");
		YEAR.setOwningType("org.eclipse.scava.repository.model.Project");
		ACTIVE.setOwningType("org.eclipse.scava.repository.model.Project");
		HOMEPAGE.setOwningType("org.eclipse.scava.repository.model.Project");
		ANALYSED.setOwningType("org.eclipse.scava.repository.model.Project");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer SHORTNAME = new StringQueryProducer("shortName"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static NumericalQueryProducer YEAR = new NumericalQueryProducer("year");
	public static StringQueryProducer ACTIVE = new StringQueryProducer("active"); 
	public static StringQueryProducer HOMEPAGE = new StringQueryProducer("homePage"); 
	public static StringQueryProducer ANALYSED = new StringQueryProducer("analysed"); 
	
	
	public String getShortName() {
		return parseString(dbObject.get("shortName")+"", "");
	}
	
	public Project setShortName(String shortName) {
		dbObject.put("shortName", shortName);
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public Project setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public int getYear() {
		return parseInteger(dbObject.get("year")+"", 0);
	}
	
	public Project setYear(int year) {
		dbObject.put("year", year);
		notifyChanged();
		return this;
	}
	public boolean getActive() {
		return parseBoolean(dbObject.get("active")+"", true);
	}
	
	public Project setActive(boolean active) {
		dbObject.put("active", active);
		notifyChanged();
		return this;
	}
	public String getHomePage() {
		return parseString(dbObject.get("homePage")+"", "");
	}
	
	public Project setHomePage(String homePage) {
		dbObject.put("homePage", homePage);
		notifyChanged();
		return this;
	}
	public boolean getAnalysed() {
		return parseBoolean(dbObject.get("analysed")+"", false);
	}
	
	public Project setAnalysed(boolean analysed) {
		dbObject.put("analysed", analysed);
		notifyChanged();
		return this;
	}
	
	
	public List<VcsRepository> getVcsRepositories() {
		if (vcsRepositories == null) {
			vcsRepositories = new PongoList<VcsRepository>(this, "vcsRepositories", true);
		}
		return vcsRepositories;
	}
	public List<CommunicationChannel> getCommunicationChannels() {
		if (communicationChannels == null) {
			communicationChannels = new PongoList<CommunicationChannel>(this, "communicationChannels", true);
		}
		return communicationChannels;
	}
	public List<BugTrackingSystem> getBugTrackingSystems() {
		if (bugTrackingSystems == null) {
			bugTrackingSystems = new PongoList<BugTrackingSystem>(this, "bugTrackingSystems", true);
		}
		return bugTrackingSystems;
	}
	public List<Person> getPersons() {
		if (persons == null) {
			persons = new PongoList<Person>(this, "persons", false);
		}
		return persons;
	}
	public List<License> getLicenses() {
		if (licenses == null) {
			licenses = new PongoList<License>(this, "licenses", false);
		}
		return licenses;
	}
	public List<MetricProvider> getMetricProviderData() {
		if (metricProviderData == null) {
			metricProviderData = new PongoList<MetricProvider>(this, "metricProviderData", true);
		}
		return metricProviderData;
	}
	public List<Company> getCompanies() {
		if (companies == null) {
			companies = new PongoList<Company>(this, "companies", true);
		}
		return companies;
	}
	
	public Project setParent(Project parent) {
		if (this.parent != parent) {
			if (parent == null) {
				dbObject.put("parent", new BasicDBObject());
			}
			else {
				createReference("parent", parent);
			}
			this.parent = parent;
			notifyChanged();
		}
		return this;
	}
	
	public Project getParent() {
		if (parent == null) {
			parent = (Project) resolveReference("parent");
		}
		return parent;
	}
	
	public ProjectExecutionInformation getExecutionInformation() {
		if (executionInformation == null && dbObject.containsField("executionInformation")) {
			executionInformation = (ProjectExecutionInformation) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("executionInformation"));
			executionInformation.setContainer(this);
		}
		return executionInformation;
	}
	
	public Project setExecutionInformation(ProjectExecutionInformation executionInformation) {
		if (this.executionInformation != executionInformation) {
			if (executionInformation == null) {
				dbObject.removeField("executionInformation");
			}
			else {
				dbObject.put("executionInformation", executionInformation.getDbObject());
			}
			this.executionInformation = executionInformation;
			notifyChanged();
		}
		return this;
	}
}
