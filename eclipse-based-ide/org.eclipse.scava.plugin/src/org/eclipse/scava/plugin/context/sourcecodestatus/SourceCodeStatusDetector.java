/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.context.sourcecodestatus;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.scava.commons.context.sourcecode.SourceCodeContext;
import org.eclipse.scava.commons.libraryapi.LibraryAPIElement;
import org.eclipse.scava.commons.transaction.APIUsageInContext;
import org.eclipse.scava.plugin.context.sourcecodestatus.astmanipulating.ASTUtils;

public class SourceCodeStatusDetector {
	public static SourceCodeContext getSourceCodeContext(IFile file, int offset, int length) throws SourceCodeStatusException {
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(file);
		ASTNode node = SourceCodeParser.parseASTFrom(compilationUnit);
		node = ASTUtils.getCoveringASTNode(offset, length, node);
		
		return SourceCodeContextBuilder.build(node, offset, length);
	}
	
	public static SourceCodeContext getSourceCodeContext(ASTNode node) throws SourceCodeStatusException {
		int offset = node.getStartPosition();
		int length = node.getLength();
		
		return SourceCodeContextBuilder.build(node, offset, length);
	}
	
	public static List<APIUsageInContext> findAPIUsagesInProject(IJavaProject project, List<LibraryAPIElement> apiElements) throws SourceCodeStatusException
	{
		return APIUsageDetector.findUsagesIn(project, apiElements);
	}
	
}
