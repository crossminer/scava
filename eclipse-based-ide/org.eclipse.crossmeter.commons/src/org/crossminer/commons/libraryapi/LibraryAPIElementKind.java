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
*    Zsolt János Szamosvölgyi
*    Endre Tamás Váradi
*    Gergõ Balogh
**********************************************************************/
package org.crossminer.commons.libraryapi;

import java.lang.reflect.Type;

import org.crossminer.commons.ITypeRepresentation;

/**
 * Provides an enumeration-safe set of kinds of {@link LibraryAPIElement}.
 * 
 * @see LibraryAPIElement
 */
public enum LibraryAPIElementKind implements ITypeRepresentation {
	SIMPLE_NAME(LibraryAPISimpleName.class),
	METHOD_SIGNATURE(LibraryAPIMethodSignature.class);
	
	private final Type type;
	
	private LibraryAPIElementKind(Type type) {
		this.type = type;
	}
	
	@Override
	public Type getType() {
		return type;
	}
}
