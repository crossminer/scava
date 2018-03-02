/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.ui;

public class UIException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3177281363620280851L;

	public UIException() {
		super();
		
	}

	public UIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public UIException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public UIException(String message) {
		super(message);
		
	}

	public UIException(Throwable cause) {
		super(cause);
		
	}

}
