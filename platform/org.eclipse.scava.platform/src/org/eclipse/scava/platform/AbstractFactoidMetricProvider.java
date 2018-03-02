/*******************************************************************************
 * Copyright (c) 2018 University of York
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
import org.eclipse.scava.platform.factoids.Factoid;
import org.eclipse.scava.platform.factoids.Factoids;
import org.eclipse.scava.repository.model.Project;

import com.mongodb.DB;

public abstract class AbstractFactoidMetricProvider implements ITransientMetricProvider<Factoids>{
	
	protected MetricProviderContext context = null;
	protected List<IMetricProvider> uses;
	
	@Override
	public String getIdentifier() {
		return this.getClass().getName();
	}

	@Override
	public abstract boolean appliesTo(Project project);

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public abstract List<String> getIdentifiersOfUses();

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	final public Factoids adapt(DB db){
		return new Factoids(db); 
	}

	@Override
	final public void measure(Project project, ProjectDelta delta, Factoids db){
		
		Factoid factoid = db.getFactoids().findOneByMetricId(getIdentifier());
		if (factoid == null) {
			factoid = new Factoid();
			factoid.setMetricId(getIdentifier());
			factoid.setName(getFriendlyName());
			List<String> uses = getIdentifiersOfUses();
			if (uses != null) {
				for (String dep : uses) {
					factoid.getMetricDependencies().add(dep);
				}
			}
			db.getFactoids().add(factoid);
		}
		measureImpl(project, delta, factoid);
		db.sync();
	}

	abstract public void measureImpl(Project project, ProjectDelta delta, Factoid factoid);
}

