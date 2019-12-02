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

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class ProcessedAST {
	private final Set<ProcessedASTNode> children;

	public ProcessedAST() {
		children = new HashSet<>();
	}

	protected Set<ProcessedASTNode> getChildren() {
		return children;
	}

	public void add(ProcessedASTNode node) {
		children.add(node);
	}

	public Stream<ProcessedASTNode> flattenChildren() {
		return children.stream().flatMap(ProcessedASTNode::flattenChildren);
	}
}
