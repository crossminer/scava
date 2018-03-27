package org.eclipse.scava.dependency.model.maven.util.internal;

import org.apache.maven.artifact.versioning.ComparableVersion;
import org.eclipse.imp.pdb.facts.IString;

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
