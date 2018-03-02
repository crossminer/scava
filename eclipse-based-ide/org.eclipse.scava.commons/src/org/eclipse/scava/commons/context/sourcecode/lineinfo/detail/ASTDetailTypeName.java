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

/**
 * Provides a representation of the name of a type from the source code.
 * It appears in the declaration of classes and interfaces.
 * @see ASTDetail
 *
 */
public class ASTDetailTypeName extends ASTDetail {
	private final String type;
	
	public ASTDetailTypeName(int startChar, int endChar, String type, ASTDetailKind kind) {
		super(startChar, endChar, kind);
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
