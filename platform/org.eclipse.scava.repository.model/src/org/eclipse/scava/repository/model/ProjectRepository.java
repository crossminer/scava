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

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class ProjectRepository extends PongoDB {
	
	public ProjectRepository() {}
	
	public ProjectRepository(DB db) {
		setDb(db);
	}
	
	protected ProjectCollection projects = null;
	protected RoleCollection roles = null;
	protected ImportDataCollection gitHubImportData = null;
	protected ImportDataCollection sfImportData = null;
	protected PersonCollection persons = null;
	protected LicenseCollection licenses = null;
	protected MetricProviderCollection metricProviders = null;
	protected SchedulingInformationCollection schedulingInformation = null;
	protected MetricAnalysisCollection metricAnalysis = null;
	protected ManagerAnalysisCollection managerAnalysis = null;
	protected CompanyCollection companies = null;
	protected ProjectErrorCollection errors = null;
	
	
	
	public ProjectCollection getProjects() {
		return projects;
	}
	
	public RoleCollection getRoles() {
		return roles;
	}
	
	public ImportDataCollection getGitHubImportData() {
		return gitHubImportData;
	}
	
	public ImportDataCollection getSfImportData() {
		return sfImportData;
	}
	
	public PersonCollection getPersons() {
		return persons;
	}
	
	public LicenseCollection getLicenses() {
		return licenses;
	}
	
	public MetricProviderCollection getMetricProviders() {
		return metricProviders;
	}
	
	public SchedulingInformationCollection getSchedulingInformation() {
		return schedulingInformation;
	}
	
	public MetricAnalysisCollection getMetricAnalysis() {
		return metricAnalysis;
	}
	
	public ManagerAnalysisCollection getManagerAnalysis() {
		return managerAnalysis;
	}
	
	public CompanyCollection getCompanies() {
		return companies;
	}
	
	public ProjectErrorCollection getErrors() {
		return errors;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		projects = new ProjectCollection(db.getCollection("projects"));
		pongoCollections.add(projects);
		roles = new RoleCollection(db.getCollection("roles"));
		pongoCollections.add(roles);
		gitHubImportData = new ImportDataCollection(db.getCollection("gitHubImportData"));
		pongoCollections.add(gitHubImportData);
		sfImportData = new ImportDataCollection(db.getCollection("sfImportData"));
		pongoCollections.add(sfImportData);
		persons = new PersonCollection(db.getCollection("persons"));
		pongoCollections.add(persons);
		licenses = new LicenseCollection(db.getCollection("licenses"));
		pongoCollections.add(licenses);
		metricProviders = new MetricProviderCollection(db.getCollection("metricProviders"));
		pongoCollections.add(metricProviders);
		schedulingInformation = new SchedulingInformationCollection(db.getCollection("schedulingInformation"));
		pongoCollections.add(schedulingInformation);
		metricAnalysis = new MetricAnalysisCollection(db.getCollection("metricAnalysis"));
		pongoCollections.add(metricAnalysis);
		managerAnalysis = new ManagerAnalysisCollection(db.getCollection("managerAnalysis"));
		pongoCollections.add(managerAnalysis);
		companies = new CompanyCollection(db.getCollection("companies"));
		pongoCollections.add(companies);
		errors = new ProjectErrorCollection(db.getCollection("errors"));
		pongoCollections.add(errors);
	}
}
