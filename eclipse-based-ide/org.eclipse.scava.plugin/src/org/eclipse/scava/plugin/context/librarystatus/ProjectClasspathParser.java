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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.scava.commons.library.Library;

public class ProjectClasspathParser {
	
	public static List<Library> getLibrariesFromProject(IJavaProject javaProject) throws LibraryStatusException {
		try {
			List<IClasspathEntry> libraryEntries = getClassPathEntriesFromProject(javaProject);
			List<Library> libraries = processClasspathEntries(libraryEntries);
			return libraries;
		} catch (JavaModelException e) {
			throw new LibraryStatusException("Error while getting information from Java Project.", e);
		}
	}
	
	
	public static List<IClasspathEntry> getAllClassPathEntriesFromProject(IJavaProject javaProject) throws JavaModelException {
		List<IClasspathEntry> libraryEntries = new LinkedList<>();
		
		IClasspathEntry[] classpath = javaProject.getRawClasspath();
		
		for (IClasspathEntry classpathEntry : classpath) {
		
				libraryEntries.add(classpathEntry);
			
		}
		
		return libraryEntries;
	}
	
	private static List<IClasspathEntry> getClassPathEntriesFromProject(IJavaProject javaProject) throws JavaModelException {
		List<IClasspathEntry> libraryEntries = new LinkedList<>();
		
		IClasspathEntry[] classpath = javaProject.getRawClasspath();
		
		for (IClasspathEntry classpathEntry : classpath) {
			if (isLibraryEntry(classpathEntry)) {
				libraryEntries.add(classpathEntry);
			}
		}
		
		return libraryEntries;
	}
	
	public static boolean isLibraryEntry(IClasspathEntry classpathEntry) {
		return classpathEntry.getEntryKind() == IClasspathEntry.CPE_LIBRARY;
	}
	
	private static List<Library> processClasspathEntries(List<IClasspathEntry> libraryEntries) throws LibraryStatusException {
		List<Library> libraries = new LinkedList<>();
		
		for (IClasspathEntry iClasspathEntry : libraryEntries) {
			Library library = LibraryParser.parseClasspathEntry(iClasspathEntry);
			libraries.add(library);
		}
		
		return libraries;
	}
}
