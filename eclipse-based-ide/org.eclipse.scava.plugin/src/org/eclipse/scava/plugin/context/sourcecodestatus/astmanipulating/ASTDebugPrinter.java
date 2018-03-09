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

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ChildListPropertyDescriptor;
import org.eclipse.jdt.core.dom.ChildPropertyDescriptor;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimplePropertyDescriptor;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;
import org.eclipse.scava.plugin.context.sourcecodestatus.astmanipulating.filtering.ASTNodeRangeFilter;
import org.eclipse.scava.plugin.context.sourcecodestatus.astmanipulating.filtering.IASTNodeFilter;

public class ASTDebugPrinter {
	public static void debugAST(ASTNode node, int offset, int length) {
		StringBuffer sb = new StringBuffer();
		print(node, 0, ASTNodeRangeFilter.offsetLength(offset, length), sb);
		
		System.out.println("==========================");
		System.out.println(sb.toString());
		System.out.println("==========================");
		
	}
	
	private static void print(ASTNode node, int deep, IASTNodeFilter filter, StringBuffer stringBuffer) {
		if (node == null)
			return;
		
		if (filter != null && !filter.check(node))
			return;
		
		@SuppressWarnings("unchecked")
		List<StructuralPropertyDescriptor> properties = node.structuralPropertiesForType();
		// stringBuffer.append("# "+node.getClass());
		//int line = ((CompilationUnit) node.getRoot()).getLineNumber(node.getStartPosition());
		// stringBuffer.append("LINE:"+line+" Start:"+node.getStartPosition()+"
		// Length:"+node.getLength()+"\n");
		
		for (StructuralPropertyDescriptor descriptor : properties) {
			
			if (descriptor.isSimpleProperty()) {
				SimplePropertyDescriptor simple = (SimplePropertyDescriptor) descriptor;
				Object value = node.getStructuralProperty(simple);
				stringBuffer.append(spaces(deep) + descriptor.getNodeClass().getSimpleName() + " " + simple.getId() + " = " + value.toString() + "\n");
				
				stringBuffer.append("NODE CLASS: " + node.getClass() + "\n");
				if (node instanceof org.eclipse.jdt.core.dom.SimpleName) {
					SimpleName simpleName = (SimpleName) node;
					IBinding binding = simpleName.resolveBinding();
					
					// stringBuffer.append("BINDING CLASS:
					// "+binding.getClass()+"\n");
					
					switch (binding.getKind()) {
						case IBinding.VARIABLE:
							// IVariableBinding variableBinding = (IVariableBinding) binding;
							// stringBuffer.append(binding.getName()+"
							// "+variableBinding.getVariableId()+"
							// "+variableBinding.getType());
							// stringBuffer.append(variableBinding+"\n");
							break;
						case IBinding.METHOD:
							/*
							 * IMethodBinding methodBinding =
							 * (IMethodBinding)binding;
							 * 
							 * ITypeBinding[] types =
							 * methodBinding.getParameterTypes();
							 */
							break;
					}
					
					stringBuffer.append(binding);
				} // else if( node instanceof org.eclipse.jdt.core.dom.var )
				
			} else if (descriptor.isChildProperty()) {
				ChildPropertyDescriptor child = (ChildPropertyDescriptor) descriptor;
				ASTNode childNode = (ASTNode) node.getStructuralProperty(child);
				
				if (childNode != null) {
					stringBuffer.append("CHILD NODE CLASS: " + childNode.getClass() + "\n");
				}
				
				if (child != null) {
					stringBuffer.append(spaces(deep) + child.getNodeClass().getSimpleName() + "/" + child.getChildType().getSimpleName() + " " + child.getId() + " [" + "\n");
					print(childNode, deep + 1, filter, stringBuffer);
					stringBuffer.append(spaces(deep) + "]" + "\n");
				}
			} else {
				ChildListPropertyDescriptor list = (ChildListPropertyDescriptor) descriptor;
				stringBuffer.append(spaces(deep) + list.getNodeClass().getSimpleName() + "/" + list.getElementType().getSimpleName() + " " + list.getId() + " {" + "\n");
				
				@SuppressWarnings("unchecked")
				List<ASTNode> childNodes = (List<ASTNode>) node.getStructuralProperty(list);
				
				for (ASTNode childNode : childNodes) {
					print(childNode, deep, filter, stringBuffer);
				}
				
				stringBuffer.append(spaces(deep) + "}" + "\n");
			}
		}
	}
	
	private static String spaces(int i) {
		StringBuffer sb = new StringBuffer(i);
		for (int j = 0; j < i; j++) {
			sb.append('\t');
		}
		
		return sb.toString();
	}
}
