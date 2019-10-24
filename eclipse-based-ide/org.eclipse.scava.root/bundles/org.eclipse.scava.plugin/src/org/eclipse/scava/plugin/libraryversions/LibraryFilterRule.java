/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions;

public class LibraryFilterRule {
	private String GroupId;
	private String ArtifactId;
	private String Version;

	public LibraryFilterRule(String groupId, String artifactId, String version) {
		super();
		GroupId = groupId;
		ArtifactId = artifactId;
		Version = version;
	}

	public String getGroupId() {
		return GroupId;
	}

	public String getArtifactId() {
		return ArtifactId;
	}

	public String getVersion() {
		return Version;
	}

	public void setGroupId(String groupId) {
		GroupId = groupId;
	}

	public void setArtifactId(String artifactId) {
		ArtifactId = artifactId;
	}

	public void setVersion(String version) {
		Version = version;
	}

	public boolean match(Library library) {
		if (getGroupId() != null && !getGroupId().isEmpty() && !library.getGroupId().matches(getGroupId())) {
			return false;
		}

		if (getArtifactId() != null && !getArtifactId().isEmpty()
				&& !library.getArtifactId().matches(getArtifactId())) {
			return false;
		}

		if (getVersion() != null && !getVersion().isEmpty() && !library.getVersion().matches(getVersion())) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "LibraryVersionRule [GroupId=" + GroupId + ", ArtifactId=" + ArtifactId + ", Version=" + Version + "]";
	}
}
