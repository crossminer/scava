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


/**
 * Provides an interface to ask for its kind.
 * Indicates that a class that implements this interface has its own kind
 * and can be asked to give it back.
 *
 */
public interface ITypeRepresentedByKind {
	/**
	 * 
	 * @return the {@link ITypeRepresentation} as its own kind
	 */
	ITypeRepresentation getKind();
}
