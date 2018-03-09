/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.context.sourcecodestatus.astmanipulating.resolving;

import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTDetailKind;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTDetailTypeName;

public class ASTTypeNameResolver {
	
	public static ASTDetailTypeName resolve(SimpleName simpleName) throws ASTResolveException {
		IBinding resolvedBinding = simpleName.resolveBinding();
		
		if (resolvedBinding.getKind() != IBinding.TYPE)
			throw new ASTResolveException("SimpleName is not bound to a variable.");
		
		ITypeBinding resolvedType = (ITypeBinding) resolvedBinding;
		
		int startChar = simpleName.getStartPosition();
		int endChar = startChar + simpleName.getLength() - 1;
		String resolvedTypeName = resolvedType.getQualifiedName();
		ASTDetailKind resolvedKind = ResolveKind(resolvedType);
		
		return new ASTDetailTypeName(startChar, endChar, resolvedTypeName, resolvedKind);
	}
	
	public static ASTDetailKind ResolveKind(ITypeBinding typeBinding) {
		return typeBinding.isInterface() ? ASTDetailKind.TYPE_INTERFACE : ASTDetailKind.TYPE_CLASS;
	}
}
