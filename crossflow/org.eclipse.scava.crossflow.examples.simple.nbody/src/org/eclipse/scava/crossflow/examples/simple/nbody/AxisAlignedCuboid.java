package org.eclipse.scava.crossflow.examples.simple.nbody;

import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

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
	private long accelTotal;

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
		Deque<NBody3DBody> quadrantBodies = new ArrayDeque<NBody3DBody>();
		universe.stream().filter(b -> b.insideCube(this)).forEach(b -> quadrantBodies.add(b));
		MemoryMeter meter = new MemoryMeter();
		memSize = meter.measure(universe);
		memSize += meter.measure(quadrantBodies);
		memSize += meter.measure(this);
		size = quadrantBodies.size();
		long loop = System.nanoTime();
		prepare(quadrantBodies);
		durations = durations.logPrepare(loop);
		loop = System.nanoTime();
		accelerate(quadrantBodies, universe);
		durations = durations.logAccel(loop);
		try {
			System.out.println("acel," + durations.calcAccelDrtn().toNanos());
			System.out.println("total," + accelTotal);
			System.out.println("queue," + (durations.calcAccelDrtn().toNanos() - accelTotal));
		} catch (RequestedDurationNotFound e) {
			
		}
		loop = System.nanoTime();
		updateVelocity(quadrantBodies);
		durations = durations.logVel(loop);
		loop = System.nanoTime();
		updatePositions(quadrantBodies);
		durations = durations.logPos(loop);
		return quadrantBodies;	
	}
	

	// loop0
	private void prepare(Deque<NBody3DBody> quadrantBodies) {
		NBody3DBody body = null;
		for (int i = 0; i < size; i++) {
			body = quadrantBodies.remove();
			body = body.prepare();
			quadrantBodies.add(body);
			
		}
	}

	// loop1
	private void accelerate(Deque<NBody3DBody> quadrantBodies, Collection<NBody3DBody> universe) {
		NBody3DBody body;
		accelTotal = 0;
		long start;
		for (int i = 0; i < size; i++) {
			body = quadrantBodies.remove();
			start = System.nanoTime();
			body = body.accelerate(universe);
			accelTotal += System.nanoTime() - start;	
			quadrantBodies.add(body);
		}
		
	}

	// loop2
	private void updateVelocity(Deque<NBody3DBody> quadrantBodies) {
		NBody3DBody body;
		for (int i = 0; i < size; i++) {
			body = quadrantBodies.remove();
			body = body.updateVelocity(dmp, timeDelta);
			quadrantBodies.add(body);
		}
	}

	// loop3
	private void updatePositions(Deque<NBody3DBody> quadrantBodies) {
		NBody3DBody body;
		for (int i = 0; i < size; i++) {
			body = quadrantBodies.remove();
			body.updatePostion(timeDelta);
			quadrantBodies.add(body);
		}
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

}
