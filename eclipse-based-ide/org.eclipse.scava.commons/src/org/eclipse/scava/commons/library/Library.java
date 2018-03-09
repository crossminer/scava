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
 * Provides a representation of a library.
 *
 */
public class Library {
	private final String id;
	private final Version version;
	private final ReleaseType release;
	private final String officialWebsite;
	
	public Library(String id, Version version, ReleaseType release, String officialWebsite) {
		super();
		this.id = id;
		this.version = version;
		this.release = release;
		this.officialWebsite = officialWebsite;
	}
	
	public boolean equals(Library lib) {
		
		return this.id.equals(lib.id) && this.officialWebsite.equals(lib.officialWebsite) && this.release.equals(
				lib.release) && this.version.equals(lib.version);
	}
	
	@Override
	public String toString() {
		return "Library [id=" + id + ", version=" + version + ", release=" + release + ", officialWebsite="
				+ officialWebsite + "]";
	}
	
	public String getId() {
		return id;
	}
	
	public Version getVersion() {
		return version;
	}
	
	public ReleaseType getRelease() {
		return release;
	}
	
	public String getOfficialWebsite() {
		return officialWebsite;
	}
}
