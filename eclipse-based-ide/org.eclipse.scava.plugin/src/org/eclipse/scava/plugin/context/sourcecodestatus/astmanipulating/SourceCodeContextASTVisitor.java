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

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.scava.commons.context.sourcecode.SourceCodeContext;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTDetail;
import org.eclipse.scava.plugin.context.sourcecodestatus.astmanipulating.filtering.IASTNodeFilter;
import org.eclipse.scava.plugin.context.sourcecodestatus.astmanipulating.resolving.ASTConstructorResolver;
import org.eclipse.scava.plugin.context.sourcecodestatus.astmanipulating.resolving.ASTLambdaExpressionResolver;
import org.eclipse.scava.plugin.context.sourcecodestatus.astmanipulating.resolving.ASTResolveException;
import org.eclipse.scava.plugin.context.sourcecodestatus.astmanipulating.resolving.ASTSimpleNameResolver;

public class SourceCodeContextASTVisitor extends ASTVisitor {
	private SourceCodeContext context;
	private IASTNodeFilter filter;
	
	public void setFilter(IASTNodeFilter filter) {
		this.filter = filter;
	}
	
	public SourceCodeContextASTVisitor(SourceCodeContext context) {
		if( context == null )
		{
			throw new NullPointerException("context can not be null");
		}
		this.context = context;
	}

	
	@Override
	public boolean visit(ClassInstanceCreation node) {
		if( filter == null || filter.check(node) )
		{
			ASTDetail detail = ASTConstructorResolver.resolve(node);
			context.addDetail(ASTUtils.getLineNumberOfNode(node), detail);
		}
		return true;
	}
	
	@Override
	public boolean visit(LambdaExpression node) {
		if( filter == null || filter.check(node) )
		{
			ASTDetail detail = ASTLambdaExpressionResolver.resolve(node);
			context.addDetail(ASTUtils.getLineNumberOfNode(node), detail);
		}
		return true;
	}
	
	@Override
	public boolean visit(SimpleName node) {
		if( filter == null || filter.check(node) )
		{
			try {
				ASTDetail detail = ASTSimpleNameResolver.resolve(node);
				context.addDetail(ASTUtils.getLineNumberOfNode(node), detail);
			} catch (ASTResolveException e) {
				
			}
		}
		return true;
	}
}
