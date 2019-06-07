/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.license;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "isDeprecatedLicenseId", "isFsfLibre", "licenseText", "standardLicenseHeaderTemplate",
		"standardLicenseTemplate", "name", "licenseComments", "licenseId", "standardLicenseHeader", "seeAlso",
		"isOsiApproved" })
public class License implements Serializable {

	/**
		 * 
		 */
	private static final long serialVersionUID = -111892632947723193L;

	private int numberOfNgrams;

	@JsonProperty("isDeprecatedLicenseId")
	private boolean isDeprecatedLicenseId;
	@JsonProperty("isFsfLibre")
	private boolean isFsfLibre;
	@JsonProperty("licenseText")
	private String licenseText;
	@JsonProperty("standardLicenseHeaderTemplate")
	private String standardLicenseHeaderTemplate;
	@JsonProperty("standardLicenseTemplate")
	private String standardLicenseTemplate;
	@JsonProperty("name")
	private String name;
	@JsonProperty("licenseComments")
	private String licenseComments;
	@JsonProperty("licenseId")
	private String licenseId;
	@JsonProperty("standardLicenseHeader")
	private String standardLicenseHeader;
	@JsonProperty("seeAlso")
	private List<String> seeAlso = null;
	@JsonProperty("isOsiApproved")
	private boolean isOsiApproved;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("isDeprecatedLicenseId")
	public boolean isIsDeprecatedLicenseId() {
		return isDeprecatedLicenseId;
	}

	@JsonProperty("isDeprecatedLicenseId")
	public void setIsDeprecatedLicenseId(boolean isDeprecatedLicenseId) {
		this.isDeprecatedLicenseId = isDeprecatedLicenseId;
	}

	@JsonProperty("isFsfLibre")
	public boolean isIsFsfLibre() {
		return isFsfLibre;
	}

	@JsonProperty("isFsfLibre")
	public void setIsFsfLibre(boolean isFsfLibre) {
		this.isFsfLibre = isFsfLibre;
	}

	@JsonProperty("licenseText")
	public String getLicenseText() {
		return licenseText;
	}

	@JsonProperty("licenseText")
	public void setLicenseText(String licenseText) {
		this.licenseText = licenseText;
	}

	@JsonProperty("standardLicenseHeaderTemplate")
	public String getStandardLicenseHeaderTemplate() {
		return standardLicenseHeaderTemplate;
	}

	@JsonProperty("standardLicenseHeaderTemplate")
	public void setStandardLicenseHeaderTemplate(String standardLicenseHeaderTemplate) {
		this.standardLicenseHeaderTemplate = standardLicenseHeaderTemplate;
	}

	@JsonProperty("standardLicenseTemplate")
	public String getStandardLicenseTemplate() {
		return standardLicenseTemplate;
	}

	@JsonProperty("standardLicenseTemplate")
	public void setStandardLicenseTemplate(String standardLicenseTemplate) {
		this.standardLicenseTemplate = standardLicenseTemplate;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("licenseComments")
	public String getLicenseComments() {
		return licenseComments;
	}

	@JsonProperty("licenseComments")
	public void setLicenseComments(String licenseComments) {
		this.licenseComments = licenseComments;
	}

	@JsonProperty("licenseId")
	public String getLicenseId() {
		return licenseId;
	}

	@JsonProperty("licenseId")
	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	@JsonProperty("standardLicenseHeader")
	public String getStandardLicenseHeader() {
		return standardLicenseHeader;
	}

	@JsonProperty("standardLicenseHeader")
	public void setStandardLicenseHeader(String standardLicenseHeader) {
		this.standardLicenseHeader = standardLicenseHeader;
	}

	@JsonProperty("seeAlso")
	public List<String> getSeeAlso() {
		return seeAlso;
	}

	@JsonProperty("seeAlso")
	public void setSeeAlso(List<String> seeAlso) {
		this.seeAlso = seeAlso;
	}

	@JsonProperty("isOsiApproved")
	public boolean isIsOsiApproved() {
		return isOsiApproved;
	}

	@JsonProperty("isOsiApproved")
	public void setIsOsiApproved(boolean isOsiApproved) {
		this.isOsiApproved = isOsiApproved;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public int getNumberOfNgrams() {
		return numberOfNgrams;
	}

	public void setNumberOfNgrams(int numberOfNgrams) {
		this.numberOfNgrams = numberOfNgrams;
	}

}
