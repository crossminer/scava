package org.eclipse.scava.metricprovider.indexing.documentation.document.extra;

import org.eclipse.scava.nlp.tools.license.LicenseInformation;

public class License {
	
	private String group;
	private String name;
	private Boolean header_found;
	private Double score;
	private boolean is_deprecated_license;
	private boolean is_fsf_libre;
	private String license_comments;
	private boolean is_osi_approved;
	
	public License(String group, String name, Boolean header_found, Double score) {
		this.group=group;
		this.name=name;
		this.header_found=header_found;
		this.score=score;
		org.eclipse.scava.nlp.tools.license.License licenceInformation = LicenseInformation.retrieve(group,name);
		this.is_deprecated_license=licenceInformation.isIsDeprecatedLicenseId();
		this.is_fsf_libre=licenceInformation.isIsFsfLibre();
		this.license_comments=licenceInformation.getLicenseComments();
		this.is_osi_approved=licenceInformation.isIsOsiApproved();
	}
	
	public String getGroup() {
		return group;
	}

	public String getName() {
		return name;
	}

	public Boolean getHeader_found() {
		return header_found;
	}

	public Double getScore() {
		return score;
	}

	public boolean isIs_deprecated_license() {
		return is_deprecated_license;
	}

	public boolean isIs_fsf_libre() {
		return is_fsf_libre;
	}

	public String getLicense_comments() {
		return license_comments;
	}

	public boolean isIs_osi_approved() {
		return is_osi_approved;
	}
	
	
}
