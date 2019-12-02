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
package org.eclipse.scava.plugin.usermonitoring.gremlin;

import java.util.Date;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.event.events.IEvent;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.MetricException;

public interface IGremlinUtils {

	public Vertex getFirstEvent();

	public Vertex getLastEvent();
	
	public Vertex getLastUploadEvent();

	public void insertVertex(IEvent event) throws MetricException;

	public long getTimeInMinuteFromFirstEvent();

	public GraphTraversal<Vertex, Vertex> getEventsBetween(Date greaterThanEqual, Date lesserThan);
	
	public void eraseEventsBefore(Long timeStamp);

}
