/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.osgi;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.IMetricProviderManager;

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
