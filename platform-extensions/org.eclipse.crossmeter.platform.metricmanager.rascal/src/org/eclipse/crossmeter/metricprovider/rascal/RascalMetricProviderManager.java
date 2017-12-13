/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jurgen Vinju - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.rascal;

import java.util.List;

import org.eclipse.crossmeter.platform.IMetricProvider;
import org.eclipse.crossmeter.platform.IMetricProviderManager;

public class RascalMetricProviderManager implements IMetricProviderManager {
	@Override
	public List<IMetricProvider> getMetricProviders() {
		return RascalManager.getInstance().getMetricProviders();
	}
}
