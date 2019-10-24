/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration.migration;

import java.io.File;
import java.util.Objects;

import org.eclipse.core.resources.IProject;
import org.eclipse.scava.plugin.libraryversions.Library;

public class ApiMigrationParameters {
	private final Library oldLibraryVersion;
	private final Library newLibraryVersion;
	private final File m3Model;
	private final IProject project;

	public ApiMigrationParameters(Library oldLibraryVersion, Library newLibraryVersion, File m3Model,
			IProject project) {
		super();
		this.oldLibraryVersion = oldLibraryVersion;
		this.newLibraryVersion = newLibraryVersion;
		this.m3Model = m3Model;
		this.project = project;
	}

	public Library getOldLibraryVersion() {
		return oldLibraryVersion;
	}

	public Library getNewLibraryVersion() {
		return newLibraryVersion;
	}

	public File getM3Model() {
		return m3Model;
	}

	public IProject getProject() {
		return project;
	}

	@Override
	public int hashCode() {
		return Objects.hash(m3Model, newLibraryVersion, oldLibraryVersion, project);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ApiMigrationParameters)) {
			return false;
		}
		ApiMigrationParameters other = (ApiMigrationParameters) obj;
		return Objects.equals(m3Model, other.m3Model) && Objects.equals(newLibraryVersion, other.newLibraryVersion)
				&& Objects.equals(oldLibraryVersion, other.oldLibraryVersion) && Objects.equals(project, other.project);
	}

}
