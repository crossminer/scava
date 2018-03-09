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
 * Extends the {@link ASTDetail} with the ability to store the resolved annotations.
 * This class behaves like the ASTDetail class, but additionally stores the source
 * code annotations which are related to the described element.
 * @see ASTDetail
 */
public class ASTAnnotatedDetail extends ASTDetail {
	private final List<ASTResolvedAnnotation> annotations;
	
	protected ASTAnnotatedDetail(int startChar, int endChar, ASTDetailKind kind,
			List<ASTResolvedAnnotation> annotations) {
		super(startChar, endChar, kind);
		this.annotations = annotations;
	}
	
	public List<ASTResolvedAnnotation> getAnnotations() {
		return annotations;
	}
	
}
