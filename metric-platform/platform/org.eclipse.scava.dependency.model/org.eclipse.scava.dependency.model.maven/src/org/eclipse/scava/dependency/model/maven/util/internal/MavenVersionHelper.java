/*********************************************************************
* Copyright (c) 2018 Centrum Wiskunde & Informatica
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.scava.dependency.model.maven.util.internal;

import org.apache.maven.artifact.versioning.ComparableVersion;

import io.usethesource.vallang.IString;

// TODO: Add maven-artifact dependency
public class MavenVersionHelper {

	/*
	 * Uses the Maven API to compare two versions. Returns:
	 * 	0: if version = refVersion;
	 * 	1: if version > refVersion;
	 *	-1: if version < refVersion;
	 */
	public static int compareVersions(IString refVersion, IString version) {
		Comparable<ComparableVersion> ref = new ComparableVersion(refVersion.getValue());
		Comparable<ComparableVersion> ver = new ComparableVersion(version.getValue());
		
		return ref.compareTo((ComparableVersion) ver);
	}
	
}
