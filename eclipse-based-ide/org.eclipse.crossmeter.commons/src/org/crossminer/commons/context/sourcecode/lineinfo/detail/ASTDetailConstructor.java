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
package org.crossminer.commons.context.sourcecode.lineinfo.detail;

import java.util.List;

/**
 * Provides informations about a constructor.
 * @see ASTDetailMethod
 *
 */
public class ASTDetailConstructor extends ASTDetailMethod {
	
	public ASTDetailConstructor(int startChar, int endChar, String signature, List<ASTResolvedAnnotation> annotations) {
		super(startChar, endChar, ASTDetailKind.CONSTRUCTOR, annotations, signature);
	}
}
