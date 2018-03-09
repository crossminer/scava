/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*    Zsolt J�nos Szamosv�lgyi
*    Endre Tam�s V�radi
*    Gerg� Balogh
**********************************************************************/
package org.eclipse.scava.commons.library;

/**
 * Provides descriptor of a version.
 * This has major, minor, sub and revision version number.
 *
 */
public class Version {
	private final int major;
	private final int minor;
	private final int sub;
	private final int revision;
	
	public Version(int major, int minor, int sub, int revision) {
		this.major = major;
		this.minor = minor;
		this.sub = sub;
		this.revision = revision;
	}
	
	/**
	 * Class constructor.
	 * This reads the version parameters from the given string.
	 * It must be in the following format: 
	 * <code>major.minor.sub.revision</code> .
	 * For example: <code>3.1.4.12</code> .
	 * There must be at least the major version number, the others are optional.
	 * So the followings are also correct:
	 * <code>3</code> ,
	 * <code>4.1</code> ,
	 * <code>5.1.0</code> .
	 * @param version the {@link String} representation of the version number
	 */
	public Version(String version) {
		String[] subversions = version.split("\\.");
		
		this.major = subversions.length > 0 && !subversions[0].isEmpty() ? Integer.parseInt(subversions[0]) : 0;
		this.minor = subversions.length > 1 ? Integer.parseInt(subversions[1]) : 0;
		this.sub = subversions.length > 2 ? Integer.parseInt(subversions[2]) : 0;
		this.revision = subversions.length > 3 ? Integer.parseInt(subversions[3]) : 0;
	}
	
	public boolean equals(Version version) {
		return this.major == version.major && this.minor == version.minor && this.sub == version.sub
				&& this.revision == version.revision;
	}
	
	@Override
	public String toString() {
		return major + "." + minor + "." + sub + "." + revision;
	}
	
	public int getMajor() {
		return major;
	}
	
	public int getMinor() {
		return minor;
	}
	
	public int getSub() {
		return sub;
	}
	
	public int getRevision() {
		return revision;
	}
}
