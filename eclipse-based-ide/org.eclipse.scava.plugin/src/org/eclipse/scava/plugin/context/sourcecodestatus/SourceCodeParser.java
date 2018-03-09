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

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;

public class SourceCodeParser {
	public static ASTNode parseASTFrom(ICompilationUnit compilationUnit) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(compilationUnit);
		parser.setResolveBindings(true);
		parser.setStatementsRecovery(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		
		return parser.createAST(null);
	}
}
