/*******************************************************************************
�* Copyright (c) 2019 Edge Hill University
�*�
�* This program and the accompanying materials are made
�* available under the terms of the Eclipse Public License 2.0
�* which is available at https://www.eclipse.org/legal/epl-2.0/
�*�
�* SPDX-License-Identifier: EPL-2.0
�******************************************************************************/
package org.eclipse.scava.metricprovider.indexing.documentation.document;

import java.util.Date;
import java.util.List;

public class DocumentationDocument {

	private String project_name;
	private String uid;
	private String documentation_id;
	private Date last_update;
	private List<String> documentation_entries;
	
	public DocumentationDocument(String projectName, String uid, String documentationId, Date lastUpdate) {
		this.project_name = projectName;
		this.uid = uid;
		this.documentation_id = documentationId;
		this.last_update = lastUpdate;
	}

	public List<String> getDocumentation_entries() {
		return documentation_entries;
	}

	public void setDocumentation_entries(List<String> documentationEntries) {
		this.documentation_entries = documentationEntries;
	}

	public String getProject_name() {
		return project_name;
	}

	public String getUid() {
		return uid;
	}

	public String getDocumentation_id() {
		return documentation_id;
	}

	public Date getLast_update() {
		return last_update;
	}

	


}
