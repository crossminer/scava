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

import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.util.FastMath;
import org.eclipse.scava.crossflow.examples.simple.nbody.Bodies.CreatingBodiesException;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyMetrics.RequestedDurationNotFound;

/**
 * The Class SimpleNBody.
 */
public class SimpleNBody implements NBodySimulation {

	//private final String fmt = "%d,%d,%f,%f,%f,%f,%f,%f,%f,%f,%f%n";
	private final List<String> stepDetails = new ArrayList<>();
	
	private final double eps = 0.00125;

	private List<NBody3DBody> universe;
	private Set<NBodyCuboid> cuboides;
	private final int numCubes;
	private Duration prprDrtn;
	private Duration calcAccelDrtn;
	private Duration calcVelDrtn;
	private Duration calcPosDrtn;
	private Duration overHeadDrtn;
	private double phi;
	private double flops;
	private double bytes;
	private long memSize = 0;
	
	public static void main(String... args) throws Exception {
		SimpleNBody sim = new SimpleNBody(Integer.parseInt(args[2]));
		sim.populateRandomly(Integer.parseInt(args[0]));
		sim.runSimulation(Integer.parseInt(args[1]));
	}
	
	
	public SimpleNBody(int numCubes) {
		super();
		this.numCubes = numCubes;
	}

	@Override
	public void populateRandomly(int size) {
		universe = new RandomBodies(size).createBodies();
	}

	@Override
	public void populateFromJson(Path data) throws CreatingBodiesException {
		universe = new JsonBodies(data).createBodies();
	}

	@Override
	public void populateRandomly(int size, double pscale, double vscale, double mscale) throws CreatingBodiesException {
		universe = new RandomBodies(size, pscale, vscale, mscale).createBodies();
	}
	
	@Override
	public void runSimulation(int steps) throws InvalidNumberOfCubesException {
		prprDrtn = Duration.ZERO;
		calcAccelDrtn = Duration.ZERO;
		calcVelDrtn = Duration.ZERO;
		calcPosDrtn = Duration.ZERO;
		setupCubes();
		int size = universe.size();
		List<NBody3DBody> newUniverse;
		long start = System.nanoTime();
		for (int i = 0; i < steps; i++) {
			newUniverse = new ArrayList<>(universe.size());
			
			for (NBodyCuboid c : cuboides) {
				newUniverse.addAll(c.stepSimulation(universe));
				try {
					prprDrtn = prprDrtn.plus(c.prepareDrtn());
					calcAccelDrtn = calcAccelDrtn.plus(c.calcAccelDrtn());
					calcVelDrtn = calcVelDrtn.plus(c.calcVelDrtn());
					calcPosDrtn = calcPosDrtn.plus(c.calcPosDrtn());
				} catch (RequestedDurationNotFound e) {
					throw new IllegalStateException(e);
				}
			}
//			int j = 0;
//			for (NBody3DBody body : newUniverse) {
//				stepDetails.add(String.format(fmt, i, j++ + 1,
//					body.position().x(),
//					body.position().y(),
//					body.position().z(),
//					body.velocity().x(),
//					body.velocity().y(),
//					body.velocity().z(),
//					body.accel().x(),
//					body.accel().y(),
//					body.accel().z()));
//			}
			memSize += cuboides.size() * cuboides.iterator().next().memSize();
			universe = newUniverse;
		}
		calculatePerformance(size, steps);
		overHeadDrtn = Duration.ofNanos(System.nanoTime() - start);
		//printResults(steps);
	}
	
	@Override
	public String getMetrics() {
		return String.format("%f,%f,%f,%f,%f,%f,%f,%f",
				prprDrtn.toNanos()/1e9, 
				calcAccelDrtn.toNanos()/1e9, 
				calcVelDrtn.toNanos()/1e9,
				calcPosDrtn.toNanos()/1e9,
				getTotalTime(), flops, bytes, overHeadDrtn.toNanos()/1.0e9);
	}

	@Override
	public double getPhi() {
		return phi;
	}
	
	

