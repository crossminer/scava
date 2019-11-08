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

import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.location.ASTLocation;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.location.IJaccardComparable;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.location.PathPart;

public class ProcessedASTNode extends ProcessedAST implements IJaccardComparable {
	private final ASTLocation location;

	public ProcessedASTNode(ASTLocation location) {
		this.location = location;
	}

	public ASTLocation getLocation() {
		return location;
	}

	@Override
	public Stream<ProcessedASTNode> flattenChildren() {
		return Stream.concat(Stream.of(this), getChildren().stream().flatMap(ProcessedASTNode::flattenChildren));
	}

	@Override
	public Set<PathPart> getJaccardInput() {
		return location.getJaccardInput();
	}

}
