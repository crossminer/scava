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
package org.eclipse.scava.commons;

import java.lang.reflect.Type;

/**
 * Provides an interface to store and get a related type.
 * An instance can represent a {@link Type} and can be asked to
 * give it back.
 * Instances can be used in a switch as references.
 *
 */
public interface ITypeRepresentation {
	/**
	 * Returns the represented type.
	 * @return the {@link Type} this represents
	 */
	Type getType();
}
