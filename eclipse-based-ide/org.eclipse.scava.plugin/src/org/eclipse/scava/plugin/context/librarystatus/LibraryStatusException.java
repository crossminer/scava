/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.context.librarystatus;

public class LibraryStatusException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4386287671904708318L;

	public LibraryStatusException() {
		super();
		
	}

	public LibraryStatusException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		
	}

	public LibraryStatusException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}

	public LibraryStatusException(String arg0) {
		super(arg0);
		
	}

	public LibraryStatusException(Throwable arg0) {
		super(arg0);
		
	}
	
}
