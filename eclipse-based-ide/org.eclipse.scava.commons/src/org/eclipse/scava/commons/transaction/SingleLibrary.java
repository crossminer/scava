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

import org.eclipse.scava.commons.library.Library;

/**
 * Provides a transferable representation of a {@link Library}.
 *
 */
public class SingleLibrary extends Transaction {
	private final Library library;
	
	public SingleLibrary(Library library) {
		super(TransactionKind.SINGLE_LIBRARY);
		this.library = library;
	}
	
	public Library getLibrary() {
		return library;
	}
	
}
