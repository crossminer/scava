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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.scava.commons.context.sourcecode.SourceCodeContext;
import org.eclipse.scava.commons.libraryapi.LibraryAPIElement;

/**
 * Provides a transferable representation of used {@link LibraryAPIElement}s in context.
 *
 */
public class APIUsageInContext extends Transaction{
	private final SourceCodeContext context;
	private final Set<LibraryAPIElement> usedAPIElements;
	
	public APIUsageInContext(SourceCodeContext context, Set<LibraryAPIElement> usedAPIElements) {
		super(TransactionKind.API_USAGE_IN_CONTEXT);
		this.context = context;
		this.usedAPIElements = usedAPIElements;
	}
	
	public APIUsageInContext(SourceCodeContext context) {
		super(TransactionKind.API_USAGE_IN_CONTEXT);
		this.context = context;
		this.usedAPIElements = new HashSet<>();
	}
	
	public SourceCodeContext getContext() {
		return context;
	}
	
	public Set<LibraryAPIElement> getUsedAPIElements() {
		return usedAPIElements;
	}
	
	public void addUsedAPIElement(LibraryAPIElement apiElement) {
		getUsedAPIElements().add(apiElement);
	}
}
