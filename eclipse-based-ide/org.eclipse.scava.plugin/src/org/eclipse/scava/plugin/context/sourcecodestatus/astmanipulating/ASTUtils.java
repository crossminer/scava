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

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.NodeFinder;

public final class ASTUtils {
	
	public static int getLineNumberOfNode(ASTNode node) {
		CompilationUnit compilationUnit = (CompilationUnit) node.getRoot();
		return compilationUnit.getLineNumber(node.getStartPosition());
	}
	

	public static ASTNode getCoveringASTNode(int offset, int length, ASTNode node) {
		NodeFinder finder = new NodeFinder(node, offset, length);
		ASTNode coveringNode = finder.getCoveringNode();
		return coveringNode;
	}

	public static ASTNode getCoveringParent(ASTNode node) {
		while( node.getParent() != null && node.getNodeType() != ASTNode.BLOCK )
		{
			node = node.getParent();
		}
		return node;
	}
}
