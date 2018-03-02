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

import java.util.List;

import org.eclipse.scava.commons.libraryapi.LibraryAPIElement;

/**
 * Provides a transferable set of {@link LibraryAPIElement}s.
 *
 */
public class APIChanges extends Transaction {
	private final List<LibraryAPIElement> apiElements;
	
	public APIChanges(List<LibraryAPIElement> apiChanges) {
		super(TransactionKind.API_CHANGES);
		this.apiElements = apiChanges;
	}
	
	public List<LibraryAPIElement> getApiElements() {
		return apiElements;
	}
	
}
