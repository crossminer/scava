/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.updater;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.scava.plugin.libraryversions.Library;
import org.eclipse.scava.plugin.libraryversions.LibraryVersionModel;
import org.eclipse.scava.plugin.properties.Properties;

public class LibraryVersionUpdaterModel extends LibraryVersionModel {
	private final IProject project;
	private final String pomLocation;

	public LibraryVersionUpdaterModel(IProject project) throws FileNotFoundException {
		super();
		this.project = project;

		IFile pom = project.getFile("pom.xml");
		if (!pom.exists()) {
			throw new FileNotFoundException(pom.toString());
		}
		pomLocation = pom.getLocation().toOSString();
	}

	public IProject getProject() {
		return project;
	}

	public String getPomLocation() {
		return pomLocation;
	}

	public void install(Map<Library, Library> libraries)
			throws FileNotFoundException, IOException, XmlPullParserException {
		MavenXpp3Reader reader = new MavenXpp3Reader();
		org.apache.maven.model.Model model = reader.read(new FileReader(pomLocation));

		libraries.forEach((oldVersion, newVersion) -> {
			String foundVersion = "${}";

			List<Dependency> dependencies = model.getDependencies();
			for (Dependency dependency : dependencies) {

				if (dependency.getGroupId().equals(oldVersion.getGroupId())
						&& dependency.getArtifactId().equals(oldVersion.getArtifactId())) {
					if (dependency.getVersion().equals(oldVersion.getVersion())) {
						dependency.setVersion(newVersion.getVersion());
						return;
					}

					foundVersion = dependency.getVersion();
					break;
				}
			}

			String propertyName = foundVersion.substring(2, foundVersion.length() - 1);
			java.util.Properties properties = model.getProperties();
			if (properties.containsKey(propertyName)) {
				properties.setProperty(propertyName, newVersion.getVersion());
			}
		});

		MavenXpp3Writer writer = new MavenXpp3Writer();
		writer.write(new FileWriter(pomLocation), model);
	}

	public String getLastJarPath() throws CoreException {
		return project.getPersistentProperty(Properties.LIBRARY_UPDATE_API_MIGRATION_JAR_PATH);
	}

	public void setLastJarPath(String path) throws CoreException {
		project.setPersistentProperty(Properties.LIBRARY_UPDATE_API_MIGRATION_JAR_PATH, path);
	}
}
