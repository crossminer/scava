/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.mantis;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class MantisIssue extends Pongo {
	
	protected List<MantisCategory> category = null;
	protected List<MantisViewState> view_state = null;
	protected List<MantisNote> notes = null;
	protected List<MantisTag> tags = null;
	protected MantisProject project = null;
	protected MantisReporter reporter = null;
	protected MantisHandler handler = null;
	protected MantisStatus status = null;
	protected MantisResoultion resolution = null;
	protected MantisPriority priority = null;
	protected MantisSeverity severity = null;
	protected MantisReproducability reproducibility = null;
	
	
	public MantisIssue() { 
		super();
		dbObject.put("project", new BasicDBObject());
		dbObject.put("reporter", new BasicDBObject());
		dbObject.put("handler", new BasicDBObject());
		dbObject.put("status", new BasicDBObject());
		dbObject.put("resolution", new BasicDBObject());
		dbObject.put("priority", new BasicDBObject());
		dbObject.put("severity", new BasicDBObject());
		dbObject.put("reproducibility", new BasicDBObject());
		dbObject.put("category", new BasicDBList());
		dbObject.put("view_state", new BasicDBList());
		dbObject.put("notes", new BasicDBList());
		dbObject.put("tags", new BasicDBList());
		_ID.setOwningType("org.eclipse.scava.repository.model.mantis.MantisIssue");
		SUMMARY.setOwningType("org.eclipse.scava.repository.model.mantis.MantisIssue");
		DESCRIPTION.setOwningType("org.eclipse.scava.repository.model.mantis.MantisIssue");
		STICKY.setOwningType("org.eclipse.scava.repository.model.mantis.MantisIssue");
		CREATED_AT.setOwningType("org.eclipse.scava.repository.model.mantis.MantisIssue");
		UPDATED_AT.setOwningType("org.eclipse.scava.repository.model.mantis.MantisIssue");
	}
	
	public static StringQueryProducer _ID = new StringQueryProducer("_id"); 
	public static StringQueryProducer SUMMARY = new StringQueryProducer("summary"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static StringQueryProducer STICKY = new StringQueryProducer("sticky"); 
	public static StringQueryProducer CREATED_AT = new StringQueryProducer("created_at"); 
	public static StringQueryProducer UPDATED_AT = new StringQueryProducer("updated_at"); 
	
	
	public String get_id() {
		return parseString(dbObject.get("_id")+"", "");
	}
	
	public MantisIssue set_id(String _id) {
		dbObject.put("_id", _id);
		notifyChanged();
		return this;
	}
	public String getSummary() {
		return parseString(dbObject.get("summary")+"", "");
	}
	
	public MantisIssue setSummary(String summary) {
		dbObject.put("summary", summary);
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public MantisIssue setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public String getSticky() {
		return parseString(dbObject.get("sticky")+"", "");
	}
	
	public MantisIssue setSticky(String sticky) {
		dbObject.put("sticky", sticky);
		notifyChanged();
		return this;
	}
	public String getCreated_at() {
		return parseString(dbObject.get("created_at")+"", "");
	}
	
	public MantisIssue setCreated_at(String created_at) {
		dbObject.put("created_at", created_at);
		notifyChanged();
		return this;
	}
	public String getUpdated_at() {
		return parseString(dbObject.get("updated_at")+"", "");
	}
	
	public MantisIssue setUpdated_at(String updated_at) {
		dbObject.put("updated_at", updated_at);
		notifyChanged();
		return this;
	}
	
	
	public List<MantisCategory> getCategory() {
		if (category == null) {
			category = new PongoList<MantisCategory>(this, "category", true);
		}
		return category;
	}
	public List<MantisViewState> getView_state() {
		if (view_state == null) {
			view_state = new PongoList<MantisViewState>(this, "view_state", true);
		}
		return view_state;
	}
	public List<MantisNote> getNotes() {
		if (notes == null) {
			notes = new PongoList<MantisNote>(this, "notes", true);
		}
		return notes;
	}
	public List<MantisTag> getTags() {
		if (tags == null) {
			tags = new PongoList<MantisTag>(this, "tags", true);
		}
		return tags;
	}
	
	public MantisIssue setProject(MantisProject project) {
		if (this.project != project) {
			if (project == null) {
				dbObject.put("project", new BasicDBObject());
			}
			else {
				createReference("project", project);
			}
			this.project = project;
			notifyChanged();
		}
		return this;
	}
	
	public MantisProject getProject() {
		if (project == null) {
			project = (MantisProject) resolveReference("project");
		}
		return project;
	}
	public MantisIssue setReporter(MantisReporter reporter) {
		if (this.reporter != reporter) {
			if (reporter == null) {
				dbObject.put("reporter", new BasicDBObject());
			}
			else {
				createReference("reporter", reporter);
			}
			this.reporter = reporter;
			notifyChanged();
		}
		return this;
	}
	
	public MantisReporter getReporter() {
		if (reporter == null) {
			reporter = (MantisReporter) resolveReference("reporter");
		}
		return reporter;
	}
	public MantisIssue setHandler(MantisHandler handler) {
		if (this.handler != handler) {
			if (handler == null) {
				dbObject.put("handler", new BasicDBObject());
			}
			else {
				createReference("handler", handler);
			}
			this.handler = handler;
			notifyChanged();
		}
		return this;
	}
	
	public MantisHandler getHandler() {
		if (handler == null) {
			handler = (MantisHandler) resolveReference("handler");
		}
		return handler;
	}
	public MantisIssue setStatus(MantisStatus status) {
		if (this.status != status) {
			if (status == null) {
				dbObject.put("status", new BasicDBObject());
			}
			else {
				createReference("status", status);
			}
			this.status = status;
			notifyChanged();
		}
		return this;
	}
	
	public MantisStatus getStatus() {
		if (status == null) {
			status = (MantisStatus) resolveReference("status");
		}
		return status;
	}
	public MantisIssue setResolution(MantisResoultion resolution) {
		if (this.resolution != resolution) {
			if (resolution == null) {
				dbObject.put("resolution", new BasicDBObject());
			}
			else {
				createReference("resolution", resolution);
			}
			this.resolution = resolution;
			notifyChanged();
		}
		return this;
	}
	
	public MantisResoultion getResolution() {
		if (resolution == null) {
			resolution = (MantisResoultion) resolveReference("resolution");
		}
		return resolution;
	}
	public MantisIssue setPriority(MantisPriority priority) {
		if (this.priority != priority) {
			if (priority == null) {
				dbObject.put("priority", new BasicDBObject());
			}
			else {
				createReference("priority", priority);
			}
			this.priority = priority;
			notifyChanged();
		}
		return this;
	}
	
	public MantisPriority getPriority() {
		if (priority == null) {
			priority = (MantisPriority) resolveReference("priority");
		}
		return priority;
	}
	public MantisIssue setSeverity(MantisSeverity severity) {
		if (this.severity != severity) {
			if (severity == null) {
				dbObject.put("severity", new BasicDBObject());
			}
			else {
				createReference("severity", severity);
			}
			this.severity = severity;
			notifyChanged();
		}
		return this;
	}
	
	public MantisSeverity getSeverity() {
		if (severity == null) {
			severity = (MantisSeverity) resolveReference("severity");
		}
		return severity;
	}
	public MantisIssue setReproducibility(MantisReproducability reproducibility) {
		if (this.reproducibility != reproducibility) {
			if (reproducibility == null) {
				dbObject.put("reproducibility", new BasicDBObject());
			}
			else {
				createReference("reproducibility", reproducibility);
			}
			this.reproducibility = reproducibility;
			notifyChanged();
		}
		return this;
	}
	
	public MantisReproducability getReproducibility() {
		if (reproducibility == null) {
			reproducibility = (MantisReproducability) resolveReference("reproducibility");
		}
		return reproducibility;
	}
	
}