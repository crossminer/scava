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
import java.util.Map;

import org.eclipse.scava.platform.AbstractFactoidMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.factoids.Factoid;
import org.eclipse.scava.platform.factoids.Factoids;
import org.eclipse.scava.platform.factoids.StarRating;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.interpreter.result.AbstractFunction;

public class RascalFactoidProvider extends AbstractFactoidMetricProvider {
	private RascalMetricProvider metric;
	
	public RascalFactoidProvider(String bundleId, String metricName, String funcName,
			String friendlyName, String description, AbstractFunction f,
			Map<String, String> uses) {
	
		metric = new RascalMetricProvider(bundleId, metricName, funcName, friendlyName, description, f, uses);
	}

	@Override
	public String getIdentifier() {
		return metric.getIdentifier();
	}
	
	@Override
	public void setUses(List<IMetricProvider> uses) {
		super.setUses(uses);
		metric.setUses(uses);
	}
	
	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		metric.setMetricProviderContext(context);
	}
	
	@Override
	public String getShortIdentifier() {
		return metric.getShortIdentifier();
	}

	@Override
	public String getFriendlyName() {
		return metric.getFriendlyName();
	}

	@Override
	public String getSummaryInformation() {
		return metric.getSummaryInformation();
	}

	@Override
	public boolean appliesTo(Project project) {
		return metric.appliesTo(project);
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return metric.getIdentifiersOfUses();
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
		IValue result = metric.compute(project, delta);
		
		if (result == null) {
			return; // something else went wrong and we should have seen error messages from that
		}
		
		valueToFactoid(result, factoid);
	}

	private void valueToFactoid(IValue value, Factoid factoid) {
		if (!(value instanceof IConstructor)) {
			throw new IllegalArgumentException("factoids should return Factoid data-types");
		}
		
		IConstructor cons = (IConstructor) value;
			
		if (!cons.getName().equals("factoid")) {
			throw new IllegalArgumentException("factoids should return Factoid data-types");
		}

		factoid.setFactoid(((IString) cons.get("freetext")).getValue());
		factoid.setStars(RascalToPongo.toRating((IConstructor) cons.get("rating")));
	}
	
	private IValue factoidToValue(Factoid factoid, IValueFactory vf) {
		StarRating stars = factoid.getStars();
		if (stars != null) {
			return vf.tuple(vf.string(stars.toString()), vf.string(factoid.getFactoid()));
		} else {
			return null;
		}
	}
	
	public IValue getMeasuredFactoid(Factoids db, IValueFactory vf) {
		Factoid factoid = db.getFactoids().findOneByMetricId(getIdentifier());
		return factoid != null ? factoidToValue(factoid, vf) : null;
	}
	
}
