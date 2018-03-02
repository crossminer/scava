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
package org.eclipse.scava.commons.context.sourcecode.lineinfo.detail;

import java.util.List;

/**
 * Provides a description of a type from the source code.
 *
 */
public class ASTResolvedType {
	private final List<String> typeHierarchy;
	private final List<String> interfaces;
	private final List<ASTResolvedType> genericTypes;
	
	public ASTResolvedType(List<String> typeHierarchy, List<String> interfaces, List<ASTResolvedType> genericTypes) {
		this.typeHierarchy = typeHierarchy;
		this.interfaces = interfaces;
		this.genericTypes = genericTypes;
	}
	
	public List<String> getTypeHierarchy() {
		return typeHierarchy;
	}
	
	public List<String> getInterfaces() {
		return interfaces;
	}
	
	public List<ASTResolvedType> getGenericTypes() {
		return genericTypes;
	}
	
}
