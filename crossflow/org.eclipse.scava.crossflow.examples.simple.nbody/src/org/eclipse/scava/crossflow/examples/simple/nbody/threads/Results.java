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

import java.util.Collection;
import java.util.UUID;

import org.eclipse.scava.crossflow.examples.simple.nbody.CuboidSimulationDurations;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBody3DBody;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyCuboid.CuboidCoordinates;

/**
 * The Class Results.
 */
public class Results implements CuboidRunnerResults {
	
	private static final long serialVersionUID = 1590697527776171251L;
	private final CuboidSimulationDurations durations;
	private final Collection<NBody3DBody> bodies;
	private final CuboidCoordinates coordinates;
	private final UUID runnerId;
	private final long memUsed;
	
	public Results(
		CuboidSimulationDurations durations,
		Collection<NBody3DBody> result,
		CuboidCoordinates coordinates,
		UUID runnerId,
		long memUsed) {
		super();
		this.durations = durations;
		this.bodies = result;
		this.coordinates = coordinates;
		this.runnerId = runnerId;
		this.memUsed = memUsed;
	}

	@Override
	public UUID runnerId() {
		return runnerId;
	}

	@Override
	public CuboidCoordinates coordiantes() {
		return coordinates;
	}

	@Override
	public CuboidSimulationDurations durations() {
		return durations;
	}

	@Override
	public Collection<NBody3DBody> bodies() {
		return bodies;
	}

	@Override
	public long memUsed() {
		return memUsed;
	}
}