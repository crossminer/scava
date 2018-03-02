/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.recommendation;

public class LibraryUpdaterException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6151764677568521882L;

	public LibraryUpdaterException() {
		super();
		
	}

	public LibraryUpdaterException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public LibraryUpdaterException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public LibraryUpdaterException(String message) {
		super(message);
		
	}

	public LibraryUpdaterException(Throwable cause) {
		super(cause);
		
	}

}
