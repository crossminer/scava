/*******************************************************************************
 * Copyright (c) 2017 Centrum Wiskunde & Informatica
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.rascal;

import java.util.List;

import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.IMetricProviderManager;

public class RascalMetricProviderManager implements IMetricProviderManager {
	@Override
	public List<IMetricProvider> getMetricProviders() {
		return RascalManager.getInstance().getMetricProviders();
	}
}
