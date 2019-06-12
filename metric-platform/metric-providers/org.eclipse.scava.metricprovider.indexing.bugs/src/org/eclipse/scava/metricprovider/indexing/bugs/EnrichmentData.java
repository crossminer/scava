/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.indexing.bugs;

import java.util.ArrayList;
import java.util.List;

public class EnrichmentData {
	
	private List<String> emotionalDimensions = new ArrayList<String>();
	private String sentiment;
	private String severity;
	private String plain_text;
	private String request_reply_classification;
	private Boolean code;
	private String content_class;
	
	
	public String getContent_class() {
		return content_class;
	}
	public void setContent_class(String content_class) {
		this.content_class = content_class;
	}
	public List<String> getEmotionalDimensions() {
		return emotionalDimensions;
	}
	public void addEmotionalDimension(String emotionalDimension){
		this.emotionalDimensions.add(emotionalDimension);
	}
	public String getSentiment() {
		return sentiment;
	}
	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public String getPlain_text() {
		return plain_text;
	}
	public void setPlain_text(String plain_text) {
		this.plain_text = plain_text;
	}

	public String getRequest_reply_classification() {
		return request_reply_classification;
	}
	public void setRequest_reply_classification(String request_reply_classification) {
		this.request_reply_classification = request_reply_classification;
	}
	public Boolean getCode() {
		return code;
	}
	public void setCode(Boolean code) {
		this.code = code;
	}	
}
