/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.webreferenceviewer.reference.sites;

public class StackOverflowErrorResponseException extends Exception {
	private static final long serialVersionUID = 1914835643262992305L;
	private final int errorId;
	private final String errorMessage;
	private final String errorName;

	public StackOverflowErrorResponseException(int errorId, String errorMessage, String errorName) {
		super();
		this.errorId = errorId;
		this.errorMessage = errorMessage;
		this.errorName = errorName;
	}

	public int getErrorId() {
		return errorId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorName() {
		return errorName;
	}

	@Override
	public String toString() {
		return "ErrorResponseException [errorId=" + errorId + ", errorMessage=" + errorMessage + ", errorName="
				+ errorName + "]";
	}

}