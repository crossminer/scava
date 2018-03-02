@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module CC

import Map;
import List;
import Set;

import analysis::statistics::Frequency;
import analysis::statistics::Inference;
import util::Math;

import org::eclipse::scava::metricprovider::MetricProvider;


public real giniCCOverMethods(map[loc, int] methodCC) {
  if (isEmpty(methodCC)) {
    throw undefined("No CC data available", |tmp:///|);
  }
  
  distCCOverMethods = distribution(methodCC);
  
  if (size(distCCOverMethods) < 2) {
    throw undefined("Not enough data available.", |tmp:///|);
  }
  
  if (sum(distCCOverMethods<1>) == 0) {
    return 1.0; // completely honest distribution of nothing over everything.
  }
  
  return round(gini([<x, distCCOverMethods[x]> | x <- distCCOverMethods]), 0.01);
}


public map[str, int] CCHistogram(map[loc, int] methodCC) {
  if (isEmpty(methodCC)) {
    throw undefined("No CC data available", |tmp:///|);
  }

  thresholds = [10, 20, 50]; // moderate, high, very high

  list[int] counts = [0, 0, 0];
  
  for (m <- methodCC) {
    cc = methodCC[m];
    for (i <- [0..size(thresholds)]) {
      if (cc > thresholds[i] && (i + 1 == size(thresholds) || cc <= thresholds[i + 1])) {
      	counts[i] += 1;
      }
    }
  }

  numMethods = size(methodCC);
  
  counts = [( numMethods | it - c | c <- counts )] + counts; // add nr of low risk methods
  risks = ["low", "moderate", "high", "very high"];
  
  return toMapUnique(zip(risks, counts));
}


public Factoid CCFactoid(map[str, int] riskCounts, str language) {
  if (isEmpty(riskCounts)) {
    throw undefined("No CC data available", |tmp:///|);
  }

  numMethods = ( 0 | it + riskCounts[r] | r <- riskCounts);

  if (numMethods == 0) {
    throw undefined("No methods", |tmp:///|);
  }
  
  percentages = [ round(100.0 * riskCounts[c] / numMethods, 0.01) | c <- riskCounts, c != "low" ]; // drop low risk counts
  
  rankings = [ [25, 0, 0], [30, 5, 0], [40, 10, 0] ];
  
  stars = 1;
  
  for (i <- [0..size(rankings)]) {
    if (all(j <- [0..size(percentages)], percentages[j] <= rankings[i][j])) {
      stars = 4 - i;
      break;
    }
  }

  txt = "The cyclomatic complexity footprint of the system\'s <language> code shows a <["", "very high", "high", "moderate", "low"][stars]> risk."; 
  txt += " The percentages of methods with moderate, high and very high risk CC are respectively <percentages[0]>%, <percentages[1]>% and <percentages[2]>%.";

  return factoid(txt, starLookup[stars]);
}

