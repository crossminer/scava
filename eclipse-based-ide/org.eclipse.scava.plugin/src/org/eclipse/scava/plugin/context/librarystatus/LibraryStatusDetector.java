/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.context.librarystatus;

import java.util.List;

import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.scava.commons.library.Library;

public class LibraryStatusDetector {
	
	public static Library libraryFrom(IClasspathEntry classpathEntry) throws LibraryStatusException {
		return LibraryParser.parseClasspathEntry(classpathEntry);
	}
	
	public static List<Library> getLibrariesFromProject(IJavaProject javaProject) throws LibraryStatusException {
		return ProjectClasspathParser.getLibrariesFromProject(javaProject);
	}
}
