/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*    Zsolt János Szamosvölgyi
*    Endre Tamás Váradi
*    Gergõ Balogh
**********************************************************************/
package org.eclipse.scava.plugin.usermonitoring;

public enum ErrorType {

	
	DATABASE_ERROR(1, "Problem occurred in local database connection."),
	EVENT_CONSUME_ERROR(2, "Problem occurred during event processing."),
	ECLIPSE_PROPERTY_ERROR(3,"Problem occurred during eclipse properties."),
	EVENT_INSERTION_ERROR(4,"Problem occurred during database storing."),
	METRIC_CALCULATION_ERROR(5,"Problem occurred during metric calculation."),
	METRIC_UPLOAD_ERROR(6,"Problem occurred during metric uploading."),
	ILLEGAL_ARGUMENT(7, "Please, check your settings in the preferences.");

	ErrorType(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	private final int errorCode;
	private final String errorMessage;

	public int getErrorCode() {
		return errorCode;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

}
