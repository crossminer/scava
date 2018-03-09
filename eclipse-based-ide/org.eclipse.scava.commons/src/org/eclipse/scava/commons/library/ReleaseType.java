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
 * Provides an enumeration-safe set of kinds of library release types.
 * @see Library
 */
public enum ReleaseType {
	STABLE("Stable"),
	ALPHA("Alpha"),
	BETA("Beta"),
	DEVELOPMENT("Development"),
	UNKNOWN("");
	
	private final String asString;
	
	private ReleaseType(String asString) {
		this.asString = asString;
	}
	
	public static ReleaseType findByString(String release) {
		for (ReleaseType releaseType : values()) {
			if (releaseType.asString.equalsIgnoreCase(release))
				return releaseType;
		}
		
		return UNKNOWN;
	}
}
