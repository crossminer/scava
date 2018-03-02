/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.context.sourcecodestatus.astmanipulating.filtering;

import org.eclipse.jdt.core.dom.ASTNode;

public class ASTNodeRangeFilter implements IASTNodeFilter {
	private int startChar;
	private int endChar;
	
	private ASTNodeRangeFilter(int startChar, int endChar) {
		this.startChar = startChar;
		this.endChar = endChar;
	}
	
	public static ASTNodeRangeFilter offsetLength(int offset, int length)
	{
		int startChar = offset;
		int endChar = offset + length;
		
		return fromTo(startChar, endChar);
	}
	
	public static ASTNodeRangeFilter fromTo(int startChar, int endChar)
	{
		return new ASTNodeRangeFilter(startChar, endChar);
	}
	
	@Override
	public boolean check(ASTNode node) {
		return node.getStartPosition() <= endChar && node.getStartPosition() + node.getLength() >= startChar;
	}
}
