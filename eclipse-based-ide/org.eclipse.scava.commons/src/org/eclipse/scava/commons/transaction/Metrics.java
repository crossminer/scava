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

/**
 * Provides a transferable representation of metrics.
 *
 */
public class Metrics extends Transaction {
	private final List<String> metrics;
	
	public Metrics(List<String> metrics) {
		super(TransactionKind.METRICS);
		this.metrics = metrics;
	}
	
	public List<String> getMetrics() {
		return metrics;
	}
	
}
