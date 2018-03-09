/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.context.sourcecodestatus.astmanipulating;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.scava.commons.context.sourcecode.SourceCodeContext;
import org.eclipse.scava.commons.libraryapi.LibraryAPIElement;
import org.eclipse.scava.commons.libraryapi.LibraryAPIElementKind;
import org.eclipse.scava.commons.libraryapi.LibraryAPIMethodSignature;
import org.eclipse.scava.commons.libraryapi.LibraryAPISimpleName;
import org.eclipse.scava.commons.transaction.APIUsageInContext;
import org.eclipse.scava.plugin.context.sourcecodestatus.SourceCodeStatusDetector;
import org.eclipse.scava.plugin.context.sourcecodestatus.SourceCodeStatusException;
import org.eclipse.scava.plugin.context.sourcecodestatus.astmanipulating.resolving.ASTMethodResolver;
import org.eclipse.scava.plugin.utils.Utils;

public class APIUsageFinderASTVisitor extends ASTVisitor {
	private List<LibraryAPIMethodSignature> methodSignatures;
	private List<LibraryAPISimpleName> simpleNames;
	
	private List<APIUsageInContext> apiUsages;
	
	public APIUsageFinderASTVisitor(List<LibraryAPIElement> apiElements) {
		
		apiUsages = new LinkedList<>();

		methodSignatures = Utils.filterType(apiElements, LibraryAPIElementKind.METHOD_SIGNATURE);
		simpleNames = Utils.filterType(apiElements, LibraryAPIElementKind.SIMPLE_NAME);
	}
	
	
	@Override
	public boolean visit(MethodInvocation node) {
		
		IMethodBinding methodBinding = node.resolveMethodBinding();
		String methodSignature = ASTMethodResolver.GetMethodSignature(methodBinding);
		
		for (LibraryAPIMethodSignature apiMethodSignature : methodSignatures) {
			
			if (methodSignature.equals(apiMethodSignature.getSignature())) {
				processFoundAPIUsage(node, apiMethodSignature);
			}
		}
		
		return true;
	}
	
	@Override
	public boolean visit(SimpleName node) {
		
		if (node.resolveBinding().getKind() == IBinding.TYPE) {
			
			ITypeBinding typeBinding = (ITypeBinding) node.resolveBinding();
			String fullyQualifiedName = typeBinding.getQualifiedName();
			
			for (LibraryAPISimpleName apiSimpleNames : simpleNames) {
				
				if (fullyQualifiedName.equals(apiSimpleNames.getFullyQualifiedName())) {
					processFoundAPIUsage(node, apiSimpleNames);
				}
			}
		}
		
		return true;
	}
	
	private void processFoundAPIUsage(ASTNode node, LibraryAPIElement apiElement) {
		try {
			SourceCodeContext context = SourceCodeStatusDetector.getSourceCodeContext(node);
			APIUsageInContext apiUsageInContext = new APIUsageInContext(context);
			apiUsageInContext.addUsedAPIElement(apiElement);
			
			apiUsages.add(apiUsageInContext);
		} catch (SourceCodeStatusException e) {
		}
	}
	
	public List<APIUsageInContext> getApiUsages() {
		return apiUsages;
	}
	
}
