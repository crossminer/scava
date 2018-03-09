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

public class ASTResolveException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5456933606048101750L;
	
	public ASTResolveException() {
		super();
		
	}
	
	public ASTResolveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}
	
	public ASTResolveException(String message, Throwable cause) {
		super(message, cause);
		
	}
	
	public ASTResolveException(String message) {
		super(message);
		
	}
	
	public ASTResolveException(Throwable cause) {
		super(cause);
		
	}
	
}
