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

import org.eclipse.scava.commons.ITypeRepresentedByKind;

/**
 * Provides informations about a specific element of the source code.
 * Describes the range of the source code to which the resolved information
 * belongs and the {@link ASTDetailKind} kind of this information.
 *
 */
public abstract class ASTDetail implements ITypeRepresentedByKind {
	private final int startChar;
	private final int endChar;
	private final ASTDetailKind kind;
	
	protected ASTDetail(int startChar, int endChar, ASTDetailKind kind) {
		this.kind = kind;
		this.startChar = startChar;
		this.endChar = endChar;
	}
	
	/**
	 * Returns the kind of this detail
	 * @return the {@link ASTDetailKind} kind of this detail
	 */
	@Override
	public ASTDetailKind getKind() {
		return kind;
	}
	
	public int getStartChar() {
		return startChar;
	}
	
	public int getEndChar() {
		return endChar;
	}
	
}
