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
package org.eclipse.scava.commons.buildsystem;

/**
 * Provides a type-safe enumeration for the types of build systems.
 *
 */
public enum BuildSystemType {
	/**
	 * Represents the Apache Maven build system
	 */
	MAVEN,
	/**
	 * Represents the Apache Ant build system
	 */
	ANT,
	/**
	 * No specific build system
	 */
	NONE
}
