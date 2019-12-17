/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*    Zsolt János Szamosvölgyi
*    Endre Tamás Váradi
*    Gergõ Balogh
**********************************************************************/
package org.eclipse.scava.plugin.usermonitoring.metric.metrics.metric;

import java.util.Map;

import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics.IBasicMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.milestones.TimeintervalGraphtraversal;

public interface IMetric {
	

	public String getID();
	
	public String getProjectId();

	public String getDescription();

	public IBasicMetric getBasicMetric();

	public Map<String, Double> getItemValuePairs(TimeintervalGraphtraversal<Vertex, Vertex> timestampedGraphTraversal);
	
	public String toString();

}
