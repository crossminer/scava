/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.bugzilla;

import java.util.Date;

import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemBug;

public class BugzillaBug  extends BugTrackingSystemBug {

	private static final long serialVersionUID = 1L;
	
	private String product;
	private String operatingSystem;
	private String platform;
	private String resolution;
	private String priority;
	private String targetMilestone;
	private Date lastChangeTime;
	private Boolean isConfirmed;
	private String environment;
	private Boolean isOpen;
	private String storyPoints;
	private String documentationAction;
	private String crm;
	private String qualityAssuranceContact;
	private String category;
	private String mountType;
	private String fixedIn;
	private Boolean isCreatorAccessible;
	private Boolean isCcAccesible;
	private String verifiedBranch;
	private String releaseNotes;
	private String docType;
	private String cloneOf;
	private String assignedTo;
	private Date lastClosed;
	private String whiteBoard;
	private String regressionStatus;
	private String classification;
	private String type;
	private String summary;
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getOperatingSystem() {
		return operatingSystem;
	}
	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getTargetMilestone() {
		return targetMilestone;
	}
	public void setTargetMilestone(String targetMilestone) {
		this.targetMilestone = targetMilestone;
	}
	public Date getLastChangeTime() {
		return lastChangeTime;
	}
	public void setLastChangeTime(Date lastChangeTime) {
		this.lastChangeTime = lastChangeTime;
	}
	public Boolean getIsConfirmed() {
		return isConfirmed;
	}
	public void setIsConfirmed(Boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public Boolean getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}
	public String getStoryPoints() {
		return storyPoints;
	}
	public void setStoryPoints(String storyPoints) {
		this.storyPoints = storyPoints;
	}
	public String getDocumentationAction() {
		return documentationAction;
	}
	public void setDocumentationAction(String documentationAction) {
		this.documentationAction = documentationAction;
	}
	public String getCrm() {
		return crm;
	}
	public void setCrm(String crm) {
		this.crm = crm;
	}
	public String getQualityAssuranceContact() {
		return qualityAssuranceContact;
	}
	public void setQualityAssuranceContact(String qualityAssuranceContact) {
		this.qualityAssuranceContact = qualityAssuranceContact;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMountType() {
		return mountType;
	}
	public void setMountType(String mountType) {
		this.mountType = mountType;
	}
	public String getFixedIn() {
		return fixedIn;
	}
	public void setFixedIn(String fixedIn) {
		this.fixedIn = fixedIn;
	}
	public Boolean getIsCreatorAccessible() {
		return isCreatorAccessible;
	}
	public void setIsCreatorAccessible(Boolean isCreatorAccessible) {
		this.isCreatorAccessible = isCreatorAccessible;
	}
	public Boolean getIsCcAccesible() {
		return isCcAccesible;
	}
	public void setIsCcAccesible(Boolean isCcAccesible) {
		this.isCcAccesible = isCcAccesible;
	}
	public String getVerifiedBranch() {
		return verifiedBranch;
	}
	public void setVerifiedBranch(String verifiedBranch) {
		this.verifiedBranch = verifiedBranch;
	}
	public String getReleaseNotes() {
		return releaseNotes;
	}
	public void setReleaseNotes(String releaseNotes) {
		this.releaseNotes = releaseNotes;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getCloneOf() {
		return cloneOf;
	}
	public void setCloneOf(String cloneOf) {
		this.cloneOf = cloneOf;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public Date getLastClosed() {
		return lastClosed;
	}
	public void setLastClosed(Date lastClosed) {
		this.lastClosed = lastClosed;
	}
	public String getWhiteBoard() {
		return whiteBoard;
	}
	public void setWhiteBoard(String whiteBoard) {
		this.whiteBoard = whiteBoard;
	}
	public String getRegressionStatus() {
		return regressionStatus;
	}
	public void setRegressionStatus(String regressionStatus) {
		this.regressionStatus = regressionStatus;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
