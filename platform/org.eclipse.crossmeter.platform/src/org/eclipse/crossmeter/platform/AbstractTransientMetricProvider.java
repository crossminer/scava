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

import java.util.Collections;
import java.util.List;

import org.eclipse.crossmeter.platform.delta.ProjectDelta;
import org.eclipse.crossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public abstract class AbstractTransientMetricProvider<T extends PongoDB> implements ITransientMetricProvider<T>{
	
	protected MetricProviderContext context = null;
	
	@Override
	public String getIdentifier() {
		return this.getClass().getName();
	}

	@Override
	public abstract boolean appliesTo(Project project);

	@Override
	public void setUses(List<IMetricProvider> uses) {
		
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Collections.emptyList();
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public abstract T adapt(DB db);

	@Override
	public abstract void measure(Project project, ProjectDelta delta, T db);

}
