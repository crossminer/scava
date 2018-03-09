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

import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMemberValuePairBinding;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTResolvedAnnotation;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTResolvedType;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTResolvedAnnotation.Parameter;

public class ASTAnnotationResolver {
	
	public static List<ASTResolvedAnnotation> resolve(IBinding binding) {
		ArrayList<ASTResolvedAnnotation> annotations = new ArrayList<>();
		
		for (IAnnotationBinding annotationBinding : binding.getAnnotations()) {
			
			ASTResolvedAnnotation annotation = processAnnotationBinding(annotationBinding);
			annotations.add(annotation);
		}
		
		return annotations;
	}
	
	public static ASTResolvedAnnotation processAnnotationBinding(IAnnotationBinding annotationBinding) {
		ASTResolvedType annotationType = ASTTypeResolver.resolve(annotationBinding.getAnnotationType());
		List<Parameter> annotationParameters = ResolveAnnotationParameters(annotationBinding);
		
		return new ASTResolvedAnnotation(annotationType, annotationParameters);
	}
	
	private static List<Parameter> ResolveAnnotationParameters(IAnnotationBinding annotationBinding) {
		ArrayList<Parameter> resolvedParameters = new ArrayList<>();
		
		IMemberValuePairBinding[] annotationParameters = annotationBinding.getAllMemberValuePairs();
		
		for (IMemberValuePairBinding annotationParameter : annotationParameters) {
			
			Parameter resolvedParameter = processAnnotationParameter(annotationParameter);
			resolvedParameters.add(resolvedParameter);
		}
		
		return resolvedParameters;
	}
	
	public static Parameter processAnnotationParameter(IMemberValuePairBinding annotationParameter) {
		String name = annotationParameter.getName();
		Object value = annotationParameter.getValue();
		boolean isTheDefaultValue = annotationParameter.isDefault();
		
		return new Parameter(name, value, isTheDefaultValue);
	}
}
