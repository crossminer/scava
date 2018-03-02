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

import org.eclipse.scava.commons.context.sourcecode.SourceCodeContext;

/**
 * Provides a transferable representation of a {@link SourceCodeContext}.
 *
 */
public class ParsedSourceCodeContext extends Transaction {
	private final SourceCodeContext context;

	public ParsedSourceCodeContext(SourceCodeContext context) {
		super(TransactionKind.PARSED_SOURCE_CODE_CONTEXT);
		this.context = context;
	}

	public SourceCodeContext getContext() {
		return context;
	}

}
