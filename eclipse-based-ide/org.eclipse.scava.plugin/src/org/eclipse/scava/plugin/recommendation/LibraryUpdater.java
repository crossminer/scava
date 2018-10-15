/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.recommendation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.scava.commons.library.Library;
import org.eclipse.scava.plugin.context.librarystatus.LibraryParser;
import org.eclipse.scava.plugin.context.librarystatus.LibraryStatusException;
import org.eclipse.scava.plugin.context.librarystatus.ProjectClasspathParser;
import org.eclipse.scava.plugin.usermonitoring.event.EventManager;
import org.eclipse.scava.plugin.usermonitoring.event.scava.ScavaEvent;
import org.eclipse.scava.plugin.usermonitoring.event.scava.ScavaEventType;
import org.eclipse.scava.plugin.utils.Utils;

public class LibraryUpdater {

	private static IClasspathEntry getSelectedLibrarysClasspath(Library library, IJavaProject project)
			throws LibraryStatusException, JavaModelException {

		if (project == null) {
			return null;
		}

		List<IClasspathEntry> projectClassPathEntries = ProjectClasspathParser
				.getAllClassPathEntriesFromProject(project);

		for (IClasspathEntry classPathEntry : projectClassPathEntries) {

			if (ProjectClasspathParser.isLibraryEntry(classPathEntry) && library.equals(LibraryParser.parseClasspathEntry(classPathEntry))) {
				return classPathEntry;
			}
		}
		return null;

	}

	public static void addLibraryToProject(IPath newLibraryPath) throws LibraryUpdaterException {
		
		try {
			IJavaProject project = Utils.getCurrentlyEditedProject();
			
			Path librarypath = downloadLibrary(new URL(newLibraryPath.toString()));
			addLibraryPathToProject(project, librarypath);
			
		} catch (JavaModelException | IOException e) {
			throw new LibraryUpdaterException("Add library has been failed.", e);
		}

	}

	public static void addLibraryPathToProject(IJavaProject project, Path librarypath) throws JavaModelException {
		
		
		
		EventManager.processEvent(new ScavaEvent(ScavaEventType.LIBRARY_ADDED));
		List<IClasspathEntry> projectClassPathEntries = ProjectClasspathParser
				.getAllClassPathEntriesFromProject(project);
		IClasspathEntry newLibraryEntry = JavaCore.newLibraryEntry(librarypath, null, null);
		
		projectClassPathEntries.add(newLibraryEntry);
		
		IClasspathEntry[] replaceEntries = projectClassPathEntries.toArray(new IClasspathEntry[0]);
		project.setRawClasspath(replaceEntries, null);
		
		
	}
	

	public static void removeLibraryFromProject(Library libraryForDeleting) throws LibraryUpdaterException{

		try {
			IJavaProject project = Utils.getCurrentlyEditedProject();

			List<IClasspathEntry> projectClassPathEntries = ProjectClasspathParser
					.getAllClassPathEntriesFromProject(project);
			if (LibraryUpdater.getSelectedLibrarysClasspath(libraryForDeleting, project) != null) {

				projectClassPathEntries.remove(LibraryUpdater.getSelectedLibrarysClasspath(libraryForDeleting, project));
				IClasspathEntry[] replaceEntries = projectClassPathEntries.toArray(new IClasspathEntry[0]);

				project.setRawClasspath(replaceEntries, null);
				EventManager.processEvent(new ScavaEvent(ScavaEventType.LIBRARY_REMOVED));
			}else{				
				throw new LibraryUpdaterException("Library path not found in classpath.");
			}
		} catch (JavaModelException | LibraryStatusException e) {
			throw new LibraryUpdaterException("Remove library has been failed.", e);
		}
	}


	private static Path downloadLibrary(URL libraryURl) throws IOException {

		String fileName = getFileName(libraryURl);
		System.out.println("\n\n\n\n"+libraryURl+"\n\n\n\n");
		IPath projectPath = Utils.getCurrentlyEditedProject().getProject().getLocation();
		File temporaryFile = new File(projectPath + fileName);
		try (InputStream in = libraryURl.openStream()) {
			Files.copy(in, temporaryFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}catch (Exception e) {
			
		e.printStackTrace();
			
		}
		System.out.println("\n\n\n\n"+ new Path(temporaryFile.getAbsolutePath())+"\n\n\n\n");
		return new Path(temporaryFile.getAbsolutePath());
	}

	private static String getFileName(URL libraryURL) {

		String fileName = libraryURL.toString();
		Pattern pattern = Pattern.compile("[^/]*.jar");
		Matcher matcher = pattern.matcher(fileName);
		if (matcher.find()) {
			fileName = "//" + matcher.group(0);
		} else {
			fileName = "//temporary.jar";
		}
		return fileName;
	}
}
