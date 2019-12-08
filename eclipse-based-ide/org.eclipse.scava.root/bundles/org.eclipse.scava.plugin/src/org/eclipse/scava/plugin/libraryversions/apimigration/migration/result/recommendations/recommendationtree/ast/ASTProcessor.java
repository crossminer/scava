/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.ast;

import java.util.Deque;
import java.util.LinkedList;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.location.ASTLocation;

public class ASTProcessor extends ASTVisitor {

	private Deque<ProcessedAST> parents;

	public ASTProcessor() {
		parents = new LinkedList<>();

		ProcessedAST root = new ProcessedAST();
		parents.add(root);
	}

	@Override
	public boolean visit(MethodInvocation node) {
		try {
			ASTLocation astLocation = new ASTLocation(node);
			process(astLocation);
		} catch (Throwable e) {

		}
		return super.visit(node);
	}

	@Override
	public void endVisit(MethodInvocation node) {
		removeParent(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		try {
			ASTLocation astLocation = new ASTLocation(node);
			process(astLocation);
		} catch (Throwable e) {

		}
		return super.visit(node);
	}

	@Override
	public void endVisit(MethodDeclaration node) {
		removeParent(node);
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		try {
			ASTLocation astLocation = new ASTLocation(node);
			process(astLocation);
		} catch (Throwable e) {

		}
		return super.visit(node);
	}

	@Override
	public void endVisit(TypeDeclaration node) {
		removeParent(node);
	}

	@Override
	public boolean visit(ClassInstanceCreation node) {
		try {
			ASTLocation astLocation = new ASTLocation(node);
			process(astLocation);
		} catch (Throwable e) {

		}
		return super.visit(node);
	}

	@Override
	public void endVisit(ClassInstanceCreation node) {
		removeParent(node);
	}

	private void process(ASTLocation astLocation) {
		ProcessedASTNode processedASTNode = new ProcessedASTNode(astLocation);

		ProcessedAST parent = parents.peek();
		parent.add(processedASTNode);

		parents.addFirst(processedASTNode);
	}

	private void removeParent(ASTNode node) {
		ProcessedAST parent = parents.peek();
		if (parent instanceof ProcessedASTNode) {
			ProcessedASTNode processedASTNode = (ProcessedASTNode) parent;

			if (processedASTNode.getLocation().getNode() == node) {
				parents.remove();
			}
		}
	}

	public ProcessedAST getProcessedAST() {
		return parents.getLast();
	}
}