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
*    Zsolt J�nos Szamosv�lgyi
*    Endre Tam�s V�radi
*    Gerg� Balogh
**********************************************************************/
package org.eclipse.scava.commons.transaction;

/**
 * Provides a transferable representation of a name.
 *
 */
public class Name extends Transaction {
	private final String name;
	
	public Name(String name) {
		super(TransactionKind.NAME);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
