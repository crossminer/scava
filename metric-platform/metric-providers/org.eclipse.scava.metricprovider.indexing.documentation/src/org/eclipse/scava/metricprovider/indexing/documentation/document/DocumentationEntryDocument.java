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

import org.eclipse.scava.metricprovider.indexing.documentation.document.extra.License;

public class DocumentationEntryDocument {

	private String project_name;
	private String uid;
	private String documentation_id;
	private String documentation_entry_id;
	private String body;
	private String original_format_mime;
	private String original_format_name;
	private Date created_at;
	
	
	// NLP
	private String sentiment;
	private String plain_text;
	private Double readability;
	private Boolean code;
	private List<String> documentation_types;
	private Boolean license_found;
	private License license;
	

	public DocumentationEntryDocument(String projectName,
										String uid,
										String documentationId,
										String documentationEntryId,
										String body,
										String originalFormatMime,
										String originalFormatName,
										Date createdAt) {
		this.project_name = projectName;
		this.uid = uid;
		this.documentation_id = documentationId;
		this.documentation_entry_id = documentationEntryId;
		this.body=body;
		this.original_format_mime=originalFormatMime;
		this.original_format_name=originalFormatName;
		this.created_at = createdAt;
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


	public String getDocumentation_entry_id() {
		return documentation_entry_id;
	}
	
	public String getBody() {
		return body;
	}
	
	public String getOriginal_format_mime() {
		return original_format_mime;
	}
	
	public String getOriginal_format_name() {
		return original_format_name;
	}

	public Date getCreated_at() {
		return created_at;
	}


	public String getSentiment() {
		return sentiment;
	}


	public String getPlain_text() {
		return plain_text;
	}


	public Boolean getCode() {
		return code;
	}
	
	public Double getReadability() {
		return readability;
	}
	
	public List<String> getDocumentation_types() {
		return documentation_types;
	}
	
	public Boolean getLicense_found() {
		return license_found;
	}
	
	public License getLicense() {
		return license;
	}

	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}


	public void setPlain_text(String plainText) {
		this.plain_text = plainText;
	}


	public void setCode(Boolean code) {
		this.code = code;
	}
	
	public void setReadability(Double readability) {
		this.readability = readability;
	}
	
	public void setDocumentation_types(List<String> documentation_types) {
		this.documentation_types = documentation_types;
	}
	
	public void setLicense_found(Boolean license_found) {
		this.license_found = license_found;
	}
	
	public void setLicense(String group, String name, Boolean header_found, Double score) {
		this.license = new License(group, name, header_found, score);
	}

}
