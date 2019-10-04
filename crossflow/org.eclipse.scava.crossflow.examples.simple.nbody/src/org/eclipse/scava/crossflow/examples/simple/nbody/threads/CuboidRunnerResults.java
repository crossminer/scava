/*********************************************************************
* Copyright (c) 2019 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* Contributors:
*     Horacio Hoyos - initial API and implementation
**********************************************************************/
package org.eclipse.scava.crossflow.examples.simple.nbody.threads;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

import org.eclipse.scava.crossflow.examples.simple.nbody.CuboidSimulationDurations;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBody3DBody;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyCuboid.CuboidCoordinates;

/**
 * The Interface CuboidRunnerResults.
 */
public interface CuboidRunnerResults extends Serializable {

	UUID runnerId();
	CuboidCoordinates coordiantes();
	CuboidSimulationDurations durations();
	Collection<NBody3DBody> bodies();
	long memUsed();
}