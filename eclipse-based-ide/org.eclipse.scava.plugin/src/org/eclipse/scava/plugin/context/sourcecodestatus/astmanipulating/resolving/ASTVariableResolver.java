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

import java.util.List;

import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTDetailKind;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTDetailVariable;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTResolvedAnnotation;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTResolvedType;

public class ASTVariableResolver {
	
	public static ASTDetailVariable resolve(SimpleName simpleName) throws ASTResolveException {
		IBinding resolvedBinding = simpleName.resolveBinding();
		
		if( resolvedBinding.getKind() != IBinding.VARIABLE )
			throw new ASTResolveException("SimpleName is not bound to a variable.");
		
		IVariableBinding resolvedVariable = (IVariableBinding) resolvedBinding;
		
		int startChar = simpleName.getStartPosition();
		int endChar = startChar + simpleName.getLength() - 1;
		ITypeBinding variableType = resolvedVariable.getType();
		ASTResolvedType resolvedType = ASTTypeResolver.resolve(variableType);
		ASTDetailKind resolvedKind = ResolveKind(variableType);
		List<ASTResolvedAnnotation> resolvedAnnotations = ASTAnnotationResolver.resolve(resolvedVariable);
		
		return new ASTDetailVariable(startChar, endChar, resolvedKind, resolvedAnnotations, resolvedType);
	}
	
	public static ASTDetailKind ResolveKind(ITypeBinding typeBinding) {
		return typeBinding.isInterface() ? ASTDetailKind.VARIABLE_INTERFACE : ASTDetailKind.VARIABLE_CLASS;
	}
}
