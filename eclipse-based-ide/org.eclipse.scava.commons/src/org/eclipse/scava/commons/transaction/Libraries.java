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

import org.eclipse.scava.commons.library.Library;

/**
 * Provides a transferable representation of a set of {@link Library}s.
 *
 */
public class Libraries extends Transaction {
	private final List<Library> libraries;
	
	public Libraries(List<Library> libraries) {
		super(TransactionKind.LIBRARIES);
		this.libraries = libraries;
	}
	
	public List<Library> getLibraries() {
		return libraries;
	}
	
}
