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
 * Provides informations about a variable.
 * @see ASTAnnotatedDetail
 *
 */
public class ASTDetailVariable extends ASTAnnotatedDetail {
	private final ASTResolvedType type;
	
	public ASTDetailVariable(int startChar, int endChar, ASTDetailKind kind, List<ASTResolvedAnnotation> annotations,
			ASTResolvedType type) {
		super(startChar, endChar, kind, annotations);
		this.type = type;
	}
	
	public ASTResolvedType getType() {
		return type;
	}
}
