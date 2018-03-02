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

import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTDetailConstructor;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTResolvedAnnotation;

public class ASTConstructorResolver {
	
	public static ASTDetailConstructor resolve(ClassInstanceCreation instanceCreation) {
		IMethodBinding resolvedConstructor = instanceCreation.resolveConstructorBinding();
		
		int startChar = instanceCreation.getStartPosition();
		int endChar = startChar + instanceCreation.getLength() - 1;
		String signature = ASTMethodResolver.GetMethodSignature(resolvedConstructor);
		List<ASTResolvedAnnotation> resolvedAnnotations = ASTAnnotationResolver.resolve(resolvedConstructor);
		
		return new ASTDetailConstructor(startChar, endChar, signature, resolvedAnnotations);
	}
}
