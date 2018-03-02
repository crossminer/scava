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

public class SourceCodeStatusException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3829191375515284493L;

	public SourceCodeStatusException() {
		super();
		
	}

	public SourceCodeStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public SourceCodeStatusException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public SourceCodeStatusException(String message) {
		super(message);
		
	}

	public SourceCodeStatusException(Throwable cause) {
		super(cause);
		
	}
	
}
