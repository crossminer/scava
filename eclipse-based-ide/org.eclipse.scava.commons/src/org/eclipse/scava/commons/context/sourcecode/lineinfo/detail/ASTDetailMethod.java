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
 * Provides informations about a method.
 * @see ASTAnnotatedDetail
 *
 */
public class ASTDetailMethod extends ASTAnnotatedDetail {
	private final String signature;
	
	public ASTDetailMethod(int startChar, int endChar, ASTDetailKind kind, List<ASTResolvedAnnotation> annotations,
			String signature) {
		super(startChar, endChar, kind, annotations);
		this.signature = signature;
	}
	
	public String getSignature() {
		return signature;
	}
}
