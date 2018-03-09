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
 * Provides a transferable representation of the usage of an api with the target api.
 * Stores the currently used {@link Library} with its usage in the source code and
 * the target Library which can be a different version of the currently used Library
 * or a completely different one.
 *
 */
public class UpdatableAPIUsage extends Transaction {
	
	private final Library usedLibrary;
	private final Library targetLibrary;
	private final List<APIUsageInContext> apiUsagesInContexts;
	
	public UpdatableAPIUsage(Library usedLibrary, Library targetLibrary, List<APIUsageInContext> apiUsagesInContexts) {
		super(TransactionKind.UPDATABLE_API_USAGE);
		this.usedLibrary = usedLibrary;
		this.targetLibrary = targetLibrary;
		this.apiUsagesInContexts = apiUsagesInContexts;
	}
	
	public Library getUsedLibrary() {
		return usedLibrary;
	}
	
	public Library getTargetLibrary() {
		return targetLibrary;
	}
	
	public List<APIUsageInContext> getApiUsagesInContexts() {
		return apiUsagesInContexts;
	}
	
}
