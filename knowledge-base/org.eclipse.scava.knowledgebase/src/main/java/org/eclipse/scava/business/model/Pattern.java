package org.eclipse.scava.business.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Pattern {
	private String id;
	private String patternCode;
	private String patternName;
	private String patternFileName;
	private List<String> patternNamespaces;
	private Artifact artifact;
	public String getPatternCode() {
		return patternCode;
	}
	public void setPatternCode(String patternCode) {
		this.patternCode = patternCode;
	}
	public String getPatternName() {
		return patternName;
	}
	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}
	public String getPatternFileName() {
		return patternFileName;
	}
	public void setPatternFileName(String patternFileName) {
		this.patternFileName = patternFileName;
	}
	public List<String> getPatternNamespaces() {
		return patternNamespaces;
	}
	public void setPatternNamespaces(List<String> patternNamespaces) {
		this.patternNamespaces = patternNamespaces;
	}
	public Artifact getArtifact() {
		return artifact;
	}
	public void setArtifact(Artifact artifact) {
		this.artifact = artifact;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
