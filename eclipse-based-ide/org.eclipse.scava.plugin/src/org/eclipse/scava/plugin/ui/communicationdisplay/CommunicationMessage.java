/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.ui.communicationdisplay;

import org.eclipse.scava.plugin.logger.LoggerMessageKind;

public class CommunicationMessage {
	private String message;
	private LoggerMessageKind kind;
	
	public CommunicationMessage(String message, LoggerMessageKind kind) {
		this.message = message;
		this.kind = kind;
	}
	
	public String getMessage() {
		return message;
	}
	
	public LoggerMessageKind getKind() {
		return kind;
	}
	
}
