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

import java.util.List;
import java.util.Random;

import org.eclipse.scava.platform.AbstractFactoidMetricProvider;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.factoids.Factoid;
import org.eclipse.scava.platform.factoids.FactoidCategory;
import org.eclipse.scava.platform.factoids.Factoids;
import org.eclipse.scava.platform.factoids.StarRating;
import org.eclipse.scava.repository.model.Project;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class CocomoFactoid extends AbstractFactoidMetricProvider{

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
		return "COCOMO is crazy."; // This method will be removed in a later version.
	}

	@Override
	public boolean appliesTo(Project project) {
		return project.getVcsRepositories().size() > 0;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return null;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
		// Assumes ALL projects are "semi-detached"
		double a = 3.0;
		double b = 1.12;
		double c = 2.5;
		double d = 0.35;
		
		int kloc = new Random().nextInt(50000000); // FIXME get from metric
		kloc /= 1000;
		
		double effortApplied = a * Math.pow(kloc, b); // person months
		double devTime = c * Math.pow(effortApplied, d); // months
		double peopleRequired = effortApplied / devTime; // count
		
		int years = (int)effortApplied / 12;
		
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

	public static void main(String[] args)  throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Xtext");
		
		CocomoFactoid f = new CocomoFactoid();
		f.adapt(db);
		f.measure(null, null, new Factoids(db));
		
		System.out.println(FactoidCategory.valueOf("asdasdasdd"));
	}

}
