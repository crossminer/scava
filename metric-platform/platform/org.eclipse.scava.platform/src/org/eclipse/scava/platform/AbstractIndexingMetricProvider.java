/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/

package org.eclipse.scava.platform;

import java.util.List;

import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.indexing.Indexing;
import org.eclipse.scava.repository.model.Project;

import com.mongodb.DB;

public abstract class AbstractIndexingMetricProvider implements ITransientMetricProvider<Indexing>{
	
	protected MetricProviderContext context = null;
	protected List<IMetricProvider> uses;
	
	@Override
	public String getIdentifier() {
		return this.getClass().getName();
	}
	
	@Override
	public abstract boolean appliesTo(Project project);

	@Override
	public abstract void setUses(List<IMetricProvider> uses);
	
	@Override
	public abstract List<String> getIdentifiersOfUses();

	@Override
	public abstract void setMetricProviderContext(MetricProviderContext context);
	
	@Override
	final public Indexing adapt(DB db){
		return new Indexing(db); 
	}
	
	@Override
	abstract public void measure(Project project, ProjectDelta delta, Indexing db);
	
	
}