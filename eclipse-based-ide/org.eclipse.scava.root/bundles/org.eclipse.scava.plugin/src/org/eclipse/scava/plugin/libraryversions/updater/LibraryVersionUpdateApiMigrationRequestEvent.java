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

import java.io.File;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.scava.plugin.libraryversions.Library;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.event.routed.RoutedEvent;

public class LibraryVersionUpdateApiMigrationRequestEvent extends RoutedEvent {

	private final IProject project;
	private final File jar;
	private final Map<Library, Library> libraries;

	public LibraryVersionUpdateApiMigrationRequestEvent(Controller source, IProject project, File jar,
			Map<Library, Library> libraries) {
		super(source);
		this.project = project;
		this.jar = jar;
		this.libraries = libraries;
	}

	public IProject getProject() {
		return project;
	}

	public File getJar() {
		return jar;
	}

	public Map<Library, Library> getLibraries() {
		return libraries;
	}

}
