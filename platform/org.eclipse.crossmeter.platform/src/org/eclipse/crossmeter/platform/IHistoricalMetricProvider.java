/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.crossmeter.platform;

import org.eclipse.crossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public interface IHistoricalMetricProvider extends IMetricProvider {
	
	public String getCollectionName();
	
	public Pongo measure(Project project);
}
