/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.factoid.cocomo;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.scava.metricprovider.rascal.PongoToRascal;
import org.eclipse.scava.metricprovider.rascal.RascalMetricProvider;
import org.eclipse.scava.metricprovider.rascal.trans.model.RascalMetrics;
import org.eclipse.scava.platform.AbstractFactoidMetricProvider;
import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.factoids.Factoid;
import org.eclipse.scava.platform.factoids.FactoidCategory;
import org.eclipse.scava.platform.factoids.Factoids;
import org.eclipse.scava.platform.factoids.StarRating;
import org.eclipse.scava.repository.model.Project;

import com.mongodb.DB;
import com.mongodb.Mongo;

import io.usethesource.vallang.IInteger;
import io.usethesource.vallang.IMap;
import io.usethesource.vallang.IValue;

public class CocomoFactoid extends AbstractFactoidMetricProvider {

	@Override
	public String getShortIdentifier() {
		return "cocomo";
	}

	@Override
	public String getIdentifier() {
		return getShortIdentifier();
	}

	@Override
	public String getFriendlyName() {
		return "Cocomo"; // This method will be removed in a later version.
	}

	@Override
	public String getSummaryInformation() {
		return "COCOMO is crazy."; // This method will be removed in a later
									// version.
	}

	@Override
	public boolean appliesTo(Project project) {
		return project.getVcsRepositories().size() > 0;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList("trans.rascal.LOC.genericLOC");
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
		// Assumes ALL projects are "semi-detached" with cost driver ratings =
		// nominal (EAF = 1.0)
		double a = 3.0;
		double b = 1.12;
		double eaf = 1.0;

		RascalMetricProvider locMetric = (RascalMetricProvider) uses.get(0);
		if (locMetric == null) {
			factoid.setFactoid("Couldn\t calculate COCOMO for this project (no LOC information).");
			factoid.setCategory(FactoidCategory.CODE);
			factoid.setStars(StarRating.ONE);
		} else {
			// List<Pongo> values = locMetric.
			try {
				Mongo mongo;
				mongo = Configuration.getInstance().getMongoConnection();
				DB db = mongo.getDB(project.getShortName());
				RascalMetrics rascalMetrics = new RascalMetrics(db, locMetric.getIdentifier());
				IMap values = (IMap) PongoToRascal.toValue(rascalMetrics, locMetric.getReturnType(), false);

				if (values != null) {
					int kloc = 0;
					Iterator<IValue> it = values.valueIterator();
					while (it.hasNext())
						kloc += ((IInteger) it.next()).intValue();

					kloc /= 1000;

					double effort = a * Math.pow(kloc, b) * eaf; // person-months
					int years = (int) effort / 12;

					factoid.setFactoid("Took an estimated " + years + " years (COCOMO model).");
					factoid.setCategory(FactoidCategory.CODE);

					if (years < 5) {
						factoid.setStars(StarRating.ONE);
					} else if (years < 10) {
						factoid.setStars(StarRating.TWO);
					} else if (years < 50) {
						factoid.setStars(StarRating.THREE);
					} else {
						factoid.setStars(StarRating.FOUR);
					}
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
	}
}
