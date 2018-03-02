/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.context.sourcecodestatus.astmanipulating.resolving;

import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTDetail;

public class ASTSimpleNameResolver {
	
	public static ASTDetail resolve(SimpleName simpleName) throws ASTResolveException {
		IBinding binding = simpleName.resolveBinding();
		
		switch (binding.getKind()) {
			case IBinding.VARIABLE: {
				return ASTVariableResolver.resolve(simpleName);
			}
			case IBinding.METHOD: {
				
				return ASTMethodResolver.resolve(simpleName);
			}
			case IBinding.TYPE:
				
				return ASTTypeNameResolver.resolve(simpleName);
			default:
				throw new ASTResolveException("Can not resolve IBinding kind: " + binding.getKind());
				
		}
	}
}
