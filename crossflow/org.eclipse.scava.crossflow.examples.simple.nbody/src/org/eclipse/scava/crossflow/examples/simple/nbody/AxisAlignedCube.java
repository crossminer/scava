package org.eclipse.scava.crossflow.examples.simple.nbody;

import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

/**
 * A quadrant represents an area inside the simulation space.
 * 
 * @author Horacio Hoyos Rodriguez
 *
 */
public class AxisAlignedCube implements NBodyCuboid {
	
	/** Dampening factor for the particles velocities */
	private final double dmp;
	/** The time delta of each simulation step */
	private final double timeDelta;
	private final double xmin;
	private final double ymin;
	private final double zmin;
	private final double xmax;
	private final double ymax;
	private final double zmax;
	// private final Queue<NBody3DBody> quadrantBodies;
	private Duration prprDrtn;
	private Duration calcAccelDrtn;
	private Duration calcVelDrtn;
	private Duration calcPosDrtn;
	private long size = 0;

	public AxisAlignedCube() {
		this(0.995, 0.001);
	}
	
	public AxisAlignedCube(double dmp, double timeDelta) {
		this(0, 0, 0, 0, 0, 0, dmp, timeDelta);
	}

	private AxisAlignedCube(
		double xmin,
		double ymin,
		double zmin,
		double xmax,
		double ymax,
		double zmax,
		double dmp,
		double timeDelta) {
		super();
		this.xmin = xmin;
		this.ymin = ymin;
		this.zmin = zmin;
		this.xmax = xmax;
		this.ymax = ymax;
		this.zmax = zmax;
		this.dmp = dmp;
		this.timeDelta = timeDelta;
		prprDrtn = Duration.ZERO;
		calcAccelDrtn = Duration.ZERO;
		calcVelDrtn = Duration.ZERO;
		calcPosDrtn = Duration.ZERO;
	}
	
	@Override
	public double xmin() {
		return xmin;
	}

	@Override
	public double ymin() {
		return ymin;
	}

	@Override
	public double zmin() {
		return zmin;
	}

	@Override
	public double xmax() {
		return xmax;
	}

	@Override
	public double ymax() {
		return ymax;
	}

	@Override
	public double zmax() {
		return zmax;
	}
	
	@Override
	public NBodyCuboid configureCuboid(
			double x_min, double x_max,
			double y_min, double y_max,
			double z_min, double z_max) {
		return new AxisAlignedCube(x_min, x_max, y_min, y_max, z_min, z_max, dmp, timeDelta);
	}

	@Override
	public Collection<NBody3DBody> stepSimulation(Collection<NBody3DBody> universe) {
		Deque<NBody3DBody> quadrantBodies = new ArrayDeque<NBody3DBody>();
		universe.parallelStream().filter(b -> b.insideCube(this)).forEach(b -> quadrantBodies.add(b));
		size = quadrantBodies.size();
		long loop = System.nanoTime();
		prepare(quadrantBodies);
		prprDrtn = prprDrtn.plus(Duration.ofNanos(System.nanoTime()).minusNanos(loop));
		loop = System.nanoTime();
		accelerate(quadrantBodies, universe);
		calcAccelDrtn = calcAccelDrtn.plus(Duration.ofNanos(System.nanoTime()).minusNanos(loop));
		loop = System.nanoTime();
		updateVelocity(quadrantBodies);
		calcVelDrtn = calcVelDrtn.plus(Duration.ofNanos(System.nanoTime()).minusNanos(loop));
		loop = System.nanoTime();
		updatePositions(quadrantBodies);
		calcPosDrtn = calcPosDrtn.plus(Duration.ofNanos(System.nanoTime()).minusNanos(loop));
		return quadrantBodies;	
	}
	

	// loop0
	private void prepare(Deque<NBody3DBody> quadrantBodies) {
		NBody3DBody body;
		for (int i = 0; i < quadrantBodies.size(); i++) {
			body = quadrantBodies.remove();
			body = body.prepare();
			quadrantBodies.add(body);
		}
	}

	// loop1
	private void accelerate(Deque<NBody3DBody> quadrantBodies, Collection<NBody3DBody> universe) {
		NBody3DBody body;
		for (int i = 0; i < quadrantBodies.size(); i++) {
			body = quadrantBodies.remove();
			body = body.accelerate(universe);
			quadrantBodies.add(body);
		}
	}

	// loop2
	private void updateVelocity(Deque<NBody3DBody> quadrantBodies) {
		NBody3DBody body;
		for (int i = 0; i < quadrantBodies.size(); i++) {
			body = quadrantBodies.remove();
			body = body.updateVelocity(dmp, timeDelta);
			quadrantBodies.add(body);
		}
	}

	// loop3
	private void updatePositions(Deque<NBody3DBody> quadrantBodies) {
		NBody3DBody body;
		for (int i = 0; i < quadrantBodies.size(); i++) {
			body = quadrantBodies.remove();
			body.updatePostion(timeDelta);
			quadrantBodies.add(body);
		}
	}
	
	@Override
	public Duration prepareDrtn() {
		return prprDrtn;
	}

	@Override
	public Duration calcAccelDrtn() {
		return calcAccelDrtn;
	}

	@Override
	public Duration calcVelDrtn() {
		return calcVelDrtn;
	}

	@Override
	public Duration calcPosDrtn() {
		return calcPosDrtn;
	}

	@Override
	public String toString() {
		return String.format("bodies=%d, prep=%s, accel=%s, vel=%s, pos=%s", size, prprDrtn, calcAccelDrtn, calcVelDrtn, calcPosDrtn);
	}
	
	

}
