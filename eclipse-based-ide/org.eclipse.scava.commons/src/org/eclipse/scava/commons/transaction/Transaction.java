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

import org.eclipse.scava.commons.ITypeRepresentedByKind;

/**
 * Provides a class which can be transferred between the server and the client.
 *
 */
public abstract class Transaction implements ITypeRepresentedByKind {
	private final TransactionKind kind;
	
	public Transaction(TransactionKind kind) {
		super();
		this.kind = kind;
	}
	
	@Override
	public TransactionKind getKind() {
		return kind;
	}
}
