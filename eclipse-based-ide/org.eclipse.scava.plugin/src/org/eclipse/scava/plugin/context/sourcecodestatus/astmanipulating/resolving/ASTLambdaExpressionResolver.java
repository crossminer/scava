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

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTDetailLambdaMethod;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTResolvedAnnotation;

public class ASTLambdaExpressionResolver {
	
	public static ASTDetailLambdaMethod resolve(LambdaExpression lambdaExpression) {
		IMethodBinding resolvedLambdaMethod = lambdaExpression.resolveMethodBinding();
		
		int startChar = lambdaExpression.getStartPosition();
		int endChar = startChar + lambdaExpression.getLength() - 1;
		String signature = ASTMethodResolver.GetMethodSignature(resolvedLambdaMethod);
		List<ASTResolvedAnnotation> resolvedAnnotations = ASTAnnotationResolver.resolve(resolvedLambdaMethod);
		
		return new ASTDetailLambdaMethod(startChar, endChar, signature, resolvedAnnotations);
	}
	
}
