/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.indexing.communicationchannels;

import java.util.ArrayList;
import java.util.List;

public class EnrichmentData {
	
	private List<String> emotional_Dimensions = new ArrayList<String>();
	private String sentiment;
	private String severity;
	private String plain_text;
	private String request_reply_classification;
	private Boolean code;
	private String content_class;
	
	
	public String getContent_class() {
		return content_class;
	}
	public void setContent_class(String content_class) throws NullPointerException {
		this.content_class = content_class;
	}
	public List<String> getEmotionalDimensions() {
		return emotional_Dimensions;
	}
	public void addEmotionalDimension(String emotionalDimension) throws NullPointerException {
		this.emotional_Dimensions.add(emotionalDimension);
	}
	public String getSentiment() {
		return sentiment;
	}
	public void setSentiment(String sentiment) throws NullPointerException {
		this.sentiment = sentiment;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) throws NullPointerException {
		this.severity = severity;
	}
	public String getPlain_text() {
		return plain_text;
	}
	public void setPlain_text(String plain_text) throws NullPointerException {
		this.plain_text = plain_text;
	}

	public String getRequest_reply_classification() {
		return request_reply_classification;
	}
	public void setRequest_reply_classification(String request_reply_classification) throws NullPointerException {
		this.request_reply_classification = request_reply_classification;
	}
	public Boolean getCode() {
		return code;
	}
	public void setCode(Boolean code) throws NullPointerException {
		this.code = code;
	}	
}
