package org.eclipse.scava.business.model;

import java.util.Date;

import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MavenLibrary {
	public MavenLibrary() {
		super();
	}

	public MavenLibrary(String artifactid, String groupid, String version) {
		super();
		this.artifactid = artifactid;
		this.groupid = groupid;
		this.version = version;
		this.artifact = new DefaultArtifact(String.format("%s:%s:%s", groupid, artifactid, version));
	}

	@Id
	private String id;
	private String completeLibrary;
	private String artifactid;
	private String groupid;
	private String version;
	private Date releasedate;
	@JsonIgnore
	@Transient
	private Artifact artifact;

	public String getCompleteLibrary() {
		return completeLibrary;
	}

	public void setCompleteLibrary(String completeLibrary) {
		this.completeLibrary = completeLibrary;
	}

	public String getVersion() {
		return version;
	}

	@JsonIgnore
	@Transient
	public String getCoordinate() {
		return String.format("%s:%s:%s", groupid, artifactid, version);
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getReleasedate() {
		return releasedate;
	}

	public void setReleasedate(Date release) {
		this.releasedate = release;
	}

	public String getArtifactid() {
		return artifactid;
	}

	public void setArtifactid(String artifactid) {
		this.artifactid = artifactid;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public Artifact getArtifact() {
		return artifact;
	}

	public void setArtifact(Artifact artifact) {
		this.artifact = artifact;
	}
}
