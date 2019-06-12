package org.eclipse.scava.nlp.tools.license.prediction;

import org.eclipse.scava.nlp.tools.predictions.singlelabel.SingleLabelPrediction;

public class LicensePrediction extends SingleLabelPrediction{

	private Boolean licenseFound, isHeader, isGroup;
	private double score, ngramsMatchedPercent;
	private String licenseName, licenseGroup;
	
	public LicensePrediction(String text) {
		super(text);	
	}
	
	public LicensePrediction(Object id, String text) {
		super(id, text);
		
	}

	public Boolean getLicenseFound() {
		return licenseFound;
	}


	public void setLicenseFound(Boolean licenseFound) {
		this.licenseFound = licenseFound;
	}


	public Boolean getIsHeader() {
		return isHeader;
	}


	public void setIsHeader(Boolean isHeader) {
		this.isHeader = isHeader;
	}


	public Boolean getIsGroup() {
		return isGroup;
	}


	public void setIsGroup(Boolean isGroup) {
		this.isGroup = isGroup;
	}


	public double getScore() {
		return score;
	}


	public void setScore(double score) {
		this.score = score;
	}


	public double getNgramsMatchedPercent() {
		return ngramsMatchedPercent;
	}


	public void setNgramsMatchedPercent(double ngramsMatchedPercent) {
		this.ngramsMatchedPercent = ngramsMatchedPercent;
	}


	public String getLicenseName() {
		return licenseName;
	}


	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}


	public String getLicenseGroup() {
		return licenseGroup;
	}


	public void setLicenseGroup(String licenseGroup) {
		this.licenseGroup = licenseGroup;
	}
	
}
