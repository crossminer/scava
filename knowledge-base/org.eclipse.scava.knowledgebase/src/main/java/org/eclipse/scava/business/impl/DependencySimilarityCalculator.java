/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.business.impl;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.scava.business.IDependencyService;
import org.eclipse.scava.business.ISingleSimilarityCalculator;
import org.eclipse.scava.business.model.Artifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
/**
 * @author Juri Di Rocco
 *
 */
@Service
@Qualifier("Dependency")
public class DependencySimilarityCalculator implements ISingleSimilarityCalculator, IDependencyService {

	
	@Autowired 
	MongoOperations mongoOperations;
	
	private static final Logger logger = Logger.getLogger(DependencySimilarityCalculator.class);
	
	@Override
	public double calculateSimilarity(Artifact prj1, Artifact prj2) {
		List<String> intersection = intersection(prj1.getDependencies(), prj2.getDependencies());
		List<String> union = union(prj1.getDependencies(), prj2.getDependencies());
		return 2*intersection.size() /(union.size()*2*1.0);
	}

	@Override
	public String getSimilarityName() {
		// TODO Auto-generated method stub
		return "Dependency";
	}
    public <T> List<T> union(List<T> list1, List<T> list2) {
        Set<T> set = new HashSet<>();

        set.addAll(list1);
        set.addAll(list2);

        return new ArrayList<>(set);
    }

    public <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }

	@Override
	public int getNumberOfProjectThatUseDependencies(String dependency) {

		Aggregation aggregation = newAggregation(
				unwind("dependencies"),
				group("dependencies").count().as("count"),
				project("count").and("dependencies").previousOperation(),
				sort(Direction.DESC,"count"),
				match(Criteria.where("dependencies").is(dependency))
			);
		AggregationResults<DependencyCount> results = mongoOperations.aggregate(aggregation, "artifact", DependencyCount.class);
		List<DependencyCount> result = results.getMappedResults();
		if(!result.isEmpty())	
			return result.get(0).getCount();
		else return 0;
	}

	@Override
	public Map<String, Integer> getMapDependencyAndNumOfProjectThatUseIt() {
		Map<String, Integer> result = new HashMap<>();
		Aggregation aggregation = newAggregation(
				unwind("dependencies"),
				group("dependencies").count().as("count"),
				project("count").and("dependencies").previousOperation(),
				sort(Direction.DESC,"count")
			);
		AggregationResults<DependencyCount> results = mongoOperations.aggregate(aggregation, "artifact", DependencyCount.class);
		List<DependencyCount> intermediateResult = results.getMappedResults();
		for (DependencyCount dependencyCount : intermediateResult) {
			result.put(dependencyCount.getDependencies(), dependencyCount.getCount());
		}
		return result;
	}
	@Override
	public boolean appliesTo(Artifact art) {
		if (!art.getDependencies().isEmpty())
			return true;
		else return false;
	}

}
class DependencyCount {
	private String dependencies;
	private int count;
	public String getDependencies() {
		return dependencies;
	}
	public void setDependencies(String depName) {
		this.dependencies = depName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
