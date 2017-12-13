/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.platform.osgi;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.crossmeter.platform.IMetricProvider;
import org.eclipse.crossmeter.platform.IMetricProviderManager;

public class ManualRegistrationMetricProviderManager implements
		IMetricProviderManager {

	protected List<IMetricProvider> mps;
	
	public ManualRegistrationMetricProviderManager() {
		this.mps = new ArrayList<>();
	
	}
	
	public void addMetricProvider(IMetricProvider mp) {
		this.mps.add(mp);
	}
	
	@Override
	public List<IMetricProvider> getMetricProviders() {
		return mps;
	}

}
