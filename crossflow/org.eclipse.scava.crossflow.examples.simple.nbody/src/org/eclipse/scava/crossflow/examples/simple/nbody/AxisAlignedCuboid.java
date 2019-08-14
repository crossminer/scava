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
package org.eclipse.scava.crossflow.examples.simple.nbody;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.github.jamm.MemoryMeter;

/**
 * A quadrant represents an area inside the simulation space.
 * 
 * @author Horacio Hoyos Rodriguez
 *
 */
public class AxisAlignedCuboid implements NBodyCuboid {

	/** Dampening factor for the particles velocities */
	private final double dmp;
	/** The time delta of each simulation step */
	private final double timeDelta;
	private final CuboidCoordinates coordinates;
	private CuboidSimulationDurations durations;
	private long size = 0;
	private long memSize = 0;

	public AxisAlignedCuboid() {
		this(0.995, 0.001);
	}
	
	public AxisAlignedCuboid(
			CuboidCoordinates coordinates) {
		this(coordinates, 0.995, 0.001);
	}
	
	public AxisAlignedCuboid(double dmp, double timeDelta) {
		this(new StockCuboidCoordinates(), dmp, timeDelta);
	}

	public AxisAlignedCuboid(
		CuboidCoordinates coordinates,
		double dmp,
		double timeDelta) {
		super();
		this.coordinates = coordinates;
		this.dmp = dmp;
		this.timeDelta = timeDelta;
		this.durations = new StockCuboidSimulationDurations();
	}

	@Override
	public Collection<NBody3DBody> stepSimulation(Collection<NBody3DBody> universe) {
		List<NBody3DBody> qb = new ArrayList<NBody3DBody>();
		universe.stream().filter(b -> b.insideCube(this)).forEach(b -> qb.add(b));
		NBody3DBody[] quadrantBodies = qb.toArray(new NBody3DBody[qb.size()]);
		MemoryMeter meter = new MemoryMeter();
		memSize = meter.measure(universe);
		memSize += meter.measure(quadrantBodies);
		memSize += meter.measure(this);
		size = qb.size();
		long loop = System.nanoTime();
		prepare(quadrantBodies);
		durations = durations.logPrepare(loop);
		loop = System.nanoTime();
		accelerate(quadrantBodies, universe);
		durations = durations.logAccel(loop);
		loop = System.nanoTime();
		updateVelocity(quadrantBodies);
		durations = durations.logVel(loop);
		loop = System.nanoTime();
		updatePositions(quadrantBodies);
		durations = durations.logPos(loop);
		return Arrays.asList(quadrantBodies);	
	}
	
	@Override
	public Duration prepareDrtn() throws RequestedDurationNotFound {
		return durations.prepareDrtn();
	}

	@Override
	public Duration calcAccelDrtn() throws RequestedDurationNotFound {
		return durations.calcAccelDrtn();
	}

	@Override
	public Duration calcVelDrtn() throws RequestedDurationNotFound {
		return durations.calcVelDrtn();
	}

	@Override
	public Duration calcPosDrtn() throws RequestedDurationNotFound {
		return durations.calcPosDrtn();
	}

	@Override
	public String toString() {
		return String.format("bodies=%d, %s", size, durations.toString());
	}

	@Override
	public CuboidCoordinates coordinates() {
		return this.coordinates;
	}

	@Override
	public CuboidSimulationDurations durations() {
		return durations;
	}

	@Override
	public long memSize() {
		return memSize;
	}
	
	private void prepare(NBody3DBody[] quadrantBodies) {
		for (int i = 0; i < size; i++) {
			quadrantBodies[i] = quadrantBodies[i].prepare();
		}
	}

	private void accelerate(NBody3DBody[] quadrantBodies, Collection<NBody3DBody> universe) {
		for (int i = 0; i < size; i++) {
			quadrantBodies[i] = quadrantBodies[i].accelerate(universe);	
		}		
	}

	private void updateVelocity(NBody3DBody[] quadrantBodies) {
		for (int i = 0; i < size; i++) {
			quadrantBodies[i] = quadrantBodies[i].updateVelocity(dmp, timeDelta);
		}
	}

	private void updatePositions(NBody3DBody[] quadrantBodies) {
		for (int i = 0; i < size; i++) {
			quadrantBodies[i] = quadrantBodies[i].updatePostion(timeDelta);
		}
	}

}
