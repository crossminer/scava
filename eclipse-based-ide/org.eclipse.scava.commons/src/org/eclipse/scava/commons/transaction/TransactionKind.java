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

import java.lang.reflect.Type;

import org.eclipse.scava.commons.ITypeRepresentation;

/**
 * Provides an enumeration-safe set of kinds of {@link Transaction}.
 *
 */
public enum TransactionKind implements ITypeRepresentation{
	NAME(Name.class),
	SINGLE_LIBRARY(SingleLibrary.class),
	LIBRARIES(Libraries.class),
	DESCRIPTION(Description.class),
	METRICS(Metrics.class),
	URL(URL.class),
	API_CHANGES(APIChanges.class),
	API_USAGE_IN_CONTEXT(APIUsageInContext.class),
	UPDATABLE_API_USAGE(UpdatableAPIUsage.class),
	PARSED_SOURCE_CODE_CONTEXT(ParsedSourceCodeContext.class),
	RECOMMENDATIONS(Recommendations.class);

	private final Type type;
	
	private TransactionKind(Type type) {
		this.type = type;
	}

	@Override
	public Type getType() {
		return type;
	}
}
