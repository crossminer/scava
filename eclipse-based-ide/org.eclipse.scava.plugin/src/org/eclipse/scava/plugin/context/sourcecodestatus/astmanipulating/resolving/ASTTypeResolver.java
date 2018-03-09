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

import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTResolvedType;

public class ASTTypeResolver {
	
	public static ASTResolvedType resolve(ITypeBinding typeBinding)
	{
		List<String> typeHierarchy = new ArrayList<>(); //Extended classes
		List<String> interfaceList = new ArrayList<>(); //Implemented interfaces
		
		processTypeHierarchy(typeBinding, typeHierarchy, interfaceList);
		List<ASTResolvedType> genericTypes = getGenericTypes(typeBinding);
		
		return new ASTResolvedType(typeHierarchy, interfaceList, genericTypes);
	}

	public static void processTypeHierarchy(ITypeBinding rootTypeBinding, List<String> typeHierarchy, List<String> interfaceList) {
		ITypeBinding typeBinding = rootTypeBinding;
		do
		{
			getExtendedClass(typeHierarchy, typeBinding);
			getImplementedInterfaces(interfaceList, typeBinding);
			
		}while( (typeBinding = typeBinding.getSuperclass()) != null );
	}

	public static void getExtendedClass(List<String> typeHierarchy, ITypeBinding typeBinding) {
		typeHierarchy.add( typeBinding.getQualifiedName() );
	}

	public static void getImplementedInterfaces(List<String> interfaceList, ITypeBinding typeBinding) {
		ITypeBinding[] interfaces = typeBinding.getInterfaces();
		for (ITypeBinding iface : interfaces) {
			getExtendedClass(interfaceList, iface);
		}
	}

	public static List<ASTResolvedType> getGenericTypes(ITypeBinding rootTypeBinding) {
		ITypeBinding[] genericTypesBindings = rootTypeBinding.getTypeArguments();
		
		List<ASTResolvedType> genericTypes = new ArrayList<>();
		
		for(int i=0; i<genericTypesBindings.length; i++)
		{
			genericTypes.add( resolve(genericTypesBindings[i]) );
		}
		
		return genericTypes;
	}
}
