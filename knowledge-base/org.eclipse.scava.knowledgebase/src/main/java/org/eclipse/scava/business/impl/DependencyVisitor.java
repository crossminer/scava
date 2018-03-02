/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.groovy.ast.CodeVisitorSupport;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;

/**
 * @author Juri Di Rocco
 *
 */
public class DependencyVisitor extends CodeVisitorSupport {
	private int dependenceLineNum = -1;
	private int columnNum = -1;
	private List<String> dependencies = new ArrayList<>();

	@Override
	public void visitMethodCallExpression(MethodCallExpression call) {
		if (!(call.getMethodAsString().equals("buildscript"))) {
			if (call.getMethodAsString().equals("dependencies") && dependenceLineNum == -1)
				dependenceLineNum = call.getLastLineNumber();
			super.visitMethodCallExpression(call);
		}
	}

	@Override
	public void visitArgumentlistExpression(ArgumentListExpression ale) {
		List<Expression> expressions = ale.getExpressions();

		if (expressions.size() == 1 && expressions.get(0) instanceof ConstantExpression) {
			String depStr = expressions.get(0).getText();
			String[] deps = depStr.split(":");

			if (deps.length == 3) {
				dependencies.add(deps[0] + ":" + deps[1]);
			}
		}

		super.visitArgumentlistExpression(ale);
	}

	@Override
	public void visitClosureExpression(ClosureExpression expression) {
		if (dependenceLineNum != -1 && expression.getLineNumber() == expression.getLastLineNumber()) {
			columnNum = expression.getLastColumnNumber();
		}

		super.visitClosureExpression(expression);
	}

	public int getDependenceLineNum() {
		return dependenceLineNum;
	}

	public int getColumnNum() {
		return columnNum;
	}

	public List<String> getDependencies() {
		return dependencies;
	}
}
