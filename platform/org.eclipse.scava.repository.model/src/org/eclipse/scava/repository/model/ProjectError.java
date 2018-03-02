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

import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;

// protected region custom-imports on begin
// protected region custom-imports end

public class ProjectError extends Pongo {
	
	
	// protected region custom-fields-and-methods on begin
	public static ProjectError create(String dateForError, String clazz, String projectId, 
			String projectName, Throwable exception, String workerIdentifier) {
		ProjectError error = new ProjectError();
		error.setDate(new Date());
		error.setDateForError(dateForError);
		error.setClazz(clazz);
		error.setProjectId(projectId);
		error.setProjectName(projectName);
		error.setStackTrace(ExceptionUtils.getStackTrace(exception));
		error.setWorkerIdentifier(workerIdentifier);
		return error;
	}
	// protected region custom-fields-and-methods end
	
	public ProjectError() { 
		super();
		DATE.setOwningType("org.eclipse.scava.repository.model.ProjectError");
		DATEFORERROR.setOwningType("org.eclipse.scava.repository.model.ProjectError");
		PROJECTID.setOwningType("org.eclipse.scava.repository.model.ProjectError");
		PROJECTNAME.setOwningType("org.eclipse.scava.repository.model.ProjectError");
		WORKERIDENTIFIER.setOwningType("org.eclipse.scava.repository.model.ProjectError");
		CLAZZ.setOwningType("org.eclipse.scava.repository.model.ProjectError");
		STACKTRACE.setOwningType("org.eclipse.scava.repository.model.ProjectError");
	}
	
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	public static StringQueryProducer DATEFORERROR = new StringQueryProducer("dateForError"); 
	public static StringQueryProducer PROJECTID = new StringQueryProducer("projectId"); 
	public static StringQueryProducer PROJECTNAME = new StringQueryProducer("projectName"); 
	public static StringQueryProducer WORKERIDENTIFIER = new StringQueryProducer("workerIdentifier"); 
	public static StringQueryProducer CLAZZ = new StringQueryProducer("clazz"); 
	public static StringQueryProducer STACKTRACE = new StringQueryProducer("stackTrace"); 
	
	
	public Date getDate() {
		return parseDate(dbObject.get("date")+"", null);
	}
	
	public ProjectError setDate(Date date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	public String getDateForError() {
		return parseString(dbObject.get("dateForError")+"", "");
	}
	
	public ProjectError setDateForError(String dateForError) {
		dbObject.put("dateForError", dateForError);
		notifyChanged();
		return this;
	}
	public String getProjectId() {
		return parseString(dbObject.get("projectId")+"", "");
	}
	
	public ProjectError setProjectId(String projectId) {
		dbObject.put("projectId", projectId);
		notifyChanged();
		return this;
	}
	public String getProjectName() {
		return parseString(dbObject.get("projectName")+"", "");
	}
	
	public ProjectError setProjectName(String projectName) {
		dbObject.put("projectName", projectName);
		notifyChanged();
		return this;
	}
	public String getWorkerIdentifier() {
		return parseString(dbObject.get("workerIdentifier")+"", "");
	}
	
	public ProjectError setWorkerIdentifier(String workerIdentifier) {
		dbObject.put("workerIdentifier", workerIdentifier);
		notifyChanged();
		return this;
	}
	public String getClazz() {
		return parseString(dbObject.get("clazz")+"", "");
	}
	
	public ProjectError setClazz(String clazz) {
		dbObject.put("clazz", clazz);
		notifyChanged();
		return this;
	}
	public String getStackTrace() {
		return parseString(dbObject.get("stackTrace")+"", "");
	}
	
	public ProjectError setStackTrace(String stackTrace) {
		dbObject.put("stackTrace", stackTrace);
		notifyChanged();
		return this;
	}
	
	
	
	
}
