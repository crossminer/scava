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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTDetailKind;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTDetailMethod;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTResolvedAnnotation;

public final class ASTMethodResolver {
	
	public static ASTDetailMethod resolve(SimpleName simpleName) throws ASTResolveException {
		IBinding resolvedBinding = simpleName.resolveBinding();
		
		if (resolvedBinding.getKind() != IBinding.METHOD)
			throw new ASTResolveException("SimpleName is not bound to a variable.");
		
		IMethodBinding resolvedMethod = (IMethodBinding) resolvedBinding;
		
		int startChar = simpleName.getStartPosition();
		int endChar = startChar + simpleName.getLength() - 1;
		String signature = GetMethodSignature(resolvedMethod);
		ASTDetailKind resolvedKind = ResolveKind(resolvedMethod);
		List<ASTResolvedAnnotation> resolvedAnnotations = ASTAnnotationResolver.resolve(resolvedMethod);
		
		return new ASTDetailMethod(startChar, endChar, resolvedKind, resolvedAnnotations, signature);
	}
	
	public static ASTDetailKind ResolveKind(IMethodBinding methodBinding) {
		if (methodBinding.getName().startsWith("set")) {
			return ASTDetailKind.SETTER;
		} else if (methodBinding.getName().startsWith("get") || methodBinding.getName().startsWith("is")) {
			return ASTDetailKind.GETTER;
		}
		
		return ASTDetailKind.METHOD;
	}
		
	public static String GetMethodSignature(IMethodBinding methodBinding) {
		
		List<String> parameters = new ArrayList<>();
		for (ITypeBinding parameter : methodBinding.getParameterTypes()) {
			parameters.add(Signature.createTypeSignature(parameter.getQualifiedName(), false));
		}
		
		String returnType = Signature.createTypeSignature(methodBinding.getReturnType().getQualifiedName(), false);
		
		String parametersAndReturnType = Signature.createMethodSignature(parameters.toArray(new String[0]), returnType);
		
		String signature = methodBinding.getDeclaringClass().getQualifiedName() + "." + methodBinding.getName() + parametersAndReturnType;
		
		return signature;
	}
}
