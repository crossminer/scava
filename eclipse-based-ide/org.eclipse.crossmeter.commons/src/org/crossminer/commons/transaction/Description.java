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
package org.crossminer.commons.transaction;

/**
 * Provides a transferable representation of a description.
 *
 */
public class Description extends Transaction{
	private final String description;
	
	public Description(String description) {
		super(TransactionKind.DESCRIPTION);
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
