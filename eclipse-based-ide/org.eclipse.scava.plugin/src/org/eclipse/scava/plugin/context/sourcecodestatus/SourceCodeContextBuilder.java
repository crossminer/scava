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

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.scava.commons.context.sourcecode.SourceCodeContext;
import org.eclipse.scava.plugin.context.sourcecodestatus.astmanipulating.SourceCodeContextASTVisitor;
import org.eclipse.scava.plugin.context.sourcecodestatus.astmanipulating.filtering.ASTNodeRangeFilter;

public class SourceCodeContextBuilder {
		
	public static SourceCodeContext build(ASTNode node, int offset, int length) throws SourceCodeStatusException {
		
		CompilationUnit compilationUnit = GetCompilationUnitOfASTNode(node);
		IFile file = GetIFileOfCompilationUnit(compilationUnit);
		
		String fullText = GetContent(file);
		String filePath = GetPath(file);
		int startLine = compilationUnit.getLineNumber(offset);
		String javaVersion = "undefined";
		
		SourceCodeContext context = new SourceCodeContext(fullText, filePath, javaVersion, offset, length, startLine);
		
		SourceCodeContextASTVisitor visitor = new SourceCodeContextASTVisitor(context);
		visitor.setFilter(ASTNodeRangeFilter.offsetLength(offset, length));
		node.accept(visitor);
		
		return context;
	}
	
	public static CompilationUnit GetCompilationUnitOfASTNode(ASTNode node) throws SourceCodeStatusException {
		ASTNode root = node.getRoot();
		if (root.getNodeType() == ASTNode.COMPILATION_UNIT) {
			return (CompilationUnit) root;
		}
		
		throw new SourceCodeStatusException("ASTNode's root is not a compilation unit.");
	}
	
	public static IFile GetIFileOfCompilationUnit(CompilationUnit compilationUnit) throws SourceCodeStatusException {
		IJavaElement javaElement = compilationUnit.getJavaElement();
		if (javaElement.getElementType() == IJavaElement.COMPILATION_UNIT) {
			ICompilationUnit iCompilationUnit = (ICompilationUnit) javaElement;
			
			try {
				return (IFile) iCompilationUnit.getCorrespondingResource();
			} catch (JavaModelException e) {
				throw new SourceCodeStatusException(e);
			}
		}
		
		throw new SourceCodeStatusException("CompilationUnit's type is not IJavaElement.COMPILATION_UNIT");
	}
	
	private static String GetContent(IFile file) {
		StringBuilder stringBuilder = new StringBuilder();
		
		try {
			InputStream inputStream = file.getContents();
			
			int data;
			while ((data = inputStream.read()) != -1) {
				stringBuilder.append((char) data);
			}
			
		} catch (CoreException | IOException e1) {
			e1.printStackTrace();
		}
		
		return stringBuilder.toString();
	}
	
	private static String GetPath(IFile file) {
		return file.getProjectRelativePath().toString();
	}
}
