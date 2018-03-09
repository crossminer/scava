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

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Iterator;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.scava.commons.library.Library;
import org.eclipse.scava.commons.library.ReleaseType;
import org.eclipse.scava.commons.library.Version;

public class LibraryParser {
	public static Library parseClasspathEntry(IClasspathEntry classpathEntry) throws LibraryStatusException {
		try (JarFile jar = openJarFile(classpathEntry)) {
			Attributes attributes = getManifestAttributesIterator(jar);
			return buildLibraryFromManifest(attributes);
		} catch (IOException e) {
			throw new LibraryStatusException(e);
		}
	}

	private static JarFile openJarFile(IClasspathEntry classpathEntry) throws LibraryStatusException {
		IPath pathToEntry = classpathEntry.getPath();

		try {

			if (!pathToEntry.toFile().exists()) {
				pathToEntry = resolveRelativeToAbsolutePath(pathToEntry);
			}

			return new JarFile(pathToEntry.toFile());
		} catch (IOException e) {
			throw new LibraryStatusException(e);
		}
	}

	private static IPath resolveRelativeToAbsolutePath(IPath absolutePathToEntry) throws IOException {
		IResource localContainer = ResourcesPlugin.getWorkspace().getRoot().findMember(absolutePathToEntry);
		if (localContainer == null) {
			throw new NoSuchFileException("THe classpath entry file not found");
		}

		absolutePathToEntry = localContainer.getLocation();
		return absolutePathToEntry;
	}

	private static Attributes getManifestAttributesIterator(JarFile jar) throws IOException {
		Manifest manifest = jar.getManifest();
		Attributes attributes = manifest.getMainAttributes();
		return attributes;
	}

	private static Library buildLibraryFromManifest(Attributes attributes) {
		Iterator<Object> attributeIterator = attributes.keySet().iterator();

		String id = null;
		Version version = null;
		ReleaseType release = ReleaseType.UNKNOWN;
		String website = null;

		while (attributeIterator.hasNext()) {
			Name key = (Name) attributeIterator.next();
			String keyword = key.toString();

			if (keyword.equals("Implementation-Version") || keyword.equals("Bundle-Version")) {
				String versionString = (String) attributes.get(key);
				version = new Version(versionString);
			}
			if (keyword.equals("Bundle-Name")) {
				id = (String) attributes.get(key);
			}
			if (keyword.equals("Bundle-DocURL")) {
				website = (String) attributes.get(key);
			}
			if (keyword.equals("Release")) {
				String releaseString = (String) attributes.get(key);
				release = ReleaseType.findByString(releaseString);
			}
		}

		return new Library(id, version, release, website);
	}

	public static Library buildLibraryFromIFile(IFile file) throws IOException {

		IPath path = LibraryParser.resolveRelativeToAbsolutePath(file.getFullPath());
		JarFile jar = new JarFile(path.toFile());
		Library buildedLibrary = LibraryParser
				.buildLibraryFromManifest(LibraryParser.getManifestAttributesIterator(jar));
		return buildedLibrary;

	}
	public static Library buildLibraryFromPath(IPath iPath) throws IOException {

		
		JarFile jar = new JarFile(iPath.toFile());
		Library buildedLibrary = LibraryParser
				.buildLibraryFromManifest(LibraryParser.getManifestAttributesIterator(jar));
		return buildedLibrary;

	}

}