	/**
	 * Divide the simulation space into the given number of cubes. The number of
	 * cubes must be a power of 2. This should be called once per simulation, at the beginning.
	 * 
	 * @return
	 * @throws InvalidNumberOfCubesException
	 */
	private void setupCubes() throws InvalidNumberOfCubesException {
		if (!((numCubes > 0) && ((numCubes & (numCubes - 1)) == 0))) {
			throw new InvalidNumberOfCubesException();
		}
		if (numCubes == 1) {
			cuboides = Collections.singleton(new AxisAlignedCuboid());
		}
		else {
			// axis 0 = x, 1 = y, 2 = z;
			int xPoints = 0;
			int yPoints = 0;
			int zPoints = 0;
			int axis = 0;
			int numcubes = numCubes;
			while (numcubes > 1) {
				switch (axis) {
				case 0:
					xPoints += 2;
					break;
				case 1:
					yPoints += 2;
					break;
				case 2:
					zPoints += 2;
				default:
					axis = 0;
				}
				numcubes /= 2;
				axis++;
			}
			cuboides = new HashSet<>();
			double[] xCoords = axisSlices(xPoints);
			double[] yCoords = axisSlices((yPoints == 0) ? 1 : yPoints);
			double[] zCoords = axisSlices((zPoints == 0) ? 1 : zPoints);
			for (int i = 0; i < xCoords.length-1; i++) {
				for (int j = 0; j < yCoords.length-1; j++) {
					for (int k = 0; k < zCoords.length-1; k++) {
						cuboides.add(new AxisAlignedCuboid(new StockCuboidCoordinates(xCoords[i], yCoords[j], zCoords[k], xCoords[i+1], yCoords[j+1], zCoords[k+1])));
					}
				}
			}
		}
	}

	private void calculatePerformance(int N, int steps) {
		// 20 floating point operations
		// 14 to calculate acceleration
		// 6 for velocity and position
		flops = ((14.0*N*N + 6.0*N) * steps)/ 1.0e9 / getTotalTime();
		// We calculated the mem size from the bumber of cuboids and their individual size
		//bytes = (4.0f * (double) N * 10.0f * (double) steps)/ 1000000000.0f / getTotalTime();
		bytes = memSize / 1000000.0f / getTotalTime();
	    // Verify solution.
	    verify();
	}


	private void printResults(int steps) {
		// Print results and stuff.
	    System.out.print("\n");
	    System.out.print(String.format(" Loop 0 = %f seconds.\n", prprDrtn.toNanos()/1.0e9));
	    System.out.print(String.format(" Loop 1 = %f seconds.\n", calcAccelDrtn.toNanos()/1.0e9));
	    System.out.print(String.format(" Loop 2 = %f seconds.\n", calcVelDrtn.toNanos()/1.0e9));
	    System.out.print(String.format(" Loop 3 = %f seconds.\n", calcPosDrtn.toNanos()/1.0e9));
	    System.out.print(String.format(" Total  = %f seconds.\n", getTotalTime()));
	    System.out.print("\n");
	    System.out.print(String.format(" GFLOP/s = %f\n", flops));
	    System.out.print(String.format(" GB/s = %f\n", bytes));
	    System.out.print("\n");
	    System.out.print(String.format(" Total time = %f seconds.\n", overHeadDrtn.toNanos()/1.0e9));
	    System.out.print(String.format(" Answer = %f\n", phi));
	    System.out.print("\n");
	    for (String s : stepDetails) {
	    	System.out.print(s);
	    }
	}
	
	private void verify() {
		phi = 0.0f;
		for (NBody3DBody body1 : universe) {
			for (NBody3DBody body2 : universe) {
				Vector3D distance = body2.position().subtract(body1.position());
				double r2 = FastMath.sqrt(distance.normSq() + eps);
				double r2inv = 1.0 / r2;
				double r6inv = r2inv*r2inv*r2inv;
				phi += body2.mass() * r6inv;
			}	
		}
	}

	private double getTotalTime() {
		return prprDrtn.plus(calcAccelDrtn).plus(calcVelDrtn).plus(calcPosDrtn).toNanos()/1.0e9;
	}


	private double[] axisSlices(double points) {
		double slice = 2 / points;
		double point = -1;
		double[] coords = new double[(int) (points + 1)];
		for (int i = 0; i <= points; i++) {
			coords[i] = point;
			point += slice;
		}
		return coords;
	}

}
